package org.castafiore.shoppingmall.user.ui;

import java.util.List;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.DeliveryOptions;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.user.ui.settings.EXCompanyCategories;
import org.castafiore.shoppingmall.user.ui.settings.EXCompanySetting;
import org.castafiore.shoppingmall.user.ui.settings.EXContactSetting;
import org.castafiore.shoppingmall.user.ui.settings.EXDeliveryOptions;
import org.castafiore.shoppingmall.user.ui.settings.EXOrganizationSetting;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;

public class EXShopSettings extends EXXHTMLFragment implements EventDispatcher, PopupContainer{

	public EXShopSettings() {
		super("ShopSettings", "/templates/EXShopSettings.xhtml");
//		try{
//			Class c = Thread.currentThread().getContextClassLoader().loadClass("org.erevolution.workflow.ErevolutionUtil");
//			c.getMethod("exportExcel", Container.class).invoke(null, this);
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		addChild(new EXButton("save", "Save Settings").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXContainer("showShopSetting", "img").setAttribute("title", "Enter detailed information about your company").addClass("settingicon").setAttribute("src", "osicons/settings/shop.png").addEvent(DISPATCHER, Event.CLICK));
		String plan = MallUtil.getCurrentMerchant().getPlan();
		
		addChild(new EXContainer("showContactSetting", "img").setAttribute("title", "Enter information about the contact person of the company").addClass("settingicon").setAttribute("src", "osicons/settings/person.png").addEvent(DISPATCHER, Event.CLICK));
		
		addChild(new EXContainer("showCategories", "img").setAttribute("title", "Enter categories of products your company sells").addClass("settingicon").setAttribute("src", "osicons/settings/person.png").addEvent(DISPATCHER, Event.CLICK));
		//addChild(new EXContainer("showPaymentSetting", "img").setAttribute("src", "osicons/settings/payment.png").addEvent(DISPATCHER, Event.CLICK));
		//addChild(new EXContainer("showOrganizationSetting", "img").setAttribute("src", "osicons/settings/organization.png").addEvent(DISPATCHER, Event.CLICK));
		addChild(new EXCompanySetting().addClass("fieldset").setStyle("padding", "12px"));
		addChild(new EXContactSetting().addClass("fieldset").setStyle("padding", "12px").setDisplay(false));
		addChild(new EXCompanyCategories().addClass("fieldset").setStyle("padding", "12px").setDisplay(false));
		addChild(new EXOverlayPopupPlaceHolder("popupContainer"));
		addChild(new EXContainer("messages", "ul").addClass("ui-state-error").setDisplay(false).setStyle("padding", "5px 30px"));
	}
	public Container fill(){

		User u = MallUtil.getCurrentUser().getUser() ;
		Merchant merchant = MallUtil.getCurrentMerchant();
		
		setValue("companyName", merchant.getCompanyName());
		setValue("username", merchant.getUsername());
		setValue("password", u.getPassword());
		
		setValue("businessRegistrationNumber", merchant.getBusinessRegistrationNumber());
		setValue("vatRegistrationNumber", merchant.getVatRegistrationNumber());
		setValue("summary", merchant.getSummary());
		setValue("companyPhone", merchant.getPhone());
		setValue("companyMobilePhone", merchant.getMobilePhone());
		setValue("companyFax", merchant.getFax());
		setValue("companyEmail", merchant.getEmail());
		setValue("businessAddressLine1", merchant.getAddressLine1());
		setValue("businessAddressLine2", merchant.getAddressLine2());
		setValue("businessAddressLine3", merchant.getCity());
		setValue("website", merchant.getWebsite());
		setValue("natureOfBusiness", merchant.getNature());
		setValue("companyCode", merchant.getCode());
		//setValue("defaultLanguage", "English");
		if(StringUtil.isNotEmpty(merchant.getLogoUrl()))
			getDescendentOfType(EXCompanySetting.class).getChild("logo").setAttribute("src", merchant.getLogoUrl());
		else{
			getDescendentOfType(EXCompanySetting.class).getChild("logo").setAttribute("src", "http://www.settle.org.uk/directory/themes/default//images/default_logo.jpg");
		}
		if(StringUtil.isNotEmpty(merchant.getBannerUrl()))
			getDescendentOfType(EXCompanySetting.class).getChild("banner").setAttribute("src", merchant.getBannerUrl());
		else{
			getDescendentOfType(EXCompanySetting.class).getChild("banner").setAttribute("src", "youdo/images/banners/banner1.jpg");
		}
		
		
	
//		String country = merchant.getCountry();
		
//		try{
//			if(country == null){
//				setValue("businessCountry", FinanceUtil.getClientCountry());
//			}else{
//				String[] parts = StringUtil.split(merchant.getCountry(), ",");
//				setValue("businessCountry",new SimpleKeyValuePair(parts[1], parts[0]));
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		String currency = merchant.getCurrency();
//		try{
//			if(currency == null){
//				setValue("businessCurrency", new SimpleKeyValuePair("MUR", "Mauritian Rupee"));
//			}else{
//				setValue("businessCurrency",new SimpleKeyValuePair(merchant.getCurrency(),""));
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
		
		setValue("firstName", u.getFirstName());
		setValue("lastName", u.getLastName());
		setValue("mobile", u.getMobile());
		setValue("phone", u.getPhone());
		setValue("email", u.getEmail());
		
		
		DefaultDataModel<Object> selCats = new DefaultDataModel<Object>();
		if(StringUtil.isNotEmpty(merchant.getCategory())){
			selCats.addItem(merchant.getCategory());
		}
		if(StringUtil.isNotEmpty(merchant.getCategory_1())){
			selCats.addItem(merchant.getCategory_1());
		}
		if(StringUtil.isNotEmpty(merchant.getCategory_2())){
			selCats.addItem(merchant.getCategory_2());
		}
		if(StringUtil.isNotEmpty(merchant.getCategory_3())){
			selCats.addItem(merchant.getCategory_3());
		}
		if(StringUtil.isNotEmpty(merchant.getCategory_4())){
			selCats.addItem(merchant.getCategory_4());
		}
		
		((EXSelect)getDescendentOfType(EXCompanyCategories.class).getDescendentByName("selectedCategories")).setModel(selCats);
		
		EXDeliveryOptions doptions= getDescendentOfType(EXDeliveryOptions.class);
		DeliveryOptions dop = merchant.getDeliveryOptions();
		if(dop != null){
			doptions.fill(dop);
		}
		
		
		
		return this;
	}
	
	
	public void setValue(String fieldName, Object value){
		((StatefullComponent)getDescendentByName(fieldName)).setValue(value);
	}
	public String getValue(String fieldName){
		return ((StatefullComponent)getDescendentByName(fieldName)).getValue().toString();
	}
	
	
	private boolean addMessage(String message){
		getChild("messages").setDisplay(true).addChild(new EXContainer("li", "li").setText(message));
		return true;
	}
	private boolean validate(){
		getChild("messages").getChildren().clear();
		getChild("messages").setRendered(false);
		boolean result = false;
		String[]fields = new String[]{
				"companyCode:Please enter a company code", 
				"companyName:Please enter your company name", 
				"businessAddressLine1:Please enter the business address line 1 of your company", 
				"businessAddressLine2:Please enter the business address line 2 of your company", 
				"businessAddressLine3:Please enter the business address line 3 of your company", 
				"businessRegistrationNumber:Please enter your business registration number",
				"companyEmail:Please enter the email of your company", 
				"companyPhone:Please enter the phone number of your company", 
				"natureOfBusiness:Please enter the nature of your business", 
				"summary:Please enter a brief description of your business"};
		
		
		String password =  getValue("password");
		if(!StringUtil.isNotEmpty(password) && password.length() < 8){
			
			addMessage("Password should be at least 8 characters long");
			result = true;
			
		}
		
		for(String s :fields){
			String[] as = StringUtil.split(s, ":");
			if(!StringUtil.isNotEmpty(getValue(as[0]))){
				 addMessage(as[1]) ;
				result = true;
			}
		}
		Merchant currentMerchant = MallUtil.getCurrentMerchant();
		String code=  getValue("companyCode");
		QueryParameters params = new QueryParameters().setEntity(Merchant.class).addRestriction(Restrictions.eq("code",code ));
		List<File> merchants = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		if(merchants.size() > 0 && !((Merchant)merchants.get(0)).getUsername().equals(currentMerchant.getUsername())){
			addMessage("Please enter a different company code. This one already exists");
			result = true;
		}
		
		params = new QueryParameters().setEntity(Merchant.class).addRestriction(Restrictions.eq("email",code ));
		merchants = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		if(merchants.size() > 0 && !((Merchant)merchants.get(0)).getUsername().equals(currentMerchant.getUsername())){
			addMessage("We already have a company with this email in our database. Please choose another one");
			result = true;
		}
		return result;
	}
	
