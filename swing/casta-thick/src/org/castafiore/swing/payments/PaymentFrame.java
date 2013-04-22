package org.castafiore.swing.payments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.castafiore.swing.orders.FSCodeVO;
import org.openswing.swing.client.ComboBoxControl;
import org.openswing.swing.client.CurrencyControl;
import org.openswing.swing.client.DateControl;
import org.openswing.swing.client.LabelControl;
import org.openswing.swing.client.TextAreaControl;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.form.client.Form;
import org.openswing.swing.form.client.FormController;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.util.client.ClientUtils;

/**
 * <p>
 * Title: OpenSwing Framework
 * </p>
 * <p>
 * Description: Demo Application: detail frame containing several input
 * controls.
 * </p>
 * <p>
 * Copyright: Copyright (C) 2006 Mauro Carniel
 * </p>
 * <p>
 * </p>
 * 
 * @author Mauro Carniel
 * @version 1.0
 */
public class PaymentFrame extends JFrame implements ChangeListener, ActionListener {

	JPanel toolbar = new JPanel();

	GridBagLayout gridBagLayout1 = new GridBagLayout();

	LabelControl lblCode = new LabelControl();
	TextControl fsCode = new TextControl();
	TextControl lblCustomer = new TextControl();
	
	LabelControl lblInstallment = new LabelControl("Installment");
	CurrencyControl txtInstallment = new CurrencyControl();
	LabelControl lblMonths = new LabelControl("Months to pay");

	LabelControl lblDate = new LabelControl();
	DateControl date = new DateControl();

	LabelControl lblPaymentMethods = new LabelControl();
	ComboBoxControl paymentMethods = new ComboBoxControl();

	LabelControl lblAmount = new LabelControl();
	CurrencyControl amount = new CurrencyControl();

	LabelControl lblChequeNo = new LabelControl();
	TextControl chequeNo = new TextControl();

	LabelControl lblPos = new LabelControl();
	ComboBoxControl pos = new ComboBoxControl();
	

	 TextAreaControl description = new TextAreaControl();
	
	JButton saveButton = new JButton(new ImageIcon(ClientUtils.getImage(ClientSettings.BUTTON_SAVE_IMAGE_NAME)));


	
	JSlider slider = new JSlider(0, 20);

	private Form mainPanel = new Form();
	FlowLayout flowLayout1 = new FlowLayout();
	
	private FSCodeVO code;
	
	private PaymentDetail detail;

