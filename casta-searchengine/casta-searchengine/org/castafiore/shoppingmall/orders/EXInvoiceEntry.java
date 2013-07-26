package org.castafiore.shoppingmall.orders;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.shoppingmall.crm.OrderLine;
import org.castafiore.shoppingmall.list.AbstractListItem;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.util.list.ListItem;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;

public class EXInvoiceEntry extends AbstractListItem implements ListItem<OrderLine>{
	
	
	private OrderLine line;
	public EXInvoiceEntry(String name) {
		super(name);
		
		addChild(new EXContainer("to_code", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("code","div")));
		addChild(new EXContainer("td_title", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("title","a").setAttribute("href", "#").setText("Thank you for your purchase").setStyle("font-weight", "bold").setStyle("color", "#111")));
		addChild(new EXContainer("td_qty", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("qty","div")));
		addChild(new EXContainer("td_price", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("price","div")));
		addChild(new EXContainer("td_total", "td").setStyle("vertical-align","top" ).addChild(new EXContainer("total","div")));	
	}
	
	@Override
	public OrderLine getItem() {
		return line;
	}

	

	
	public BigDecimal getQuantity(){
		return new BigDecimal(getDescendentByName("qty").getText());
	}
	@Override
	public void setItem(OrderLine file) {
		this.line = file;
		//setAttribute("path", file.getAbsolutePath());
		getDescendentByName("code").setText(file.getCode());
		
		
		String title = file.getTitle();
		
		try{
			final StringBuilder b = new StringBuilder(file.getTitle() + "<br></br>");
			if(file.getOptions() != null){
				Container c = DesignableUtil.buildContainer(new ByteArrayInputStream(file.getOptions().getBytes()), false);
			
				final Map<String, String> mptions = new HashMap<String, String>();
			
			
				ComponentUtil.iterateOverDescendentsOfType(c, StatefullComponent.class, new ComponentVisitor() {
					
					@Override
					public void doVisit(Container c) {
						StatefullComponent stf = (StatefullComponent)c;
						mptions.put(c.getName(), stf.getValue().toString());
						b.append(c.getName() + ":" + stf.getValue().toString() ).append("<br></br>");
					}
				});
			}
			title = b.toString();
		}catch(Exception e){
			e.printStackTrace();
			//return item.getTitle();
		}
		
		getDescendentByName("title").setText(title);
		getDescendentByName("price").setText(file.getTotal()==null?"0.00":StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),file.getTotal()));
		getDescendentByName("qty").setText(file.getQty().toPlainString());
		BigDecimal vd = file.getTotal();
		if(vd== null){
			vd = new BigDecimal("0");
		}
		vd = vd.multiply(new BigDecimal(0));
		getDescendentByName("total").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),line.getTotal()));
		
	}


}
