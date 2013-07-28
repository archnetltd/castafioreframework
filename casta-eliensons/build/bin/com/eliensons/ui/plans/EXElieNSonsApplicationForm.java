package com.eliensons.ui.plans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.castafiore.designable.checkout.EXCheckout;
import org.castafiore.inventory.orders.OrderService;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.crm.ContractDetail;
import org.castafiore.shoppingmall.crm.Dependent;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Value;

import com.eliensons.ui.sales.EXElieNSonsMiniCart;
import com.eliensons.ui.sales.EXElieNSonsPaymentInformation;
import com.eliensons.ui.sales.EXElieNSonsPaymentInformation.Member;

public class EXElieNSonsApplicationForm extends EXXHTMLFragment implements Event{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static List<String> dates = new ArrayList<String>();
	static List<String> genders = new ArrayList<String>();
	static List<String> status = new ArrayList<String>();
	
	static List<String> title = new ArrayList<String>();
	
	static{
		dates.add("date");
		dates.add("effDate");
		
	}
	
	public EXElieNSonsApplicationForm(){
		this(CastafioreApplicationContextHolder.getCurrentApplication().getDescendentOfType(EXElieNSonsMiniCart.class));
	} 
	
	public EXElieNSonsApplicationForm(EXElieNSonsMiniCart minicart) {
		this("", minicart);
	}
	
	
	
	public EXElieNSonsApplicationForm(String name, EXElieNSonsMiniCart minicart) {
		super(name, "templates/EXElieNSonsApplicationForm.xhtml");
		for(String s : fields){
			if(dates.contains(s)){
				addChild(new EXDatePicker(s));
			}else if(s.equals("pos")){
				if(minicart != null){
					addChild(new EXInput(s, minicart.getPos()));
				}else{
					addChild(new EXInput(s));
				}
			}else if(s.equalsIgnoreCase("salesAgent")){
				if(minicart != null)
					addChild(new EXInput(s, minicart.getAgent()));
				else
					addChild(new EXInput(s));
			}
			else{
				addChild(new EXInput(s));
			}

		}
		
		EXDatePicker dPicker = (EXDatePicker)getDescendentByName("effDate");
		
		if(minicart != null){
			Date invoiceDate = minicart.getInvoiceDate();
			
			Date effDate = DateUtils.add(invoiceDate, Calendar.MONTH, 6);
			
			dPicker.setValue(effDate);
			
			EXDatePicker uidate = (EXDatePicker)getDescendentByName("date");
			uidate.setValue(invoiceDate);
			
			if(minicart.getItems().size() > 0){
				String plan = minicart.getItems().get(0).getTitle();
				
				setValue("plan", plan);
			}
			
			try{
			setValue("fsNumber", minicart.getInvoiceNumber());
			setValue("firstPaymentDate", new SimpleDateFormat("dd/MM/yyyy").format(minicart.getStatePaymentDate()));
			setValue("nextPaymentDate", new SimpleDateFormat("dd/MM/yyyy").format(minicart.getNextPaymentDate()));
			setValue("totalPayments", "0");
			setValue("arrears", "0");
			setValue("state", "11");
			setValue("costOfFuneral", "0");
			setValue("dateTerminated", "");
			setValue("dateCancelled", "");
			setValue("monthlyInstallment", minicart.getInstallment().toPlainString());
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else{
			
		}
		
		
		if(minicart != null){
			addClass("FromMiniCart");
		}
		
		
		addChild(new EXButton("continue", "Continue").addEvent(this, CLICK));
		
		addChild(new EXContainer("sameAsContact", "button").setText("Same as contact").addEvent(this, CLICK));
		
		ComponentUtil.iterateOverDescendentsOfType(this, EXInput.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				c.addEvent(new Event() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void Success(ClientProxy container, Map<String, String> request)
							throws UIException {
						
					}
					
					@Override
					public boolean ServerAction(Container container, Map<String, String> request)
							throws UIException {
						return false;
					}
					
					@Override
					public void ClientAction(ClientProxy container) {
						container.mergeCommand(new ClientProxy(container.getIdRef() +" input").keydown(container.clone().appendJSFragment("if (event.keyCode==13) {var inputs = $(':input');inputs.get(inputs.index(this) + 1).focus(); return event.keyCode }")));
						
					}
				}, Event.READY);
				
			}
		});
	}
	public static String[] fields = new String[]{
		"salesAgent","pos","date","fsNumber","plan","effDate",
		"cfullName","cSurname","cidnumber","cresi","cresi2","telHome","cell","cemail",
		"pfullName","pSurname","pidnumber","presi","presi2","ptelhome","pcell","email",
		"sfullName","sidnumber",
		"firstPaymentDate","nextPaymentDate","totalPayments","arrears","status", "costOfFuneral", "dateTerminated","deathDate", "dateCancelled","monthlyInstallment", 
		"accountNumber", "bankName",
		"c1name","c11",
		"c2name","c21",
		"c3name","c31",
		"c4name","c41",
		"c5name","c51",
		"c6name","c61", "firstPaymentMethod", "monthlyPaymentMethod"
	};
	
	public void addEventss(){
		getChild("pstatus").addEvent(this, BLUR);
		getChild("sstatus").addEvent(this, BLUR);
	}
	
	 
	
	
	
	private final static SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy");

	
