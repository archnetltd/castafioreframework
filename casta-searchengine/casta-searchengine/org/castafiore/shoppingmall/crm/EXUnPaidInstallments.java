package org.castafiore.shoppingmall.crm;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.persistence.Dao;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.reports.ReportsUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.input.Decoder;
import org.castafiore.ui.input.Encoder;

public class EXUnPaidInstallments extends EXContainer implements StatefullComponent, Event{

	public Calendar start_ = null;
	
	public Calendar rstart_ = null;
	
	private OrderInfo order;
	
	
	
	public EXUnPaidInstallments(String name, OrderInfo order) {
		super(name, "div");
		
		setUsername(order);
		
	}
	
	public EXUnPaidInstallments(String name) {
		super(name, "div");
		
	}
	
	public void setUsername(OrderInfo order){
		//Order order = ReportsUtil.getOrder(username);
		if(order == null){
			throw new UIException("Cannot find order with the specified fscode");
		}
		getChildren().clear();
		setRendered(false);
		
		
		int totalInstallments = ReportsUtil.getTotalInstallments(order.getStartInstallmentDate());
		BigDecimal totalPayment = ReportsUtil.getTotalPaymentsMade(order.getFsCode());
		BigDecimal installment = order.getInstallment();
		if(installment == null){
			installment = BigDecimal.ZERO;
		}
		BigDecimal totalToPay = installment.multiply(new BigDecimal(totalInstallments));
		BigDecimal arrear = totalToPay.subtract(totalPayment);
		
		if(installment.doubleValue() == 0){
			installment = new BigDecimal(300);
		}
		
		if(arrear.doubleValue() >= 0){
			BigDecimal monthsNotPaid = BigDecimal.ZERO;
			double remainder = arrear.doubleValue()%installment.doubleValue();
			BigDecimal b = arrear.subtract(new BigDecimal(remainder));
			
			if(b.doubleValue() > 0){
				monthsNotPaid = b.divide(installment);
			}
			if(remainder > 0){
				monthsNotPaid = monthsNotPaid.add(new BigDecimal(1));
			}
			
			
			SimpleDateFormat format = new SimpleDateFormat("MMM yyyy");
			
			BigDecimal monthPaid = new BigDecimal(totalInstallments).subtract(monthsNotPaid);
			Calendar start = Calendar.getInstance();
			start.setTime(order.getDateOfTransaction());
			if(start.get(Calendar.DATE) >=15 && !(totalInstallments==0)){
				start.add(Calendar.MONTH, 1);
			}
			start.set(Calendar.DATE, 1);
			start.set(Calendar.HOUR, 1);
			start.set(Calendar.MINUTE, 1);
			start.set(Calendar.SECOND, 1);
			for(int i =1; i < monthPaid.doubleValue();i++){
				start.add(Calendar.MONTH, 1);
			}
			
			this.rstart_ = (Calendar)start.clone();
			rstart_.add(Calendar.MONTH, 1);
			Container table = new EXContainer("t", "table");
			Container tr = new EXContainer("", "tr");
			tr.addChild(new EXContainer("td", "td").setText(format.format(start.getTime()))).addChild(new EXContainer("td", "td").setText("Paid"));
			table.addChild(tr);
			for(int i=0; i < 15;i++){
				
			
				start.add(Calendar.MONTH, 1);
				if(start_ == null){
					start_ = (Calendar)start.clone();
				}
				Container tr1 = new EXContainer("", "tr");
				tr1.addChild(new EXContainer("td", "td").setText(format.format(start.getTime()))).addChild( new EXContainer("", "td").addChild(new EXCheckBox("cb").setAttribute("month", format.format(start.getTime())).addEvent(this, Event.CHANGE)));
				table.addChild(tr1);
			}
			addChild(table);
			
			
		}else{
			double remainder = totalPayment.doubleValue()%installment.doubleValue();
			BigDecimal b = totalPayment.subtract(new BigDecimal(remainder));
			BigDecimal monthPaid = BigDecimal.ZERO;
			if(b.doubleValue() > 0){
				monthPaid = b.divide(installment);
			}
			if(remainder > 0){
				monthPaid = monthPaid.add(new BigDecimal(1));
			}
			
			
			SimpleDateFormat format = new SimpleDateFormat("MMM yyyy");
			
			//BigDecimal monthPaid = new BigDecimal(totalInstallments).subtract(monthsNotPaid);
			
			Calendar start = Calendar.getInstance();
			start.setTime(order.getDateOfTransaction());
			if(start.get(Calendar.DATE) >=15){
				start.add(Calendar.MONTH, 1);
			}
			start.set(Calendar.DATE, 1);
			start.set(Calendar.HOUR, 1);
			start.set(Calendar.MINUTE, 1);
			start.set(Calendar.SECOND, 1);
			for(int i =1; i < monthPaid.doubleValue();i++){
				start.add(Calendar.MONTH, 1);
			}
			this.rstart_ = (Calendar)start.clone();
			rstart_.add(Calendar.MONTH, 1);
			Container table = new EXContainer("t", "table");
			Container tr = new EXContainer("", "tr");
			tr.addChild(new EXContainer("td", "td").setText(format.format(start.getTime()))).addChild(new EXContainer("td", "td").setText("Paid"));
			table.addChild(tr);
			for(int i=0; i < 15;i++){
				
				start.add(Calendar.MONTH, 1);
				if(start_ == null){
					start_ = start;
				}
				Container tr1 = new EXContainer("", "tr");
				tr1.addChild(new EXContainer("td", "td").setText(format.format(start.getTime()))).addChild( new EXContainer("", "td").addChild(new EXCheckBox("cb").setAttribute("month", format.format(start.getTime())).addEvent(this, Event.CHANGE)));
				table.addChild(tr1);
			}
			addChild(table);
			
		}
			
		
		
		setAttribute("code", order.getFsCode());
	}

