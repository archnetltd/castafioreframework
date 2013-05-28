package org.castafiore.swing.sales;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.openswing.swing.client.ComboBoxControl;
import org.openswing.swing.client.DateControl;
import org.openswing.swing.client.LabelControl;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.form.client.Form;
import org.openswing.swing.form.model.client.ValueChangeEvent;
import org.openswing.swing.form.model.client.ValueChangeListener;
import org.openswing.swing.wizard.client.WizardInnerPanel;

public class CatalogueInnerPanel extends WizardInnerPanel implements ValueChangeListener, DataCollector{
	
	Form form = new Form();
	ComboBoxControl plans = new ComboBoxControl();
	TextControl invoice = new TextControl();
	DateControl date = new DateControl();
	ComboBoxControl agent = new ComboBoxControl();
	ComboBoxControl source = new ComboBoxControl();
	ComboBoxControl promotion = new ComboBoxControl();
	ComboBoxControl pointOfSales = new ComboBoxControl();
	Map<String,Form> options = new HashMap<String, Form>();
	JPanel formPanel = new JPanel();
	
	public CatalogueInnerPanel() {
		super();
		jbInit();
	}

	public String getPanelId() {
	    return getClass().getName();
	}
	
	
	@Override
	public void collect(SaveContractDTO dto) {
		dto.setPlan(this.plans.getValue().toString());
		dto.setFsCode(this.invoice.getText());
		dto.setDate(date.getDate());
		dto.setSalesAgent(agent.getValue().toString());
		dto.setSource(agent.getValue().toString());
		dto.setPromotion(promotion.getValue().toString());
		dto.setPointOfSale(pointOfSales.getValue().toString());
		
		Component[] comps = formPanel.getComponents();
		for(Component c : comps){
			if(c.getName().endsWith(dto.getPlan())){
				Form opts = (Form)c;
				//opts.get
			}
		}
		
	}

	public void jbInit(){
		setLayout(new GridLayout(2, 1));
		form.setLayout(new GridBagLayout());
		
		
		
		TitledBorder border = new TitledBorder("Contract Information");
		border.setTitleColor(Color.ORANGE);

		Domain plandom = new Domain("PLANS");
		plandom.addDomainPair("A", "Plan A");
		plandom.addDomainPair("B", "Plan B");
		plandom.addDomainPair("C", "Plan C");
		plandom.addDomainPair("H", "Plan Hindou");
		plans.setDomain(plandom);
		plans.addValueChangedListener(this);
		plans.setValue("A");
		
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
		
		add(form);
		
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
		
		
		
		formPanel.setLayout(new CardLayout(10, 10));
		add(formPanel);
		try{
			Form f = OptionsBuilder.buildForm("A");
			f.setName("A");
			options.put("A", f);
			formPanel.add(f);
			}catch(Exception e){
				e.printStackTrace();
			}
	}

	@Override
	public void valueChanged(ValueChangeEvent e) {
		
		String value = plans.getValue().toString();
		
		if(!options.containsKey(value)){
			try{
				Form f = OptionsBuilder.buildForm(value);
				formPanel.add(f);
			options.put(value, f);
			}catch(Exception ee){
				ee.printStackTrace();
				throw new RuntimeException(ee);
			}
		}
		
		if(options.containsKey(value)){
			Form f = options.get(value);
			f.setVisible(true);
			Iterator<String> it = options.keySet().iterator();
			while(it.hasNext()){
				options.get(it.next()).setVisible(false);
			}
			f.setVisible(true);
		}
		
		formPanel.validate();
	
	}

	
	
	
	

}
