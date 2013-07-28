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
 package org.castafiore.shop.ui;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.catalogue.ui.EXCatalogue;
import org.castafiore.designer.designable.data.AbstractDataDesignableFactory;
import org.castafiore.ui.Container;

public class EXShopDesignableFactory extends AbstractDataDesignableFactory{

	public EXShopDesignableFactory() {
		super("Online shop");
		setText("Online shop");
		
	}

	@Override
	public String getCategory() {
		return "E Commerce";
	}

	@Override
	public Container getInstance() {
		try{
			List products = new ArrayList();//BaseSpringUtil.getBeanOfType(RepositoryService.class).executeQuery(new QueryParameters().setEntity(org.castafiore.catalogue.Product.class), Util.getRemoteUser());
			
			EXCatalogue catalogue = new EXCatalogue("nn",products);
			
			EXShop shop = new EXShop("shop", catalogue);
			return shop;
			//addChild(catalogue);
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public String getUniqueId() {
		return "ecommerce/shop";
	}
	
	

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		if(attributeName.equals("Title")){
			c.getDescendentOfType(EXCatalogue.class).setTitle(attributeValue);
		}else{
			super.applyAttribute(c, attributeName, attributeValue);
		}
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{"Title"};
	}


	

	

}
