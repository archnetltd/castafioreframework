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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import org.castafiore.wfs.session.Session;


/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */


public interface File extends Serializable {
	
	public final static int STATE_NEW = 0;
	
	public final static int STATE_INDEXED = 1;
	
	public final static int STATE_INDEXED_WITH_ERROR = 2;
	
	public final static int STATE_ARCHIVED = 3;
	
	public final static int STATE_DELETED = 4;
	
	
	public final static int STATE_PUBLISHED= 51;
	
	public final static int STATE_DRAFT = 50;
	
	
	
	public <T extends File> T getAncestorOfType(Class<T> classType) ;
	
	public String getOwner();

	public void setOwner(String owner) ;

	public File makeOwner(String owner) ;
	
	public Calendar getLastModified() ;

	public long getSize() ;

	public void setSize(long size) ;
	
	public String getName();

	//public void setName(String name);

//	public Integer getId() ;
//
//	public void setId(Integer id);

	public Directory getParent() ;

	public void setParent(Directory parent) ;

	public String getClazz() ;

	public Calendar getDateCreated();
	
	public String getPath();
	
	public String getAbsolutePath();
	
	//public void setAbsolutePath(String absolutePath);
	
	public boolean isDirectory();
	
	public long length();
	
	public long lastModified();
	
	public File[] listFiles();
	
	
	public String[] listFileNames();
	
	
	public void save();
	
	
	
	//public void saveIn(String path);
	
	public void evict();
	
	public void remove();
	
	public void refresh();
	
	public Session getSession();
	
	
	public int getDepth();
	
	
	@Override
	public boolean equals(Object o);
	
	public boolean canRead();
	
	public boolean canWrite(String username) ;
	
	public boolean canRead(String username)	;
	
	public boolean canWrite();
	
	public boolean isFile();
	
	

	public String getEditPermissions() ;

	public void setEditPermissions(String editPermissions);

	public String getReadPermissions();

	public void setReadPermissions(String readPermissions);
	
	public boolean isLocked();
	
	public void lock();
	
	public void releaseLock();

	public String getContainsText() ;

	public void setContainsText(String containsText);

	public int getStatus();

	public void setStatus(int status) ;
	
	public void setMetadata(String name, String value);
	
	public boolean hasMetadata(String name);
	
	public String getMetadata(String category);
	
	public Set<Metadata> getMetadata();
	
	public <T extends File> T createFile(String name,Class<T> type);
	
	public void copyTo(String destination);
	
	public void moveTo(String destination);
}
