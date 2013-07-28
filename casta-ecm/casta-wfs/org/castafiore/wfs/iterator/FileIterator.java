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
package org.castafiore.wfs.iterator;

import java.io.Serializable;
import java.util.List;

import org.castafiore.wfs.types.File;


/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Dec 8, 2008
 */
public interface FileIterator<T extends File> extends Serializable {
	
	public void invalidate();
	
	public T get(int index);
	
	public T get();
	
	public T next();
	
	public boolean hasNext();
	
	public int count();
	
	public void initialise(Integer directoryId, String username)throws Exception;
	
	public List<T> toList();
	
	
	public T first();
	
	public T last();
	
	public T previous();
	
	
	
	

}
