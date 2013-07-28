package org.castafiore.designer.dataenvironment.ui;

import java.util.Map;

import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.designer.dataenvironment.DatasourceConfigForm;
import org.castafiore.designer.dataenvironment.DatasourceFactory;
import org.castafiore.designer.dataenvironment.DatasourceFactoryService;
import org.castafiore.designer.dataenvironment.DatasourceViewer;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXEditableLabel;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class EXDateEnvironmentTable extends EXTable implements TableModel , CellRenderer, Event{

	public EXDateEnvironmentTable(String name, PortalContainer pc) {
		
		super(name, null);
		this.portal = pc;
		setCellRenderer(this);
		setModel(this);
	}

	private static String[] labels = new String[]{"Name", "Open", "Cacheable", "Actions", "Log"};
	
	private static String[] acts = new String[]{"database_edit", "database_delete", "connect", "database_refresh"};
	
	private static String[] titles = new String[]{"Edit this datasource", "Delete this datasource", "Test connection", "Refresh datasource"};
	
	private static String[] names = new String[]{"edit", "delete", "connect", "refresh"};
	
	//private List<Datasource> datasources = new ArrayList<Datasource>();
	
	private PortalContainer portal;
	
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
		return portal.getDatasources().size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = row + (getRowsPerPage()*page);
		return portal.getDatasources().get(rrow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Datasource source = (Datasource)getValueAt(column, row, page);
		if(!source.isOpen())
			source.open();
		if(column == 0){
			return new EXEditableLabel("name", source.getName()).addEvent(this, CHANGE).setAttribute("index", (row + (page*getRowsPerPage())) + "");
		}else if(column == 1){
			return new EXCheckBox("open", source.isOpen());
		}else if(column == 2){
			return new EXContainer("cacheable", "span").setText(source.cacheable() + "");
		}else if(column == 3){

			Container c = new EXContainer("actions", "div");
			for(int i = 0; i < acts.length; i++){
				
				Container a = new EXContainer(names[i], "a").setStyle("padding", "0 6px").addEvent(this, CLICK).setAttribute("href", "#u").setText("<img src='icons/"+acts[i]+".png'").setAttribute("title", titles[i]);
				c.addChild(a);
			}
			c.setAttribute("index", (row + (page*getRowsPerPage())) + "");
			if(source.isOpen())
				source.close();
			return c;
		}else 
			return new EXContainer("", "p").setStyle("width", "200px").setText(source.getLog());
		
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		
		Datasource source = (Datasource)getValueAt(column, row, page);
		if(!source.isOpen())
			source.open();
		if(column == 0){
			((EXEditableLabel)component.setAttribute("index", (row + (page*getRowsPerPage())) + "")).setValue(source.getName());
		}else if(column == 1){
			((EXCheckBox)component).setChecked(source.isOpen());
		}else if(column == 2){
			component.setText(source.cacheable() + "");
		}else if(column == 3){
			
			component.setAttribute("index", (row + (page*getRowsPerPage())) + "");
			if(source.isOpen())
				source.close();
		}else{
			component.setText(source.getLog());
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		int index = 0;
		if(container instanceof EXEditableLabel){
			index =Integer.parseInt(container.getAttribute("index")); 
		}else{
			index = Integer.parseInt(container.getParent().getAttribute("index"));
		}
		
		Datasource datasource = portal.getDatasources().get(index);
		
		DatasourceFactoryService service = SpringUtil.getBeanOfType(DatasourceFactoryService.class);
		
		DatasourceFactory factory = service.getDatasourceFactory(datasource.getFactoryId());
		
		if(container.getName().equalsIgnoreCase("delete")){
			portal.getDatasources().remove(index);
		}else if(container.getName().equalsIgnoreCase("edit")){
			DatasourceConfigForm form = factory.getAdvancedConfig();
			form.setDatasource(datasource);
			getAncestorOfType(PopupContainer.class).addPopup(form);
		}else if(container.getName().equalsIgnoreCase("connect")){
			DatasourceViewer v = factory.getViewer();
			v.setDatasource(datasource);
			getAncestorOfType(PopupContainer.class).addPopup(v.setStyle("z-index", "3000"));

		}else if(container.getName().equalsIgnoreCase("refresh")){
			factory.refresh(datasource);
		}else if(container.getName().equalsIgnoreCase("name")){
			EXEditableLabel label = (EXEditableLabel)container;
			String value = label.getValue();
			datasource.setName(value);
		}
		getAncestorOfType(EXPagineableTable.class).refresh();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
