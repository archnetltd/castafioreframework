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
 package org.castafiore.catalogue.designables;

import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designer.designable.datarepeater.EXDataContainer;
import org.castafiore.shop.ui.EXCart;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Restrictions;

public class EXProductContainer extends EXDataContainer implements ProductContainer, Event {
	
	private Product p;

	public EXProductContainer(String name) {
		super(name);
		EXContainer title = new EXContainer("title", "a");
		title.setAttribute("href", "#");
		addChild(title);
		addChild(ComponentUtil.getContainer("image", "a"));
		addChild(ComponentUtil.getContainer("date", "span"));
		addChild(ComponentUtil.getContainer("description", "div"));
		addChild(ComponentUtil.getContainer("price", "div"));
		
		Container addToCart = ComponentUtil.getContainer("addToCart", "a", "Add to cart", "links");
		addToCart.setAttribute("method", "addToCart");
		addToCart.setAttribute("ancestor", getClass().getName());
		addToCart.setAttribute("href", "#");
		addToCart.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		addChild(addToCart);
	}

	public void addToCart(){
		Product p = getProduct();
		if(p!= null){
			Application app = getRoot();
			if(app.getDescendentOfType(EXCart.class) != null){
				app.getDescendentOfType(EXCart.class).addProduct(p);
			}
		}
	}
	
	public void showProduct(){
		
	}
	
	public Product getProduct() {
		return p;
	}

	public void setProduct(Product product) {
		p= product;
		refresh();		
	}
	
	@Override
	public void refresh() {
		super.refresh();
		Product product = getProduct();
		if(product != null){
			getChild("title").setText(product.getCode() + "-" + product.getTitle());
			getChild("date").setText(product.getDateCreated().getTime().toString());
			getChild("description").setText(product.getSummary());
						
		}
	}

	public void ClientAction(ClientProxy container) {
		
	}
	
	public boolean ServerAction(Container container, Map<String, String> request)throws UIException {
		
		QueryParameters params = new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("code", request.get("casta_code")));
		Product p = (Product)SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser()).get(0);
			
		setProduct(p);
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}

}
