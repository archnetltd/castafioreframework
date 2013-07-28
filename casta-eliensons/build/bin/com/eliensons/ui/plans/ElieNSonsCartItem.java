package com.eliensons.ui.plans;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.CartItem;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class ElieNSonsCartItem extends CartItem{
	
	protected BigDecimal installment = BigDecimal.ZERO; 
	
	protected BigDecimal joiningFee = BigDecimal.ZERO;
	
	protected String advance = "Oui";
	
	private Map<String,String> getGenOptions()throws Exception{
		Container c = DesignableUtil.buildContainer(new ByteArrayInputStream(getOptions().getBytes()), false);
		
		final Map<String, String> mptions = new HashMap<String, String>();
	
	
		ComponentUtil.iterateOverDescendentsOfType(c, StatefullComponent.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				StatefullComponent stf = (StatefullComponent)c;
				mptions.put(c.getName(), stf.getValue().toString());
			}
		});
		
		
		return mptions;
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
		currency = "MUR";
		summary = product.getSummary();
		detail = product.getDetail();
		try{
		Map<String,String> opt = getGenOptions();
		String couverture = opt.get("couverture");
		String age = opt.get("age");
		Properties prop = new Properties();
		prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/eliensons/ui/plans/plans.properties"));
		
		String key = code + ".";
		if(true){
			if(couverture.equalsIgnoreCase("familiale")){
				key = key + "familiale";
			}else{
				age = age.replace("Ans","").replace("+", "").trim();
				key = key + age;
			}
		}
		
		String p = prop.get(key).toString();
		totalPrice = new BigDecimal(p);
		
		installment = totalPrice;
		
		
		
		String inscription = opt.get("inscription");
		advance = opt.get("advance");
		if(couverture.equalsIgnoreCase("familiale")){
			joiningFee = totalPrice;
			if(inscription.equalsIgnoreCase("oui") && advance.equalsIgnoreCase("oui")){
				totalPrice = totalPrice.multiply(new BigDecimal(2));
			}else if(inscription.equalsIgnoreCase("non") && advance.equalsIgnoreCase("non")){
				totalPrice = BigDecimal.ZERO;
			}
			
			if(inscription.equalsIgnoreCase("non")){
				joiningFee = BigDecimal.ZERO;
			}
		}else if(couverture.equalsIgnoreCase("individuel")){
			joiningFee = new BigDecimal(100);
			if(inscription.equalsIgnoreCase("oui") && advance.equalsIgnoreCase("oui")){
				totalPrice = totalPrice.add(new BigDecimal(100));
			}else if(inscription.equalsIgnoreCase("non") && advance.equalsIgnoreCase("non")){
				totalPrice = BigDecimal.ZERO;
			}else if(inscription.equalsIgnoreCase("oui") && advance.equalsIgnoreCase("non")){
				totalPrice = new BigDecimal(100);
			}
			if(inscription.equalsIgnoreCase("non")){
				joiningFee = BigDecimal.ZERO;
			}
		}
		
		title = product.getTitle() + " couverture " + couverture;
		if(couverture.equalsIgnoreCase("individuel")){
			title = title + " (" + opt.get("age") + ")";
		}
		
		
		
		}catch(Exception e){
			//throw new RuntimeException(e);
			e.printStackTrace();
		}
	}

	public BigDecimal getInstallment() {
		return installment;
	}

	public BigDecimal getJoiningFee() {
		return joiningFee;
	}

	public String getAdvance() {
		return advance;
	}
	
	

}
