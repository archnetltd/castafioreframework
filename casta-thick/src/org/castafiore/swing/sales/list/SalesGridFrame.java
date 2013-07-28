package org.castafiore.swing.sales.list;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.castafiore.swing.sales.SalesFrame;
import org.castafiore.swing.sales.SaveContractDTO;
import org.openswing.swing.client.EditButton;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.InsertButton;
import org.openswing.swing.client.ReloadButton;
import org.openswing.swing.mdi.client.DefaultToolBar;
import org.openswing.swing.mdi.client.InternalFrame;
import org.openswing.swing.table.columns.client.CurrencyColumn;
import org.openswing.swing.table.columns.client.DateColumn;
import org.openswing.swing.table.columns.client.TextColumn;

public class SalesGridFrame extends InternalFrame implements ActionListener{
	
	GridControl sales = new GridControl();
	
	TextColumn fsCode = new TextColumn();
	DateColumn date = new DateColumn();
	TextColumn plan = new TextColumn();
	TextColumn planDetail = new TextColumn();
	TextColumn customer = new TextColumn();
	CurrencyColumn installment = new CurrencyColumn();
	TextColumn status = new TextColumn();
	
	DefaultToolBar toolBar = new DefaultToolBar();
	
	
	
	public SalesGridFrame() {
		super();
		jbInit();
	}



	public void jbInit(){
		setLayout(new BorderLayout());
		sales.setValueObjectClassName(SaveContractDTO.class.getName());
		fsCode.setColumnFilterable(true);
		fsCode.setColumnName("fsCode");
		fsCode.setColumnSortable(true);
		fsCode.setColumnVisible(true);
		fsCode.setColumnSelectable(true);
		sales.getColumnContainer().add(fsCode,null);
		
		
		date.setColumnFilterable(true);
		date.setColumnName("date");
		date.setColumnSortable(true);
		date.setColumnVisible(true);
		date.setEditableOnEdit(true);
		date.setEditableOnInsert(true);
		sales.getColumnContainer().add(date,null);
		
		plan.setColumnFilterable(true);
		plan.setColumnName("plan");
		plan.setColumnSortable(true);
		plan.setColumnVisible(true);
		plan.setEditableOnEdit(true);
		plan.setEditableOnInsert(true);
		sales.getColumnContainer().add(plan,null);
		
		planDetail.setColumnFilterable(true);
		planDetail.setColumnName("planDetail");
		planDetail.setColumnSortable(true);
		planDetail.setColumnVisible(true);
		planDetail.setEditableOnEdit(true);
		planDetail.setEditableOnInsert(true);
		sales.getColumnContainer().add(planDetail,null);
		
		customer.setColumnFilterable(true);
		customer.setColumnName("customer");
		customer.setColumnSortable(true);
		customer.setColumnVisible(true);
		customer.setEditableOnEdit(true);
		customer.setEditableOnInsert(true);
		sales.getColumnContainer().add(customer,null);
		
		installment.setColumnFilterable(true);
		installment.setColumnName("installment");
		installment.setColumnSortable(true);
		installment.setColumnVisible(true);
		installment.setEditableOnEdit(true);
		installment.setEditableOnInsert(true);
		sales.getColumnContainer().add(installment,null);
		
		status.setColumnFilterable(true);
		status.setColumnName("status");
		status.setColumnSortable(true);
		status.setColumnVisible(true);
		status.setEditableOnEdit(true);
		status.setEditableOnInsert(true);
		sales.getColumnContainer().add(status,null);
		
		
		SalesGridController controller = new SalesGridController();
		sales.setController(controller);
		sales.setGridDataLocator(controller);
		
		//sales.setInsertButton(insert);
		//sales.setEditButton(edit);
		setSize(800, 400);
		toolBar.bindGrid(sales);
		toolBar.setEditButton(true);
		toolBar.setNavigatorBar(true);
		toolBar.setFilterButton(true);
		toolBar.setCopyButton(false);
		toolBar.setExportButton(true);
		for(ActionListener l : toolBar.getInsertButton().getActionListeners())
			toolBar.getInsertButton().removeActionListener(l);
				
		toolBar.getInsertButton().addActionListener(this);
		
		sales.setExpandableColumn(0);
		
		sales.setExpandableRowController(new SalesOptionsExpand());
	
		getContentPane().add(toolBar, BorderLayout.NORTH);
		getContentPane().add(sales, BorderLayout.CENTER);
		setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
	
		add(new SalesFrame());
	}

}
