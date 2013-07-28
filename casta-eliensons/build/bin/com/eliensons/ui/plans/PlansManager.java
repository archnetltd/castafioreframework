package com.eliensons.ui.plans;

import groovy.text.TemplateEngine;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.plaf.ComponentUI;

import org.apache.commons.lang.ArrayUtils;
import org.castafiore.catalogue.Product;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.OrderEntry;
import org.castafiore.shoppingmall.checkout.SalesOrderEntry;
import org.castafiore.shoppingmall.delivery.Delivery;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.orders.DefaultGUIReactor;
import org.castafiore.shoppingmall.orders.EXWorkflowForm;
import org.castafiore.shoppingmall.orders.GUIReactor;
import org.castafiore.shoppingmall.orders.OrdersUtil;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.shoppingmall.orders.WorkflowForm;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
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
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PlansManager extends OrdersUtil implements OrdersWorkflow{

	@Override
	public String getResourceClassPath() {
		return "com/eliensons/ui/plans";
	}
	
	
	protected  String getOrderLine(SalesOrderEntry entry, int index){
		try{
			String style = "style=background:steelBlue";
			if((index %2 ) == 0){
				style = "";
			}
		
			
			
			
			//String code = entry.getProductCode();
			
			
			//QueryParameters param= new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("code", code));
			
			
			//Product p = (Product)SpringUtil.getRepositoryService().executeQuery(param, Util.getRemoteUser()).get(0);
			
			//BinaryFile options = p.getOption();
			
			final StringBuilder b = new StringBuilder(entry.getTitle() + "<br></br>");
			if(entry.getOptions() != null){
				Container c = DesignableUtil.buildContainer(new ByteArrayInputStream(entry.getOptions().getBytes()), false);
			
				final Map<String, String> mptions = new HashMap<String, String>();
			
			
				ComponentUtil.iterateOverDescendentsOfType(c, StatefullComponent.class, new ComponentVisitor() {
					
					@Override
					public void doVisit(Container c) {
						StatefullComponent stf = (StatefullComponent)c;
						mptions.put(c.getName(), stf.getValue().toString());
						b.append(c.getName() + ":" + stf.getValue().toString() ).append("<br></br>");
					}
				});
				
				b.append("<br></br>").append(entry.getSummary() ).append("<br></br>").append(entry.getDetail());
			}
			
			String currency = FinanceUtil.getCurrentCurrency();
			String s = "<tr "+style+"><td>"+entry.getProductCode()+"</td><td>"+b+"</td><td style='text-align:right'>"+entry.getQuantity()+"</td><td style='text-align:right'>"+ StringUtil.toCurrency(currency,entry.getPrice())+"</td><td style='text-align:right'>"+StringUtil.toCurrency(currency,entry.getTotal())+"</td></tr>";
			return s;
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	public  String buildOrder(Order order, Merchant merchant){
		String s = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream(getResourceClassPath() + "/Order.xhtml"));
		Map<String , String> rep = new HashMap<String, String>();
		
		List<OrderEntry> entries  = order.getEntries();
		StringBuilder or = new StringBuilder();
		
		BillingInformation billing = order.getBillingInformation();
		int ii = 0;
		for(OrderEntry entry : entries){
			or.append(getOrderLine((SalesOrderEntry)entry, ii));
			ii++;
		}
		Delivery del = order.getDelivery();
		String currentCurrency = FinanceUtil.getCurrentCurrency();
		rep.put("$orderlines", or.toString());
		rep.put("$invoicenumber", order.getCode());
		rep.put("$date", new SimpleDateFormat("dd/MM/yyyy").format(order.getDateOfTransaction()));
		
		rep.put("$subtotal", StringUtil.toCurrency(currentCurrency,order.getSubTotal()));
		rep.put("$tax", StringUtil.toCurrency(currentCurrency, order.getTax()));
		rep.put("$delivery", StringUtil.toCurrency( currentCurrency,del.getPrice()));
		
		rep.put("$status", getStatus(order.getStatus()));
		rep.put("$total",StringUtil.toCurrency(currentCurrency,order.getTotal().doubleValue() + del.getPrice().doubleValue()));
		
		rep.put("$bfirstName", billing.getFirstName());
		rep.put("$blastName", billing.getLastName());
		rep.put("$bcompanyName", billing.getCompany());
		rep.put("$baddline1", billing.getAddressLine1());
		rep.put("$baddline2", billing.getAddressLine2());
		rep.put("$bcity", billing.getCity());
		rep.put("$bphone", billing.getPhone());
		rep.put("$bmobile", billing.getMobile());
		rep.put("$bemail", billing.getEmail());
		rep.put("$bcountry", billing.getCountry());
		rep.put("$bzipcode", billing.getZipPostalCode());
		rep.put("$bfax", billing.getFax());
		
		
		

		rep.put("$mcompanyName", merchant.getTitle());
		rep.put("$maddline1", merchant.getAddressLine1());
		rep.put("$maddline2", merchant.getAddressLine2());
		rep.put("$mcity", merchant.getCity());
		rep.put("$mphone", merchant.getPhone());
		rep.put("$mmobile", merchant.getMobilePhone());
		rep.put("$memail", merchant.getEmail());
		rep.put("$mfax", merchant.getFax());
		rep.put("$mbrn", merchant.getBusinessRegistrationNumber());
		rep.put("$mvatreg", merchant.getVatRegistrationNumber());

		s = compile(s, rep);
		
		return s;
		
		
	}


	/* (non-Javadoc)
	 * @see org.castafiore.shoppingmall.orders.OrdersWorkflow#addButtons(org.castafiore.ui.Container, int, java.lang.String, org.castafiore.wfs.types.File)
	 */
	@Override
	public void addButtons(Container parent, int status, String actor,String organization,String input) {
		addButtons(parent, status, actor, input);
		
	}


	/* (non-Javadoc)
	 * @see org.castafiore.shoppingmall.orders.OrdersWorkflow#addSearchButtons(org.castafiore.ui.Container)
	 */
	@Override
	public void addSearchButtons(Container parent) throws Exception {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see org.castafiore.shoppingmall.orders.OrdersWorkflow#executeXML(org.castafiore.ui.Container)
	 */
	@Override
	public void executeXML(Container source) throws Exception {
		executeXML(source, "merchant",null);
		
	}


	/* (non-Javadoc)
	 * @see org.castafiore.shoppingmall.orders.OrdersWorkflow#getHtml(groovy.text.TemplateEngine, java.util.Map)
	 */
	@Override
	public String getHtml(TemplateEngine e, Map input) throws Exception {
		
		Order order = (Order)input.get("input");
		Merchant merchant = MallUtil.getMerchant(order.getOrderedFrom());
		return buildOrder(order, merchant);
	}


	@Override
	public WorkflowForm createForm(NodeList fields, String title, File file,
			String actor, String organization, Container source) {
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
							
							executeXML(container,"merchant",nl);
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


	@Override
	public GUIReactor getReactor() {
		return new DefaultGUIReactor();
	}


	

}
