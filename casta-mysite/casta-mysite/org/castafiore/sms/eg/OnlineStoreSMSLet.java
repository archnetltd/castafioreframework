package org.castafiore.sms.eg;

import java.io.IOException;
import java.io.Reader;

import org.castafiore.sms.SMSException;
import org.castafiore.sms.SMSLet;
import org.castafiore.sms.SMSRequest;
import org.castafiore.sms.SMSResponse;

public class OnlineStoreSMSLet extends SMSLet {

	@Override
	public void doService(SMSRequest request, SMSResponse response)
			throws SMSException, IOException {
		
		String body = readReader(request.getReader()).trim();
		
		String[] kv = body.split(":");
		if(kv != null && kv.length == 2){
			if(kv[0].equalsIgnoreCase("search")){
				response.setResponse(searchProducts(kv[1]));
			}else if(kv[0].equalsIgnoreCase("add")){
				request.getSession().put("cart", kv[1]);
				response.setResponse("Products added to cart");
			}else if(kv[0].equalsIgnoreCase("checkout")){
				response.setResponse(request.getSession().get("cart").toString());
			}
		}
	}
	
	
	public String searchProducts(String text){
		//dummy method to search products.
		//
		StringBuilder b = new StringBuilder();
		b.append("2 articles found").append("\n=============\n");
		b.append("23FY\n").append("White widow\n").append("250 MUR\n");
		b.append("------------------------------\n");
		b.append("14TN\n").append("Purple haze\n").append("225 MUR\n");
		b.append("------------------------------");
		return b.toString();
	}
	
	public String readReader(Reader reader)throws IOException{
		StringBuilder b = new StringBuilder();
		char[] buf = new char[1024];
		int numRead=0;
		while((numRead=reader.read(buf)) != -1){
		    String readData = String.valueOf(buf, 0, numRead);
		    b.append(readData);
		    buf = new char[1024];
		}
		return b.toString();
	}
}
