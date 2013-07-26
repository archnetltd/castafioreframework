package org.castafiore.shoppingmall.merchant;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class EXMerchantCardV2 extends EXContainer implements TabModel {

	
	private String[] labels = new String[]{"Description", "Contact Info", "Email"};
	
	public EXMerchantCardV2(String name, Merchant m) {
		
		super(name, "div");
		setAttribute("merchant", m.getUsername());
		addChild(new EXContainer("companyName", "h5").setText(m.getTitle()));
		
		addChild(new EXContainer("banner", "img").setStyle("width", "100%").setAttribute("src", m.getBannerUrl()));
		addChild(new EXContainer("logo", "img").setAttribute("src", m.getLogoUrl()));
		
		EXTabPanel panel = new EXTabPanel("panel", this);
		addChild(panel);
	}
	
	
	private Container getDescription(){
		Merchant m = MallUtil.getMerchant(getAttribute("merchant"));
		EXBorderLayoutContainer bd = new EXBorderLayoutContainer("desc");
		
		bd.addChild(new EXContainer("lblNature", "label").setStyle("display", "block").setText("Nature of Business"),EXBorderLayoutContainer.LEFT);
		bd.addChild(new EXContainer("lblNature", "span").setStyle("display", "block").setText(m.getNature()),EXBorderLayoutContainer.LEFT);
		
		bd.addChild(new EXContainer("lblNature", "label").setStyle("display", "block").setText("Categories"),EXBorderLayoutContainer.LEFT);
	
		bd.addChild(new EXContainer("lblNature", "span").setStyle("display", "block").setText(m.getCategory()),EXBorderLayoutContainer.LEFT);
		bd.addChild(new EXContainer("lblNature", "span").setStyle("display", "block").setText(m.getCategory_1()),EXBorderLayoutContainer.LEFT);
		bd.addChild(new EXContainer("lblNature", "span").setStyle("display", "block").setText(m.getCategory_2()),EXBorderLayoutContainer.LEFT);
		bd.addChild(new EXContainer("lblNature", "span").setStyle("display", "block").setText(m.getCategory_3()),EXBorderLayoutContainer.LEFT);
		bd.addChild(new EXContainer("lblNature", "span").setStyle("display", "block").setText(m.getCategory_4()),EXBorderLayoutContainer.LEFT);
		
		bd.addChild(new EXContainer("lblNature", "label").setStyle("display", "block").setText("Description"),EXBorderLayoutContainer.RIGHT);
		bd.addChild(new EXContainer("description", "p").setStyle("display", "block").setText(m.getSummary()), EXBorderLayoutContainer.RIGHT);
		
		return bd;
		
		
	}
	
	
	private Container getContactInfo(){
		Merchant m = MallUtil.getMerchant(getAttribute("merchant"));
		Container c = new EXContainer("", "div");
		EXFieldSet contact = new EXFieldSet("contact", "Contact person", true);
		User u = SpringUtil.getSecurityService().loadUserByUsername(m.getUsername());
		contact.addField("Name :", new EXLabel("name", u.getFirstName() + " " + u.getLastName()));
		contact.addField("Phone :", new EXLabel("phone", u.getPhone()));
		contact.addField("Mobile :", new EXLabel("mobile", u.getMobile()));
		contact.addField("Email :", new EXLabel("email", u.getEmail()));
		c.addChild(contact);
		
		
		EXFieldSet add = new EXFieldSet("add", "Address", true);
		add.addField("Address Line 1 :", new EXLabel("line1", m.getAddressLine1()));
		add.addField("Address Line 2 :", new EXLabel("line2", m.getAddressLine2()));
		add.addField("City :", new EXLabel("city", m.getCity()));
		add.addField("Postal Code :", new EXLabel("postalCode", m.getZipPostalCode()));
		add.addField("Country :", new EXLabel("country", m.getCountry()));
		
		add.addField("Mobile :", new EXLabel("mobile", m.getMobilePhone()));
		add.addField("Phone :", new EXLabel("phone", m.getPhone()));
		add.addField("Email :", new EXLabel("email", m.getEmail()));
		add.addField("Website :", new EXLabel("website", m.getWebsite()));
		c.addChild(add);
		
		return c;
	}
	
	private Container getEmail(){
		Merchant m = MallUtil.getMerchant(getAttribute("merchant"));
		return new EXContainer("", "div");
	}
	
	
	
	
	
	

	@Override
	public int getSelectedTab() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		if(index == 0)
			return getDescription();
		else if(index == 1)
			return getContactInfo();
		else
			return getEmail();
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return labels[index];
	}

	@Override
	public int size() {
		return labels.length;
	}

}
