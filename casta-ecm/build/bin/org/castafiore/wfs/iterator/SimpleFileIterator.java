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
 package org.castafiore.wfs.iterator;

import java.util.List;


import org.castafiore.wfs.types.File;

public class SimpleFileIterator implements FileIterator<File> {
	
	private List<File> files;
	
	private int pointer = -1;
	public SimpleFileIterator(List<File> files)
	{
		this.files = files;
	}
	

	public int count() {
		return files.size();
	}

	public File get(int index) {
		return files.get(index);
	}

	public boolean hasNext() {
		return (pointer < (files.size()-1) || files.size()==-1);
	}

	public void initialise(Integer directoryId, String username)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void invalidate() {
		pointer = -1;
		
	}
	
	public File get() {
		return get(pointer);
	}

	public File next() {
		pointer ++;
		File f =files.get(pointer);
		
		return f;
	}


	
	public List toList() {
		return files;
	}


	@Override
	public File first() {
		pointer =-1;
		return next();
	}


	@Override
	public File last() {
		pointer = count()-2;
		return next();
	}


	@Override
	public File previous() {
		pointer =pointer -2;
		return next();
	}

}
