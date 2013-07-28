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
 package org.castafiore.wfs.service;

import java.util.HashMap;
import java.util.Map;

public class SimpleMemoryLockedFileRegistry implements LockedFilesRegistry {

	private Map<String, String> lockedFiles = new HashMap<String, String>();
	
	public boolean isLocked(String fileId) {
		return lockedFiles.containsKey(fileId);
	}

	public String isLockedBy(String fileId) {
		if(isLocked(fileId)){
			return lockedFiles.get(fileId);
		}else{
			return null;
		}
	}

	public void releaseLock(String fileId) {
		lockedFiles.remove(fileId);
		
	}

	public void lockFile(String fileId, String lockRequester) {
		lockedFiles.put(fileId, lockRequester);
		
	}

	public Map<String, String> getLockedFiles() {
		return lockedFiles;
	}

	public void setLockedFiles(Map<String, String> lockedFiles) {
		this.lockedFiles = lockedFiles;
	}
	
	

}
