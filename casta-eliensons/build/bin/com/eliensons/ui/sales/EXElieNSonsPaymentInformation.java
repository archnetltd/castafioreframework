package com.eliensons.ui.sales;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.checkout.EXCheckout;
import org.castafiore.designable.checkout.EXPaymentInformation;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.JavascriptUtil;
import org.castafiore.utils.StringUtil;

import com.eliensons.ui.plans.EXElieNSonsApplicationForm;

public class EXElieNSonsPaymentInformation extends EXPaymentInformation{

	public EXElieNSonsPaymentInformation(String name) {
		super(name);
		setTemplateLocation("templates/EXElieNSonsPaymentInformation.xhtml");
		
		addChild(new EXSelect("firstPaymentMethod",new DefaultDataModel<Object>().addItem("Cash", "Cheque", "Bank Transfer")));
		
		
		addChild(new EXInput("bankName"));
		
	}
	
	
	
	
	protected String getContractUrl(){
		
		
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		EXElieNSonsApplicationForm bi = co.getDescendentOfType(EXElieNSonsApplicationForm.class);

		EXElieNSonsMiniCart cart = (EXElieNSonsMiniCart)getRoot().getDescendentOfType(EXMiniCart.class);

		EXSelect agent = (EXSelect)cart.getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("agent");
	 	User uAgent = (User)agent.getValue();		
		String icode = ((EXInput)cart.getAncestorOfType(EXElieNSonsSales.class).getDescendentByName("invoiceNumber")).getValue().toString();
		String pt =((EXSelect)getChild("paymentMethod")).getValue().toString();
		Map<String,String> res = new HashMap<String, String>();

		
		res.put("cname", bi.getValue("cFullName") + " " + bi.getValue("cSurname"));
		res.put("caddress", bi.getValue("cresi") + " "  + bi.getValue("cresi2"));
		res.put("cphone", bi.getValue("telHome") + "/" + bi.getValue("cell"));
		res.put("email", bi.getValue("cemail"));
		res.put("fsNumber",icode );
		res.put("date", new SimpleDateFormat("dd/MMM/yyyy").format(new Date()));
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 6);
		Date effDate = cal.getTime();
		res.put("effDate",  new SimpleDateFormat("dd/MMM/yyyy").format(effDate));
		
		res.put("salesman", uAgent.getUsername());
		res.put("plan", bi.getValue("plan"));
		res.put("jFee", StringUtil.toCurrency("MUR", cart.getJoiningFee()));
		res.put("inst", StringUtil.toCurrency("MUR", cart.getInstallment()));
		res.put("total", StringUtil.toCurrency("MUR", cart.getTotal()));
		res.put("paymentMode",pt);
		
		cal.add(Calendar.MONTH, -5);
		res.put("firstInstallment", new SimpleDateFormat("dd/MMM/yyyy").format(cal.getTime()));
		
		
		if(StringUtil.isNotEmpty(bi.getValue("pidnumber"))){
			res.put("dob", bi.getValue("pidnumber"));
		}
		//res.put("cname", f.get("cfullName") + " " + f.get("cSurname"));
		res.put("address", bi.getValue("presi") + " " + bi.getValue("presi2"));
		res.put("phone", bi.getValue("ptelhome")+ "/" + bi.getValue("pcell"));
		res.put("email", bi.getValue("email"));
		res.put("name", bi.getValue("pfullName") + " " + bi.getValue("pSurname"));
		
		res.put("sname", bi.getValue("sfullName"));
		
		List<Member> members = new ArrayList<Member>();
		
		Member principal = new Member();
		principal.name = res.get("name");
		principal.id = res.get("dob");
		members.add(principal);
		
		if(StringUtil.isNotEmpty(bi.getValue("sfullName"))){
			Member spouse = new Member();
			spouse.name = bi.getValue("sfullName") ;
			spouse.id = bi.getValue("sidnumber");
			members.add(spouse);
		}
		
		for(int i =1; i <=6; i++){
			Member dep = new Member();
			if( StringUtil.isNotEmpty(bi.getValue("c"+i+"name"))){
				dep.name = bi.getValue("c"+i+"name");
				
				dep.id =  bi.getValue("c"+i+"1");
				members.add(dep);
			}else{
				break;
			}
			
		}
		
		
		int count = 1;
		for(Member dep : members){
			res.put("member" + count, dep.name);
			res.put("dob" + count, dep.id);
			res.put("sn" + count, count + "");
			count++;
		}
		
		for(int i=count;i<=6;i++){
			res.put("member" + i, "");
			res.put("dob" + i, "");
			res.put("sn" + i, i + "");
		}
		
		
		
		Iterator<String> iter = res.keySet().iterator();
		StringBuilder b = new StringBuilder();
		b.append("contract.jsp?0=0");
		while(iter.hasNext()){
			String key = iter.next();
			String val = res.get(key);
			b.append("&").append(key).append("=").append(val);
			
		}
		return b.toString();
	}
	
	public static class Member{
		
		
		public String name;
		
		public String id;
		
		
		
	}
	public boolean validate(){
		if(((EXSelect)getChild("paymentMethod")).getValue().equals("Bank Transfer") || ((EXSelect)getChild("firstPaymentMethod")).getValue().equals("Bank Transfer")){
			String cn = getDescendentOfType(EXInput.class).getValue().toString();
			if(!StringUtil.isNotEmpty(cn)){
				getDescendentOfType(EXInput.class).addClass("ui-state-error");
				return false;
			}
		}
		return true;
	}
	
	
	
	
	public void fillOrder(Order order){
		String pt =((EXSelect)getChild("paymentMethod")).getValue().toString();
		String firstPaymentMethod = ((EXSelect)getChild("firstPaymentMethod")).getValue().toString();
		order.setPaymentMethod(pt);
		String cn =  getDescendentOfType(EXInput.class).getValue().toString();
		order.setChequeNo(cn);
		order.setProperty("firstPaymentMethod", firstPaymentMethod);
		
		order.setBankName(((EXInput)getChild("bankName")).getValue().toString());
	}
	
	public boolean ServerAction(Container container, Map<String, String> request)throws UIException {
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		if(container.getName().equals("back")){
			Container c =co.getChild("billing");
			c.getChildByIndex(1).setDisplay(true);
			setDisplay(false);
		}else{
			if(validate()){
				Container c =co.getChild("review");
				EXMiniCart cart = getRoot().getDescendentOfType(EXMiniCart.class);
				if(c.getChildren().size() ==1){
					EXElieNSonsOrderReview review = new EXElieNSonsOrderReview("", cart,0);
					
					Container cont=review.getDescendentByName("continue");
					cont.addEvent(new Event() {
						
						@Override
						public void Success(ClientProxy container, Map<String, String> request)
								throws UIException {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public boolean ServerAction(Container container, Map<String, String> request)
								throws UIException {
							// TODO Auto-generated method stub
							return false;
						}
						
						@Override
						public void ClientAction(ClientProxy container) {
							String js = "window.open('"+JavascriptUtil.javaScriptEscape(getContractUrl())+"', '_blank');";
							container.appendJSFragment(js);
							
						}
					}, Event.CLICK);
					
					
					c.addChild(review.setStyle("width", "697px"));
				}
				else
					c.getChildByIndex(1).setDisplay(true);
				setDisplay(false);
			}
		}
		
		return true;
		}

}