	public void save(Container source){
		if(validate()){
			return;
		}
		
		User u = MallUtil.getCurrentUser().getUser();
		u.setPassword(getValue("password"));
		u.setFirstName(getValue("firstName"));
		u.setLastName(getValue("lastName"));
		u.setEmail(getValue("email"));
		u.setPhone(getValue("phone"));
		u.setMobile(getValue("mobile"));
		
		u.setMerchant(true);
		SpringUtil.getSecurityService().saveOrUpdateUser(u);
		
		
		Merchant merchant = MallUtil.getCurrentMerchant();
		merchant.setAddressLine1(getValue("businessAddressLine1"));
		merchant.setAddressLine2(getValue("businessAddressLine2"));
		merchant.setCity(getValue("businessAddressLine3"));
		merchant.setEmail(getValue("companyEmail"));
		merchant.setPhone(getValue("companyPhone"));
		merchant.setMobilePhone(getValue("companyMobilePhone"));
		merchant.setBusinessRegistrationNumber(getValue("businessRegistrationNumber"));
		merchant.setVatRegistrationNumber(getValue("vatRegistrationNumber"));
		merchant.setWebsite(getValue("website"));
		merchant.setFax(getValue("companyFax"));
		merchant.setNature(getValue("natureOfBusiness"));
		merchant.setSummary(getValue("summary"));
		
		
		//SimpleKeyValuePair sk = (SimpleKeyValuePair)((EXSelect)getDescendentOfType(EXCompanySetting.class).getDescendentByName("businessCountry")).getValue();
		
		merchant.setCountry("MU,Mauritius");
		
		//SimpleKeyValuePair cur = (SimpleKeyValuePair)((EXSelect)getDescendentOfType(EXCompanySetting.class).getDescendentByName("businessCurrency")).getValue();
		
		merchant.setCurrency("MUR");
		
		
		//SimpleKeyValuePair language = (SimpleKeyValuePair)((EXSelect)getDescendentOfType(EXCompanySetting.class).getDescendentByName("defaultLanguage")).getValue();
		
		merchant.setLanguage("English");
		
		String companyName =  getValue("companyName");
		merchant.setCompanyName(companyName);
		merchant.setTitle(companyName);
		merchant.setCode(getValue("companyCode"));
		merchant.setBannerUrl(getDescendentOfType(EXCompanySetting.class).getChild("banner").getAttribute("src"));
		merchant.setLogoUrl(getDescendentOfType(EXCompanySetting.class).getChild("logo").getAttribute("src"));
		
		EXDeliveryOptions doptions= getDescendentOfType(EXDeliveryOptions.class);
		doptions.createOrUpdate(merchant);
		
		
		List<Container> cats=  getDescendentOfType(EXCompanyCategories.class).getDescendentByName("selectedCategories").getChildren();
		merchant.setCategory("");
		merchant.setCategory_1("");
		merchant.setCategory_2("");
		merchant.setCategory_3("");
		merchant.setCategory_4("");
		if(cats.size() >0){
			merchant.setCategory(cats.get(0).getText());
		} if(cats.size() > 1){
			merchant.setCategory_1(cats.get(1).getText());
		} if(cats.size() > 2){
			merchant.setCategory_2(cats.get(2).getText());
		} if(cats.size() > 2){
			merchant.setCategory_2(cats.get(2).getText());
		} if(cats.size() > 3){
			merchant.setCategory_3(cats.get(3).getText());
		} if(cats.size() > 4){
			merchant.setCategory_4(cats.get(4).getText());
		}
		
		merchant.save();
		
		
		
		addMessage("Company settings correctly saved");
		//SpringUtil.getRepositoryService().update(merchant, Util.getRemoteUser());
		
	}
	@Override
	public void executeAction(Container source) {
		
		if(!source.getName().equals("save")){
			for(Container c : getChildren()){
				if(c instanceof EXXHTMLFragment){
					c.setDisplay(false);
				}
			}
		}
		if(source.getName().contains("showShopSetting")){
			getDescendentOfType(EXCompanySetting.class).setDisplay(true);
			getDescendentByName("showShopSetting").addClass("selected");
			getDescendentByName("showContactSetting").removeClass("selected");
			getDescendentByName("showCategories").removeClass("selected");
		}else if(source.getName().contains("showContactSetting")){
			getDescendentByName("showShopSetting").removeClass("selected");
			getDescendentByName("showContactSetting").addClass("selected");
			getDescendentByName("showCategories").removeClass("selected");
			getDescendentOfType(EXContactSetting.class).setDisplay(true);
		}else if(source.getName().contains("showCategories")){
			getDescendentByName("showShopSetting").removeClass("selected");
			getDescendentByName("showContactSetting").removeClass("selected");
			getDescendentByName("showCategories").addClass("selected");
			getDescendentOfType(EXCompanyCategories.class).setDisplay(true);
		}else if(source.getName().contains("showOrganizationSetting")){
			getDescendentOfType(EXOrganizationSetting.class).setDisplay(true);
		}else if(source.getName().equalsIgnoreCase("save")){
			save(source);
		}
		
	}
	@Override
	public void addPopup(Container popup) {
		
		getChild("popupContainer").addChild(popup);
	}

}
