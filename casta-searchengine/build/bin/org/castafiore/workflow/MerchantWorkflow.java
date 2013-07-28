/**
 * 
 */
package org.castafiore.workflow;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.castafiore.designer.Studio;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.api.RelationshipManager;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.orders.DefaultGUIReactor;
import org.castafiore.shoppingmall.orders.EXInvoiceHeader;
import org.castafiore.shoppingmall.orders.EXWorkflowForm;
import org.castafiore.shoppingmall.orders.GUIReactor;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.shoppingmall.orders.WorkflowForm;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXDateTimePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author acer
 *
 */
public class MerchantWorkflow implements OrdersWorkflow {

	//protected Map<String,Properties> workflows = new HashMap<String, Properties>();
	
	protected static ThreadLocal<Properties> TLocal = new ThreadLocal<Properties>();
	
	private GUIReactor reactor = new DefaultGUIReactor();
	
	private String path=null;
	
	
	
	
	
	public MerchantWorkflow(GUIReactor reactor, String path) {
		super();
		this.reactor = reactor;
		this.path = path;
	}
	
	public MerchantWorkflow(){
		//path= "/root/users/" + Util.getLoggedOrganization() + "/workflow";
	}

	public GUIReactor getReactor() {
		return reactor;
	}

	public void setReactor(GUIReactor reactor) {
		this.reactor = reactor;
	}

	protected Properties getWorkflow()throws Exception{
		
		if(TLocal.get() != null){
			return TLocal.get();
		}
		
		if(path == null){
			path= "/root/users/" + Util.getLoggedOrganization() + "/workflow/workflow.properties";
		}
		
		
		//@Patch erevolution
		if(CastafioreApplicationContextHolder.getCurrentApplication().getContextPath().contains("erevolution")){
			path = patchErevolution();
		}
		//end @patch erevolution
		
		
		if(SpringUtil.getRepositoryService().itemExists(path)){
			
			InputStream in = ((BinaryFile)SpringUtil.getRepositoryService().getDirectory(path, Util.getRemoteUser())).getInputStream();
			Properties p = new Properties();
			p.load(in);
			//workflows.put(m, p);
			if(TLocal.get() == null){
				TLocal.set(p);
			}
			return p;
			
		}else{
			Properties prop = new Properties();
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/orders/workflow.properties"));
			//workflows.put(m,prop);
			return prop;
		}
	}
	
	protected String patchErevolution(){
		RelationshipManager r = SpringUtil.getRelationshipManager();
		if(r.hasRelationship("erevolution", Util.getLoggedOrganization(), "Tier 1 Agent")){
			System.out.println("Loading workflow :/root/users/erevolution/Tier1Workflow/workflow.properties");
			return "/root/users/erevolution/Tier1Workflow/workflow.properties";
		}if(r.hasRelationship("erevolution", Util.getLoggedOrganization(), "Tier 2 Agent")){
			System.out.println("Loading workflow :/root/users/erevolution/Tier2Workflow/workflow.properties");
			return "/root/users/erevolution/Tier2Workflow/workflow.properties";
		}else{
			System.out.println("Loading workflow :/root/users/erevolution/workflow/workflow.properties");
			return "/root/users/erevolution/workflow/workflow.properties";
		}
		
	}
	
	
	@Override
	public void addButtons(Container parent, int status,String actor, String organization,String input) {
		
		try{
			String key = "if." + status + "." + actor;
			
			
			//Merchant merchant = MallUtil.getCurrentMerchant();
			Properties prop = getWorkflow();
			
//			if(plan.equalsIgnoreCase("free") && actor.equalsIgnoreCase("customer")){
//				key = "if." + status + "." + actor + ".free";
//			}
			
			if(prop.containsKey(key)){
				String property = prop.getProperty(key);
				String[] parts = StringUtil.split(property, ",");
				for(int i =0; i < parts.length; i ++){
					String s = parts[i];
					String text = prop.getProperty(s);
					//Container button = new EXContainer(s, "button");
					//button.setText(text);
					EXButton button = new EXButton(s, text);
					button.setAttribute("path", input);
					button.addEvent(this, Event.CLICK);
					button.setAttribute("actor", actor);
					button.setAttribute("organization", organization);
					parent.addChild(button);
				}
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}
	
	protected InputStream getContent(String value, String cMerchant)throws Exception{
		
		String path = "/root/users/" +cMerchant + "/workflow/actions/" + value + ".xml";
		if(SpringUtil.getRepositoryService().itemExists(path)){
			return ((BinaryFile)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser())).getInputStream();
		}else{
			return Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/orders/" + value + ".xml");
		}
	}


	public String getHtml(TemplateEngine e, Map v) throws Exception{
		try{
		String merchant =	((Merchant)v.get("merchant")).getUsername();
		String s = cp(IOUtil.getStreamContentAsString(getContent("OrderTemplate",merchant)),e,v);

		return s;
		}catch(Exception ee){
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintWriter w = new PrintWriter(out);
			ee.printStackTrace(w);
			return out.toString();
			//throw new RuntimeException(ee);
		}
	}
	

	
	
	
	@Override
	public void executeXML(Container source) throws Exception {
		
		Map input = new HashMap();
		File fInput = SpringUtil.getRepositoryService().getFile(source.getAttribute("path"), Util.getRemoteUser());
		String actor = source.getAttribute("actor");
		input.put("input", fInput);
		Merchant m = null;
		
		if(actor.equalsIgnoreCase("merchant"))
			m= MallUtil.getCurrentMerchant();
		else if(fInput instanceof Order){
			m = MallUtil.getMerchant(((Order)fInput).getOrderedFrom());
			
		}
		//MallUtil.getMerchant(source.getAttribute("merchant"));
//		try{
//			m = MallUtil.getCurrentMerchant();
//			if(m == null){
//				m = MallUtil.getMerchant(source.getAttribute("merchant"));
//			}
//		}catch(Exception e){
//			m = MallUtil.getMerchant(source.getAttribute("merchant"));
//		}
		
		input.put("merchant", m);
		
		//name of xml
		String sname = source.getName();
		//the xml file
		InputStream xml = getContent(sname,source.getAttribute("organization"));

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);
		doc.getDocumentElement().normalize();
		
		NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
		
		_executeXML(nodeList, source, input);
		
	}
	
	
	
