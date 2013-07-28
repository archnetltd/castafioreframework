package org.castafiore.shoppingmall.user.ui;

import java.io.InputStream;
import java.util.List;

import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.shoppingmall.orders.OrdersUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;

public class EXUploadExcel extends EXDynaformPanel implements EventDispatcher{

	public EXUploadExcel(String name) {
		super(name, "Upload the excel file and click on save");
		addField("Upload file :",new EXUpload("uploadfile"));
		addButton(new EXButton("save", "Save"));
		addButton(new EXButton("cancel", "Cancel"));
		
		getDescendentByName("cancel").addEvent(CLOSE_EVENT, Event.CLICK);
		getDescendentByName("save").addEvent(DISPATCHER, Event.CLICK);
		
		
	}

	@Override
	public void executeAction(Container source) {
		try{
			InputStream in = ((EXUpload)getField("uploadfile")).getFile().getInputStream();
			List<String> result = OrdersUtil.createProducts(in);
			getAncestorOfType(org.castafiore.ui.ex.panel.Panel.class)
			.remove();
		}catch(Exception e){
			throw new UIException(e);
		}
	}
}
