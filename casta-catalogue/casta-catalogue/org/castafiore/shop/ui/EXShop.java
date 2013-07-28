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

import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.catalogue.ui.EXCatalogue;
import org.castafiore.catalogue.ui.EXProduct;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXShop extends EXContainer {

	public EXShop(String name, EXCatalogue catalogue) {
		super(name, "div");
		
		addChild(catalogue);
		

		setStyle("margin", "10px");
		
	}
	
	
	
	public final static Event SHOW_CART_PANEL = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXShop.class).getDescendentByName("cartPanel").setDisplay(true);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	public final static Event ADD_TO_CART = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this,"Add this product to cart?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			EXProduct uiProduct = container.getAncestorOfType(EXProduct.class);
			Product  p = uiProduct.getProduct();
			container.getAncestorOfType(EXShop.class).getDescendentOfType(EXCart.class).addProduct(p);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

}
