package org.castafiore.inventory.orders;

import java.util.List;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;

public class BNewLineForm extends EXDynaformPanel implements Event{

	public BNewLineForm(String name) {
		super(name, "New Line");
		addField("Product :", (StatefullComponent)new EXSelect(name, getProductsModel()).addEvent(this, CHANGE));
		addField("Price :", (StatefullComponent)new EXInput("price").addEvent(this, BLUR));
		addField("Quantity :", (StatefullComponent)new EXInput("quantity").addEvent(this, BLUR));
		addField("Total :", (StatefullComponent)new EXInput("total"));
		
		addButton((Button)new EXButton("cancel", "Cancel").addEvent(this, CLICK));
		addButton((Button)new EXButton("saveandaddnew", "Save and Add new").addEvent(this, CLICK));
		addButton((Button)new EXButton("save", "Save and Close").addEvent(this, CLICK));
		
		setStyle("width", "400px");
		
		
	}
	
	
	public DataModel<Object> getProductsModel(){
		List products = MallUtil.getCurrentUser().getMerchant().getMyProducts(Product.STATE_DRAFT, 0,-1);
		DefaultDataModel<Object> result = new DefaultDataModel<Object>();
		for(Object p : products){
			String title = ((Product)p).getTitle();
			String path = ((Product)p).getAbsolutePath();
			SimpleKeyValuePair kv = new SimpleKeyValuePair(path, title);
			result.addItem(kv);
		}
		return result;
	}
	
	public void New(){
		getField("price").setValue("0");
		getField("quantity").setValue("0");
		getField("total").setValue("0");
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
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

}
