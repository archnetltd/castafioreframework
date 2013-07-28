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
 package org.castafiore.catalogue.ui;

import org.castafiore.catalogue.Product;
import org.castafiore.shop.ui.EXCart;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;

public class EXProductDetail extends EXXHTMLFragment {
	private Product product = null;

	public EXProductDetail(String name) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/catalogue/resources/EXProductDetail.xhtml"));
		Container img = ComponentUtil.getContainer("image", "img", null, null);
		img.setAttribute("src", "");
		addChild(img);
		img.setAttribute("width", "300");
		img.setAttribute("height", "300");
		
		addChild(ComponentUtil.getContainer("title", "h1", "title", "ui-product-title"));
		addChild(ComponentUtil.getContainer("price", "span", "Rs 123", "ui-price"));
		
		EXButton cancel = new EXButton("cancel", "Cancel");
		cancel.setStyle("font-size", "12px");
		addChild(cancel);
		
		EXButton addToCart = new EXButton("addToCart", "Add to cart");
		addToCart.setStyle("font-size", "12px");
		addChild(addToCart);
		addToCart.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		addToCart.setAttribute("ancestor", getClass().getName());
		addToCart.setAttribute("method", "addToCart");
		addChild(ComponentUtil.getContainer("descriptionOf", "span", "Description",null));
		addChild(ComponentUtil.getContainer("description", "p", "<p>Description goes here</p>",null));
		
	}
	public void addToCart(){
		Application app = getRoot();
		EXCart cart = app.getDescendentOfType(EXCart.class);
		if(cart != null){
			cart.addProduct(this.product);
		}
	}
	
	public void setProduct(Product p){
		this.product = p;
		
		getDescendentByName("title").setText(p.getTitle());
		getDescendentByName("description").setText(p.getSummary());
		getDescendentByName("price").setText(StringUtil.toCurrency("MUR",p.getTotalPrice()));
		getDescendentByName("image").setAttribute("src", p.getImageUrl(""));
		
		
	}

}
