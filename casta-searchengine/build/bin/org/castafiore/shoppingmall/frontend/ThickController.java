package org.castafiore.shoppingmall.frontend;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.inventory.orders.OrderService;
import org.castafiore.security.api.SecurityService;
import org.castafiore.shoppingmall.crm.PaymentDetail;
import org.castafiore.shoppingmall.crm.subscriptions.Payment;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.security.SecurityManager;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ThickController implements Controller{
	
	private final static ObjectMapper mapper = new ObjectMapper();

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase("fscodes")){
			List<FSCodeVO> codes = new OrderService().getFSCodes();
			
			//ObjectMapper mapper = new ObjectMapper();
			String s = mapper.writeValueAsString(codes);
			OutputStream out = response.getOutputStream();
			out.write(s.getBytes());
			out.flush();
			out.close();
			
		}else if(action.equalsIgnoreCase("findpayments")){
			String fsCode = request.getParameter("fs");
			int page = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			Payment ex = new Payment();
			ex.setAccountCode(fsCode);
			List<Payment> payments =  new OrderService().searchPaymentByExample(ex, page, pageSize);
			String s = mapper.writeValueAsString(payments);
			OutputStream out = response.getOutputStream();
			out.write(s.getBytes());
			out.flush();
			out.close();
			
			
		}else if(action.equalsIgnoreCase("inst")){
			String fs = request.getParameter("fs");
			
			BigDecimal val = new OrderService().getInstallment(fs);
			OutputStream out = response.getOutputStream();
			out.write(val.toString().getBytes());
			out.flush();
			out.close();
		}else if(action.equalsIgnoreCase("savepayment")){
			String accCode = request.getParameter("accCode");
			BigDecimal ttl = new BigDecimal(request.getParameter("ttl"));
			String paymentMethod = request.getParameter("paymentMethod");
			String chequeNo = request.getParameter("chequeNo");
			String pos = request.getParameter("pos");
			String description = request.getParameter("description");
			
			String code = new OrderService().savePayment(accCode, ttl, paymentMethod, chequeNo, pos, description);
			
			OutputStream out = response.getOutputStream();
			out.write(code.getBytes());
			out.flush();
			out.close();
			
			
		}else if(action.equalsIgnoreCase("getpdetails")){
			String fsCode = request.getParameter("fs");
			PaymentDetail detail = new OrderService().getPaymentDetail(fsCode);
			String s = mapper.writeValueAsString(detail);
			OutputStream out = response.getOutputStream();
			out.write(s.getBytes());
			out.flush();
			out.close();
		}else if(action.equalsIgnoreCase("auth")){
			String username = request.getParameter("username");
			String password = request.getParameter("pwd");
			
			String s =  SpringUtil.getBeanOfType(SecurityService.class).login(username, password) + "";
			OutputStream out = response.getOutputStream();
			out.write(s.getBytes());
			out.flush();
			out.close();
		}
		
		
		return null;
	}

}
