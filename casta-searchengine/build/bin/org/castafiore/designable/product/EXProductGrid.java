package org.castafiore.designable.product;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;

public class EXProductGrid extends EXContainer{

	private Class<? extends EXProductItem> productClass;
	
	
	public EXProductGrid(String name) {
		super(name, "ul");
		addClass("products-grid first");
		addClass("EXProductGrid");
		initGrid(10);
	}
	
	public EXProductGrid(String name, Class<? extends EXProductItem> pclass) {
		super(name, "ul");
		addClass("products-grid first");
		addClass("EXProductGrid");
		this.productClass = pclass;
		initGrid(10);
	}
	
	public void initGrid(int size){
		int pageSize = getChildren().size();
		if(size > pageSize){
			for(int i = getChildren().size(); i < size; i++){
				//addChild(new EXProductItem(""));
				if(productClass == null)
					addChild(new EXProductItem(""));
				else{
					try{
					addChild(productClass.newInstance());
					}catch(Exception e){
						throw new UIException(e);
					}
				}
			}
		}else if(size < pageSize){
			this.getChildren().clear();
			setRendered(false);
			for(int i = 0;i < size;i++){
				if(productClass == null)
					addChild(new EXProductItem(""));
				else{
					try{
					addChild(productClass.newInstance());
					}catch(Exception e){
						throw new UIException(e);
					}
				}
			}
		}
		
	}
	
	public void setProducts(List<Product> products){
		int pageSize = getChildren().size();
		for(int i = 0; i < pageSize; i++){
			try{
				Product p = products.get(i);
				((EXProductItem)getChildByIndex(i).setDisplay(true)).setProduct(p);
				
			}catch(Exception e){
				e.printStackTrace();
				((EXProductItem)getChildByIndex(i)).setDisplay(false);
			}
		}
	}

}
