package org.castafiore.swing.sales;

import java.awt.BorderLayout;

import org.openswing.swing.client.GridControl;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;
import org.openswing.swing.util.java.Consts;
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



	@Override
	public void init() {
		super.init();
		grid.setMode(Consts.EDIT);
	}



	public void jbInit(){
		Domain genders = new Domain("GENDERS");
		genders.addDomainPair("Male", "Male");
		genders.addDomainPair("Female", "Female");
		gender.setDomain(genders);
		gender.setMinWidth(70);
		gender.setMaxWidth(70);
		gender.setColumnName("gender");
		gender.setEditableOnEdit(true);
		
		name.setEditableOnEdit(true);
		name.setColumnName("name");
		name.setMinWidth(260);
		serial.setColumnName("id");
		serial.setAutoFitColumn(true);
		nic.setColumnName("nic");
		nic.setEditableOnEdit(true);
		nic.setVisible(true);
		nic.setMinWidth(110);
		
		grid.getColumnContainer().add(serial, null);
		grid.getColumnContainer().add(name,null);
		grid.getColumnContainer().add(nic,null);
		grid.getColumnContainer().add(gender,null);
		DependentsGridController  c = new DependentsGridController();
		grid.setController(c);
		grid.setGridDataLocator(c);
		grid.setValueObjectClassName(DependantVO.class.getName());
		
		
		
		setLayout(new BorderLayout());
		add(grid, BorderLayout.CENTER);
		
		
		
		grid.setVisible(true);
		grid.reloadData();
		setVisible(true);
	}

}
