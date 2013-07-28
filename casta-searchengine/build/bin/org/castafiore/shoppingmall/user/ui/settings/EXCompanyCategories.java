package org.castafiore.shoppingmall.user.ui.settings;

import java.util.List;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.types.File;

public class EXCompanyCategories extends EXXHTMLFragment implements Event{
	
	public EXCompanyCategories() {
		super("EXCompanyCategories", "webos/gs/Categories.xhtml");
		addChild(new EXContainer("errormessage", "div").addClass("ui-state-error").setStyle("padding", "0 10px").setDisplay(false));
		addChild(new EXContainer("sendButton", "button").setText(">").addEvent(this, Event.CLICK));
		addChild(new EXContainer("returnButton", "button").setText(">").addEvent(this, Event.CLICK));
		setStyle("clear", "both");
		
		
		List<File> files = MallUtil.getCurrentMall().getRootCategory().getFiles().toList();
		DefaultDataModel<Object> data = new DefaultDataModel<Object>();
		for(File f : files){
			data.addItem(f.getName());
		}
		addChild(new EXSelect("allCategories", data).setAttribute("size", "10").setStyle("width", "150px"));
		addChild(new EXSelect("selectedCategories", new DefaultDataModel<Object>()).setAttribute("size", "10").setStyle("width", "150px"));
		
		
		
		
	}
	
	public void setErrorMessage(String message){
		getChild("errormessage").setText( message).setDisplay(true);
	}
	
	public boolean validate(){
		if(getChild("selectedCategories").getChildren().size() == 0){
			setErrorMessage("<label>Please select at least 1 category of product");
			return false;
		}return true;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(getChild("selectedCategories").getChildren().size() >= 5){
			request.put("error", "true");
			return true;
		}
		
		if(container.getName().equals("sendButton")){
			String cat = ((EXSelect)getChild("allCategories")).getValue().toString();
			DefaultDataModel<Object> model = (DefaultDataModel<Object>)((EXSelect)getChild("selectedCategories")).getModel();
			model.addItem(cat);
			((EXSelect)getChild("selectedCategories")).setModel(model);
		}else{
			String cat = ((EXSelect)getChild("allCategories")).getValue().toString();
			
			DefaultDataModel<Object> model = (DefaultDataModel<Object>)((EXSelect)getChild("selectedCategories")).getModel();
			
			DefaultDataModel<Object> nModel = new DefaultDataModel<Object>();
			for(int i = 0; i < model.getSize(); i ++){
				if(!nModel.getValue(i).toString().equals(cat)){
					nModel.addItem(model.getValue(i));
				}
			}
			((EXSelect)getChild("selectedCategories")).setModel(nModel);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("error")){
			container.alert("A maximum of 5 categories is allowed");
		}
	}

}
