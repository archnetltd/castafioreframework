package org.castafiore.shoppingmall.pos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.ShippingInformation;
import org.castafiore.shoppingmall.delivery.Delivery;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.relationship.EXFastSearchRelated;
import org.castafiore.shoppingmall.relationship.OnSelectItemHandler;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.wfs.Util;

public class EXPointOfSales extends EXBorderLayoutContainer implements Event{
	
	public static int MODE_PURCHASES =0;
	public static int MODE_SALES = 1;
	
	private int mode_;

	public EXPointOfSales(String name, int mode) {
		super(name);
		this.mode_ = mode;
		for(int i =0; i <5;i++){
			getContainer(i).setStyle("padding", "0").setStyle("margin", "0").setStyle("vertical-align", "top");
		}
		
		EXFieldSet fs = new EXFieldSet("main", "Main Info", true);
		fs.addField("Date :", new EXDatePicker("date"));
		fs.addField("Order Number :", new EXInput("order", SpringUtil.getRepositoryService().getNextSequence(mode == MODE_PURCHASES?"Purchases_Sequence" : "Sales_Sequence", Util.getRemoteUser()) +"" ));
		List<String> suppliers = SpringUtil.getRelationshipManager().getRelatedOrganizations(Util.getLoggedOrganization(), mode == MODE_PURCHASES?"Supplier" : "Customer");
		fs.addField(mode==MODE_PURCHASES? "Supplier :":"Customer :", new EXAutoComplete("player", "", suppliers));
		fs.getDescendentByName("player").setAttribute("title", "Click to open search dialog").addEvent(this, CLICK);
		List<String> agents = new ArrayList<String>();
		
		try{
			List<User> users = SpringUtil.getSecurityService().getUsers("agent:" + Util.getLoggedOrganization(), Util.getLoggedOrganization());
			for(User u : users){
				agents.add(u.getUsername());
			}
			fs.addField("Agent :", new EXAutoComplete("agent", "", agents));
		}catch(Exception e){
			
		}

		addChild(fs, EXBorderLayoutContainer.TOP);
		
		addChild(new EXPointOfSalesCart("items").setStyle("width", "650px"), CENTER);
		
		getDescendentOfType(EXItemsTable.class).setModel(getDescendentOfType(EXItemsTable.class));
		
		addChild(new EXSearchProductTable("ss","erevolution").setStyle("width", "300px"), RIGHT);
	}
	
	public void addProduct(Product p){
		//
		getDescendentOfType(EXMiniCart.class).addToCart(p, 1, this);
		getDescendentOfType(EXItemsTable.class).refresh();
	}
	
