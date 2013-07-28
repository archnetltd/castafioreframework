package org.castafiore.shoppingmall.crm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.inventory.orders.OrderService;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.AutoCompleteSource;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.table.EXScrollableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.js.JArray;
import org.castafiore.utils.StringUtil;

public class EXSubscribersList extends EXBorderLayoutContainer implements
		Event, AutoCompleteSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXSubscribersList(String name) {

		super(name);
		setStyle("clear", "both");
		addClass("ex-content");

		addSearch();
		EXTable t = new EXTable("", new SubscribersTableModel("Default"));
		t.setStyle("clear", "both");
		t.setStyle("width", "95%");
		t.setCellRenderer(new SubscriberListCellRenderer());
		EXScrollableTable pTable = new EXScrollableTable("", t);
		pTable.setStyle("height", "350px");
		addChild(pTable, CENTER);
		pTable.getParent().setStyle("padding", "0").setStyle("margin", "0")
				.setStyle("vertical-align", "top")
				.setStyle("min-width", "600px");
		getContainer(TOP).setStyle("padding", "0");
		getContainer(CENTER).setStyle("padding", "0").addClass("fieldset")
				.setStyle("padding", "12px");
		getContainer(LEFT).setStyle("padding", "0");
		getContainer(RIGHT).setStyle("padding", "0");
		//appForm(this);
	}

	public EXPanel appForm(Container source) {
		try {
			EXPanel panel = new EXPanel("", "Application form");
			panel.setCloseButtonEvent(Panel.HIDE_EVENT);
			Container c = (Container) Thread
					.currentThread()
					.getContextClassLoader()
					.loadClass(
							"com.eliensons.ui.plans.EXElieNSonsApplicationForm")
					.newInstance();
			panel.setBody(c);
			panel.setStyle("width", "737px");
			panel.setStyle("z-index", "4000");
			source.getAncestorOfType(PopupContainer.class).addPopup(panel);
			panel.setStyle("top", "0px");
			panel.setDisplay(false);
			return panel;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public JArray getSource(String param) {

		try {

			
			List<String> terms = new ArrayList<String>();
			List<OrderInfo> result = OrderService.fullTextSearch(param, 0, 10);
			for (OrderInfo info : result) {
				String code = info.getFsCode();
				if(StringUtil.isNotEmpty(code)){
					if(!terms.contains(code)){
						terms.add(code);
					}
					if(terms.size() > 10){
						break;
					}
				}
				
//				for (java.lang.reflect.Field f : OrderInfo.class
//						.getDeclaredFields()) {
//					if (!f.getName().equalsIgnoreCase("absolutePath") && f.getType().isAssignableFrom(String.class)) {
//						
//						if (f.get(info) != null) {
//							String s = f.get(info).toString();
//							if(s.length() > 0){
//								if (!terms.contains(s)) {
//									terms.add(s);
//									if(terms.size() >10){
//										break;
//									}
//								}
//							}
//						}
//					}
//				}
			}
			JArray array = new JArray();
			for(String s : terms){
				array.add(s);
			}
			array.sort();
			return array;

		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	private List<OrderInfo> searchUsername(String term) {

		try {
			return OrderService.fullTextSearch(term, 0, 100);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	private void addSearch() {
		Container container = new EXContainer("", "div").addClass(
				"fg-toolbar ui-widget-header ui-helper-clearfix").setStyle(
				"padding", "12px");
		container
				.addChild(
						new EXContainer("", "label")
								.setText("Search clients :"))
				.addChild(
						new EXAutoComplete("searchInput", "").setSource(this)
								.setStyle("margin-left", "30px")
								.setStyle("width", "300px"))
				.addChild(
						new EXContainer("search", "a")
								.setAttribute("href", "#")
								.setText(
										"<img src='icons-2/fugue/icons/binocular.png'></img>")
								.addEvent(this, CLICK));
		addChild(container, TOP);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXSubscribersList.class))
				.makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String search = ((EXInput) getDescendentByName("searchInput"))
				.getValue().toString();

		EXTable table = getDescendentOfType(EXTable.class);
		List<OrderInfo> ss = searchUsername(search);
		((SubscribersTableModel) table.getModel()).filter(ss);
		table.setModel(table.getModel());

		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		

	}

}