protected String getContractUrl(EXElieNSonsApplicationForm bi, ContractDetail detail){
	
		String icode = detail.getFsCode();;
		String pt =detail.getPaymentMethod();
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
		
		res.put("salesman", detail.getSalesAgent());
		res.put("plan", bi.getValue("plan"));
		res.put("jFee", StringUtil.toCurrency("MUR", detail.getJoiningFee()));
		res.put("inst", StringUtil.toCurrency("MUR", detail.getInstallment()));
		res.put("total", StringUtil.toCurrency("MUR", detail.getTotal()));
		res.put("paymentMode",pt);
		
		cal.add(Calendar.MONTH, -5);
		res.put("firstInstallment", new SimpleDateFormat("dd/MMM/yyyy").format(cal.getTime()));
		
		if(StringUtil.isNotEmpty(bi.getValue("pidnumber"))){
			res.put("dob", bi.getValue("pidnumber") );
		}
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
			spouse.name = bi.getValue("sfullName");
			spouse.id = bi.getValue("sidnumber");
			members.add(spouse);
		}
		
		for(int i =1; i <=6; i++){
			Member dep = new Member();
			if( StringUtil.isNotEmpty(bi.getValue("c"+i+"name"))){
				dep.name = bi.getValue("c"+i+"name") ;
				
				dep.id = bi.getValue("c"+i+"1") ;
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
	
	public void setInfoF(String abs){
		long start = System.currentTimeMillis();
		setAttribute("path", abs);
		ContractDetail detail = new OrderService().loadContract(abs);
		setValue("plan", detail.getPlanDetail());
		setValue("monthlyInstallment", detail.getInstallment().toPlainString());
		setValue("salesAgent", detail.getSalesAgent());
		setValue("pos", detail.getPointOfSale());
		setValue("date", format.format(detail.getRegistrationDate()));
		setValue("effDate", format.format(detail.getEffectiveDate()));
		setValue("cfullName", detail.getContactLastName());
		setValue("cSurname", detail.getContactFirstName());
		setValue("cidnumber", detail.getContactNIC());
		setValue("cemail", detail.getContactEmail());
		setValue("telHome", detail.getContactTel());
		setValue("cell", detail.getContactMobile());
		setValue("cresi", detail.getContactAddressLine1());
		setValue("cresi2", detail.getContactAddressLine2());
		
		setValue("pfullName", detail.getPrincipalFirstName());
		setValue("pSurname", detail.getPrincipalLastName());
		setValue("pidnumber", detail.getPrincipalNIC());
		setValue("email", detail.getPrincipalEmail());
		setValue("ptelhome", detail.getPrincipalTel());
		setValue("pcell", detail.getPrincipalMobile());
		setValue("presi", detail.getPrincipalAddressLine1());
		setValue("presi2", detail.getPrincipalAddressLine2());
		setValue("bankName", detail.getBankName());
		setValue("accountNumber", detail.getAccountNumber());
		
		setValue("sidnumber", detail.getSpouseNIC());
		setValue("sfullName", detail.getSpouseLastName());
		
		int count = 1;
		for(Dependent d : detail.getDependants()){
			String name = d.getName();
			String nic = d.getNic();
			
			setValue("c"+count+"name", name);
			setValue("c"+count+"1", nic);
			count++;
		}
		
		getChild("continue").setText("Save Application Form").setStyle("width", "185px");//.setDisplay(false);
		try{
		addChild(new EXContainer("downAppForm", "a").setAttribute("href", getContractUrl(this, detail)).setText("Download application form"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Time taken to load form : " + (System.currentTimeMillis() -start) + "ms");
	}
	
	
	
	 
	
	
	public void sameAsContact(){
		setValue("pfullName", getValue("cfullName"));
		setValue("pSurname", getValue("cSurname"));
		setValue("pidnumber", getValue("cidnumber"));
		setValue("email", getValue("cemail"));
		setValue("ptelhome", getValue("telHome"));
		setValue("pcell", getValue("cell"));
		setValue("presi", getValue("cresi"));
		setValue("presi2", getValue("cresi2"));
		
	}
	
	public BillingInformation createInfo(Order order){
		BillingInformation bi = order.createFile("billing", BillingInformation.class);
		bi.setCountry("Mauritius");
		bi.setEmail(getValue("cemail"));
		bi.setFirstName(getValue("cSurname"));
		bi.setLastName(getValue("cfullName"));
		bi.setPhone(getValue("telHome"));
		bi.setMobile(getValue("cell"));
		bi.setAddressLine1(getValue( "cresi"));
		bi.setNic(getValue("cidnumber"));
		bi.setAddressLine2(getValue("cresi2"));
		
		Value val = bi.createFile("applicationForm", Value.class);
		for(String s : fields){
			String value = getValue(s);
			
			val.setString(val.getString() + "-:;:-" + s + ":" + value);
		}
		return bi;
	}
	
	
	public void update(){
		
		BillingInformation bi = (BillingInformation)SpringUtil.getRepositoryService().getFile(getAttribute("path") + "/billing", Util.getRemoteUser());
		bi.setCountry("Mauritius");
		bi.setEmail(getValue("cemail"));
		bi.setFirstName(getValue("cSurname"));
		bi.setLastName(getValue("cfullName"));
		bi.setPhone(getValue("telHome"));
		bi.setMobile(getValue("cell"));
		bi.setAddressLine1(getValue( "cresi"));
		bi.setNic(getValue("cidnumber"));
		
		bi.setAddressLine2(getValue("cresi2"));
		Value val = bi.getFile("applicationForm", Value.class);
		for(String s : fields){
			String value = getValue(s);
			val.setString(val.getString() + "-:;:-" + s + ":" + value);
		}
		
		
		Order o = bi.getAncestorOfType(Order.class);
		o.setPointOfSale(getValue("pos"));
		o.setBankName(getValue("bankName"));
		o.setChequeNo(getValue("accountNumber"));
		o.setInstallment(new BigDecimal(getValue("monthlyInstallment")));
		try{
		o.setDateOfTransaction(new SimpleDateFormat("dd-MM-yyyy").parse(getValue("date")));
		o.setStartInstallmentDate(new SimpleDateFormat("dd-MM-yyyy").parse(getValue("date")));
		}catch(Exception e){
			e.printStackTrace();
		}
		o.save();
	}
	

	
	public String getValue(String field){
		
		return ((StatefullComponent)getChild(field)).getRawValue();
	}
	
	public void setValue(String field, String value){
		try{
		 ((StatefullComponent)getChild(field)).setRawValue(StringUtil.isNotEmpty(value)?value:"");
		}catch(Exception e){
			
		}
	}
	
	
	
	
	public void ok(){
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		Container payment =co.getChild("payment");
		if(payment.getChildren().size() ==1){
			payment.addChild(new EXElieNSonsPaymentInformation("").setStyle("width", "697px"));
		}else{
			payment.getChildByIndex(1).setDisplay(true);
		}
		
		setDisplay(false);
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		
		if(container.getText().equalsIgnoreCase("Save Application Form")){
			update();
			return true;
		}if(container.getName().equalsIgnoreCase("sameAsContact")){
			sameAsContact();
			return true;
		}
		ok();
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}

}