	private String cp(String src, TemplateEngine engine , Map variable)throws Exception{
		String ttx =   src ;
		Template tpl = engine.createTemplate(ttx);
		Writable w = tpl.make(variable);
		StringWriter sw = new StringWriter();
		w.writeTo(sw);
		return sw.toString().trim();
	}
	
	private void _executeXML(NodeList nodeList,  Container source, Map v)throws Exception{
		
		TemplateEngine e = new SimpleTemplateEngine();
		File file = (File)v.get("input");
		for(int i = 0; i < nodeList.getLength(); i ++){
			Node n = nodeList.item(i);
			
			String name = n.getNodeName();
			if(name.equals("status")){
				String status =  cp( n.getTextContent(),e,v);
				file.setStatus(Integer.parseInt(status));
				file.save();
			}else if(name.equals("mails")){
				sendMails(n.getChildNodes(),e,v);
			}else if(name.equals("display")){
				String textContent = cp(n.getTextContent(),e,v);
				if(source.getAncestorOfType(EXInvoiceHeader.class) != null)
					source.getAncestorOfType(EXInvoiceHeader.class).setMessage(textContent);
				else 
					source.getRoot().getDescendentOfType(EXInvoiceHeader.class).setMessage(textContent);
			}else if(name.equals("method")){
				String amethod = n.getTextContent();
				String[] as = StringUtil.split(amethod, ".");
				String method = as[as.length-1];
				String cls = amethod.replace("." + method, "");
				
				try{
					Class clazz =Thread.currentThread().getContextClassLoader().loadClass(cls);
					clazz.getMethod(method, Container.class).invoke(clazz.newInstance(), source);
				}catch(Exception ee){
					throw new UIException(ee);
				}
			}else if(name.equalsIgnoreCase("script")){
				String script = n.getTextContent();
				GroovyShell sh = new GroovyShell(new Binding(v));
				sh.setVariable("Studio", new Studio());
				//sh.parse(scriptText, fileName)

				
				if(source.getAncestorOfType(DynaForm.class) != null){
					DynaForm form = source.getAncestorOfType(DynaForm.class);
					List<StatefullComponent> fields = form.getFields();
					for(StatefullComponent field : fields){
						sh.setVariable(field.getName(), field.getValue());
					}
				}
				
				sh.evaluate(script);
			}else if(name.equalsIgnoreCase("form")){
				WorkflowForm panel = createForm(n.getChildNodes(), n.getAttributes().getNamedItem("title").getTextContent(),file,source.getAttribute("organization"), source.getAttribute("actor"),source);
				panel.setStyle("width", "550px").setStyle("z-index", "3000");
				source.getAncestorOfType(PopupContainer.class).addPopup(panel);
			}
		}
		
		if(file != null){
			try{
				
				reactor.react(source, source.getAttribute("actor"), source.getAttribute("organization"), file, this);
			}catch(Exception eee){
				
			}
		}
	}

