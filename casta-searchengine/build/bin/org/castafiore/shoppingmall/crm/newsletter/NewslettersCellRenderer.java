package org.castafiore.shoppingmall.crm.newsletter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Value;

public class NewslettersCellRenderer implements CellRenderer, Event{
	
	List<String> categories = null;
	

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Newsletter nl = (Newsletter)model.getValueAt(column, row, page);
		Container span = new EXContainer("", "span");
		if(categories == null){
			List<Directory> diCar =MallUtil.getCurrentMerchant().getSubscriptionCategories().toList();
			categories = new ArrayList<String>();
			for(Directory d: diCar){
				categories.add(d.getName());
			}
		}
		if(column == 0){
			span.setText(nl.getTitle());
		}else if(column == 1){
			span.setText(nl.getNewsletterTypeLabel());
		}else if(column == 2){
			span.setText(nl.getNewsletterStatusLabel());
		}else if(column == 4){
			
			
			
			return getTable(nl,null);
			
		}else if(column == 3){
			Container btns = new EXContainer("buttons", "div").addClass("buttons");
			btns.addChild(new EXIconButton("delete", Icons.ICON_CIRCLE_CLOSE).setAttribute("title", "Delete newsletter").addEvent(this, Event.CLICK).setAttribute("newsletter", nl.getAbsolutePath()));
			if(nl.getStatus() == nl.STATE_NEW){
				btns.addChild(new EXIconButton("send", Icons.ICON_CIRCLE_ARROW_E).setAttribute("title", "Send newsletter").addEvent(this, Event.CLICK).setAttribute("newsletter", nl.getAbsolutePath()));
			}
			
			
			return btns;
		}
		
		return span;
	}
	
	
	private Container getTable(Newsletter nl, Container t){
		List<Value> values = nl.getFiles(Value.class).toList();
		List<String> present = new ArrayList<String>();
		for(Value val : values){
			present.add(val.getName());
		}
		
		DefaultDataModel<Object> da = new DefaultDataModel<Object>();
		for(String s : categories){
			if(!present.contains(s)){
				da.addItem(s);
			}
		}
		
		EXSelect select = new EXSelect("", da);
		
		if(t == null){
			t = new EXContainer("listgroups", "table").addClass("EXGrid").addChild(new EXContainer("thead", "thead")).addChild(new EXContainer("tbody", "tbody")).setStyle("width", "200px");
		Container thead = t.getChildByIndex(0);
		thead.addChild(new EXContainer("th", "th").addClass("ui-widget-header")
				
				.addChild(select.addClass("group-select"))
			);
		thead.addChild(new EXContainer("th", "th").addClass("ui-widget-header")
				.addChild(new EXIconButton("add", Icons.ICON_PLUS)
								.setStyle("float", "right")
								.setStyle("margin", "0 5px")
								.setStyle("height", "5px")
								.setStyle("padding", "5px").addEvent(this, Event.CLICK).setAttribute("newsletter", nl.getAbsolutePath())
				)
						);
		}else{
			t.getDescendentOfType(EXSelect.class).setModel(da);
		}
		Container tbody = t.getChildByIndex(1);
		tbody.getChildren().clear();
		tbody.setRendered(false);
		for(int i = 0; i < present.size(); i ++){
			tbody.addChild(new EXContainer("tr", "tr")
							.addChild(new EXContainer("td", "td").setText(present.get(i)))
							.addChild(new EXContainer("td", "td").addChild(new EXIconButton("remove", Icons.ICON_MINUS).setAttribute("item", present.get(i)).addEvent(this, Event.CLICK).setAttribute("newsletter", nl.getAbsolutePath()).setStyle("float", "right").setStyle("margin", "0 5px").setStyle("height", "5px").setStyle("padding", "5px")))
					);
		}
		return t;
	}

	@Override
	public void onChangePage(Container span, int row, int column,
			int page, TableModel model, EXTable table) {
		Newsletter nl = (Newsletter)model.getValueAt(column, row, page);
		//Container span = new EXContainer("", "span");
		if(column == 0){
			span.setText(nl.getTitle());
		}else if(column ==1){
			span.setText(nl.getNewsletterTypeLabel());
		}else if(column == 2){
			span.setText(nl.getNewsletterStatusLabel());
		}else if(column == 3){
			getTable(nl, span);
		}else if(column == 4){
			for(Container c : span.getChildren()){
				c.setAttribute("newsletter", nl.getAbsolutePath());
			}
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXNewsletterList.class)).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		Newsletter nl = (Newsletter)SpringUtil.getRepositoryService().getFile(container.getAttribute("newsletter"), Util.getRemoteUser());
		if(container.getName().equalsIgnoreCase("add")){
			Object ov = container.getParent().getParent().getDescendentOfType(EXSelect.class).getValue();
			if(ov != null){
				nl.addGroup(ov.toString());
			}
			nl.save();
			
		}else if(container.getName().equalsIgnoreCase("remove")){
			String torem = container.getAttribute("item");
			nl.getFile(torem).remove();
			nl.save();
		}else if(container.getName().equals("delete")){
			Directory parent = nl.getParent();
			nl.remove();
			parent.save();
			container.getParent().getParent().getParent().remove();
		}else if(container.getName().equals("send")){
			nl.send();
			nl.save();
			container.getParent().getParent().getParent().getChildByIndex(2).getChildByIndex(0).setText("Sent");
			container.remove();
		}
		
		//Container t = container.getAncestorByName("listgroups");
		//getTable(nl, t);
		return true;
		
		
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	

}
