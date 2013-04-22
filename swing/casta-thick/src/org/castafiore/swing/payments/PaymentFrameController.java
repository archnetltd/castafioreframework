package org.castafiore.swing.payments;

import org.castafiore.swing.orders.FSCodeVO;
import org.openswing.swing.form.client.FormController;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOResponse;
import org.openswing.swing.message.receive.java.ValueObject;

public class PaymentFrameController extends FormController{

	  private PaymentFrame frame = null;
	 
	public PaymentFrameController(FSCodeVO vo) {
		super();
		
		if(vo == null){
			throw new RuntimeException("Please select an fsCode first");
		}
		frame = new PaymentFrame(this,vo);
	}

	@Override
	public Response insertRecord(ValueObject newPersistentObject)
			throws Exception {
		
		
		
		Payment p = (Payment)newPersistentObject;
//		
//		Object o= new PaymentService().savePayement(p);
//		
		return new VOResponse(p);
		
	}

	
	
	

}
