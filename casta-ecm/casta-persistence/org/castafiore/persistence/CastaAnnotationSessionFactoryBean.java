/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.persistence;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.castafiore.wfs.WFSEventsInterceptor;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.util.ClassUtils;

public final class CastaAnnotationSessionFactoryBean extends LocalSessionFactoryBean implements ApplicationContextAware {
	private ApplicationContext context = null;
	
	@Override
	protected final void postProcessMappings(Configuration config) throws HibernateException {
		AnnotationConfiguration annConfig = (AnnotationConfiguration) config;
		
		super.postProcessMappings(config);
		
		Map<String, AnnotatedClass> beans = context.getBeansOfType(AnnotatedClass.class);
		
		Iterator<String> iters = beans.keySet().iterator();
		while(iters.hasNext()){
			AnnotatedClass bean = beans.get(iters.next());
			List<Class> annots = bean.getAnnotatedClasses();
			for (Class c : annots){
				annConfig.addAnnotatedClass(c);
			}
		}
	}

	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.context = ctx;
		
	}
	
	private static final String RESOURCE_PATTERN = "/**/*.class";


	private Class[] annotatedClasses;

	private String[] annotatedPackages;

	private String[] packagesToScan;

	private TypeFilter[] entityTypeFilters = new TypeFilter[] {
			new AnnotationTypeFilter(Entity.class, false),
			new AnnotationTypeFilter(Embeddable.class, false),
			new AnnotationTypeFilter(MappedSuperclass.class, false),
			new AnnotationTypeFilter(org.hibernate.annotations.Entity.class, false)};

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();


	


	@Override
	public void setConfigurationClass(Class configurationClass) {
		if (configurationClass == null || !AnnotationConfiguration.class.isAssignableFrom(configurationClass)) {
			throw new IllegalArgumentException(
					"AnnotationSessionFactoryBean only supports AnnotationConfiguration or subclasses");
		}
		super.setConfigurationClass(configurationClass);
	}

	/**
	 * Specify annotated classes, for which mappings will be read from
	 * class-level JDK 1.5+ annotation metadata.
	 * @see org.hibernate.cfg.AnnotationConfiguration#addAnnotatedClass(Class)
	 */
	public void setAnnotatedClasses(Class[] annotatedClasses) {
		this.annotatedClasses = annotatedClasses;
	}

	/**
	 * Specify the names of annotated packages, for which package-level
	 * JDK 1.5+ annotation metadata will be read.
	 * @see org.hibernate.cfg.AnnotationConfiguration#addPackage(String)
	 */
	public void setAnnotatedPackages(String[] annotatedPackages) {
		this.annotatedPackages = annotatedPackages;
	}

	/**
	 * Set whether to use Spring-based scanning for entity classes in the classpath
	 * instead of listing annotated classes explicitly.
	 * <p>Default is none. Specify packages to search for autodetection of your entity
	 * classes in the classpath. This is analogous to Spring's component-scan feature
	 * ({@link org.springframework.context.annotation.ClassPathBeanDefinitionScanner}).
	 */
	public void setPackagesToScan(String[] packagesToScan) {
		this.packagesToScan = packagesToScan;
	}

	/**
	 * Specify custom type filters for Spring-based scanning for entity classes.
	 * <p>Default is to search all specified packages for classes annotated with
	 * <code>@javax.persistence.Entity</code>, <code>@javax.persistence.Embeddable</code>
	 * or <code>@javax.persistence.MappedSuperclass</code>, as well as for
	 * Hibernate's special <code>@org.hibernate.annotations.Entity</code>.
	 * @see #setPackagesToScan
	 */
	public void setEntityTypeFilters(TypeFilter[] entityTypeFilters) {
		this.entityTypeFilters = entityTypeFilters;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourcePatternResolver = (resourceLoader != null ?
				ResourcePatternUtils.getResourcePatternResolver(resourceLoader) :
				new PathMatchingResourcePatternResolver());
	}


	

	/**
	 * Perform Spring-based scanning for entity classes.
	 * @see #setPackagesToScan
	 */
	protected void scanPackages(AnnotationConfiguration config) {
		if (this.packagesToScan != null) {
			try {
				for (String pkg : this.packagesToScan) {
					String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
							ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
					Resource[] resources = this.resourcePatternResolver.getResources(pattern);
					MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
					for (Resource resource : resources) {
						if (resource.isReadable()) {
							MetadataReader reader = readerFactory.getMetadataReader(resource);
							String className = reader.getClassMetadata().getClassName();
							if (matchesFilter(reader, readerFactory)) {
								config.addAnnotatedClass(this.resourcePatternResolver.getClassLoader().loadClass(className));
							}
						}
					}
				}
			}
			catch (IOException ex) {
				throw new MappingException("Failed to scan classpath for unlisted classes", ex);
			}
			catch (ClassNotFoundException ex) {
				throw new MappingException("Failed to load annotated classes from classpath", ex);
			}
		}
	}

	/**
	 * Check whether any of the configured entity type filters matches
	 * the current class descriptor contained in the metadata reader.
	 */
	private boolean matchesFilter(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
		if (this.entityTypeFilters != null) {
			for (TypeFilter filter : this.entityTypeFilters) {
				if (filter.match(reader, readerFactory)) {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * Delegates to {@link #postProcessAnnotationConfiguration}.
	 */
	@Override
	protected final void postProcessConfiguration(Configuration config) throws HibernateException {
		postProcessAnnotationConfiguration((AnnotationConfiguration) config);
	}

	/**
	 * To be implemented by subclasses that want to to perform custom
	 * post-processing of the AnnotationConfiguration object after this
	 * FactoryBean performed its default initialization.
	 * @param config the current AnnotationConfiguration object
	 * @throws HibernateException in case of Hibernate initialization errors
	 */
	protected void postProcessAnnotationConfiguration(AnnotationConfiguration config) throws HibernateException {
	}


	protected Configuration newConfiguration() throws HibernateException {
		return new AnnotationConfiguration().setInterceptor(new WFSEventsInterceptor());
	}
	
	

}
