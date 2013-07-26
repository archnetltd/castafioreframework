package org.castafiore.inventory.product;

import java.math.BigDecimal;
import java.util.HashMap;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class BProductForm extends EXDynaformPanel implements EventDispatcher{

	public BProductForm(String name) {
		super(name, "New Product");
		addField("Code",new EXInput("code"));
		addField("Title",new EXInput("title"));
		addField("Description", new EXTextArea("description"));
		addField("Reorder Level", new EXInput("reorderlevel"));
		addField("Cost",new EXInput("cost"));
		addField("Price",new EXInput("price"));
		
		addButton((Button)new EXButton("save", "Save and Close").addEvent(DISPATCHER, Event.CLICK));
		addButton((Button)new EXButton("saveaddnew", "Save and Add new").addEvent(DISPATCHER, Event.CLICK));
		addButton((Button)new EXButton("cancel", "Cancel").addEvent(CLOSE_EVENT, Event.CLICK));
		setStyle("width", "500px");
		
		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "padding", "3px");
		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "margin", "10px 3px");
		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "width", "200px");
	}
	
	public void setProduct(Product p){
		if(p == null){
			setAttribute("path", "new");
			clear();
		}else{
			setAttribute("path", p.getAbsolutePath());
			setValue("code", p.getCode());
			setValue("title", p.getTitle());
			setValue("reorderlevel",p.getReorderLevel().toString());
			setValue("cost", StringUtil.toCurrency(MallUtil.getCurrentMerchant().getCurrency(),p.getCostPrice()));
			setValue("price", StringUtil.toCurrency(MallUtil.getCurrentMerchant().getCurrency(),p.getTotalPrice()));
			setValue("description", p.getSummary());
			
		}
	}
	
	public void clear(){
		setValue("code", "");
		setValue("title", "");
		setValue("description", "");
		setValue("price", "0");
		setValue("cost", "0");
		setValue("reorderlevel", "0");
	}
	
	public void setValue(String field, String value){
		getField(field).setValue(value);
	}
	
	public String getValue(String field){
		return getField(field).getValue().toString();
	}
	public void save(){
		String path = getAttribute("path");
		Product p = null;
		if(path.equals("new")){
			p = MallUtil.getCurrentUser().getMerchant().createProduct();
		}else{
			p = (Product)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		}
		p.setCode(getValue("code"));
		p.setTitle(getValue("title"));
		p.setSummary(getValue("description"));
		p.setReorderLevel(new BigDecimal(getValue("reorderlevel")));
		p.setTotalPrice(new BigDecimal(getValue("price")));
		p.setCostPrice(new BigDecimal(getValue("cost")));
		p.save();
		
	}
	
	
	public void New(){
		setProduct(null);
	}
	
	public void saveNClose(Container source){
		save();
		back(source);
	}
	
	public void back(Container source){
		CLOSE_EVENT.ServerAction(source, new HashMap<String, String>());
	}
	
	public void SaveNNew(){
		save();
		New();
	}

	@Override
	public void executeAction(Container source) {
		
		if(source.getName().equals("saveaddnew")){
			SaveNNew();
		}else if(source.getName().equals("save")){
			saveNClose(source);
		}
	}

}
