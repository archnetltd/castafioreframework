package org.castafiore.ecm.ui.fileexplorer.views.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.button.EXButtonWithLabel;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.CloseFinderHandler;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.table.EXEditableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.EditableCellRenderer;
import org.castafiore.ui.ex.form.table.EditableTableModel;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;

public class EXReferenceEditor extends EXContainer implements EditableCellRenderer, EditableTableModel, Event, ArticleEditorTab, SelectFileHandler, CloseFinderHandler{
	
	private String [] labels = new String[]{"References", "Remove"};
	
	private List<Shortcut> attachements = new ArrayList<Shortcut>();

	public EXReferenceEditor(String name) {
		super(name,"div");
		
		
		EXButtonWithLabel btn = new EXButtonWithLabel("add", "Add New", "AddPage.gif", "small");
		addChild(btn.setStyle("margin", "7px").addEvent(this, CLICK));
		addChild(new EXEditableTable("edit", this,this));
		
	}
	
	public void fill(Article art){
		if(art != null)
		attachements = art.getShortcuts().toList();
	}
	
	
	public void save(Article art, boolean isNew){
		if(isNew){
			for(Shortcut bf : attachements){
				Shortcut bb  = art.createFile(bf.getName(), Shortcut.class);
				bb.setReference(bf.getReference());				
			}
		}else{
			List<Shortcut> attachs = art.getShortcuts().toList();
			for(Shortcut bf : attachs){
				bf.remove();
			}
			
			for(Shortcut bf : attachements){
				Shortcut bb = art.createFile(bf.getName(), Shortcut.class);
				bb.setReference(bf.getReference());
			}
		}
	}
	
	
	public void addRow(){
		attachements.add(new Shortcut());
		getDescendentOfType(EXEditableTable.class).setModel(this);
	}

	@Override
	public StatefullComponent getInputAt(int row, int column, int page,
			EditableTableModel model, EXEditableTable table) {
		
		int rrow = row + page*getRowsPerPage();
		EXFieldSet fs = new EXFieldSet("upl", "Upload", false);
		fs.setAttribute("index", rrow + "");
		fs.addField(new EXUpload("key"));
		
		Container cell = fs.getDescendentOfType(EXUpload.class);
		
		Container td = cell.getParent();
		cell.remove();
		
		td.setRendered(false);
		
		EXFinder finder = new EXFinder("finder", null,this, "/root/users/" + Util.getRemoteUser());
		td.addChild(finder);
		fs.getChildByIndex(0).remove();
		td.getParent().getChildByIndex(0).remove();
		td.setStyle("padding", "0").setStyle("border", "none");
		return fs;
	
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		int rrow = row + page*getRowsPerPage();
		Shortcut bf = (Shortcut) model.getValueAt(column, row, page);		
		if(column == 1){
			return new EXContainer("del", "a").addEvent(this, CLICK).addChild(new EXContainer("img", "img").setAttribute("src", ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/icons/small/DustBin.gif"))).setAttribute("index", rrow + "");
		}
		Container ctn = new EXContainer("lblKey", "label").setText(bf.getReference());
		return ctn;
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		Shortcut bf = (Shortcut) model.getValueAt(column, row, page);
		component.setText(bf.getReference());
	}

	@Override
	public void setValueAt(int col, int row, int page, Object value) {
		int rrow = row + (getRowsPerPage()*page);
		
			attachements.get(rrow).setReference(value.toString());
		
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(Article art, boolean isNew) {
		if(!isNew){
			
			fill((Article)SpringUtil.getRepositoryService().getFile(art.getAbsolutePath(), Util.getRemoteUser()));
			getDescendentOfType(EXTable.class).setModel(this);
		}
		
	}

	@Override
	public void onSelectFile(File bf, EXFinder container) {
		int index = Integer.parseInt(container.getAncestorOfType(EXFieldSet.class).getAttribute("index"));

		Shortcut bb = attachements.get(index);
		bb.setName(bf.getName());
		bb.setReference(bf.getAbsolutePath());
		Container c = container.getAncestorOfType(EXFieldSet.class).getParent();
		c.getAncestorOfType(EXEditableTable.class).cancelEdit(container.getAncestorOfType(EXFieldSet.class));
		
		
	}

	@Override
	public void onClose(EXFinder container) {
		Container c = container.getAncestorOfType(EXFieldSet.class).getParent();
		c.getAncestorOfType(EXEditableTable.class).cancelEdit(container.getAncestorOfType(EXFieldSet.class));
		
	}
}
