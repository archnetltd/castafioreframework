package org.castafiore.designable.product;

import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.AbstractXHTML;
import org.castafiore.designable.EXCatalogue;
import org.castafiore.designable.EXEcommerce;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCalendar;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Restrictions;

public class EXProductResultBar extends AbstractXHTML implements Event{

	public EXProductResultBar(String name) {
		super(name);
		addClass("toolbar");
		addClass("EXProductResultBar");
		addChild(new EXContainer("amount", "p").addClass("amount"));
		addChild(new EXContainer("sortName", "a").setAttribute("href", "#sn").setAttribute("title", "Name").setText("Name").addEvent(this, CLICK));
		addChild(new EXContainer("sortPrice", "a").setAttribute("href", "#sp").setAttribute("title", "Price").setText("Price").addEvent(this, CLICK));
		addChild(new EXContainer("paginator", "ol"));
		addChild(new EXSelect("limiters", new DefaultDataModel<Object>().addItem("5", "10", "15", "30", "50")).addEvent(this, Event.CHANGE));
		getDescendentOfType(EXSelect.class).setValue("10");
		setStyle("margin-top", "10px");
		
	}
	
	public void reset(int pSize, int total, int page){
		
		String amount = "Items 1 to " + pSize + " of " + total;
		
		if(pSize >= total){
			amount = "Items 1 to " + total;
		}
		if(total == 1){
			amount = "One item found";
		}
		getChild("amount").setText(amount);
		
		setAttribute("page", page + "");
		
		int pages = ((total- (total%pSize))/pSize) + 1;
		setAttribute("pages", pages + "");
		Container paginator = getChild("paginator");
		paginator.getChildren().clear();
		paginator.setRendered(false);
		if(pages > 1){
			
			Container previous = new EXContainer("li", "li").addChild(new EXContainer("previous", "a").setAttribute("href", "#paginator").addEvent(this, Event.CLICK).setText("<img src=\"http://buy.hmrawat.com/skin/frontend/default/hmrawatblack/images/pager_arrow_left.gif\" alt=\"Next\" class=\"v-middle\">"));
			paginator.addChild(previous);
			
			for(int i = 1; i <= pages; i++){
				if(i == (page+1)){
					Container c = new EXContainer("li", "li");
					paginator.addChild(c.setText(i+""));
				}else {
					Container c = new EXContainer("li", "li").addChild(new EXContainer("a", "a").setAttribute("href", "#paginator").setText((i) + "").addEvent(this, Event.CLICK));
					paginator.addChild(c);
				}
			}
			
			Container next = new EXContainer("li", "li").addChild(new EXContainer("next", "a").setAttribute("href", "#paginator").addEvent(this, Event.CLICK).setText("<img src=\"http://buy.hmrawat.com/skin/frontend/default/hmrawatblack/images/pager_arrow_right.gif\" alt=\"Next\" class=\"v-middle\">"));
			paginator.addChild(next);
			
			if(page == 0){
				previous.setDisplay(false);
			}else if(page == (pages-1)){
				next.setDisplay(false);
			}
			
			
			
			
			
			
			
		}
		
		
	}



	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXCatalogue.class)).makeServerRequest(this);
		
	}



	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		
		
		if(container.getName().equals("a")){
			int page = Integer.parseInt(container.getText())-1;
			container.getAncestorOfType(EXCatalogue.class).goToPage(page);
			
		}else if(container.getName().equals("sortName")){
			getAncestorOfType(EXEcommerce.class).getDescendentOfType(EXCatalogue.class).orderBy("title");
			
		}else if(container.getName().equals("sortPrice")){
			getAncestorOfType(EXEcommerce.class).getDescendentOfType(EXCatalogue.class).orderBy("totalPrice");
		}else if(container.getName().equals("limiters")){
			int size = Integer.parseInt(((EXSelect)container).getValue().toString());
			getAncestorOfType(EXEcommerce.class).getDescendentOfType(EXProductGrid.class).initGrid(size);
			getAncestorOfType(EXEcommerce.class).getDescendentOfType(EXCatalogue.class).goToPage(0);
		}else if(container.getName().equals("next")){
			int curPage = Integer.parseInt(getAttribute("page"));
			
			container.getAncestorOfType(EXCatalogue.class).goToPage(curPage+1);
		}else if(container.getName().equals("previous")){
int curPage = Integer.parseInt(getAttribute("page"));
			
			container.getAncestorOfType(EXCatalogue.class).goToPage(curPage-1);
		}
		
		return true;
		
		
	}



	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	

}
