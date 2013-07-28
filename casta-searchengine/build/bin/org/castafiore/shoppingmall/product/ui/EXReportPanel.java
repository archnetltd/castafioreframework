package org.castafiore.shoppingmall.product.ui;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.back.EXWindow;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXReportPanel extends EXXHTMLFragment implements Event{

	public EXReportPanel(String name) {
		super(name, "templates/EXReportPanel.xhtml");
		
		addChild(new EXDatePicker("startDate"));
		addChild(new EXDatePicker("endDate"));
		List<Product> products = MallUtil.getCurrentMerchant().getManager().getMyProducts(Product.STATE_PUBLISHED);
		DefaultDataModel<Object> model = new DefaultDataModel<Object>();
		for(Product p :products){
			model.addItem(new SimpleKeyValuePair(p.getCode(),p.getTitle()));
		}
		addChild(new EXSelect("product", model).setAttribute("multiple", "multiple").setAttribute("size", "10").setStyle("width", "353px"));
		
		addChild(new EXContainer("productQty", "button").setText("Product Qty report").addEvent(this, CLICK));
		addChild(new EXContainer("salesReport", "button").setText("Product sales Report"));
		addChild(new EXContainer("productList", "button").setText("Product List"));
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXWindow.class)).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		Date startDate = (Date)((EXDatePicker)getChild("startDate")).getValue();
		Date endDate = (Date)((EXDatePicker)getChild("endDate")).getValue();
		String code =((SimpleKeyValuePair) ((EXSelect)getChild("product")).getValue()).getKey();
		EXTable table = getDescendentOfType(EXTable.class);
		if(table == null){
			addChild(new EXTable("report",new ProductQtyReportModel(code, startDate, endDate)));
		}else{
			table.setModel(new ProductQtyReportModel(code, startDate, endDate));
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
