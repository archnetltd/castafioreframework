package org.castafiore.shoppingmall.ng.v2.registrations;

import java.util.Iterator;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.back.OSBarItem;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.ng.v2.AccordeoPanelModel;
import org.castafiore.shoppingmall.ng.v2.EXAccordeonPanel;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Directory;

public class MerchantRegistrationModel implements AccordeoPanelModel{

	private String[] labels = new String[]{"Company Information" ,"Description", "Categories", "Terms and Conditions"};

	@Override
	public int getSelectedTab() {
		
		return 0;
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		if(index == 0){
			return new EXUserInformation("ui");
		}else if(index == 5){
			return new EXCompanyInformation("ci");
		}else if(index == 1){
			return new EXCompanyRelevantInformation("cri");
		}else if(index == 2){
			return new EXCategories("cat");
		}else{
			return null;
		}
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return labels[index];
	}

	@Override
	public int size() {
		
		return labels.length;
	}
	public String getValue(String field, Container panel){
		return ((StatefullComponent)panel.getDescendentByName(field)).getValue().toString();
	}
	
	public void register(Container panel){
		//check username and password
		
		String username = ((EXInput)panel.getDescendentByName("user.username")).getValue().toString();
		String password = ((EXInput)panel.getDescendentByName("user.password")).getValue().toString();
		
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setFirstName(getValue("user.firstName", panel));
		u.setLastName(getValue("user.lastName", panel));
		u.setEmail(getValue("company.email", panel));
		u.setPhone(getValue("company.telephone", panel));
		
		u.setMerchant(true);
		Address add = new Address();
		add.setDefaultAddress(true);
		add.setCity(getValue("company.city", panel));
		
		
		
		
		add.setCountry("Mauritius");
		add.setCountryCode("MU");
		add.setLine1(getValue("company.addressline1", panel));
		add.setLine2(getValue("company.addressline2", panel));
		add.setCity(getValue("company.city", panel));
		 
		u.addAddress(add);
		try{
			SpringUtil.getSecurityService().registerUser(u);
			
			Merchant merchant = MallUtil.getCurrentMall().createMerchant(u.getUsername());//new Merchant();
			merchant.setTitle(getValue("company.name", panel));
			merchant.setEmail(u.getEmail());
			merchant.setMobilePhone(u.getPhone());
			merchant.setUsername(u.getUsername());
			merchant.setCompanyName(getValue("company.name", panel));
			merchant.setAddressLine1(getValue("company.addressline1", panel));
			merchant.setAddressLine2(getValue("company.addressline2", panel));
			merchant.setCity(getValue("company.city", panel));
			merchant.setPlan("Free");
			merchant.setStatus(12);
			
//			Directory apps = merchant.createFile("MyApplications", Directory.class);
//			
//			
//			Map<String, OSBarItem> myapps = SpringUtil.getApplicationContext().getBeansOfType(OSBarItem.class);
//			Iterator<String> iter = myapps.keySet().iterator();
//			while(iter.hasNext()){
//				OSBarItem item = myapps.get(iter.next());
//				Article app = apps.createFile(item.getAppName(), Article.class);
//				app.setTitle(item.getTitle());
//				app.setSummary(item.getIcon());
//			}
			panel.getDescendentOfType(EXCompanyCategories.class).fillMerchant(merchant);
			
			merchant.save();
			
			EXPanel f = new EXPanel("as", "Successfully registered");
			
			Container msg = new EXXHTMLFragment("", "templates/v2/registrations/Final.xhtml");
			f.setBody(msg);
			Container parent = panel.getParent();
			panel.remove();
			parent.addChild(f.setStyle("z-index", "4000").setStyle("width", "600px"));
			
			//setMessage("You have been successfully registered. Now close this window, then login with the username and password you chose");
			
			//EXCheckout co = getAncestorOfType(EXCheckout.class);
		}catch(Exception e){
			//setMessage("An unknow error has occured while registering user. Please try again later");
			e.printStackTrace();
		}
		
			
		
		
		
	}

	@Override
	public boolean onNext(Container fromBody,  int fromIndex,
			 EXAccordeonPanel panel) {

		if(fromIndex == 2){
			register(panel);
			
			
			
			
			
			
			
		}
		return true;
		
	
		
	}
	
}
