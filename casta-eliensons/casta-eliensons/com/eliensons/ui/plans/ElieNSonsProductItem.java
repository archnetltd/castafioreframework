package com.eliensons.ui.plans;

import java.math.BigDecimal;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.product.EXProductItem;
import org.castafiore.ui.events.Event;
import org.castafiore.utils.StringUtil;

public class ElieNSonsProductItem extends EXProductItem {

	public ElieNSonsProductItem() {
		super("ElieNSonsProductItem");
		setTemplateLocation("templates/designable/EXProductItem.xhtml");
		getDescendentByName("productImageLink").setStyle("display", "none");
		setStyle("background", "white");
		getDescendentByName("addToCart").setText("Select Plan");
		getDescendentByName("productName").setStyle("font-size", "20px");
		setStyle("padding", "0");
		setStyle("margin", "8px 0");

	}

	public void setProduct(Product p) {
		setAttribute("path", p.getAbsolutePath());
		getChild("productName").setText(p.getTitle());

		String currency = "MUR";

		BigDecimal curr = p.getTotalPrice();
		getChild("price").setText(StringUtil.toCurrency(currency, curr));

		if (p.getCurrentQty() == null || p.getCurrentQty().doubleValue() <= 0) {
			getChild("addToCart").setText("Sold out !!")
					.setStyle("font-weight", "bold")
					.setStyle("font-size", "15px").addClass("ui-state-error")
					.removeClass("ui-state-default").getEvents().clear();

		} else {
			getChild("addToCart").getEvents().clear();
			getChild("addToCart").setText("Add to cart")
					.setStyle("font-weight", "normal")
					.setStyle("font-size", "12px").addClass("ui-state-default")
					.removeClass("ui-state-error").addEvent(this, Event.CLICK);
		}
	}

}
