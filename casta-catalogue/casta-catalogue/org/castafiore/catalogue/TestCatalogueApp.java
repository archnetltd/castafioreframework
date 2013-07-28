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
 package org.castafiore.catalogue;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.castafiore.catalogue.ui.EXCatalogue;
import org.castafiore.security.api.SecurityService;
import org.castafiore.shop.ui.EXShop;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;

public class TestCatalogueApp extends EXApplication{

	public TestCatalogueApp() {
		super("catalogue");
		
		
		Properties prop = new Properties();
		
		
		try{
			BaseSpringUtil.getBeanOfType(SecurityService.class).login("system", "admin");
			//prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/catalogue/ui/sample-data.properties"));
			List products = BaseSpringUtil.getBeanOfType(RepositoryService.class).executeQuery(new QueryParameters().setEntity(Product.class), "system");
			
			
			EXCatalogue catalogue = new EXCatalogue("nn",products);
			
			EXShop shop = new EXShop("shop", catalogue);
			addChild(shop);
			//addChild(catalogue);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
