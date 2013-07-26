package org.castafiore.shoppingmall.product.ui.tab;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.ng.v2.EXGeoCoding;
import org.castafiore.shoppingmall.product.ui.simple.EXSimpleImageUpload;
import org.castafiore.shoppingmall.product.ui.simple.EXSimpleMain;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;

public class ProductCardTabModel implements TabModel{

	private static String[] LABELS = new String[]{"Main", "Price", "Images", "Categories","Options", "Other setting"};
	protected Product product;
	
	private List<String> uis = new ArrayList<String>();
	private List<String> labels = new ArrayList<String>();
	
	
	public ProductCardTabModel(Product product, boolean simple)throws Exception {
		super();
		this.product = product;
		loadUIs(simple);
	}
	
	private void loadSimple(){
		labels.add("Main");
		labels.add("Images");
		labels.add("Categories");
		uis.add(EXSimpleMain.class.getName());
		uis.add(EXSimpleImageUpload.class.getName());
		uis.add(EXCategories.class.getName());
	}
	private void loadUIs(boolean simple)throws Exception{
		
		if(simple){
			loadSimple();
			return;
		}
		
		
		
		String prefs = MallUtil.getCurrentMerchant().getPreference("product.edit", "");
		
		if(StringUtil.isNotEmpty(prefs)){
			
			List<String> as = IOUtil.readStreamByLine(new ByteArrayInputStream(prefs.getBytes()));
			//String[] as = StringUtil.split(prefs, ";");
			for(String s : as){
				String[] kv = StringUtil.split(s, "=");
				labels.add(kv[0]);
				uis.add(kv[1]);
			}
		}else{
			
			uis.add(EXMain.class.getName());
			uis.add(EXPrice.class.getName());
			uis.add(EXImages.class.getName());
			uis.add(EXCategories.class.getName());
			uis.add(EXOptions.class.getName());
			uis.add(EXOtherSettings.class.getName());
			for(String s : LABELS){
				labels.add(s);
			}
		}
	}
	
	
	

	
	@Override
	public int getSelectedTab() {
		return 0;
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		
//		if(index == 0){
//			return new EXMain().setProduct(product);
//		}else if(index == 1){
//			return new EXPrice().setProduct(product);
//		}else if(index == 2){
//			return new EXImages().setProduct(product);
//		}else if(index == 3){
//			return new EXCategories().setProduct(product);
//		}else if(index == 4){
//			return new EXOptions().setProduct(product);
//		}else{
//			//return new EXGeoCoding().setProduct(product);
//			return new EXOtherSettings().setProduct(product);
//		}
		
		try{
			Class c = Thread.currentThread().getContextClassLoader().loadClass(uis.get(index));
			ProductEditForm form = (ProductEditForm)c.newInstance();
			form.setProduct(product);
			return form;
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return  labels.get(index);
	}

	@Override
	public int size() {
		
//		if(MallUtil.getCurrentMerchant().getPlan().equalsIgnoreCase("free")){
//			return 4;
//		}
		return labels.size();
	}

}
