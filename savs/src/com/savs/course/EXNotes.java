package com.savs.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.ecm.ui.fileexplorer.button.EXButtonWithLabel;
import org.castafiore.shoppingmall.product.ui.tab.EXAbstractProductTabContent;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXEditableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.EditableCellRenderer;
import org.castafiore.ui.ex.form.table.EditableTableModel;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.BinaryFile;

public class EXNotes extends EXAbstractProductTabContent implements EditableCellRenderer, EditableTableModel, Event{
	
private String [] labels = new String[]{"Attachement", "Remove"};
	
	private List<BinaryFile> attachements = new ArrayList<BinaryFile>();

	public EXNotes() {
		super();
		
		setTemplateLocation(ResourceUtil.getDownloadURL("classpath", "com/savs/course/EXNotes.xhtml"));
		EXButtonWithLabel btn = new EXButtonWithLabel("add", "Add New", "AddPage.gif", "small");
		addChild(btn.setStyle("margin", "7px").addEvent(this, CLICK));
		addChild(new EXEditableTable("edit", this,this));
		
	}
	
	
	
	
	public void save(Article art, boolean isNew){
		if(isNew){
			for(BinaryFile bf : attachements){
				BinaryFile bb  = art.createFile(bf.getName(), BinaryFile.class);
				bb.setUrl(bf.getUrl());				
			}
		}else{
			List<BinaryFile> attachs = art.getAttachments().toList();
			for(BinaryFile bf : attachs){
				bf.remove();
			}
			
			for(BinaryFile bf : attachements){
				BinaryFile bb = art.createFile(bf.getName(), BinaryFile.class);
				bb.setUrl(bf.getUrl());
			}
		}
	}
	
	
	public void addRow(){
		attachements.add(new BinaryFile());
		getDescendentOfType(EXEditableTable.class).setModel(this);
	}

	@Override
	public StatefullComponent getInputAt(int row, int column, int page,
			EditableTableModel model, EXEditableTable table) {
		
		int rrow = row + page*getRowsPerPage();
		EXFieldSet fs = new EXFieldSet("upl", "Upload", false);
		fs.setAttribute("index", rrow + "");
		fs.addField(new EXUpload("key"));
		fs.getDescendentOfType(EXUpload.class).setStyle("height", "80px").getParent().getParent().getChildByIndex(0).remove();
		fs.getDescendentOfType(EXUpload.class).getParent().setStyle("padding", "0").setStyle("border", "none");
		fs.addButton(new EXButton("save", "Save"));
		fs.addButton(new EXButton("cancel", "Cancel"));
		fs.getDescendentByName("save").addEvent(this, CLICK).setStyle("margin", "12px");
		fs.getDescendentByName("cancel").addEvent(this, CLICK).setStyle("margin", "12px");
		return fs;
		//return new EXUpload("key"); 		
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		int rrow = row + page*getRowsPerPage();
		BinaryFile bf = (BinaryFile) model.getValueAt(column, row, page);		
		if(column == 1){
			return new EXContainer("del", "a").addEvent(this, CLICK).addChild(new EXContainer("img", "img").setAttribute("src", ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/icons/small/DustBin.gif"))).setAttribute("index", rrow + "");
		}
		Container ctn = new EXContainer("lblKey", "label").setText(bf.getName());
		return ctn;
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		BinaryFile bf = (BinaryFile) model.getValueAt(column, row, page);
		component.setText(bf.getName());
	}

	@Override
	public void setValueAt(int col, int row, int page, Object value) {
		int rrow = row + (getRowsPerPage()*page);
		if(value instanceof BinaryFile){
			attachements.get(rrow).setUrl(((BinaryFile)value).getUrl());
		}
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return labels[index];
	}

	@Override
	public int getRowCount() {
		return attachements.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = row + (getRowsPerPage()*page);
		return attachements.get(rrow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0)
		return true;
		else
			return false;
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("save")){
			int index = Integer.parseInt(container.getAncestorOfType(EXFieldSet.class).getAttribute("index"));
			
			BinaryFile bf = (BinaryFile)container.getAncestorOfType(EXFieldSet.class).getField("key").getValue();
			BinaryFile bb = attachements.get(index);
			bb.setName(bf.getName());
			bb.setUrl(bf.getUrl());
			Container c = container.getAncestorOfType(EXFieldSet.class).getParent();
			c.getAncestorOfType(EXEditableTable.class).cancelEdit(container.getAncestorOfType(EXFieldSet.class));
			return true;
		}else if(container.getName().equalsIgnoreCase("cancel")){
			Container c = container.getAncestorOfType(EXFieldSet.class).getParent();
			c.getAncestorOfType(EXEditableTable.class).cancelEdit(container.getAncestorOfType(EXFieldSet.class));
			
			
			return true;
		}
		
		if(container.getName().equalsIgnoreCase("del")){
			int index = Integer.parseInt(container.getAttribute("index"));
			attachements.remove(index);
			getDescendentOfType(EXEditableTable.class).setModel(this);
			return true;
		}
		
		addRow();
		getDescendentOfType(EXEditableTable.class).startEdit(attachements.size()-1, 0, 0);
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}

	

	@Override
	public Container setProduct(Product product) {
		attachements = SavsUtil.getHandouts(product);
		return this;
	}

	@Override
	public void fillProduct(Product product) {
		try{
		for(BinaryFile bf : attachements){
			SavsUtil.addHandout(bf.getName(), bf, product);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
