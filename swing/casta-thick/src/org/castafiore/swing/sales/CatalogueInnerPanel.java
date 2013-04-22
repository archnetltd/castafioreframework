package org.castafiore.swing.sales;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.border.TitledBorder;

import org.openswing.swing.client.ComboBoxControl;
import org.openswing.swing.client.DateControl;
import org.openswing.swing.client.LabelControl;
import org.openswing.swing.client.PropertyGridControl;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.form.client.Form;
import org.openswing.swing.wizard.client.WizardInnerPanel;

public class CatalogueInnerPanel extends WizardInnerPanel{
	
	Form form = new Form();
	ComboBoxControl plans = new ComboBoxControl();
	TextControl invoice = new TextControl();
	DateControl date = new DateControl();
	ComboBoxControl agent = new ComboBoxControl();
	ComboBoxControl source = new ComboBoxControl();
	ComboBoxControl promotion = new ComboBoxControl();
	ComboBoxControl pointOfSales = new ComboBoxControl();
	
	
	public CatalogueInnerPanel() {
		super();
		jbInit();
	}

	public String getPanelId() {
	    return getClass().getName();
	  }

	public void jbInit(){
		setLayout(new BorderLayout());
		form.setLayout(new GridBagLayout());
		add(form, BorderLayout.CENTER);
		//form.setSize(new Dimension(500, 400));
		TitledBorder border = new TitledBorder("Contract Information");
		border.setTitleColor(Color.ORANGE);

		Domain plandom = new Domain("PLANS");
		plandom.addDomainPair("A", "Plan A");
		plandom.addDomainPair("B", "Plan B");
		plandom.addDomainPair("C", "Plan C");
		plandom.addDomainPair("H", "Plan Hindou");
		plans.setDomain(plandom);
		
		Domain posdom = new Domain("POS");
		posdom.addDomainPair("Roche Brunes", "Roches Brunes");
		pointOfSales.setDomain(posdom);
		
		
		Domain sourcedom = new Domain("SOURCE");
		sourcedom.addDomainPair("Web", "Web");
		sourcedom.addDomainPair("Radio", "Radio");
		sourcedom.addDomainPair("Mouth to ear", "Mouth to ear");
		sourcedom.addDomainPair("Tv", "Tv");
		sourcedom.addDomainPair("Other", "Other");
		source.setDomain(sourcedom);
		
		Domain promdom = new Domain("PROMOTION");
		promdom.addDomainPair("No", "No Promotion");
		promdom.addDomainPair("3Months", "3 Months Promotion");
		promotion.setDomain(promdom);
		
		
		
		form.setBorder(border);
		form.add(new LabelControl("Plan"), 			new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(plans, 							new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		form.add(new LabelControl("Invoice"), 		new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(invoice, 							new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		form.add(new LabelControl("Date"), 			new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(date, 								new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		form.add(new LabelControl("POS"), 			new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(pointOfSales, 						new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		
		form.add(new LabelControl("Agent"), 		new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(agent, 							new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		form.add(new LabelControl("Source"), 		new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(source, 							new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		form.add(new LabelControl("Promotion"), 	new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(promotion, 						new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
	}
	
	
	

}
