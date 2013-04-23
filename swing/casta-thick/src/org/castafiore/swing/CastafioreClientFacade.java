package org.castafiore.swing;

import org.castafiore.swing.options.OptionsFrame;
import org.castafiore.swing.payments.PaymentGridFrameController;
import org.castafiore.swing.sales.SalesFrame;
import org.openswing.swing.mdi.client.ClientFacade;
import org.openswing.swing.mdi.client.MDIFrame;


public class CastafioreClientFacade implements ClientFacade {
	
	
	public void getPayments(){
		new PaymentGridFrameController(null);
	}
	
	public void getSales(){
		
		MDIFrame.add(new SalesFrame());
	}
	
	
	public void getOptions(){
		MDIFrame.add(new OptionsFrame());
	}
	
}
