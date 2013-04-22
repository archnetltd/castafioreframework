package org.castafiore.swing;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.openswing.swing.internationalization.java.Language;
import org.openswing.swing.mdi.client.ClientFacade;
import org.openswing.swing.mdi.client.Clock;
import org.openswing.swing.mdi.client.GenericStatusPanel;
import org.openswing.swing.mdi.client.MDIController;
import org.openswing.swing.mdi.client.MDIFrame;
import org.openswing.swing.mdi.java.ApplicationFunction;
import org.openswing.swing.permissions.client.LoginDialog;
import org.openswing.swing.tree.java.OpenSwingTreeNode;

public class CastafioreClientMDIController implements MDIController{
	
	private CastafioreClientFacade facade = new CastafioreClientFacade();

	@Override
	public void afterMDIcreation(MDIFrame frame) {
		GenericStatusPanel userPanel = new GenericStatusPanel();
	    userPanel.setColumns(12);
	    MDIFrame.addStatusComponent(userPanel);
	    userPanel.setText("Kureem Rossaye");
	    MDIFrame.addStatusComponent(new Clock());
		
	}

	@Override
	public String getMDIFrameTitle() {
		return "Enterprise Resource Planning Castafiore platform";
	}

	@Override
	public int getExtendedState() {
		return JFrame.MAXIMIZED_BOTH;
	}

	@Override
	public String getAboutText() {
		 return
			        "This is a copyright protected application\n"+
			        "\n"+
			        "Copyright: Copyright (C) 2013 Archnet Ltd\n"+
			        "Author: Kureem Rossaye";
	}

	@Override
	public String getAboutImage() {
		 return "about.jpg";
	}

	@Override
	public void stopApplication() {
		System.exit(0);
		
	}

	@Override
	public boolean viewChangeLanguageInMenuBar() {
		return true;
	}

	@Override
	public ArrayList getLanguages() {
		 ArrayList list = new ArrayList();
		    list.add(new Language("EN","English"));
		    list.add(new Language("FR","French"));
		    return list;
	}

	@Override
	public boolean viewLoginInMenuBar() {
		return true;
	}

	@Override
	public JDialog viewLoginDialog(JFrame parentFrame) {
		 JDialog d = new LoginDialog(parentFrame,true,new CastafioreLoginController());
		    return d;
	}

	@Override
	public boolean viewFunctionsInTreePanel() {
		return true;
	}

	@Override
	public boolean viewFunctionsInMenuBar() {
		return true;
	}

	@Override
	public ClientFacade getClientFacade() {
		return facade;
	}

	@Override
	public DefaultTreeModel getApplicationFunctions() {
		 DefaultMutableTreeNode root = new OpenSwingTreeNode();
		    DefaultTreeModel model = new DefaultTreeModel(root);
		    ApplicationFunction n1 = new ApplicationFunction("Payments","Payments",null, "getPayments");
		    ApplicationFunction n2 = new ApplicationFunction("Sales", "Sales",null,"getSales");
		    root.add(n1);
		    root.add(n2);
		    return model;
	}

	@Override
	public boolean viewOpenedWindowIcons() {
		return true;
	}

	@Override
	public boolean viewFileMenu() {
		return true;
	}

}
