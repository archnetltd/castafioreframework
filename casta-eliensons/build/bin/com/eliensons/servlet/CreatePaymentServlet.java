package com.eliensons.servlet;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.crm.EXUnPaidInstallments;
import org.castafiore.shoppingmall.crm.subscriptions.PaymentsModel;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.orders.OrdersUtil;
import org.castafiore.shoppingmall.reports.ReportsUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;
import org.castafiore.web.servlet.AbstractCastafioreServlet;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

public class CreatePaymentServlet extends AbstractCastafioreServlet{

	@Override
	public void doService(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String action = request.getParameter("action");
		if("save".equals("action")){
			String fscode= request.getParameter("fsCode");
			String amount = request.getParameter("amount");
			String paymentMethod = request.getParameter("paymentMethod");
			String pos = request.getParameter("pos");
			String description = request.getParameter("description");
			OutputStream out = response.getOutputStream();
			
			String result =savePayment(fscode, Double.parseDouble(amount), pos,paymentMethod, description);
			out.write(result.getBytes());
			out.flush();
			out.close();
		}else{
			
		}
		
		
		
	}
	
	
	
	
	
private String  savePayment( String accCode, Double ttl, String pos,String paymentMethod, String description)throws UIException{
		
		try{
			
			
			String name = new Date().getTime() + "";
			
			
			
			
			
			String query = "insert into WFS_FILE (clazz, dateCreated, lastModified, parent_id, name,summary, title, code,total, accountCode, dateOfTransaction,absolutePath,pointOfSale,paymentMethod, DTYPE, size, status, commentable, dislikeit, likeit,ratable) values " +
					"" +
					"('org.castafiore.accounting.CashBookEntry',now(),now(), '/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook', ?,?, ?, ?, ?, ?, ?,? ,?,?,'CashBookEntry',1,1,true,1,1, true);";
			
			SpringUtil.getBeanOfType(Dao.class).getSession().createSQLQuery(query).setParameter(0, name)
			.setParameter(1, description).setParameter(2, description).setParameter(3, accCode)
			.setParameter(4, accCode).setParameter(5, ttl).setParameter(6, new Timestamp(System.currentTimeMillis()))
			.setParameter(7, "/root/users/"+Util.getLoggedOrganization()+"/Applications/e-Shop/elieandsons/DefaultCashBook/" + name).setParameter(8, pos).setParameter(9, paymentMethod).executeUpdate();
			
			return "Success";
			
			
			//update order
			
		}catch(Exception e){
			e.printStackTrace();
			return e.getMessage();
			//throw new UIException(e);
		}
		
		
	}

}
