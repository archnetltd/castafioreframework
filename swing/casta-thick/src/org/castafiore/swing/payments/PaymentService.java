package org.castafiore.swing.payments;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.swing.orders.FSCodeVO;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentService {

	private final static ObjectMapper mapper = new ObjectMapper();
	
	private final static String ENDPOINT = "http://68.68.109.26/elie";

	public List<Payment> getPayments(String FSCode) {

		if(FSCode == null || FSCode.length() <=0){
			return new ArrayList<Payment>();
		}
		try {
			String data = readUrl(ENDPOINT + "/castafiore/methods?controller=orderscontroller&action=findpayments&page=0&pageSize=100&fs=" + FSCode);

			Payment[] da = mapper.readValue(data.getBytes("UTF-8"),
					Payment[].class);
			List<Payment> result = new ArrayList<Payment>();
			for (Payment d : da) {
				result.add(d);
			}
			return result;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
//	public BigDecimal getInstallment(String fsCode){
//		try {
//			String data = readUrl("http://localhost:8080/casta-ui/castafiore/methods?controller=orderscontroller&action=inst&fs=" + fsCode);
//
//			return new BigDecimal(data.replace("\n", ""));
//
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
	
	
	
	public boolean authenticate(String username, String password){
		try {
			String data = readUrl(ENDPOINT + "/castafiore/methods?controller=orderscontroller&action=auth&username=" + username + "&pwd=" + password);

			return new Boolean(data.replace("\n", ""));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public PaymentDetail getPaymentDetail(String fsCode){
		try {
			String data = readUrl(ENDPOINT + "/castafiore/methods?controller=orderscontroller&action=getpdetails&fs=" + fsCode);

			PaymentDetail pd = mapper.readValue(data.getBytes("UTF-8"), PaymentDetail.class);
			
			return pd;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String readUrl(String url) throws Exception {
		URL yahoo = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yahoo.openStream()));

		String inputLine;
		StringBuilder b = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			b.append(inputLine).append("\n");
		// System.out.println(inputLine);

		in.close();

		return b.toString();

	}

	public List<FSCodeVO> getFSCodes() {
		try {
			String data = readUrl(ENDPOINT + "/castafiore/methods?controller=orderscontroller&action=fscodes");

			FSCodeVO[] da = mapper.readValue(data.getBytes("UTF-8"),
					FSCodeVO[].class);
			List<FSCodeVO> result = new ArrayList<FSCodeVO>();
			for (FSCodeVO d : da) {
				result.add(d);
			}
			return result;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object savePayement(String accCode, BigDecimal ttl,String paymentMethod,String chequeNo,String pos, String description) {
		try{
		String url = ENDPOINT + "/castafiore/methods?controller=orderscontroller&action=savepayment";
		StringBuilder b = new StringBuilder();
		b.append(url);
		b.append("&accCode=").append(URLEncoder.encode(accCode));
		b.append("&ttl=").append(URLEncoder.encode(ttl.toPlainString()));
		b.append("&paymentMethod=").append(URLEncoder.encode(paymentMethod));
		b.append("&chequeNo=").append(URLEncoder.encode(chequeNo));
		b.append("&pos=").append(URLEncoder.encode(pos));
		b.append("&description=").append(URLEncoder.encode(description, "UTF-8"));
		
		String data = readUrl(b.toString());
		return data.replace("\n", "");
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
