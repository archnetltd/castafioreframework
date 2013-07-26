package org.castafiore.searchengine.back;

import java.util.List;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

public class EXApplicationRegistry extends EXWindow implements Event{

	public EXApplicationRegistry(String name) {
		super(name, "Application registry");
		EXBorderLayoutContainer border = new EXBorderLayoutContainer("");
		setBody(border);
		
		border.getParent().addClass("ui-widget-content");
		
		Merchant m = MallUtil.getCurrentMerchant();
		
		List<OSBarItem> items = m.getMyApps();
		boolean first = true;
		for(OSBarItem item : items){
			String icon = item.getIcon();
			String title =item.getTitle();
			String appName = item.getAppName();
			
			Container img = new EXContainer(appName, "img").setAttribute("src", icon);
			img.setStyle("cursor", "pointer").addEvent(this, CLICK);
			img.setAttribute("title", title).setAttribute("permission", item.getPermission());
			
			border.addChild(img.setStyle("width", "64px"), EXBorderLayoutContainer.LEFT);
			if(first){
				EXFieldSet fs = new EXFieldSet("fs", "Configure " + title, false);
				fs.addField("Name :", new EXLabel("name",appName));
				fs.addField("Title :", new EXInput("title",title));
				fs.addField("Icon :", new EXInput("icon",icon));
				fs.addField("Permissions :", new EXInput("permission",item.getPermission()));
				fs.setStyle("width", "500px");
				border.addChild(fs, EXBorderLayoutContainer.CENTER);
				first = false;
				fs.getParent().setStyle("vertical-align", "top");
				ComponentUtil.iterateOverDescendentsOfType(fs, StatefullComponent.class, new ComponentVisitor() {
					
					@Override
					public void doVisit(Container c) {
						c.setStyle("width", "300px");
						
					}
				});
				
				EXInput in = new EXInput("dfsd");
				fs.addField("",in );
				Container parent = in.getParent();
				parent.getChildren().clear();
				parent.setRendered(false);
				
				parent.addChild(new EXContainer("save", "button").setText("Save").addEvent(this, CLICK));
			}
		}
		
		
		
//		EXFieldSet fs = new EXFieldSet("fs", "Configurer", false);
//		fs.addField("Name :", new EXInput("name"));
//		fs.addField("Title :", new EXInput("title"));
//		fs.addField("Icon :", new EXInput("icon"));
//		fs.addField("Permissions :", new EXInput("permission"));
//		border.addChild(fs, EXBorderLayoutContainer.CENTER);
		
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		
		container.mask().makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("save")){
			
			EXFieldSet fs = getDescendentOfType(EXFieldSet.class);
			String name = fs.getField("name").getValue().toString();
			String icon = fs.getField("icon").getValue().toString();
			String title = fs.getField("title").getValue().toString();
			String permission = fs.getField("permission").getValue().toString();
			
			Merchant m = MallUtil.getCurrentMerchant();
			m.saveApp(name, title, icon, permission);
			
			
		}else{
		
			String name = container.getName();
			String icon = container.getAttribute("src");
			String permission = container.getAttribute("permission");
			String title = container.getAttribute("title");
			EXFieldSet fs = getDescendentOfType(EXFieldSet.class);
			fs.getField("icon").setValue(icon);
			fs.getField("name").setValue(name);
			fs.getField("permission").setValue(permission);
			fs.getField("title").setValue(title);
			fs.setTitle("Configure " + title);
		
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
