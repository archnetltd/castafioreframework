package org.castafiore.designable;

import groovy.lang.Writable;
import groovy.text.Template;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.groovy.GroovyUtil;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;

public class CartItem {
	
	//private Product product;
	
	public Map<String, String> request = new HashMap<String, String>();
	
	protected BigDecimal qty;
	
	protected String img;
	
	protected String options;
	
	protected String code;
	
	protected BigDecimal totalPrice;
	
	protected BigDecimal taxRate;
	
	protected String currency;
	
	protected String absolutePath;
	
	protected String title;
	
	protected BigDecimal weight;
	
	protected BigDecimal length;
	
	protected BigDecimal width;
	
	protected BigDecimal height;
	
	protected String priceScript;
	
	protected String detail;
	
	protected String summary;
	
	

	public String getDetail() {
		return detail;
	}


	public String getSummary() {
		return summary;
	}


	public String getTitle() {
		return title;
	}


	public String getCurrency() {
		return currency;
	}


	public String getAbsolutePath() {
		return absolutePath;
	}


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public BigDecimal getTaxRate() {
		return taxRate;
	}


	public String getCode() {
		return code;
	}


	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public BigDecimal getWeight() {
		return weight;
	}


	public BigDecimal getLength() {
		return length;
	}


	public BigDecimal getWidth() {
		return width;
	}


	public BigDecimal getHeight() {
		return height;
	}

	public Map<String,String> getOptionMap()throws Exception{
		final Map<String, String> opt = new HashMap<String, String>();
		if(options != null){
			Container c = DesignableUtil.buildContainer(new ByteArrayInputStream(options.getBytes()), false);
		
			//final Map<String, String> mptions = new HashMap<String, String>();
		
		
			ComponentUtil.iterateOverDescendentsOfType(c, StatefullComponent.class, new ComponentVisitor() {
				
				@Override
				public void doVisit(Container c) {
					StatefullComponent stf = (StatefullComponent)c;
					opt.put(c.getName(), stf.getValue().toString());
					//b.append(c.getName() + ":" + stf.getValue().toString() ).append("<br></br>");
				}
			});
		}
		
		return opt;
	}

	public void setProduct(Product product) {
		
		title = product.getTitle();
		img = product.getImageUrl("");
		code = product.getCode();
		absolutePath = product.getAbsolutePath();
		taxRate = product.getTaxRate();
		totalPrice = product.getTotalPrice();
		weight = product.getWeight();
		length = product.getLength();
		height = product.getHeight();
		width = product.getWidth();
		String currentCurrency = FinanceUtil.getCurrentCurrency();
		currency = currentCurrency;
		summary = product.getSummary();
		detail = product.getDetail();
		String merchantCurrenct = MallUtil.getMerchant(product.getProvidedBy()).getCurrency();
		try{
			String script = product.getPriceScript();
			if(StringUtil.isNotEmpty(script)){
				Container root = DesignableUtil.buildContainer(new ByteArrayInputStream(options.getBytes()), false);
				Map variables = root.getDescendentOfType(EXDynaformPanel.class).getFieldValues();
				Template tpl = GroovyUtil.getGroovyTemplate(script);
				Writable writable = tpl.make(variables);
				CharArrayWriter writer =  new CharArrayWriter(); 
				writable.writeTo(writer);
				String result = writer.toString().replace('\n', ' ').replace('\r', ' ').replace('\n', ' ').trim();
				BigDecimal price = new BigDecimal(result);
				totalPrice = price;
			}
			
		}catch(Exception e){
			throw new UIException(e);
			//totalPrice = product.getTotalPrice();
		}
		
		if(!currentCurrency.equals(merchantCurrenct)){
			try{
			totalPrice = FinanceUtil.convert(totalPrice, merchantCurrenct, currentCurrency);
			
			}catch(Exception e){
				totalPrice = product.getTotalPrice();
				currency = merchantCurrenct;
			}
		}
		
	}
	
	
	
	public BigDecimal getPriceExcludingTax(){
		
		BigDecimal tr = taxRate;
		if(taxRate == null){
			tr = new BigDecimal("15");
		}
		
		BigDecimal tp =new BigDecimal(0);
		if(totalPrice != null){
			tp = totalPrice;
		}
		return new BigDecimal("100").subtract(tr).multiply(tp).divide(new BigDecimal(100));
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	
	
	public void changeCurrency(String newCurrency){
		if(!currency.equals(newCurrency)){
			try{
			totalPrice = FinanceUtil.convert(totalPrice, currency, newCurrency);
			currency = newCurrency;
			}catch(Exception e){
				
			}
		}
	}
	
	

}
