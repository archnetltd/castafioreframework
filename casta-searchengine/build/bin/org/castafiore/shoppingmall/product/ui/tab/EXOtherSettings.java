package org.castafiore.shoppingmall.product.ui.tab;

import java.math.BigDecimal;

import org.castafiore.catalogue.Product;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.EXInput;

public class EXOtherSettings extends EXAbstractProductTabContent {

	public EXOtherSettings() {
		super();
		addChild(new EXInput("weight"));
		addChild(new EXInput("height"));
		addChild(new EXInput("width"));
		addChild(new EXInput("length"));
		
	}
	
	@Override
	public Container setProduct(Product product){
		if(product != null){
			BigDecimal weight = product.getWeight();
			BigDecimal height = product.getHeight();
			BigDecimal width = product.getWidth();
			BigDecimal length = product.getLength();
			
			if(weight == null){
				weight = new BigDecimal(10);
			}
			if(height == null){
				height = new BigDecimal(10);
			}
			if(width == null){
				width = new BigDecimal(10);
			}
			if(length == null){
				length = new BigDecimal(10);
			}
			
			((EXInput)getChild("height")).setValue(height.toPlainString());
			((EXInput)getChild("weight")).setValue(weight.toPlainString());
			((EXInput)getChild("length")).setValue(length.toPlainString());
			((EXInput)getChild("width")).setValue(width.toPlainString());
		}
		return this;
			
	}

	@Override
	public void fillProduct(Product product) {
		try{
		product.setHeight(new BigDecimal(((EXInput)getChild("height")).getValue().toString()));
		product.setWeight(new BigDecimal(((EXInput)getChild("weight")).getValue().toString()));
		product.setLength(new BigDecimal(((EXInput)getChild("length")).getValue().toString()));
		product.setWidth(new BigDecimal(((EXInput)getChild("width")).getValue().toString()));
		}catch(Exception e){
			
		}

		
	}
	

}
