package org.castafiore.swing.payments;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import org.castafiore.swing.orders.FSCodeLookupController;
import org.castafiore.swing.orders.FSCodeVO;
import org.openswing.swing.client.CodLookupControl;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.InsertButton;
import org.openswing.swing.client.LabelControl;
import org.openswing.swing.client.ReloadButton;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.form.client.Form;
import org.openswing.swing.mdi.client.InternalFrame;
import org.openswing.swing.table.columns.client.CurrencyColumn;
import org.openswing.swing.table.columns.client.DateColumn;
import org.openswing.swing.table.columns.client.TextColumn;

/**
 * @author Kureem Rossaye
 * @version 1.0
 */
public class PaymentGridFrame extends InternalFrame {

	private static final long serialVersionUID = 8012457819602595895L;
	GridControl payments = new GridControl();
	JPanel buttonsPanel = new JPanel();
	ReloadButton reloadButton = new ReloadButton();
	InsertButton insertButton = new InsertButton();
	FlowLayout flowLayout1 = new FlowLayout();
	DateColumn colDate = new DateColumn();
	TextColumn colFsCode = new TextColumn();
	CurrencyColumn colAmount = new CurrencyColumn();
	TextColumn colPos = new TextColumn();
	TextColumn colPaymentMethod = new TextColumn();
	CodLookupControl fsCode = new CodLookupControl();
	private Form form = new Form();

	private PaymentGridFrameController controller;
	
	private FSCodeVO vo;
	

	public PaymentGridFrame(PaymentGridFrameController controller) {
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.controller = controller;
		try {
			jbInit();
			setSize(900, 675);
			payments.setController(controller);
			payments.setGridDataLocator(controller);
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadData() {
		payments.reloadData();
	}

	

	public FSCodeVO getVo() {
		return vo;
	}

	public void setVo(FSCodeVO vo) {
		this.vo = vo;
	}

	private void jbInit() throws Exception {
		buttonsPanel.setLayout(flowLayout1);
		flowLayout1.setAlignment(FlowLayout.LEFT);
		//payments.setInsertButton(insertButton);
		payments.setFunctionId("F1");
		payments.setReloadButton(reloadButton);
		payments.setValueObjectClassName(Payment.class.getName());

		colAmount.setColumnFilterable(true);
		colAmount.setColumnName("total");
		colAmount.setColumnSortable(true);
		colAmount.setDecimals(2);

		colDate.setColumnName("dateCreated");
		colDate.setColumnFilterable(true);
		colDate.setColumnSortable(true);

		colFsCode.setColumnFilterable(true);
		colFsCode.setColumnName("code");
		colFsCode.setColumnSortable(true);

		colPos.setColumnFilterable(true);
		colPos.setColumnName("pointOfSale");
		colPos.setColumnSortable(true);

		colPaymentMethod.setColumnFilterable(true);
		colPaymentMethod.setColumnName("paymentMethod");
		colPaymentMethod.setColumnSortable(true);

		insertButton.setText("insertButton1");
		insertButton.addActionListener(new GridFrame_insertButton_actionAdapter(this));

		fsCode.setLookupController(new FSCodeLookupController(controller));

		this.getContentPane().add(payments, BorderLayout.CENTER);
		this.getContentPane().add(buttonsPanel, BorderLayout.NORTH);
		buttonsPanel.add(insertButton, null);
		buttonsPanel.add(reloadButton, null);
		buttonsPanel.add(form, null);
		LabelControl lblSearch = new LabelControl("Search FS Code : ");
		form.add(lblSearch);
		form.add(fsCode);

		TextControl fullName = new TextControl();
		fullName.setAttributeName("customer");
		fullName.setPreferredSize(new Dimension(250, 35));

		form.setLayout(new FlowLayout());
		form.setVOClassName(FSCodeVO.class.getName());
		fsCode.setAttributeName("fsCode");
		form.add(fullName);
		payments.getColumnContainer().add(colDate, null);
		payments.getColumnContainer().add(colFsCode, null);
		payments.getColumnContainer().add(colAmount, null);
		payments.getColumnContainer().add(colPaymentMethod, null);
		payments.getColumnContainer().add(colPos, null);
	}

	void insertButton_actionPerformed(ActionEvent e) {
		new PaymentFrameController(vo);
	}
}




class GridFrame_insertButton_actionAdapter implements java.awt.event.ActionListener {
	PaymentGridFrame adaptee;

	GridFrame_insertButton_actionAdapter(PaymentGridFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.insertButton_actionPerformed(e);
	}
}
