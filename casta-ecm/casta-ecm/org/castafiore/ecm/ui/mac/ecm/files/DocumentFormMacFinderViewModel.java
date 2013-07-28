package org.castafiore.ecm.ui.mac.ecm.files;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.mac.EXMacFinderColumn;
import org.castafiore.ui.mac.item.EXMacButtonRow;
import org.castafiore.ui.mac.item.EXMacFieldItem;
import org.castafiore.ui.mac.item.MacColumnItem;
import org.castafiore.ui.mac.renderer.MacFinderColumnViewModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;

public class DocumentFormMacFinderViewModel implements MacFinderColumnViewModel, Event{

	private String filePath;
	
	
	public DocumentFormMacFinderViewModel(String filePath) {
		super();
		this.filePath = filePath;
	}

	@Override
	public MacColumnItem getValueAt(int index) {
		if(index == 0){
			return new EXMacFieldItem("", "Please enter the name of the document",new EXInput("name"));
		}else if(index ==1){
			return new EXMacFieldItem("", "Please enter the title",new EXInput("title"));
		}else if(index == 2){
			return new EXMacFieldItem("", "Please enter short summary",new EXRichTextArea("summary"));
		}else if(index == 3){
			return new EXMacFieldItem("", "Please enter full description",new EXRichTextArea("detail"));
		}else{
			EXMacButtonRow row = new EXMacButtonRow("buttons");
			row.addButton("save", "Save this document", "ui-icon-disk", this);
			return row;
		}
	}

	@Override
	public int size() {
		return 5;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXMacFinderColumn.class));
		container.makeServerRequest(this);
		
	}
	
	public String getFieldValue(String field, Container caller){
		String result =((StatefullComponent)caller.getAncestorOfType(EXMacFinderColumn.class).getDescendentByName(field)).getValue().toString();
		return result;
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String name =getFieldValue("name", container);
		String title =getFieldValue("title", container);
		String summary =getFieldValue("summary", container);
		String detail =getFieldValue("detail", container);
		File f = SpringUtil.getRepositoryService().getDirectory(filePath, Util.getRemoteUser());
		
		//Article doc = new Article();
		//doc.setName(name);
		Article doc = f.createFile(name, Article.class);
		doc.setTitle(title);
		doc.setSummary(summary);
		doc.setDetail(detail);
		doc.makeOwner(Util.getRemoteUser());
		f.save();
		//SpringUtil.getRepositoryService().update(f, Util.getRemoteUser());
		return true;
		//parent.openUsing(caller)
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
