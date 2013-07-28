package org.castafiore.splashy;

import java.util.List;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.catalogue.Product;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.splashy.EXJustSlider.EXJustSliderItem;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;

public class EXJustSliderConfig extends EXPanel implements Event, ConfigForm{
	
	private EXJustSlider slider = null;

	public EXJustSliderConfig(String name) {
		super(name, "Just Slider Configuration");
		setStyle("width", "600px");
		EXGrid grid = new EXGrid("gg", 3, 1);
		List<Product> products = MallUtil.getCurrentMerchant().getManager().getMyProducts(Product.STATE_PUBLISHED);
		DefaultDataModel<Object> kvs = new DefaultDataModel<Object>();
		for(Product p : products){
			kvs.addItem(new SimpleKeyValuePair(p.getAbsolutePath(), p.getTitle()));
		}
		grid.getCell(0, 0).addChild(new EXSelect("products", kvs).setAttribute("size", "10").setStyle("width", "150px"));
		grid.getCell(2, 0).addChild(new EXSelect("selectedProducts", new DefaultDataModel<Object>()).setAttribute("size", "10").setStyle("width", "150px"));
		
		
		grid.getCell(1, 0).addChild(new EXContainer("returnButton", "button").setText("<").addEvent(this, Event.CLICK));
		grid.getCell(1, 0).addChild(new EXContainer("sendButton", "button").setText(">").addEvent(this, Event.CLICK));
		setBody(grid);
		setShowFooter(true);
		getFooterContainer().addChild(new EXButton("save", "Save").addEvent(this, CLICK));
		getFooterContainer().addChild(new EXButton("cancel", "Cancel").addEvent(CLOSE_EVENT, CLICK));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("save")){
			applyConfigs();
			return true;
		}
		
		if(container.getName().equals("sendButton")){
			KeyValuePair cat = (KeyValuePair)((EXSelect)getDescendentByName("products")).getValue();
			DefaultDataModel<Object> model = (DefaultDataModel<Object>)((EXSelect)getDescendentByName("selectedProducts")).getModel();
			model.addItem(cat);
			((EXSelect)getDescendentByName("selectedProducts")).setModel(model);
		}else{
			KeyValuePair cat = (KeyValuePair)((EXSelect)getDescendentByName("products")).getValue();
			
			DefaultDataModel<Object> model = (DefaultDataModel<Object>)((EXSelect)getDescendentByName("selectedProducts")).getModel();
			//model.getData().remove(cat);
			DefaultDataModel<Object> nModel = new DefaultDataModel<Object>();
			for(int i = 0; i < model.getSize(); i ++){
				KeyValuePair kv = (KeyValuePair)model.getValue(i);
				if(!cat.getKey().equalsIgnoreCase(kv.getKey())){
					nModel.addItem(model.getValue(i));
				}
			}
			((EXSelect)getDescendentByName("selectedProducts")).setModel(nModel);
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyConfigs() {
		DefaultDataModel<Object> model = (DefaultDataModel<Object>)((EXSelect)getDescendentByName("selectedProducts")).getModel();
		String prop = "";
		
		for(int i = 0; i < model.getSize(); i++){
			String path = ((KeyValuePair)model.getValue(i)).getKey();
			if(i == 0){
				prop = path;
			}else{
				prop = prop + ";" + path;
			}
		}
		
		this.slider.setProducts(prop);
	}

	@Override
	public void setContainer(Container container) {
		String val = container.getAttribute("products");
		setRendered(false);
		String[] paths = StringUtil.split(val, ";");
		QueryParameters params = new QueryParameters().setEntity(Product.class);
		for(String path : paths){
			params.addSearchDir(path);
		}
		params.addSearchDir("/oo");
		List<Product> products = (List)SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		DefaultDataModel<Object> kvs = new DefaultDataModel<Object>();
		for(Product p : products){
			kvs.addItem(new SimpleKeyValuePair(p.getAbsolutePath(), p.getTitle()));
		}
		((EXSelect)getDescendentByName("selectedProducts")).setModel(kvs);
		this.slider = (EXJustSlider)container;
	}

	

}
