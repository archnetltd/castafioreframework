package org.castafiore.imports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.castafiore.shoppingmall.imports.Importer;
import org.castafiore.shoppingmall.imports.OrderImporterTemplateV2;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.springframework.core.io.Resource;

public class EXImportContractsPanel extends EXPanel implements Event, PopupContainer{

	public EXImportContractsPanel(String name) {
		super(name, "Imports");
		
		EXToolBar tb = new EXToolBar("tb");
		EXIconButton btn = new EXIconButton("bew", "Add New", Icons.ICON_PLUSTHICK);
		btn.setAttribute("title", "Upload a new import file");
		tb.addItem(btn);
		btn.addEvent(this, CLICK);
		
		Container body = new EXContainer("bd", "div");
		body.addChild(tb);
		//body.addChild(new EXOverlayPopupPlaceHolder("overlay"));
		
		setStyle("width", "700px");
		setBody(body);
		
		ImportsModel model = new ImportsModel();
		
		EXTable table = new EXTable("reclist",model);
		table.setCellRenderer(model);
		body.addChild(table);
		
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("bew")){
			EXDynaformPanel panel = new EXDynaformPanel("sdf", "Upload file");
			panel.addField("Upload file :", new EXUpload("upl"));
			panel.addButton(new EXButton("save", "Save"));
			panel.getDescendentByName("save").addEvent(this, CLICK);
			panel.setStyle("width", "500px");
			panel.setStyle("z-index", "4000");
			getAncestorOfType(PopupContainer.class).addPopup(panel);
		}else{
			BinaryFile bf = (BinaryFile)((EXUpload)container.getAncestorOfType(EXDynaformPanel.class).getField("upl")).getFile();
			if(bf != null){
				try{
					//Directory dir =SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getLoggedOrganization() + "/imports", Util.getRemoteUser());
					//BinaryFile b = dir.createFile(bf.getName(), BinaryFile.class);
					bf.putInto("/root/users/" + Util.getLoggedOrganization() + "/imports");
					BinaryFile logs = bf.createFile("logs.txt", BinaryFile.class);
					logs.write("*".getBytes());
//					InputStream in = bf.getInputStream();
//					bf.write(in);
					
					bf.save();
					
					ImportsModel model = new ImportsModel();
					EXTable table = getDescendentOfType(EXTable.class);
					table.setModel(model);
					table.setCellRenderer(model);
					container.getAncestorOfType(EXDynaformPanel.class).remove();
				}catch(Exception e){
					throw new UIException(e);
				}
			}
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
