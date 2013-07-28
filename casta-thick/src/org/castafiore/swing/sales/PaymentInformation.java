package org.castafiore.swing.sales;

import net.miginfocom.swing.MigLayout;

import org.openswing.swing.client.ComboBoxControl;
import org.openswing.swing.client.LabelControl;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.form.client.Form;
import org.openswing.swing.wizard.client.WizardInnerPanel;

public class PaymentInformation extends WizardInnerPanel {

	Form formPaymentOptions = new Form();
	TextControl paymentMethods = new TextControl();
	TextControl chequeNo = new TextControl();
	TextControl bankAccountNumber = new TextControl();
	TextControl bankName = new TextControl();
	
	public PaymentInformation() {
		super();
		jbInit();
	}
	
	public void jbInit(){
		formPaymentOptions.setLayout(new MigLayout());
		
		formPaymentOptions.add(new LabelControl("paymentMethod"), "align right");
		formPaymentOptions.add(paymentMethods, "wrap,width 200:250:400");
		
		formPaymentOptions.add(new LabelControl("chequeNo"), "align right");
		formPaymentOptions.add(chequeNo, "wrap,width 200:250:400");
		
		formPaymentOptions.add(new LabelControl("bankAccountNumber"), "align right");
		formPaymentOptions.add(bankAccountNumber, "wrap,width 200:250:400");
		
		formPaymentOptions.add(new LabelControl("bankName"), "align right");
		formPaymentOptions.add(bankName, "width 200:250:400");
		
		
		add(formPaymentOptions);
	}
	
	

}
