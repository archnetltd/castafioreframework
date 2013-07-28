package org.castafiore.swing.sales;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.openswing.swing.client.ComboBoxControl;
import org.openswing.swing.client.DateControl;
import org.openswing.swing.client.LabelControl;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.domains.java.DomainPair;
import org.openswing.swing.form.client.Form;
import org.openswing.swing.form.model.client.ValueChangeEvent;
import org.openswing.swing.form.model.client.ValueChangeListener;
import org.openswing.swing.wizard.client.WizardInnerPanel;

public class CatalogueInnerPanel extends WizardInnerPanel implements ValueChangeListener,DataCollector{
	
	Form form = new Form();
	ComboBoxControl plans = new ComboBoxControl();
	TextControl invoice = new TextControl();
	DateControl date = new DateControl();
	ComboBoxControl agent = new ComboBoxControl();
	ComboBoxControl source = new ComboBoxControl();
	ComboBoxControl promotion = new ComboBoxControl();
	ComboBoxControl pointOfSales = new ComboBoxControl();
	JPanel formPanel = new JPanel();
	
	
	
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
	
	
	public CatalogueInnerPanel() {
		super();
		jbInit();
	}

	public String getPanelId() {
	    return getClass().getName();
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
		
		
		MigLayout la = new MigLayout();
		//la.setVgap(10);
		//la.setHgap(10);
		form.setLayout(la);
		form.setBorder(border);
		
		//form.add(new LabelControl(label),			new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		//form.add(control,	 						new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		form.add(new LabelControl("Plan"), "align right");//, 			new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(plans, "width 100:200:300");//, 							new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,new Insets(5, 5, 5, 5), 0, 0));
		//plans.setPreferredSize(new Dimension(200, 30));
		
		form.add(new LabelControl("Invoice"), "align right");//, 		new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(invoice, "wrap,width 100:200:300");//, 							new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		form.add(new LabelControl("Date"), "align right");//, 			new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(date, "width 100:200:300");//, 								new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		form.add(new LabelControl("POS"), "align right");//, 			new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(pointOfSales, "wrap,width 100:200:300");//, 						new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		
		form.add(new LabelControl("Agent"), "align right");//, 		new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(agent , "width 100:200:300");//, 							new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		form.add(new LabelControl("Source"), "align right");//, 		new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(source, "wrap,width 100:200:300");//, 							new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		form.add(new LabelControl("Promotion"), "align right");//, 	new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		form.add(promotion , "width 100:200:300");//, 						new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));
		
		add(form);
		
		formPanel.setLayout(new CardLayout(0, 0));
		add(formPanel);
		try{
			for(DomainPair s : plandom.getDomainPairList()){
				Form f = OptionsBuilder.buildForm(s.getCode().toString());
				//options.put(s.getCode().toString(), f);
				formPanel.add(f, s.getCode());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}

	@Override
	public void valueChanged(ValueChangeEvent e) {
		
		String value = plans.getValue().toString();
		
		CardLayout l =(CardLayout)formPanel.getLayout();
		l.show(formPanel, value);
	
	}
	
	
	

}
