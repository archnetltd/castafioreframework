package org.castafiore.designer.newportal.body;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.castafiore.designer.newportal.EXNewPortal;
import org.castafiore.designer.newportal.icons.EXBlankSite;
import org.castafiore.designer.newportal.icons.EXFullArc;
import org.castafiore.designer.newportal.icons.EXLeftPanelIcon;
import org.castafiore.designer.newportal.icons.EXPortalIcon;
import org.castafiore.designer.newportal.icons.EXRightPanelIcon;
import org.castafiore.designer.newportal.icons.EXThreeRowsIcon;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.layout.EXUnOrderedList;

public class EXNewPortalBody extends AbstractWizardBody{
	
	private Map<String, String> templates = new HashMap<String, String>();

	public EXNewPortalBody(String name) {
		super(name);
		
		EXBorderLayoutContainer bd = new EXBorderLayoutContainer("df");
		 
		Container uitemplates = new EXContainer("", "div");
		bd.addChild(uitemplates,bd.CENTER);
		for(String s : EXBorderLayoutContainer.CONTENT_NAMES){
			bd.getChild(s).setStyle("margin", "0").setStyle("padding", "0").setStyle("vertical-align", "top");
		}
		EXUnOrderedList uitypes = new EXUnOrderedList("dsf");
		uitypes.addChild(new EXLabel("blank", "Empty Sites"), "0");
		uitypes.addChild(new EXLabel("css", "CSS Templates"), "1");
		uitypes.addChild(new EXLabel("html5", "HTML5 Templates"), "2");
		
		bd.addChild(uitypes, bd.LEFT);
		
		uitemplates.addChild(new EXBlankSite());
		uitemplates.addChild(new EXThreeRowsIcon());
		uitemplates.addChild(new EXLeftPanelIcon());
		uitemplates.addChild(new EXRightPanelIcon());
		uitemplates.addChild(new EXFullArc());
		
		addChild(bd);
	//	addIcons();
	}
	
	
	private void addIcons(){
		try{
			
			Source source  = new Source(new URL("http://www.primarycss.com/"));
			 List<Element> elems = source.getAllElements();
			 for(Element e : elems){
				 if(e.getStartTag().getName().equalsIgnoreCase("div") && "maincontent".equals(e.getAttributeValue("class"))){
					 //main content
					 for(Element ee : e.getAllElements()){
						 if(ee.getStartTag().getName().equalsIgnoreCase("a") ){
							 if(ee.getAttributeValue("href").startsWith("Layout.aspx?jib=")){
								 final String jb = ee.getAttributeValue("href").replace("Layout.aspx?jib=", "").trim();
								 Container img = new EXContainer(jb, "img").setStyle("margin", "12px").setStyle("padding", "12px").setStyle("background", "silver");
								 addChild(img);
								 for(Element eee : ee.getAllElements()){
									 if(eee.getStartTag().getName().equalsIgnoreCase("img")){
										 img.setAttribute("src", "http://www.primarycss.com/" + eee.getAttributeValue("src"));
										 img.setAttribute("jb", jb);
										 img.addEvent(new Event(){
											 @Override
												public void ClientAction(ClientProxy container) {
													container.mask(container.getAncestorOfType(EXPortalIcon.class));
													container.makeServerRequest(this);
													
												}
												
												

												@Override
												public boolean ServerAction(Container container, Map<String, String> request)
														throws UIException {
													for(Container c : container.getParent().getChildren()){
														c.setStyle("background-color", "transparent");
														c.setAttribute("selected", "false");
														c.setStyle("border", "none");
													}
													container.setStyle("background", "red");
													container.setAttribute("selected", "true");
													container.setStyle("border", "solid red");
													return true;
												}

												@Override
												public void Success(ClientProxy container, Map<String, String> request)
														throws UIException {
												}
										 }, Event.CLICK);
									 }
								 }
							 }
						 }
					 }
				 }
			 }
			
		}catch(Exception e){
			throw new UIException(e);
		}
		
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
	public AbstractWizardBody clickButton(Container button) {
		if(button.getName().equals("next")){
			String selected =getSelected();
			if(selected != null){
				getAncestorOfType(EXNewPortal.class).getNewPortal().setPortalSelected(selected);
				return new EXSelectBannerBody("EXSelectBannerBody");
			}
			
		}else if(button.getName().equals("back")){
			Container parent = getAncestorOfType(EXNewPortal.class).getParent();
			getAncestorOfType(EXNewPortal.class).remove();
			parent.setRendered(false);
		}
		return null;
	}

}
