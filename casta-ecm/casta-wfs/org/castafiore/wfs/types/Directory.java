/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.wfs.types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.castafiore.profile.Profiler;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.RepositoryException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.iterator.SimpleFileIterator;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.BatchSize;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 29, 2008
 */

@Entity
public class Directory extends FileImpl{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@BatchSize(size=15)
	@OneToMany(mappedBy="parent", cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, orphanRemoval=true, targetEntity=FileImpl.class)
	@JoinColumn(name="parent_id")
	@AccessType(value="field")
	@OrderBy("dateCreated")
	protected Set<File> children = new HashSet<File>();
	
	public Directory (){
		super();
	}
		
	public FileIterator<File> getFiles()
	{
		return getFiles((FileFilter)null);
	}

	
	public <T extends File> T createFileInDir(String name, Class<T> type, String relativeDir){
		Directory dir = getFile(relativeDir, Directory.class);
		if(dir == null){
			dir = createFile(relativeDir, Directory.class);
		}
		
		return dir.createFile(name, type);
		
	}
	
	
	
	public File getDescendent(String relativePath){
		String p = getAbsolutePath() + "/" + relativePath;
		return getSession().getFile(p);
	}
	
	public File getFile(final String fname) 
	{
		FileIterator<File> iterator = getFiles(new FileFilter(){

			public boolean accept(File pathname) {
				boolean res = fname.equals(pathname.getName());
				return res;
				
			}
			
		});
		if(iterator.hasNext())
		{
			return iterator.next();
		}
		return null;

	}
	
	public <T extends File> T getDescendentOfType(Class<T> type)
	{
		Assert.notNull(type, "cannot search a descendent with type null");
		if(type.isAssignableFrom(getClass()))
		{
			return (T)this;
		}
		else
		{
			for(Directory child : this.getFiles(Directory.class).toList())
			{
				T c = child.getDescendentOfType(type);
				if(c != null)
				{
					return c;
				}
			}
		}
		
		return null;
	}
	
	public FileIterator<File> getFiles(FileFilter filter){
		
		List<File> result = new ArrayList<File>();
		
		try{
			if(children.size() > 0)
			{
				for(File f : children)
				{
					if(f.getStatus() != File.STATE_DELETED &&  f.canRead() && (filter == null || filter.accept(f)))
					{ 
						result.add(f);
					}
				}
			}
			
		}catch(Exception li){
			if(getAbsolutePath() != null){
				QueryParameters params = new QueryParameters().setEntity(FileImpl.class).addRestriction(Restrictions.eq("parent.absolutePath", getAbsolutePath()));
				List<File> files = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
				for(File f : files)
				{
					if(f.getStatus() != File.STATE_DELETED &&  f.canRead() && (filter == null || filter.accept(f)))
					{ 
						result.add(f);
					}
				}
			}else{
				throw new RepositoryException(new IllegalStateException("illegal state when getting children",li));
			}
		}

		
		return new SimpleFileIterator(result);
	}
	
	public <T extends File>T getFile(String name,Class<T> clazz){
		FileIterator<T> iter = getFiles(clazz);
		if(iter != null){
			while(iter.hasNext()){
				T next = iter.next();
				if(next.getName().equals(name)){
					return next;
				}
			}
			//return iter.next();
		}return null;
	}
	
	public void deleteProperty(String name){
		Value val = getFile(name, Value.class);
		if(val != null){
			val.remove();
		}
	}
	
	public void setProperty(String name, String value){
		Value val = getFile(name, Value.class);
		if(val == null){
			val = createFile(name, Value.class);
		}
		
		val.setString(value);
		
	}
	
	
	public String getProperty(String name){
		Value val = getFile(name, Value.class);
		if(val != null){
			return val.getString();
		}return null;
	}
	
	public <T extends File> FileIterator<T> getFiles(Class<T> clazzzz){
		return (FileIterator<T>)getFiles(new ClassTypeFilter(clazzzz));
	}
	
	public <T extends File>T getFirstChild(Class<T> clazz){
		FileIterator<T> iter = getFiles(clazz);
		if(iter != null && iter.hasNext()){
			return iter.next();
		}return null;
	}
	
	public FileIterator<?> getFiles(final Class<? extends File>[] clazz)
	{
		return getFiles(new FileFilter(){

			public boolean accept(File pathname) {
				for(Class<? extends File> cls :clazz)
				{
					if(pathname.getClass().isAssignableFrom(cls))
					{
						return true;
					}
				}
				return false;
			}
			
		});
	}
	
	
	public  class ClassTypeFilter implements FileFilter{
		
		private Class<? extends File> classType;
		
		

		public ClassTypeFilter(Class<? extends File> classType) {
			super();
			this.classType = classType;
		}



		public boolean accept(File file) {
			Class pathClass = file.getClass();
			boolean result =classType.isAssignableFrom(pathClass);
			return result;
		}
		
	}

}


