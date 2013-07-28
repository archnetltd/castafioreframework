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
 package org.castafiore.wfs.aop;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.castafiore.wfs.LockedFileException;
import org.castafiore.wfs.service.LockedFilesRegistry;
import org.castafiore.wfs.types.File;

public class LockingInterceptor implements MethodInterceptor , Serializable{

	//private Map<Integer, String> lockedFiles = new HashMap<Integer, String>();
	
	private LockedFilesRegistry registry;
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String methodName = invocation.getMethod().getName();
		if(methodName.equals("update") || methodName.equals("delete") || methodName.equals("saveIn")){
			File f = null;
			String path = null;
			
			if(methodName.equals("update") || methodName.equals("delete")){
				f = (File)invocation.getArguments()[0];
			}else{
				f = (File)invocation.getArguments()[1];
			}
			
			path = f.getAbsolutePath();
			
			if(path != null){
				if(registry.isLocked(path)){
					throw new LockedFileException("The file " + f.getAbsolutePath() + " cannot be modified since it is locked by some other user " + registry.isLockedBy(path));
				}
			}
		}
		else if(methodName.equals("lockFile")){
			File f = (File)invocation.getArguments()[0];
			String username = (String)invocation.getArguments()[1];
			registry.lockFile(f.getAbsolutePath(), username);
		}
		else if(methodName.equals("unlockFile")){
			File f = (File)invocation.getArguments()[0];
			registry.releaseLock(f.getAbsolutePath());
		}
		return invocation.proceed();
	}

	public LockedFilesRegistry getRegistry() {
		return registry;
	}

	public void setRegistry(LockedFilesRegistry registry) {
		this.registry = registry;
	}

	
	
	

}
