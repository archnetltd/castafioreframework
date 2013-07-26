package org.castafiore.shoppingmall.ng.v2;

import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.shoppingmall.product.ui.tab.EXAbstractProductTabContent;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.StringUtil;

public class EXGeoCoding extends EXAbstractProductTabContent implements Event{

	public EXGeoCoding() {
		//super(name, "templates/EXGeoCoding.xhtml");
		setTemplateLocation("templates/EXGeoCoding.xhtml");
		setStyle("width", "500px").setStyle("height", "400px");
		
		addChild(new EXInput("zoomLevel").addClass("ZoomLevel").setDisplay(false));
		addChild(new EXInput("location").addClass("Location").setDisplay(false));
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.appendJSFragment("saveMap();");
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillProduct(Product product) {
		product.setProperty("location", ((EXInput)getDescendentByName("location")).getValue().toString());
		product.setProperty("zoomLevel", ((EXInput)getDescendentByName("zoomLevel")).getValue().toString());
		
	}

	@Override
	public Container setProduct(Product product) {
		if(product != null){
			((EXInput)getDescendentByName("location")).setValue(product.getProperty("location"));
			if(StringUtil.isNotEmpty(product.getProperty("zoomLevel"))){
				((EXInput)getDescendentByName("zoomLevel")).setValue(product.getProperty("zoomLevel"));
			}else{
				((EXInput)getDescendentByName("zoomLevel")).setValue("8");
			}
		}
		
		
		return this;
	}

}
