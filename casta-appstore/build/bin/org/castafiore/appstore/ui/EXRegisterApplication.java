package org.castafiore.appstore.ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.castafiore.appstore.AppPackage;
import org.castafiore.appstore.AppStoreUtil;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXRegisterApplication extends EXDynaformPanel implements Event , FileFilter, SelectFileHandler{

	public EXRegisterApplication(String name) {
		super(name, "Register your components");
		addField("Name :",new EXInput("name"));
		addField("Title :", new EXInput("title"));
		addField("Description :" ,new EXTextArea("description"));
		addField("Version :",new EXInput("version"));
		addField("Type :", new EXSelect("type",new DefaultDataModel<Object>().addItem("Application", "Component", "Template", "Module", "Macro", "Graphic")));
		List cats = AppStoreUtil.getCategories();
		addField("Catgory :",new EXSelect("category", new DefaultDataModel<Object>(cats)));
		addField("Price :",new EXInput("price"));
		
		EXButton next = new EXButton("next", "Next");
		next.addEvent(this, Event.CLICK);
		addButton(next);
		EXButton cancel = new EXButton("cancel", "Cancel");
		cancel.addEvent(CLOSE_EVENT, Event.CLICK);
		addButton(cancel);
		setStyle("width", "600px");
		getDescendentOfType(EXTextArea.class).setStyle("height", "90px");
		
	}
	
	public void createPackage(){
		Map<String, StatefullComponent> map = getFieldsMap();
		
		String name = map.get("name").getValue().toString();
		String description = map.get("description").getValue().toString();
		String title = map.get("title").getValue().toString();
		String version = map.get("version").getValue().toString();
		String price = map.get("price").getValue().toString();
		String type = map.get("type").getValue().toString();
		String category = map.get("category").getValue().toString();
		String url = getDescendentOfType(EXFinder.class).getDescendentByName("finderTitle").getText();
		
		Directory dir =  SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser(), Util.getRemoteUser());
		Directory myapps = (Directory)dir.getFile("MyApplications");
		if(myapps == null){
			myapps = dir.createFile("MyApplications", Directory.class);
		}
		
		AppPackage app = myapps.createFile(name, AppPackage.class);
		app.setSummary(description);
		app.setTitle(title);
		app.setCategory(category);
		app.setTotalPrice(new BigDecimal(Double.parseDouble(price)));
		app.setPackageType(type);
		app.setVersion(version);
		app.setRootDir(url);
		app.setDownloaded(new BigDecimal(0));
		dir.save();
		this.remove();
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getText().equals("Next")){
		
			Map<String, StatefullComponent> map = getFieldsMap();
			
			String name = map.get("name").getValue().toString();
			String description = map.get("description").getValue().toString();
			String title = map.get("title").getValue().toString();
			String version = map.get("version").getValue().toString();
			String price = map.get("price").getValue().toString();
			String type = map.get("type").getValue().toString();
			String category = map.get("category").getValue().toString();
			
			boolean correct = true;
			if(!StringUtil.isNotEmpty(name)){
				map.get("name").addClass("ui-state-error");
				correct = false;
			}
			
			if(!StringUtil.isNotEmpty(title)){
				map.get("title").addClass("ui-state-error");
				correct = false;
			}
			
			if(!StringUtil.isNotEmpty(version)){
				map.get("version").addClass("ui-state-error");
				correct = false;
			}
			
			if(!StringUtil.isNotEmpty(name)){
				map.get("price").addClass("ui-state-error");
				correct = false;
			}else{
				try{
					Double.parseDouble(price);
				}catch(Exception e){
					map.get("price").addClass("ui-state-error");
					correct = false;
				}
			}
			
			
			if(correct){
			
				EXFinder finder = new EXFinder("", this, this, "/root/users/" + Util.getRemoteUser());
				Container b = getBodyContainer();
				for(Container c : b.getChildren()){
					c.setDisplay(false);
				}
				finder.getDescendentByName("header").setStyle("display", "none");
				finder.getDescendentByName("finderTitle").setDisplay(false);
				getDescendentByName("content").setStyle("padding", "0");
				b.addChild(finder);
				setStyle("width", "894px");
				getDescendentByName("cancel").setText("Back");
				getDescendentByName("next").setText("Finish");
				
			}
		}else if(container.getText().equalsIgnoreCase("cancel")){
			this.remove();
		}else if(container.getText().equalsIgnoreCase("back")){
			Container b = getBodyContainer();
			for(Container c : b.getChildren()){
				if(b instanceof EXFinder){
					c.remove();
				}else{
					c.setDisplay(true);
				}
				//c.setDisplay(false);
			}
		}else if(container.getText().equalsIgnoreCase("finish")){
			createPackage();
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean accept(File pathname) {
		return true;
	}

	@Override
	public void onSelectFile(File file, EXFinder finder) {
		// TODO Auto-generated method stub
		
	}

}
