package org.castafiore.swing.sales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.openswing.swing.client.LabelControl;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.form.client.Form;
import org.openswing.swing.wizard.client.WizardInnerPanel;

public class AppFormContact extends WizardInnerPanel implements DataCollector{
	
	private Form contactForm = new Form();
	
	private Form principalForm = new Form();
	  

	TextControl fullName = new TextControl();
	TextControl surname = new TextControl();
	TextControl nic = new TextControl();
	TextControl email = new TextControl();
	TextControl phone = new TextControl();
	TextControl cell = new TextControl();
	TextControl addressLine1 = new TextControl();
	TextControl addressLine2 = new TextControl();
	
	
	TextControl pfullName = new TextControl();
	TextControl psurname = new TextControl();
	TextControl pnic = new TextControl();
	TextControl pemail = new TextControl();
	TextControl pphone = new TextControl();
	TextControl pcell = new TextControl();
	TextControl paddressLine1 = new TextControl();
	TextControl paddressLine2 = new TextControl();
	
	
	public AppFormContact() {
		super();
		jbInit();
	}

	public String getPanelId() {
	    return getClass().getName();
	  }
	public void jbInit(){
		setLayout(new BorderLayout());
		contactForm.setLayout(new MigLayout());
		
		TitledBorder border = new TitledBorder("Contact Person Information");
		border.setTitleColor(Color.ORANGE);
		
		principalForm.setLayout(new MigLayout());
		TitledBorder pborder = new TitledBorder("Contact Person Information");
		pborder.setTitleColor(Color.ORANGE);
		
		JPanel forms = new JPanel();
		forms.setLayout(new GridLayout(2, 1));
		
		forms.add(contactForm);
		forms.add(principalForm);
		add(forms, BorderLayout.CENTER);
		
		
		
		
		contactForm.setBorder(border);
		contactForm.add(new LabelControl("Full Names"), "align right");	//new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		contactForm.add(fullName,"width 100:200:300");// 							new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		contactForm.add(new LabelControl("Surname"), "align right");	//, 		new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		contactForm.add(surname , "wrap,width 100:200:300");//							new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		contactForm.add(new LabelControl("IC Number"), "align right");	//,		new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		contactForm.add(nic, "width 100:200:300");//								new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		contactForm.add(new LabelControl("Email"), "align right");	//, 			new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		contactForm.add(email, "wrap,width 100:200:300");//, 						new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		
		contactForm.add(new LabelControl("Phone"), "align right");	//, 		new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		contactForm.add(phone, 	"width 100:200:300");//						new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		contactForm.add(new LabelControl("Mobile"), "align right");	//, 		new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		contactForm.add(cell, "wrap,width 100:200:300");//, 								new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		contactForm.add(new LabelControl("Address Line 1"), "align right");	//, 	new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		contactForm.add(addressLine1,"width 100:200:300");// 							new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		contactForm.add(new LabelControl("Address Line 2"), "align right");	//, 	new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		contactForm.add(addressLine2, "wrap,width 100:200:300");//, 							new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		
		
		principalForm.setBorder(pborder);
		principalForm.add(new LabelControl("Full Names"), "align right");	//, 	new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		principalForm.add(pfullName, "width 100:200:300");//							new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		principalForm.add(new LabelControl("Surname"), "align right");	//, 		new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		principalForm.add(psurname, "wrap,width 100:200:300");//, 							new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		principalForm.add(new LabelControl("IC Number"), "align right");	//,		new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		principalForm.add(pnic, "width 100:200:300");//								new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		principalForm.add(new LabelControl("Email"), "align right");	//, 			new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		principalForm.add(pemail, "wrap,width 100:200:300");//, 						new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		
		principalForm.add(new LabelControl("Phone"), "align right");	//, 		new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		principalForm.add(pphone, "width 100:200:300");//							new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		principalForm.add(new LabelControl("Mobile"), "align right");	//, 		new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		principalForm.add(pcell, "wrap,width 100:200:300");//, 								new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		principalForm.add(new LabelControl("Address Line 1"), "align right");	//, 	new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		principalForm.add(paddressLine1, "width 100:200:300");//							new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		principalForm.add(new LabelControl("Address Line 2"), "align right");	//, 	new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		principalForm.add(paddressLine2, "wrap,width 100:200:300");//, 							new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
	}
	
	@Override
	public void collect(SaveContractDTO dto) {
		dto.setContactAddressLine1(this.addressLine1.getText());
		dto.setContactAddressLine2(addressLine2.getText());
		dto.setContactEmail(email.getText());
		dto.setContactFirstName(this.fullName.getText());
		dto.setContactLastName(surname.getText());
		dto.setContactMobile(cell.getText());
		dto.setContactNic(nic.getText());
		dto.setContactPhone(phone.getText());
		
		dto.setPrincipalAddressLine1(this.paddressLine1.getText());
		dto.setPrincipalAddressLine2(paddressLine2.getText());
		dto.setPrincipalEmail(pemail.getText());
		dto.setPrincipalFirstName(this.pfullName.getText());
		dto.setPrincipalLastName(psurname.getText());
		dto.setPrincipalMobile(pcell.getText());
		dto.setPrincipalNic(pnic.getText());
		dto.setPrincipalPhone(pphone.getText());

	}


}
