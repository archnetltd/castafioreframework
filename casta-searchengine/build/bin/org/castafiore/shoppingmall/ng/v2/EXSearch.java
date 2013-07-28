package org.castafiore.shoppingmall.ng.v2;

import java.util.List;
import java.util.Map;

import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;

public class EXSearch extends EXContainer implements Event{

	public EXSearch(String name) {
		super(name, "div");
		addClass("search");
		addChild(new EXContainer("search", "img").setAttribute("src", "emimg/item/images/search.png").addEvent(this, CLICK));
		addChild(new EXAutoComplete("searchInput", "", getDictionary()));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}
	
	private List<String> getDictionary(){
		return SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery("select distinct title from WFS_FILE where dtype='Product' ORDER BY title").list();
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXMall mall = container.getAncestorOfType(EXMall.class);
		EXCatalogue p = (EXCatalogue)mall.goToPage(EXCatalogue.class);
		String val = getDescendentOfType(EXInput.class).getValue().toString();
		p.search("fullText:" + val, null);
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
