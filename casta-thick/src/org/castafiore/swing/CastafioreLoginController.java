package org.castafiore.swing;

import java.util.Map;

import org.castafiore.swing.payments.PaymentService;
import org.openswing.swing.mdi.client.MDIFrame;
import org.openswing.swing.permissions.client.LoginController;


public class CastafioreLoginController implements LoginController{

	@Override
	public int getMaxAttempts() {
		return 3;
	}

	@Override
	public void stopApplication() {
		System.exit(0);
		
	}

	@Override
	public boolean authenticateUser(Map loginInfo) throws Exception {
		System.out.println(loginInfo.toString());
		boolean b  = new PaymentService().authenticate(loginInfo.get("username").toString(), loginInfo.get("password").toString());
		return b;
	}

	@Override
	public void loginSuccessful(Map loginInfo) {
		//new PaymentGridFrameController();
		new MDIFrame(new CastafioreClientMDIController());
		
	}

}