	public void setMode(int mode){
		
		if(this.mode_ != mode){
			this.mode_ = mode;
			getDescendentByName("main").getDescendentByName("player_label").setText(mode_==MODE_PURCHASES? "Supplier :":"Customer :");
			Container stf = getDescendentByName("main").getDescendentByName("player");
			Container parent = stf.getParent();
			stf.remove();
			parent.addChild(new EXAutoComplete("player", "", SpringUtil.getRelationshipManager().getRelatedOrganizations(Util.getLoggedOrganization(), mode_ == MODE_PURCHASES?"Supplier" : "Customer")));
			getDescendentOfType(EXFieldSet.class).getField("order").setValue(SpringUtil.getRepositoryService().getNextSequence(mode == MODE_PURCHASES?"Purchases_Sequence" : "Sales_Sequence"));
			
		}
		
	}
	
	
	public Order createOrder(){
		//create a purchase for this company
		//create a sales for the other supplier
		//both with same reference
		if(mode_ == MODE_SALES){
			String customer = ((StatefullComponent)getDescendentByName("supplier")).getValue().toString();
			Merchant mSupplier = MallUtil.getCurrentMerchant();
			String supplier = mSupplier.getUsername();
			User m = SpringUtil.getSecurityService().loadUserByUsername(customer);
		 	Order order = mSupplier.getManager().createOrder(supplier);
		 	order.setOrderedFrom(supplier);
		 	order.setOrderedBy(Util.getLoggedOrganization());
		 	order.setOwner(((StatefullComponent)getDescendentByName("agent")).getValue().toString());
		 	order.setTotal(getDescendentOfType(EXPointOfSalesCart.class).getTotal());
		 	order.setInstallment(new BigDecimal(0));
		 	order.setJoiningFee(new BigDecimal(0));
		 	getDescendentOfType(EXPointOfSalesCart.class).createItems(order);
		 	
		 	Address mm = m.getDefaultAddress();
		 	BillingInformation bif = order.createBillingInformation();
		 	bif.setAddressLine1(mm.getLine1());
		 	bif.setAddressLine2(mm.getLine2());
		 	bif.setCity(mm.getCity());
		 	//bif.setCompany(m.getCompanyName());
		 	bif.setCountry(mm.getCountry());
		 	//User contact =SpringUtil.getSecurityService().loadUserByUsername(m.getUsername());
		 	bif.setDateOfBirth(m.getDateOfBirth());
		 	bif.setEmail(m.getEmail());
		 	bif.setFax(m.getFax());
		 	bif.setFirstName(m.getFirstName());
		 	bif.setGender(m.getGender());
		 	bif.setLastName(m.getLastName());
		 	bif.setMaritalStatus(m.getMaritalStatus());
		 	bif.setMobile(m.getMobile());
		 	bif.setNic(m.getNic());
		 	bif.setPhone(m.getPhone());
		 	bif.setTitle(m.getTitle());
		 	bif.setUsername(m.getUsername());
		 	bif.setZipPostalCode(mm.getPostalCode());
		 	
		 	
		 	ShippingInformation sif = order.createShippingInformation();
		 	sif.setAddressLine1(mm.getLine1());
		 	sif.setAddressLine2(mm.getLine2());
		 	sif.setCity(mm.getCity());
		 	//sif.setCompany(m.getCompanyName());
		 	sif.setCountry(mm.getCountry());
		 	sif.setDateOfBirth(m.getDateOfBirth());
		 	sif.setEmail(m.getEmail());
		 	sif.setFax(m.getFax());
		 	sif.setFirstName(m.getFirstName());
		 	sif.setGender(m.getGender());
		 	sif.setLastName(m.getLastName());
		 	sif.setMaritalStatus(m.getMaritalStatus());
		 	sif.setMobile(m.getMobile());
		 	sif.setNic(m.getNic());
		 	sif.setPhone(m.getPhone());
		 	sif.setTitle(m.getTitle());
		 	sif.setUsername(m.getUsername());
		 	sif.setZipPostalCode(mm.getPostalCode());
		 	
		 	Delivery delivery = order.createDelivery();
		 	delivery.setWeight(new BigDecimal(200));
		 	delivery.setPrice(new BigDecimal(200));
		 	
		 	return order;
		}
		
		
		else{
			String supplier = ((StatefullComponent)getDescendentByName("player")).getValue().toString();
			Merchant m = MallUtil.getCurrentMerchant();
		 	Order order = m.getManager().createOrder(supplier);
		 	order.setOrderedFrom(supplier);
		 	order.setOrderedBy(Util.getLoggedOrganization());
		 	order.setOwner(((StatefullComponent)getDescendentByName("agent")).getValue().toString());
		 	order.setCode( ((StatefullComponent)getDescendentByName("main").getDescendentByName("order")).getValue().toString());
		 	getDescendentOfType(EXPointOfSalesCart.class).createItems(order);
		 	order.setTotal(getDescendentOfType(EXPointOfSalesCart.class).getTotal());
		 	order.setInstallment(new BigDecimal(0));
		 	order.setJoiningFee(new BigDecimal(0));
		 	
		 	BillingInformation bif = order.createBillingInformation();
		 	bif.setAddressLine1(m.getAddressLine1());
		 	bif.setAddressLine2(m.getAddressLine2());
		 	bif.setCity(m.getCity());
		 	bif.setCompany(m.getCompanyName());
		 	bif.setCountry(m.getCountry());
		 	User contact =SpringUtil.getSecurityService().loadUserByUsername(m.getUsername());
		 	bif.setDateOfBirth(contact.getDateOfBirth());
		 	bif.setEmail(m.getEmail());
		 	bif.setFax(m.getFax());
		 	bif.setFirstName(contact.getFirstName());
		 	bif.setGender(contact.getGender());
		 	bif.setLastName(contact.getLastName());
		 	bif.setMaritalStatus(contact.getMaritalStatus());
		 	bif.setMobile(m.getMobilePhone());
		 	bif.setNic(contact.getNic());
		 	bif.setPhone(m.getPhone());
		 	bif.setTitle(contact.getTitle());
		 	bif.setUsername(contact.getUsername());
		 	bif.setZipPostalCode(m.getZipPostalCode());
		 	
		 	
		 	ShippingInformation sif = order.createShippingInformation();
		 	sif.setAddressLine1(m.getAddressLine1());
		 	sif.setAddressLine2(m.getAddressLine2());
		 	sif.setCity(m.getCity());
		 	sif.setCompany(m.getCompanyName());
		 	sif.setCountry(m.getCountry());
		 	sif.setDateOfBirth(contact.getDateOfBirth());
		 	sif.setEmail(m.getEmail());
		 	sif.setFax(m.getFax());
		 	sif.setFirstName(contact.getFirstName());
		 	sif.setGender(contact.getGender());
		 	sif.setLastName(contact.getLastName());
		 	sif.setMaritalStatus(contact.getMaritalStatus());
		 	sif.setMobile(m.getMobilePhone());
		 	sif.setNic(contact.getNic());
		 	sif.setPhone(m.getPhone());
		 	sif.setTitle(contact.getTitle());
		 	sif.setUsername(contact.getUsername());
		 	sif.setZipPostalCode(m.getZipPostalCode());
		 	
		 	Delivery delivery = order.createDelivery();
		 	delivery.setWeight(new BigDecimal(200));
		 	delivery.setPrice(new BigDecimal(200));
		 	
		 	

		 	
		 	return order;
		}
	 	
	 	
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String org = Util.getLoggedOrganization();
		String relationship = "Supplier";
		
		
		final EXInput input = (EXInput)container;
		
		OnSelectItemHandler handler = new OnSelectItemHandler() {
			
			@Override
			public void selectItem(Merchant m) {
				input.setValue(m.getUsername());
				
			}
		};
		
		EXFastSearchRelated fs = new EXFastSearchRelated("", org, relationship, handler);
		addPopup(fs);
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
