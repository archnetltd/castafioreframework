package com.savs.course;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.castafiore.catalogue.Product;
import org.castafiore.shoppingmall.product.ui.tab.EXAbstractProductTabContent;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.types.BinaryFile;

public class EXSessions extends EXAbstractProductTabContent implements Event{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container body = new EXContainer("body", "tbody");

	public EXSessions() {
		addClass("TabContent");
		setTemplateLocation(ResourceUtil.getDownloadURL("classpath", "com/savs/course/EXSessions.xhtml"));
		addChild(body);
		addChild(new EXContainer("addSession", "button").setText("Add New").addEvent(this, CLICK));
	}

	@Override
	public Container setProduct(Product product) {
		
		try{
		List<BinaryFile> atta = product.getAttachments().toList();
		if(atta.size() > 0){
			Properties prop = new Properties();
			prop.load(atta.get(0).getInputStream());
			for(Object key : prop.keySet()){
				String value = prop.getProperty(key.toString());
				addLine(key.toString(), value);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this;
	}

	@Override
	public void fillProduct(Product product) {
		List<BinaryFile> atta = product.getAttachments().toList();
		try{
			if(atta.size() == 0){
				BinaryFile bf = product.createFile("sessions", BinaryFile.class);
				
				bf.write(getProperties().getBytes());
			}else{
				BinaryFile bf = atta.get(0);
				bf.write(getProperties().getBytes());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	private String getProperties (){
		StringBuilder b = new StringBuilder();
		for(Container c : body.getChildren()){
			String key = c.getChildByIndex(1).getText(false);
			String value = c.getChildByIndex(2).getText(false);
			b.append(key).append("=").append(value).append("\n");
		}
		
		return b.toString();
	}
	
	public void addRawLine(){
		
		Container tr = new EXContainer("", "tr");
		
		DefaultDataModel<Object> catModel = new DefaultDataModel<Object>();
		catModel.addItem("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
		
		EXSelect cat = new EXSelect("day",catModel);
		if(catModel.getSize() > 0)
			cat.setValue(catModel.getValue(0));
		
		Container number = new EXContainer("", "td").setText((body.getChildren().size() +1) + "");
		Container uiCategory = new EXContainer("", "td").addChild(cat);
		
		Container uisCategory = new EXContainer("", "td");
		
		EXInput input = new EXInput("time");
		uisCategory.addChild(input);
		
		
		Container eventtd = new EXContainer("", "td").addChild(new EXContainer("img", "img").setAttribute("src", "icons-2/fugue/icons/tick-button.png").addEvent(this, Event.CLICK));
		
		tr.addChild(number);
		tr.addChild(uiCategory);
		tr.addChild(uisCategory);
		tr.addChild(eventtd);
		body.addChild(tr);
	}
	
	
public void addLine(String category, String sCategory){
		
		EXContainer tr = new EXContainer("", "tr");
		if((body.getChildren().size() % 2)== 0){
			tr.addClass("even");
		}
		Container number = new EXContainer("", "td").setText((body.getChildren().size() +1) + "");
		Container uiCategory = new EXContainer("", "td").setText(category);
		Container uisCategory = new EXContainer("", "td").setText(sCategory);
		Container eventtd = new EXContainer("", "td").addChild(new EXContainer("img", "img").setAttribute("src", "icons-2/fugue/icons/minus-button.png").addEvent(this, Event.CLICK));
		
		tr.addChild(number);
		tr.addChild(uiCategory);
		tr.addChild(uisCategory);
		tr.addChild(eventtd);
		body.addChild(tr);
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("addSession")){
			addRawLine();
			return true;
		}
		
		Container tr = container.getParent().getParent();
		if(container.getAttribute("src").equalsIgnoreCase("icons-2/fugue/icons/tick-button.png")){
			//int index = Integer.parseInt(tr.getChildByIndex(0).getText());
			String category = tr.getChildByIndex(1).getDescendentOfType(StatefullComponent.class).getValue().toString();
			String sCategory =tr.getChildByIndex(2).getDescendentOfType(StatefullComponent.class).getValue().toString();
			tr.getChildByIndex(1).getChildren().clear();
			tr.getChildByIndex(1).setText(category);
			tr.getChildByIndex(2).getChildren().clear();
			tr.getChildByIndex(2).setText(sCategory);
			container.setAttribute("src", "icons-2/fugue/icons/minus-button.png");
			tr.setRendered(false);
		}else{
		
			
			tr.remove();
			int index = 1;
			for(Container c : body.getChildren()){
				c.getChildByIndex(0).setText(index + "");
				index++;
			}
			body.setRendered(false);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}

}
