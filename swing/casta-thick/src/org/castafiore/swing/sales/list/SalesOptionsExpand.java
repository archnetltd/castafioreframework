package org.castafiore.swing.sales.list;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.castafiore.swing.sales.SaveContractDTO;
import org.openswing.swing.client.ExpandableRowController;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.mdi.client.DefaultToolBar;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.model.client.VOListTableModel;

public class SalesOptionsExpand extends ExpandableRowController{

	@Override
	public boolean isRowExpandable(VOListTableModel model, int rowNum) {
		return true;
	}

	@Override
	public JComponent getComponentToShow(VOListTableModel model, int rowNum) {
		SaveContractDTO d = (SaveContractDTO)model.getObjectForRow(rowNum);
		return new DefaultToolBar();
		
	}

	@Override
	public boolean removeShowedComponent(VOListTableModel model, int rowNum,
			JComponent showedComponent) {
		// TODO Auto-generated method stub
		return super.removeShowedComponent(model, rowNum, showedComponent);
	}

	@Override
	public Component getFocusableComponent(JComponent showedComponent) {
		// TODO Auto-generated method stub
		return super.getFocusableComponent(showedComponent);
	}

}
