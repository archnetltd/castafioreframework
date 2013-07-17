/*
 * 
 */
package org.castafiore.ui.ex.table;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXEditableTable extends EXTable implements Event{

	public EXEditableTable(String name, EditableTableModel model, EditableCellRenderer renderer) {
		super(name, model, renderer);
	}

	public EXEditableTable(String name, EditableTableModel model) {
		super(name, model);
	}


	public void setCellRenderer(EditableCellRenderer celleRendere) {
		super.setCellRenderer(celleRendere);
	}

	public void changePage(int page)
	{
		if(tableModel_ == null){
			return;
		}
		this.currentPage = page;
		Container tbody =  getChild("tbody");
		int rowsPerpage = tableModel_.getRowsPerPage();
		
		int rowCount = tableModel_.getRowCount();
		
		int maxRow = (page+1)*rowsPerpage;
		
		int rowsToShow = rowsPerpage;
		
		if(maxRow > rowCount)
			rowsToShow = rowCount - ((page)*rowsPerpage);
		
		for (int row = 0; row < rowsToShow; row++)
		{
			Container tr =  tbody.getChildByIndex(row);

			for (int col = 0; col < tableModel_.getColumnCount(); col++)
			{
				Container td = null;
				if(tr.getChildren().size() <= col)
				{
					td = new EXContainer("", "td").setAttribute("c", col + "").setAttribute("r", row + "").setAttribute("m", "r");
					td.addEvent(this, CLICK);
					if(tableModel_.isCellEditable(row, col)){
						td.addEvent(this, CLICK);
					}
					
					tr.addChild(td);
				}
				else
				{
					td = tr.getChildByIndex(col);
					if(td.getEvents().size() == 0){
						td.setAttribute("c", col + "").setAttribute("r", row + "").setAttribute("m", "r");
						if(tableModel_.isCellEditable(row, col)){
							td.addEvent(this, CLICK);
						}else{
							td.getEvents().clear();
							td.setRendered(false);
						}
					}
				}


				Container component = null;
				if (td.hasChildren())
				{
					component =  td.getChildByIndex(0);
					celleRendere_.onChangePage(component, row, col, page, tableModel_, this);
				}
				else
				{
					component = celleRendere_.getComponentAt(row, col, page, tableModel_, this);
					td.addChild(component);
					td.setRendered(false);
				}

			}
			
			if(rowDecorator_ != null){
				rowDecorator_.decorateRow(row, (EXContainer)tr, this, tableModel_);
			}
		}
		
		if(rowsToShow < rowsPerpage)
		{
			for(int i = rowsToShow; i < rowsPerpage; i ++)
			{
				if(i < tbody.getChildren().size())
				{
					Container row = tbody.getChildByIndex(i);
					
					row.getChildren().clear();
					row.setRendered(false);
				}
			}
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}
	
	
	public void startEdit(int row, int col, int page){
		Object value = super.tableModel_.getValueAt(col, row, page);
		StatefullComponent sft = ((EditableCellRenderer)this.celleRendere_).getInputAt(row, col, page,(EditableTableModel)tableModel_ , this);
		
		sft.setValue(value);
		
		Container container = getChild("tbody").getChildByIndex(row).getChildByIndex(col);
		
		container.getChildren().clear();
		container.addChild(sft.addEvent(this, BLUR).setAttribute("r", row + "").setAttribute("c", col + "").setAttribute("m", "e"));
		container.getEvents().clear();
	}
	
	public void cancelEdit(StatefullComponent stf){	
		Container td = stf.getParent();
		int row = Integer.parseInt(stf.getAttribute("r"));
		int col = Integer.parseInt(stf.getAttribute("c"));
		int page = currentPage;
		Container c = celleRendere_.getComponentAt(row, col, page, tableModel_, this);
		td.getChildren().clear();
		td.setRendered(false);
		td.addChild(c);
		td.addEvent(this, CLICK);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		int row = Integer.parseInt(container.getAttribute("r"));
		int col = Integer.parseInt(container.getAttribute("c"));
		int page = currentPage;
		boolean editMode =  container.getAttribute("m").equalsIgnoreCase("e");
		
		if(!editMode){
			Object value = super.tableModel_.getValueAt(col, row, page);
			StatefullComponent sft = ((EditableCellRenderer)this.celleRendere_).getInputAt(row, col, page,(EditableTableModel)tableModel_ , this);
			
			sft.setValue(value);
			
			container.getChildren().clear();
			container.addChild(sft.addEvent(this, BLUR).setAttribute("r", row + "").setAttribute("c", col + "").setAttribute("m", "e"));
			container.getEvents().clear();
		}else{
			Container td = container.getParent();
			Object value = container.getAncestorOfType(StatefullComponent.class).getValue();
			((EditableTableModel)tableModel_).setValueAt(col, row, page, value);
			Container c = celleRendere_.getComponentAt(row, col, page, tableModel_, this);
			td.addEvent(this, CLICK);
			td.addChild(c);
			container.setAttribute("m", "r");
		}
		
		
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
}
