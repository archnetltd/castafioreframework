package org.castafiore.splashy.templates;

import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;

public class EXPreview extends EXContainer implements Event{

	public EXPreview(String name, Product p) {
		super(name, "div");
		addChild(new EXContainer("editPortal", "a").setText("Use this template").setAttribute("href", "#ep").addClass("button-3").setAttribute("style", "position: absolute;top:20px;right: 70px").addEvent(this, Event.CLICK));
		addChild(new EXContainer("back", "a").setText("Select another template").setAttribute("href", "#bl").addClass("button-3").setAttribute("style", "position: absolute;top:20px;right: 225px").addEvent(this, Event.CLICK));
		addChild(new EXContainer("iframe", "div"));
		if(p != null){
			setProduct(p);
		}
		addChild(new EXContainer("", "style").setText("section{padding:0}aside{display:none}footer{display:none}"));
	}
	
	public void setProduct(Product p){
		setAttribute("path", p.getAbsolutePath());
		List<BinaryFile> bfs = p.getFiles(BinaryFile.class).toList();
		for(BinaryFile bf : bfs){
			if(bf.getName().endsWith(".ptl")){
				String ptl = "portal.jsp?portal=" + bf.getAbsolutePath();
				getChild("iframe").setText("<iframe  frameborder=\"0\" marginheight=\"0\" align=\"top\" src=\""+ptl+"\" style=\"width: 100%;min-height: 2000px\"></iframe>");
				break;
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
		if(container.getName().equalsIgnoreCase("editPortal")){
			Product p = (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			container.getAncestorOfType(EXSplashyTemplatesApplication.class).edit(p);
		}else if(container.getName().equalsIgnoreCase("back")){
			getAncestorOfType(EXSplashyTemplatesApplication.class).list();
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
