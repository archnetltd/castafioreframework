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
 package org.castafiore.contact.ui;

import java.util.List;

import org.castafiore.contact.Contact;
import org.castafiore.designer.designable.datarepeater.EXDataContainerDesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;

public class EXContactsDesignableFactory extends EXDataContainerDesignableFactory{

	public EXContactsDesignableFactory() {
		super();
		setText("Contacts");
	}

	@Override
	public Container getInstance() {
		
		
		
		return new EXContacts("Contact", "Contacts", (List)BaseSpringUtil.getBeanOfType(RepositoryService.class).executeQuery(new QueryParameters().setEntity(Contact.class), Util.getRemoteUser()));
	}

	public String getUniqueId() {
		return "community:contacts";
	}

	@Override
	public String getCategory() {
		return "Community";
	}


}
