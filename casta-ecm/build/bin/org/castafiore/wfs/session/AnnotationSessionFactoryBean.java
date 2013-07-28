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
 package org.castafiore.wfs.session;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class AnnotationSessionFactoryBean extends LocalSessionFactoryBean {
	
	private static final Logger LOG = Logger.getLogger(AnnotationSessionFactoryBean.class);

    private List<String> annotatedClasses_;
    
    /**
     * @return the classes.
     */
    public List getAnnotatedClasses() {
        return annotatedClasses_;
    }
    
    /**
     * @param classes The classes to set.
     */
    public void setAnnotatedClasses(List<String> classes) {
        annotatedClasses_ = classes;
    }

    
    
    
    @Override
	protected Configuration newConfiguration() throws HibernateException {
    	return new AnnotationConfiguration().configure(Thread.currentThread().getContextClassLoader().getResource("org/castafiore/persistence/annot-hibernate.cfg.xml") );
	}

	@Override
    protected void postProcessConfiguration(Configuration config) throws HibernateException {
        /*super.postProcessConfiguration(config);
        config.setProperty("show_sql", "true");

        if (!(config instanceof AnnotationConfiguration)) {
            throw new ApplicationContextException("The configuration must be AnnotationConfiguration.");
        }

        if (annotatedClasses_ == null) {
            LOG.info("No annotated classes to register with Hibernate.");
            return;
        }

        for (String className : annotatedClasses_) {
            try {
                Class clazz = config.getClass().getClassLoader().loadClass(className);
                ((AnnotationConfiguration)config).addAnnotatedClass(clazz);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Class " + className + " added to Hibernate config.");
                }
            }
            catch (MappingException e) {
                throw new ApplicationContextException("Unable to register class " + className, e);
            }
            catch (ClassNotFoundException e) {
                throw new ApplicationContextException("Unable to register class " + className, e);
            }
        }*/
    }

}
