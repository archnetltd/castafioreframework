package org.castafiore.shoppingmall.ng;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.types.Directory;

public class EXMallMenu extends EXContainer implements Event{
	
	private final static Properties prop = new Properties();
	
	static{
		//prop= new Properties();
		try{
		prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("menu.properties"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public EXMallMenu(String name) {
		super(name, "ul");
		addClass("menu");
		
//		String[] textures = new String[]{"PaleRed","PaleBlue","PaleGreen", "Red" , "Blue", "Green", "Yellow","Fushia"};
//		
//		Directory root = MallUtil.getCurrentMall().getRootCategory();
//		int t=0;
//		List<Directory> categories = root.getFiles(Directory.class).toList();
//		for(Directory dir : categories){
//			if(dir.getName().length() <= 17){
//				Container li = new EXContainer(dir.getName(), "li").addClass("ui-state-default");
//				li.setStyle("background-image", "url('emalltheme/state"+textures[t]+".png')");
//				li.addChild(new EXContainer("", "a").setAttribute("href", "#").setText(dir.getName()));
//				addChild(li.addEvent(this, Event.READY).setAttribute("open", "false"));
//				li.addChild(new EXMallSubMenu(textures[t], dir).setDisplay(false));
//				t++;
//			}
//		}
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	private void init(){
		int items = Integer.parseInt(prop.getProperty("numitems"));
		for(int i = 1; i <= items;i++ ){
			String skin = prop.getProperty(i + ".skin");
			String[] images = StringUtil.split(prop.getProperty(i + ".img"), ",");
			String[] cats = StringUtil.split(prop.getProperty(i + ".labels"), ",");
			String category = prop.getProperty(i + ".label");
			
			Container li = new EXContainer(category, "li").addClass("ui-state-default");
			li.setStyle("background-image", "url('blueprint/images/bkmenu.png')");
			
			li.setStyle("background-position", "-" +((i-1)*122) + "px 0px");
			li.addChild(new EXContainer("", "label").setText(category).setStyle("color", "#666").setStyle("cursor", "pointer"));
			addChild(li.addEvent(this, Event.READY).setAttribute("open", "false"));
			li.addChild(new EXMallSubMenu(skin,images,cats,category).setDisplay(false));
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		
		ClientProxy open = container.clone().removeClass("ui-state-default").addClass("ui-state-active").setAttribute("open", "true").getDescendentOfType(EXMallSubMenu.class).setStyle("display", "block");
		ClientProxy close = container.clone().removeClass("ui-state-active").addClass("ui-state-default").setAttribute("open", "false").getDescendentOfType(EXMallSubMenu.class).setStyle("display", "none");
		container.mouseover(container.clone().IF(container.getAttribute("open").equal("false"), open, container.clone()))
		
			.mouseout(container.clone().IF(container.getAttribute("open").equal("true"),close, container.clone()));
		
		
		
		
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
