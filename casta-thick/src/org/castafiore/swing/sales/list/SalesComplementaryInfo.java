package org.castafiore.swing.sales.list;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.castafiore.swing.payments.Payment;
import org.castafiore.swing.payments.PaymentDetail;
import org.castafiore.swing.payments.PaymentService;
import org.castafiore.swing.sales.SaveContractDTO;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.table.columns.client.CurrencyColumn;
import org.openswing.swing.table.columns.client.DateColumn;
import org.openswing.swing.table.columns.client.TextColumn;
import org.openswing.swing.table.java.GridDataLocator;

public class SalesComplementaryInfo extends JPanel implements GridDataLocator{
	
	
	SaveContractDTO dto;
	
	JTable table = new JTable();
	
	public SalesComplementaryInfo(SaveContractDTO dto) {
		super();
		this.dto = dto;
		initJb();
		
		
	}


	public void initJb(){
		
		SimpleDateFormat formate = new SimpleDateFormat("dd-MMM-yyyy");
		
		TableColumn code = new TableColumn(0, 100,new DefaultTableCellRenderer(),null);
		code.setHeaderValue("Code");
		
		TableColumn date = new TableColumn(0, 100,new DefaultTableCellRenderer(),null);
		date.setHeaderValue("Date");
	
		TableColumn amount = new TableColumn(1, 100,new DefaultTableCellRenderer(),null);
		amount.setHeaderValue("Amount");
		
		TableColumn paymentMethod = new TableColumn(2,150,new DefaultTableCellRenderer(),null);
		paymentMethod.setHeaderValue("Payment method");
		
		table.getColumnModel().addColumn(code);
		table.getColumnModel().addColumn(date);
		table.getColumnModel().addColumn(amount);
		table.getColumnModel().addColumn(paymentMethod);
		
		
		
		PaymentService s = new PaymentService();
		List<Payment> payments = s.getPayments(dto.getFsCode());
		
		 DefaultTableModel model = new DefaultTableModel(0,4);
		 model.setColumnIdentifiers(new Object[]{"Code", "Date", "Amount", "Payment Method"});
		 for(Payment p : payments){
			 Object[] data = new Object[4];
			 data[0] = p.getCode();
			 data[1] =p.getDateCreated()!= null? formate.format(p.getDateCreated()) : "";
			 data[2] = p.getTotal();
			 data[3] = p.getPaymentMethod();
			 model.addRow(data);
		 }
		 
		 table.setModel(model);
		 table.getTableHeader().setVisible(true);
		 setLayout(new FlowLayout(FlowLayout.LEFT));
		 JScrollPane scr = new JScrollPane(table);
		 scr.setPreferredSize(new Dimension(400, 100));
		 scr.setBorder(new EmptyBorder(0, 0, 0, 0) );
		 add(scr);
		 
		 PaymentDetail detail = s.getPaymentDetail(dto.getFsCode());
		 
		 JPanel panel = new JPanel();
		 panel.setLayout(new GridLayout(3,2,5,5));
		 panel.add(new JLabel("Start Payment : "));
		 panel.add(new JLabel(formate.format(new Date( detail.getStartPaymentMonth()) )));
		 panel.add(new JLabel("Installments Paid : "));
		 panel.add(new JLabel(detail.getInstallmentsPaid() + ""));
		 panel.add(new JLabel("Arrear :"));
		 panel.add(new JLabel(detail.getArrear().toPlainString()));
		 
		 add(panel);
		
//		colAmount.setColumnFilterable(true);
//	    colAmount.setColumnName("amount");
//	    colAmount.setColumnSortable(true);
//	    colAmount.setDecimals(2);
//	    
//	    colDate.setColumnName("date");
//	    colDate.setColumnFilterable(true);
//	    colDate.setColumnSortable(true);
//	    
//	   
//	    
//	    colPaymentMethod.setColumnFilterable(true);
//	    colPaymentMethod.setColumnName("paymentMethod");
//	    colPaymentMethod.setColumnSortable(true);
//	    
//	    
//	    payments.getColumnContainer().add(colDate, null);
//	    payments.getColumnContainer().add(colAmount, null);
//	    payments.getColumnContainer().add(colPaymentMethod, null);
//	    payments.setValueObjectClassName(Payment.class.getName());
//	    payments.setGridDataLocator(this);
//	    
//	    payments.setHeaderHeight(0);
	   // add(payments);
	}


	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns,
			ArrayList currentSortedColumns,
			ArrayList currentSortedVersusColumns, Class valueObjectType,
			Map otherGridParams) {
		PaymentService s = new PaymentService();
		List<Payment> payments = s.getPayments(dto.getFsCode());
		
		return new VOListResponse(payments, false, payments.size());
		
	}

}
