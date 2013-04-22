package org.castafiore.swing.sales;

import java.awt.BorderLayout;

import org.openswing.swing.mdi.client.InternalFrame;
import org.openswing.swing.wizard.client.WizardPanel;

public class SalesFrame extends InternalFrame{
	
	
	private WizardPanel wizard;
	
	public SalesFrame() {
		super();
		jbInit();
	}

	public void jbInit(){
		setLayout(new BorderLayout());
		wizard = new WizardPanel();
		
		wizard.addPanel(new CatalogueInnerPanel());
		wizard.addPanel(new AppFormContact());
		wizard.addPanel(new DependentsForm());
		setVisible(true);
		wizard.setVisible(true);
		
		setSize(500, 400);
		add(wizard, BorderLayout.CENTER);
	}

}
