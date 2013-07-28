package org.castafiore.designer.newportal.body;

import java.io.File;
import java.util.Map;

import org.castafiore.designer.newportal.EXNewPortal;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXTextArea;

public class EXSelectMenuBody extends AbstractWizardBody implements Event{
	private String selectedMenu = null;

	private final static String[] LAYOUTS= {"menu2", "menu3", "menu4", "menu5", "menu6", "menu7"};
	public EXSelectMenuBody(String name) {
		super(name);
		addClass("EXSelectMenuBody");
		File dir = new File("/usr/local/software/tomcat6/emallofmauritius/mall/designer/menu/vertical");
		//File dir = new File("C:\\apache-tomcat-6.0.18\\webapps\\casta-ui\\designer\\menu\\vertical\\");
		File[] contents = dir.listFiles();
		//for(String s : LAYOUTS){
		for(File f : contents){
			if(f.isDirectory() ){
				continue;
			}
			addChild(new EXContainer(f.getName().replace("'", "").replace(" ", "").replace(".bmp", ""), "img").setAttribute("src", "designer/menu/vertical/" + f.getName()).addEvent(this, CLICK));
			
		}
			
	}
	@Override
	public AbstractWizardBody clickButton(Container button) {
		if(button.getName().equals("next")){
			String selected =getSelected();
			if(selected != null){
				getAncestorOfType(EXNewPortal.class).getNewPortal().setMenu(selected);
				//String text = getDescendentOfType(EXTextArea.class).getValue().toString();
				getAncestorOfType(EXNewPortal.class).getNewPortal().setFooterText("");
				getAncestorOfType(EXNewPortal.class).getDescendentByName("next").setText("Finish");
				
			
				return new EXSelectFolderBody("EXSelectFolderBody");
				//return new EXSelectFooterBody("EXSelectFooterBody");
			}
		}else if(button.getName().equals("back")){
			return new EXSelectBannerBody("EXSelectBannerBody");
		}
		return null;
	}
	
	
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}
	
	public String getSelected(){
		for(Container c : getChildren()){
			if("true".equals(c.getAttribute("selected"))){
				return c.getName();
			}
		}
		return null;
	}
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		for(Container c : container.getParent().getChildren()){
			c.setStyle("border", "none");
			c.setAttribute("selected", "false");
		}
		container.setStyle("border", "dotted 5px red");
		container.setAttribute("selected", "true");
		
		return true;
	}
	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
