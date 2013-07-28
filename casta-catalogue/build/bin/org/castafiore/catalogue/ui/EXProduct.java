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
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;

public class EXProduct extends EXXHTMLFragment {
	
	private Product product = null;

	public EXProduct(Product product) {
		super("EXProduct",ResourceUtil.getDownloadURL("classpath", "org/castafiore/catalogue/resources/EXProduct.xhtml"));
		Container img = ComponentUtil.getContainer("image", "img", null, null);
		img.setAttribute("src", "");
		img.setAttribute("width", "75");
		addChild(img);
		setStyle("width", "240px");
		setStyle("height", "200px");
		setStyle("border-right", "solid 1px silver");
		
		addChild(ComponentUtil.getContainer("title", "label", "title", ""));
		
		addChild(ComponentUtil.getContainer("price", "label", "Rs 123", ""));
		addChild(new EXContainer("summary", "div").setStyle("width", "130px").setStyle("height", "50px").setStyle("overflow", "hidden"));
		EXContainer a = new EXContainer("viewDetails", "a");
		a.setAttribute("href", "#");
		a.setText("View detail");
		a.setStyle("color", "#CD0A0A");
		a.setStyle("font-size", "10px");
		a.addEvent(EXCatalogue.SHOW_DETAIL, Event.CLICK);
		addChild(a);
		setProduct(product);
	}
	
	
	public void setProduct(Product p){
		
		String title = p.getTitle();
		if(title.length() > 30){
			title = title.substring(0, 27) + "...";
		}
		getDescendentByName("title").setText(title);
		getDescendentByName("price").setText(StringUtil.toCurrency("MUR",p.getTotalPrice()));
		getChild("image").setAttribute("src", p.getImageUrl(""));
		getChild("summary").setText(p.getSummary());
		this.product = p;
	}


	public Product getProduct() {
		return product;
	}	

}
