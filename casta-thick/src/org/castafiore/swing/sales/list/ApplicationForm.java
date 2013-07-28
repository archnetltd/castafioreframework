package org.castafiore.swing.sales.list;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.JPanel;

import org.castafiore.swing.sales.SaveContractDTO;
import org.openswing.swing.client.DateControl;
import org.openswing.swing.client.PropertyGridControl;
import org.openswing.swing.client.TextControl;

public class ApplicationForm extends JPanel{
	
	SaveContractDTO dto ;
	PropertyGridControl principalProperties;
	
	PropertyGridControl contactProperties;
	

	
	
	
	
	public ApplicationForm(SaveContractDTO dto) {
		super();
		this.dto = dto;
		jbInit();
	}

	public void jbInit(){
		
		//principal contact
		principalProperties = new PropertyGridControl();
		principalProperties.setPropertyValueWidth(225);
		addProperty(dto.getPrincipalFirstName(), "First Name",principalProperties);
		addProperty(dto.getPrincipalLastName(), "Last Name",principalProperties);
		addProperty(dto.getPrincipalNic(), "NIC",principalProperties);
		addProperty(dto.getPrincipalPhone(), "Phone",principalProperties);
		addProperty(dto.getPrincipalMobile(), "Mobile",principalProperties);
		addProperty(dto.getPrincipalEmail(), "Email",principalProperties);
		addProperty(dto.getPrincipalAddressLine1(), "Address Line 1",principalProperties);
		addProperty(dto.getPrincipalAddressLine2(), "Address Line 2",principalProperties);
		
		
		//contact person
		contactProperties = new PropertyGridControl();
		contactProperties.setPropertyValueWidth(225);
		addProperty(dto.getContactFirstName(), "First Name",contactProperties);
		addProperty(dto.getContactLastName(), "Last Name",contactProperties);
		addProperty(dto.getContactNic(), "NIC",contactProperties);
		addProperty(dto.getContactPhone(), "Phone",contactProperties);
		addProperty(dto.getContactMobile(), "Mobile",contactProperties);
		addProperty(dto.getContactEmail(), "Email",contactProperties);
		addProperty(dto.getContactAddressLine1(), "Address Line 1",contactProperties);
		addProperty(dto.getContactAddressLine2(), "Address Line 2",contactProperties);
		
	principalProperties.setPreferredSize(new Dimension(302, 187));
	contactProperties.setPreferredSize(new Dimension(302, 187));
		
		
	setLayout(new FlowLayout(FlowLayout.LEFT));
	
	add(contactProperties);
	add(principalProperties);
		
		
	}
	
	protected ApplicationForm addProperty( String value,String label, PropertyGridControl properties){
		properties.addProperty(label, new TextControl(), value, value);
		return this;
	}
	
	
	protected ApplicationForm addProperty( Date value,String label, PropertyGridControl properties){
		properties.addProperty(label, new DateControl(), value,value);
		
		return this;
	}
	
	
	
	
	
	

}
