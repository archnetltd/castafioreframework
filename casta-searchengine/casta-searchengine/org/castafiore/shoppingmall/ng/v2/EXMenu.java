package org.castafiore.shoppingmall.ng.v2;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.ShoppingMall;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.types.File;

public class EXMenu extends EXContainer implements Event{

private final static Properties prop = new Properties();
	
	static{
		//prop= new Properties();
		try{
		prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("menu.properties"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public EXMenu(String name) {
		super(name, "div");
		addClass("menu");
		init();
	}
	
	
	private void init(){
		//int items = Integer.parseInt(prop.getProperty("numitems"));
		
		
		ShoppingMall mall = MallUtil.getCurrentMall();
		List<File> dir = mall.getRootCategory().getFiles().toList();
		//List<Merchant> merchants = mall.getMerchants();
		for(File m : dir){
	//	for(int i = 1; i <= merchants.size();i++ ){
			
			//String category = prop.getProperty(i + ".label");
			String cat = m.getName();
			if(cat != null){
				Container dt = new EXContainer("", "dt").addClass("item");
				dt.setText("<a href=\"#m\">"+cat+"</a><img src=\"emimg/item/images/ar.png\"></img>");
				dt.setAttribute("notation", "cat:" + cat);
				dt.addEvent(this, CLICK);
				addChild(dt);
				addChild(new EXContainer("", "dd").setDisplay(false));
			}
		}
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXMall mall = container.getAncestorOfType(EXMall.class);
		EXCatalogue p = (EXCatalogue)mall.goToPage(EXCatalogue.class);
		p.search(container.getAttribute("notation"), null);
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