	@Override
	public Decoder getDecoder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Encoder getEncoder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRawValue() {
		return "";
	}

	@Override
	public Object getValue() {
		return "";
	}

	@Override
	public void setDecoder(Decoder decoder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEncoder(Encoder encoder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRawValue(String rawValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		
	}
	
	public Date getLastDate()throws Exception{
		String last = null;
		for(Container c : getChild("t").getChildren()){
			EXCheckBox cb = c.getDescendentOfType(EXCheckBox.class);
			if(cb != null && cb.isChecked()){
				last = cb.getAttribute("month");
			}
			//if(c.getDescendentOfType(EXCheckBox.class) != null && c.getDescendentOfType(EXCheckBox.class))
		}
		
		
		if(last != null){
			return new SimpleDateFormat("dd MMM yyyy").parse("1 " + last);
		}
		return null;
	}
	
	

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String month = container.getAttribute("month");
		EXCheckBox cn = (EXCheckBox)container;
		if(cn.isChecked()){
			//check before
			//uncheck after
			boolean passed = false;
			for(Container tr :  getChild("t").getChildren()){
				if(tr.getDescendentOfType(EXCheckBox.class) != null){
					if(passed){
						tr.getDescendentOfType(EXCheckBox.class).setChecked(false);
					}else{
						if(month.equals(tr.getDescendentOfType(EXCheckBox.class).getAttribute("month"))){
							passed = true;
						}else{
							tr.getDescendentOfType(EXCheckBox.class).setChecked(true);
						}
					}
				}
			}
		}else{
			boolean passed = false;
			for(Container tr :  getChild("t").getChildren()){
				if(tr.getDescendentOfType(EXCheckBox.class) != null){
					
					if(!passed && month.equals(tr.getDescendentOfType(EXCheckBox.class).getAttribute("month"))){
						passed = true;
					}else{
						//tr.getDescendentOfType(EXCheckBox.class).setChecked(false);
					}
					
					if(passed){
						tr.getDescendentOfType(EXCheckBox.class).setChecked(false);
					}else{
						
					}
				}
			}
		}
		int total = 0;
		for(Container tr :  getChild("t").getChildren()){
			EXCheckBox cb = tr.getDescendentOfType(EXCheckBox.class);
			if(cb != null){
				
				if(cb.isChecked()){
					total = total +1;
				}
			}
		}
		
		String username = getAttribute("code");
		String sql = "select installment from WFS_FILE where dtype='Order' and code='"+username+"'";
		List result = SpringUtil.getBeanOfType(Dao.class).getSession().createSQLQuery(sql).list();
		BigDecimal installment = BigDecimal.ZERO; 
		if(result.size() >0){
			installment = (BigDecimal)result.get(0);
		}
		
		BigDecimal totalInstallment = installment.multiply(new BigDecimal(total));
		getAncestorOfType(EXDynaformPanel.class).getField("amount").setValue(totalInstallment.toPlainString());
	//	setRendered(false);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
