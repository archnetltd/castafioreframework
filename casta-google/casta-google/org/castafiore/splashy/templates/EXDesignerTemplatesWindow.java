package org.castafiore.splashy.templates;

import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.toolbar.menu.EXTemplatesWindow;
import org.castafiore.splashy.TemplatesUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXDesignerTemplatesWindow extends EXPanel implements EXTemplatesWindow, Event{

	public EXDesignerTemplatesWindow() {
		super("kokomilodfs", "Select a template");
		setStyle("width", "900px");
		setStyle("z-index", "2000");
		setShowFooter(true);
		List<Product> products = TemplatesUtil.getTemplates("Device", "Browser");
		Container ul = new EXContainer("", "ul").setStyle("list-style", "none") ;
		for(Product p : products){
			EXTemplateItem item = new EXTemplateItem("sd", p);
			item.addClass("templateItem-designer");
			item.getChild("preview").setAttribute("href", "portal.jsp?portal=" + p.getAbsolutePath() + "/template.ptl").setAttribute("target", "_blank").getEvents().clear();
			item.getChild("edit").setText("Select");
			ul.addChild(new EXContainer("li", "li").setStyle("cursor", "pointer").setStyle("margin", "12px").setStyle("float", "left").addChild(item));
		}
		setBody(ul);
		EXButton use = new EXButton("use", "Use template");
		EXButton cancel = new EXButton("cancel", "Cancel");
		cancel.addEvent(CLOSE_EVENT, Event.CLICK);
		use.addEvent(this, CLICK);
		getFooterContainer().addChild(use.setStyle("float", "right")).addChild(cancel.setStyle("float", "right"));
		
		
	}

	@Override
	public void useTemplate() {
		
		String prod = getAttribute("selectedTemplate");
		//delete current template
		
		String dir = "/root/users/" + Util.getRemoteUser();
		Directory d = SpringUtil.getRepositoryService().getDirectory(dir, Util.getRemoteUser());
		String name = null;
		for(BinaryFile bf : d.getFiles(BinaryFile.class).toList()){
			if(bf.getName().endsWith(".ptl")){
				bf.remove();
				name = bf.getName();
			}
		}
		
		
		String files = name.replace(".ptl", "_files");
		d.getFile(files).remove();
		d.save();
		
		//create new website
		BinaryFile portal = (BinaryFile)SpringUtil.getRepositoryService().getFile(prod + "/template.ptl", Util.getRemoteUser());
		
		
		try{
		DesignableUtil.copyProject(portal, dir);
		getAncestorOfType(EXDesigner.class).open(dir, "template.ptl");
		
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		useTemplate();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
