package org.castafiore.shoppingmall.ng;

import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Directory;

public class EXMallSubMenu extends EXContainer implements Event{

	
	public EXMallSubMenu(String name, String[] imgs, String[] labels, String category) {
		super(name, "ul");
		addClass("ui-widget-content").addClass("sub-menu");
		
		for(int i =0; i < imgs.length; i++){
			Container li = new EXContainer("", "li");
			addChild(li);
			li.setAttribute("cat", category + "/" + labels[i]); 
			Container div= new EXContainer("", "div").addClass("sub-menu-item");
			div.addChild(new EXContainer("", "img").setAttribute("src", "emalltheme/menu/" + imgs[i]));
			div.addChild(new EXContainer("", "h5").setStyle("color", "black").setText(labels[i]));
			div.addChild(new EXContainer("", "p").setText("Lorem ipsum dolor amet Lorem ipsum dolor amet Lorem ipsum dolor amet Lorem ipsum dolor amet"));
			li.addChild(div);
			li.addEvent(this, READY);
		}
		// TODO Auto-generated constructor stub
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mouseover(container.clone().addClass("ui-state-highlight")).mouseout(container.clone().removeClass("ui-state-highlight")).click(container.clone().mergeCommand(new ClientProxy("#loader").setStyle("display", "block")).makeServerRequest(this));
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String notation = "cat:" + container.getAttribute("cat");
		//List<Product> products = MallUtil.getCurrentMall().searchProducts(, 0	, 10);
		container.getRoot().getDescendentOfType(EXMallNG.class).getDescendentOfType(EXCatalogueNG.class).search(notation, getName());
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		container.mergeCommand(new ClientProxy("#loader").setStyle("display", "none"));
	}

}