	@Override
	public String getColor(int status) {
		try{
		Properties prop = getWorkflow();
		return prop.getProperty(status + ".color");
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	private  void sendMail(NodeList list, TemplateEngine e, Map v)throws Exception{
		
		String subject = "";
		String from = "";
		String to = "";
		String content = "";
		
		for(int i = 0; i < list.getLength(); i ++){
			Node n = list.item(i);
			String name = n.getNodeName();
			if(name.equals("subject")){ 
				subject =  cp( n.getTextContent(),e,v);
			}else if(name.equals("from")){
				from = cp(n.getTextContent(),e,v);
			}
			else if(name.equals("to")){
				to = cp(n.getTextContent(),e,v);
			}else if(name.equals("content")){
				content = cp( n.getTextContent(),e,v);
			}
		}
		try{
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(subject);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(content, true);
			sender.send(message);
		}catch(Exception ee){
			ee.printStackTrace();
		}
		
	}
	
	
	public WorkflowForm createForm(NodeList fields, String title, File file, String actor, String organization,Container source){
		
		EXWorkflowForm panel = new EXWorkflowForm("panel", title);
		
		for(int i = 0;i < fields.getLength(); i++){
			Node n = fields.item(i);
			String name = n.getNodeName();
			if(name.equalsIgnoreCase("input")){
				String type =n.getAttributes().getNamedItem("type").getTextContent();
				StatefullComponent stf = null;
				if(type.equalsIgnoreCase("text")){
					stf = new EXInput(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("password")){
					stf = new EXPassword(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("date")){
					stf = new EXDatePicker(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("datetime")){
					stf = new EXDateTimePicker(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("color")){
					stf = new EXColorPicker(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("textarea")){
					stf = new EXTextArea(n.getAttributes().getNamedItem("name").getTextContent());
				}else if(type.equalsIgnoreCase("select")){
					String[] vals = StringUtil.split(n.getAttributes().getNamedItem("options").getTextContent(), ",");
					DefaultDataModel<Object> model = new DefaultDataModel<Object>();
					for(String s : vals)
						model.addItem(s);
					
					stf = new EXSelect(n.getAttributes().getNamedItem("name").getTextContent(),model);
					
				}
				
				if(n.getAttributes().getNamedItem("width") != null){
					String width = n.getAttributes().getNamedItem("width").getTextContent();
					stf.setStyle("width", width);
				}
				
				if(n.getAttributes().getNamedItem("height") != null){
					String width = n.getAttributes().getNamedItem("height").getTextContent();
					stf.setStyle("height", width);
				}
				
				if(stf != null){
					panel.addField(n.getAttributes().getNamedItem("label").getTextContent(), stf);
				}
			}else if(name.equalsIgnoreCase("btn")){
				String btnName = n.getAttributes().getNamedItem("name").getTextContent();
				String label =n.getAttributes().getNamedItem("label").getTextContent();
				EXButton btn = new EXButton(btnName, label);
				btn.setAttribute("source", source.getId());
				Node action  = null;
				panel.addButton(btn);
				NodeList actions = n.getChildNodes();
				btn.setAttribute("path", file.getAbsolutePath());
				btn.setAttribute("actor", actor);
				
				for(int j = 0; j <actions.getLength(); j++){
					Node nn = actions.item(j);
					if(nn.getNodeName().equalsIgnoreCase("action")){
						action = nn;
						break;
					}
				}
				final NodeList nl = action.getChildNodes();
				if(action != null){
					
					btn.addEvent(new Event() {
						
						@Override
						public void Success(ClientProxy container, Map<String, String> request)
								throws UIException {
							
							
						}
						
						@Override
						public boolean ServerAction(Container container, Map<String, String> request)
								throws UIException {
							try{
							String path = container.getAttribute("path");
							File input = SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
							Map variables = new HashMap();
							variables.put("input", input);
							variables.put("merchant", MallUtil.getCurrentMerchant());
							variables.put("source", container.getAttribute("source"));
							
							if(container.getAncestorOfType(EXDynaformPanel.class) != null){
								Map<String,String> fields = container.getAncestorOfType(EXDynaformPanel.class).getFieldValues();
								variables.putAll(fields);
							}
							
							_executeXML(nl, container,variables);
							}catch(Exception e){
								throw new RuntimeException(e);
							}
							return true;
						}
						
						@Override
						public void ClientAction(ClientProxy container) {
							container.mask().makeServerRequest(this);
							
						}
					},  CLICK);
				}
			}
		}
		return panel;
	}

	private  void sendMails(NodeList list, TemplateEngine e, Map v)throws Exception{
		v.put("html", getHtml(e, v));
		for(int i = 0; i < list.getLength(); i ++){
			Node n = list.item(i);
			String name = n.getNodeName();
			if(name.equals("mail")){
				sendMail(n.getChildNodes(),e,v);
			}
		}
	}

	

	public  String getStatus(int status){
		try{
		Properties prop = getWorkflow();
		return prop.getProperty(status + ".label");
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
	}
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			executeXML(container);
		}catch(Exception e){
			throw new UIException(e);
		}
		return true;
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);

	}


	
	@Override
	public void addSearchButtons(Container parent)throws Exception {
		Properties prop = getWorkflow();
		Iterator iter = prop.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next().toString();
			if(key.startsWith("search")){
				String status = key.replace("search.", "");
				String label = prop.getProperty(key);
				Container btn = new EXButton("search" + status, label).setAttribute("state", status);
				parent.addChild(btn);
			}
		}
		
	}


	@Override
	public int[] getAvailableStates() {
		List<Integer> ad = new ArrayList<Integer>();
		try{
			Properties prop = getWorkflow();
			Iterator<Object> iter = prop.keySet().iterator();
			while(iter.hasNext()){
				String key = iter.next().toString();
				if(key.endsWith(".label")){
					ad.add( Integer.parseInt(key.replace(".label", "")));
					
				}
			}
			int[] res = new int[ad.size()];
			for(int i=0;i<ad.size(); i++){
				res[i] = ad.get(i);
			}
			return res;
			//return ad.toArray(new int[ad.size()]);
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}

}
