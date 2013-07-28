package org.castafiore.shoppingmall.product.ui.tab;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.catalogue.Product;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.ex.form.list.DefaultDataModel;


public class EXMain extends EXAbstractProductTabContent implements Event{

	public EXMain() {
		super();
		addChild(new EXInput("title").addClass("span-11"));
		addChild(new EXInput("code").addClass("span-11"));

		addChild(new EXRichTextArea("description").setStyle("width", "490px").setStyle("height", "200px"));
		
Map<String, String> llls = new HashMap<String, String>(); 
		
		DefaultDataModel<Object> model = new DefaultDataModel<Object>();
		for(Locale l : Locale.getAvailableLocales()){
			//
			if(!llls.containsKey(l.getLanguage())){
				llls.put(l.getLanguage(), l.getDisplayLanguage());
				model.addItem(new SimpleKeyValuePair(l.getLanguage(),l.getDisplayLanguage()));
			}
		}
	}


	
	

	@Override
	public Container setProduct(Product product){
		
		if(product != null){
			((StatefullComponent)getChild("title")).setValue(product.getTitle());
			((StatefullComponent)getChild("code")).setValue(product.getCode());
			((StatefullComponent)getChild("description")).setValue(product.getSummary());
			
		}else{
			((StatefullComponent)getChild("title")).setValue("");
			((StatefullComponent)getChild("code")).setValue("");
			((StatefullComponent)getChild("description")).setValue("");
		}
		return this;
			
	}

	@Override
	public void fillProduct(Product product) {
		
		product.setTitle(((StatefullComponent)getChild("title")).getValue().toString());
		product.setCode(((StatefullComponent)getChild("code")).getValue().toString());
		product.setSummary(((StatefullComponent)getChild("description")).getValue().toString());
	}



	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
	}



	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		return true;
	}



	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	

}
