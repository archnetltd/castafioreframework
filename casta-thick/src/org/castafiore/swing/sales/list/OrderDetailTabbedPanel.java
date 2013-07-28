package org.castafiore.swing.sales.list;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JTabbedPane;

import org.castafiore.swing.sales.DependantVO;
import org.castafiore.swing.sales.DependentsGridController;
import org.castafiore.swing.sales.SaveContractDTO;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;

public class OrderDetailTabbedPanel extends JTabbedPane{
	
	private SaveContractDTO dto;
	
	TextColumn serial= new TextColumn();
	TextColumn name = new TextColumn();
	TextColumn nic = new TextColumn();
	ComboColumn gender = new ComboColumn();
	
	GridControl grid = new GridControl();
	
	public OrderDetailTabbedPanel(SaveContractDTO dto) {
		super();
		this.dto = dto;
		setPreferredSize(new Dimension(600,220));
		jbInit();
	}



	public void jbInit(){
		super.addTab("Application", new ApplicationForm(dto));
		
		
		//dependants
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
				DependentsGridController  c = new DependentsGridController(dto);
				grid.setController(c);
				grid.setGridDataLocator(c);
				grid.setValueObjectClassName(DependantVO.class.getName());
				
				grid.setVisible(true);
				grid.reloadData();
				
				addTab("Dependants",grid);
				
				super.addTab("Payment Info", new SalesComplementaryInfo(dto));
	}
	

}
