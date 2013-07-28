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
 package org.castafiore.wfs.types.config;

import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;

public class ShortcutConfig extends AbstractFileConfig {
	
	private String reference;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public void fillInstance(File empty) {
		//empty.setName(getName());
		((Shortcut)empty).setReference(getReference());
		
	}

	@Override
	public File getNewInstance() {
		
		RepositoryService service = getApplicationContext().getBean(RepositoryService.class);
		return service.getDirectory(getParentDir(), service.getSuperUser()).createFile(getName(), Shortcut.class);
		//return new Shortcut();
	}
	
	

}
