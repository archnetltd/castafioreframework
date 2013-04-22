package org.castafiore.swing.sales;

import java.awt.BorderLayout;

import org.openswing.swing.client.GridControl;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;
import org.openswing.swing.wizard.client.WizardInnerPanel;

public class DependentsForm extends WizardInnerPanel{
	
	TextColumn serial= new TextColumn();
	TextColumn name = new TextColumn();
	TextColumn nic = new TextColumn();
	ComboColumn gender = new ComboColumn();
	
	GridControl grid = new GridControl();
	
	
	public DependentsForm() {
		super();
		jbInit();
	}

	

	@Override
	public String getPanelId() {
		return getClass().getName();
	}



	public void jbInit(){
		Domain genders = new Domain("GENDERS");
		gender.setDomain(genders);
		gender.setColumnName("gender");
		name.setEditableOnEdit(true);
		name.setColumnName("name");
		serial.setColumnName("id");
		nic.setColumnName("nic");
		nic.setEditableOnEdit(true);
		nic.setVisible(true);
		grid.getColumnContainer().add(serial, null);
		grid.getColumnContainer().add(name,null);
		grid.getColumnContainer().add(nic,null);
		grid.getColumnContainer().add(gender,null);
		grid.setController(new DependentsGridController());
		grid.setValueObjectClassName(DependantVO.class.getName());
		
		setLayout(new BorderLayout());
		add(grid, BorderLayout.CENTER);
		
		grid.reloadData();
		
		grid.setVisible(true);
		setVisible(true);
	}

}
