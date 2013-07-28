package org.castafiore.designer.toolbar.menu;

import java.util.Iterator;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.layout.EXDroppableXYLayoutContainer;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.portalmenu.EXPagesDataModel;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXNewPageForm extends EXDynaformPanel implements Event{
	
	private String portalPath ;

	public EXNewPageForm(String name, BinaryFile portal) {
		super(name, "Create a new page");
		addField("Page name :", new EXInput("name"));
		portalPath = portal.getAbsolutePath();
		EXPagesDataModel model = new EXPagesDataModel(portal, "Root");
		EXSelect saveIn = new EXSelect("saveIn", model);
		addField("Save page in :", saveIn);
		
		model.setDefault("");
		EXSelect template = new EXSelect("template", model);
		addField("Template :", template);
		
		EXButton save = new EXButton("save", "Save");
		addButton(save);
		save.addEvent(this, Event.CLICK);
		
		EXButton cancel = new EXButton("cancel", "Cancel");
		cancel.addEvent(CLOSE_EVENT, Event.CLICK);
		addButton(cancel);
		
		ComponentUtil.applyStyleOnAll(this, StatefullComponent.class, "width", "195px");
		setWidth(Dimension.parse("354px"));
	}
	
	public void save(){
		try{
			String path =  ((SimpleKeyValuePair)getField("saveIn").getValue()).getKey();
			if(path.equals("Root")){
				path = portalPath + "/pages";
			}
			String name = getField("name").getValue().toString();
			
			String template =((SimpleKeyValuePair)getField("template").getValue()).getKey();
			Container uiTemplate = null;
			if(template.trim().length() > 0){
				BinaryFile page = (BinaryFile)SpringUtil.getRepositoryService().getFile(template, Util.getRemoteUser());
				uiTemplate= DesignableUtil.buildContainer(page.getInputStream(), false);
				
			}
			savePage(name, path, getAncestorOfType(EXDesigner.class), uiTemplate);
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	private void savePage(String pageName, String save,EXDesigner designer, Container template)throws Exception
	{
		
		Container page = DesignableUtil.getInstance("core:div");
		page.setStyle("position", "relative");
		page.setName(pageName);
		page.setAttribute("text", " ");
		page.setText("");
		
		page.setAttribute("Text", ""); 	
		if(template != null){
			for(String s : template.getStyleNames()){
				page.setStyle(s, template.getStyle(s));
			}
			page.setStyleClass(template.getStyleClass());
		}else{
			page.setStyle("width", "100%");
			page.setStyle("min-height", "500px");
			page.setStyle("padding", "0");
			page.setStyle("margin", "0");
		}
		PageContainer pc = designer.getRootLayout().getDescendentOfType(PageContainer.class);
		
		if(pc == null){
			throw new UIException("Please add a page holder before creating a page. A page holder tells us where to place pages in your site");
		}
		
		pc.setPage(page);
		
		designer.getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).refresh();
		designer.getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).open();
		
		RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
		Directory saveIn = futil.getDirectory(save);
		BinaryFile bf = saveIn.createFile(pageName,BinaryFile.class);//new BinaryFile();
		//bf.setName(pageName);
		bf.write(DesignableUtil.generateXML(page, null).getBytes());
		bf.save();
		//service.update(saveIn, Util.getRemoteUser());
		page.setAttribute("pagepath", bf.getAbsolutePath());
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXNewPageForm.class));
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		save();
		remove();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
