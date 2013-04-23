package org.castafiore.swing.sales;

import java.awt.BorderLayout;

import org.openswing.swing.mdi.client.InternalFrame;
import org.openswing.swing.wizard.client.WizardPanel;

public class SalesFrame extends InternalFrame{
	
	
	private WizardPanel wizard;
	
	private CatalogueInnerPanel catalogue;
	
	private AppFormContact appForm;
	
	private DependentsForm dependants;
	
	
	public SalesFrame() {
		super();
		jbInit();
	}

	public void jbInit(){
		setLayout(new BorderLayout());
		wizard = new WizardPanel();
		
		catalogue = new CatalogueInnerPanel();
		appForm = new AppFormContact();
		dependants = new DependentsForm();
		
		wizard.addPanel(catalogue);
		wizard.addPanel(appForm);
		wizard.addPanel(dependants);
		setVisible(true);
		wizard.setVisible(true);
		
		setSize(500, 400);
		add(wizard, BorderLayout.CENTER);
	}

}