	public PaymentFrame(FormController dataController, FSCodeVO vo) {
		try {
			this.code = vo;
			
			detail = new PaymentService().getPaymentDetail(code.getFsCode());

			jbInit();

			mainPanel.setFormController(dataController);

			setSize(375, 600);
			//pack();
			setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		
		mainPanel.setVOClassName(Payment.class.getName());
		mainPanel.setLayout(gridBagLayout1);

		lblCode.setText("FS Code");
		lblDate.setText("Date");
		lblPaymentMethods.setText("combobox");
		lblAmount.setText("Amount");
		lblChequeNo.setText("Cheque No");
		lblPos.setText("Pos");

		fsCode.setRequired(true);
		fsCode.setLinkLabel(lblCode);
		fsCode.setAttributeName("code");
		fsCode.setText(code.getFsCode());
		fsCode.setEnabled(false);
		fsCode.setFocusable(false);
		fsCode.setEnabledOnEdit(false);
		fsCode.setEnabledOnInsert(false);
		
		txtInstallment.setText(detail.getInstallment().toPlainString());
		txtInstallment.setEnabled(false);
		txtInstallment.setEnabledOnInsert(false);
		txtInstallment.setEnabledOnEdit(false);
		txtInstallment.setFocusable(false);

		lblCustomer.setCanCopy(false);
		lblCustomer.setEnabledOnEdit(false);
		lblCustomer.setEnabledOnInsert(false);
		lblCustomer.setFocusable(false);
		lblCustomer.setText(code.getCustomer());
		lblCustomer.setEnabled(false);

		date.setRequired(true);
		date.setLinkLabel(lblDate);
		date.setAttributeName("dateOfTransaction");
		date.setValue(new Date());

		amount.setLinkLabel(lblAmount);
		amount.setDecimals(2);
		amount.setMinValue(0);
		amount.setRequired(true);
		amount.setAttributeName("total");
		amount.setValue(this.detail.getInstallment().doubleValue());

		paymentMethods.setLinkLabel(lblPaymentMethods);
		Domain methods = new Domain("PAYMENTMETHODS");
		methods.addDomainPair("Cash", "Cash");
		methods.addDomainPair("Cheque", "Cheque");
		methods.addDomainPair("Bank Transfer", "Bank Transfer");
		methods.addDomainPair("Standing Order", "Standing Order");
		paymentMethods.setDomain(methods);
		paymentMethods.setRequired(true);
		paymentMethods.setAttributeName("paymentMethod");

		chequeNo.setLinkLabel(lblChequeNo);
		chequeNo.setAttributeName("chequeNo");

		Domain poss = new Domain("POSS");
		poss.addDomainPair("Curepipe", "Curepipe");
		poss.addDomainPair("Roche Brunes", "Roche Brunes");
		poss.addDomainPair("Mahebourg", "Mahebourg");
		poss.addDomainPair("Beau Bassin", "Beau Bassin");
		pos.setRequired(true);
		pos.setDomain(poss);
		pos.setLinkLabel(lblPos);
		pos.setAttributeName("pointOfSale");
		
		description.setAttributeName("description");

		toolbar.setLayout(flowLayout1);
		flowLayout1.setAlignment(FlowLayout.LEFT);
		
		
		slider = new JSlider(1, 12);
		
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setValue(1);
		slider.setPreferredSize(new Dimension(250, 50));
		slider.addChangeListener(this);
		
		Hashtable<Integer, Component> dict = new Hashtable<Integer, Component>();
		for(int i =1;i<=12;i++){
			dict.put(i, new JLabel(i+""));
		}
		slider.setLabelTable(dict);

		this.getContentPane().add(toolbar, BorderLayout.NORTH);

		saveButton.addActionListener(this);

		toolbar.add(saveButton, null);

		this.getContentPane().add(mainPanel, BorderLayout.CENTER);

		// date
		TitledBorder border = new TitledBorder("Payment information");
		border.setTitleColor(Color.ORANGE);

		mainPanel.setBorder(border);
		mainPanel.add(lblDate, 			new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(date, 			new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));

		// fscode
		mainPanel.add(lblCode, 			new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,	new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(fsCode, 			new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,	new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(lblCustomer, 		new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		
		mainPanel.add(lblMonths,		new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(slider, 			new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		
		mainPanel.add(lblInstallment,	new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(txtInstallment,	new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));

		// amount
		mainPanel.add(lblAmount, 		new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,	new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(amount, 			new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));

		// payment Method
		mainPanel.add(lblPaymentMethods,new GridBagConstraints(0, 6, 1, 1,0.0, 0.0, GridBagConstraints.NORTHWEST,GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(paymentMethods, 	new GridBagConstraints(1, 6, 1, 1, 0.0,0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));

		// cheque no
		mainPanel.add(lblChequeNo, 		new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(chequeNo,    		new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,new Insets(5, 5, 5, 5), 0, 0));

		// pos
		mainPanel.add(lblPos, 			new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(pos, 				new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,new Insets(5, 5, 5, 5), 0, 0));
		
		mainPanel.add(description,   	new GridBagConstraints(0, 9, 2, 1, 1.0, 1.0,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		stateChanged(null);
	
	}

	public Form getMainPanel() {
		return mainPanel;
	}

	

	@Override
	public void stateChanged(ChangeEvent e) {
		amount.setValue(((Integer)slider.getValue()) * detail.getInstallment().intValue());
		genDescription();
	}
	
	private void genDescription(){
	
		int months = slider.getValue();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(detail.getStartPaymentMonth());
		cal.add(Calendar.MONTH, months-1);
		
		
		
		String next = new SimpleDateFormat("MMM/yyyy").format(cal.getTime());
		
		
		String description = "Payment of installment for " + detail.getFsCode()  +"\n up to " + new SimpleDateFormat("dd/MMM/yyyy").format(cal.getTime()) + "\n next installment for " + next;
		this.description.setText(description);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//
		
		String accCode = detail.getFsCode();
		BigDecimal ttl = new BigDecimal( amount.getDouble());
		String paymentMethod = paymentMethods.getValue().toString();
		String chequeNo = this.chequeNo.getText();
		String pos = this.pos.getValue().toString();
		String description = this.description.getValue().toString();
		
		new PaymentService().savePayement(accCode, ttl, paymentMethod, chequeNo, pos, description);
		
//		try{
//		JEditorPane ed = new JEditorPane();
//		this.description.setVisible(false);
//		mainPanel.add(ed,   	new GridBagConstraints(0, 9, 2, 1, 1.0, 1.0,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
//		
//		ed.setContentType("text/html");
//		String txt = PaymentService.readUrl("http://localhost:8080/casta-ui/contract.jsp?0=0&total=200.00%20MUR&member2=&member3=&paymentMode=Cash&member4=&phone=495-3996/&member5=&member6=&sn2=2&sn1=1&sn6=6&sn5=5&sn4=4&effDate=20/Oct/2013&sn3=3&inst=0.00%20MUR&date=20/Apr/2013&sname=%20&fsNumber=A&member1=Georgy%20Letendrine%20JACQUES&name=Georgy%20Letendrine%20JACQUES&dob3=&dob4=&dob5=&dob6=&cphone=495-3996/&caddress=Cite%20Brostal%20Leovillome%2030%20%20&dob1=L110244010507G%20&dob2=&firstInstallment=20/May/2013&salesman=elieandsons&jFee=0.00%20MUR&plan=dfgdfgdf&email=&address=Cite%20Brostal%20Leovillome%2030%20%20&dob=L110244010507G%20&cname=JACQUES%20Georgy%20Letendrine");
//		System.out.println(txt);
//		ed.setText(txt);
//	
//		}catch(Exception ee){
//			ee.printStackTrace();
//		}
		
	}

}
