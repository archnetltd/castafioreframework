package org.castafiore.site.wizard;

import java.util.Iterator;
import java.util.Map;

import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.back.OSBarItem;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.site.CollectedUserDate;
import org.castafiore.site.wizard.events.NextEvent;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Shortcut;

public class GetStartedPage extends EXXHTMLFragment {
	
	private final static String[] ITEMS = new String[]{"layout", "banner", "menu", "info"};
	
	private CollectedUserDate userdate = new CollectedUserDate();
	
	private final static String[] TITLES = new String[]{
			"Please select your preferred layout for your website",
			"Please choose a banner from the list provided",
			"We are almost done. Please choose a menu from the list provided",
			"Final step. We need some basic information about you to create your account"
		};
	
	
	private final static String[] SUB_TITLES = new String[]{
		"Note : You will be able to change your layout the way you want later",
		"Note : You will be able to change or upload your banner later",
		"Note : You will be able to change or build your menu later",
		"Note : YouDo will not give away any personal information about you"
	};
	
	
	private final static Container[] C_ITEMS = new EXContainer[]{
		
		new SelectLayout(),
		new SelectBanner(),
		new SelectMenu(),
		new UserInfo()
	};
	
	
	private int currentIndex =-1 ;

	public GetStartedPage(String name) {
		super(name, "youdo-templates/pages/GetStarted.xhtml");
		addChild(new EXContainer("title", "h4"));
		addChild(new EXContainer("sub-title" , "h5").addClass("welcome2"));
		addChild(new EXContainer("next", "a").addClass("coolButton").setText("Next").addEvent(new NextEvent(), Event.CLICK));
		addChild(new EXContainer("itemContainer", "div"));
		addChild(new Paginator());
		setCurrentIndex(0);
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		if(currentIndex != this.currentIndex){
			this.currentIndex = currentIndex;
			getChild("title").setText(TITLES[currentIndex]);
			getChild("sub-title").setText(SUB_TITLES[currentIndex]);
			getChild("itemContainer").getChildren().clear();
			Container item = C_ITEMS[currentIndex];
			getChild("itemContainer").addChild(item);
			getChild("itemContainer").setRendered(false);
			if(currentIndex == 3){
				
				getDescendentByName("next").setText("Finish");
			}else{
				
				getDescendentByName("next").setText("Next");
			}
			
			if(currentIndex == 0 || currentIndex == 3){
				getDescendentOfType(Paginator.class).setDisplay(false);
			}else{
				getDescendentOfType(Paginator.class).setDisplay(true);
			}
		}
	}
	
	
	public User moveNext(){
		if(currentIndex < C_ITEMS.length-1){
			setCurrentIndex((currentIndex+1));
			return null;
		}else{
			
			
			//load up userdata
			getDescendentOfType(UserInfo.class).fillUserData(this.userdate);
			User u = createCompany();
			return u;
		}
	}
	
	
	
	/**
	 * user accumulated userinfo to generate company.
	 */
	public User createCompany(){
		try{
			
			User user = new User();
			user.setUsername(this.userdate.getUsername());
			user.setPassword(userdate.getPassword());
			user.setFirstName(userdate.getContactName());
			user.setLastName(userdate.getContactLastName());
			user.setLastName(userdate.getUsername());
			user.setEmail(userdate.getEmail());
			user.setMobile(userdate.getMobilePhone());
			user.setPhone(userdate.getPhone());
			user.setEnabled(true);
			user.setMerchant(true);
			SecurityService service = SpringUtil.getSecurityService();
			
			service.registerUser(user);
			
			service.assignSecurity(user.getUsername(), "member", "users");
			service.assignSecurity(user.getUsername(), "member", "merchants");
			
			service.login(userdate.getUsername(), userdate.getPassword());
			
			Merchant merchant = MallUtil.getCurrentMall().createMerchant(userdate.getUsername());//new Merchant();
			merchant.setTitle(userdate.getCompanyName());
			merchant.setEmail(userdate.getEmail());
			merchant.setMobilePhone(userdate.getMobilePhone());
			merchant.setUsername(user.getUsername());
			merchant.setCompanyName(userdate.getCompanyName());
			
			//add applications
			Directory apps = merchant.createFile("MyApplications", Directory.class);
			
			
			Map<String, OSBarItem> myapps = SpringUtil.getApplicationContext().getBeansOfType(OSBarItem.class);
			Iterator<String> iter = myapps.keySet().iterator();
			while(iter.hasNext()){
				OSBarItem item = myapps.get(iter.next());
				Article app = apps.createFile(item.getAppName(), Article.class);
				app.setTitle(item.getTitle());
				app.setProperty("icon", item.getIcon());
			}
			
			
			merchant.save();
			
			DesignableUtil.generateSite((Application)getRoot(), userdate.getBanner(), userdate.getMenu(), "empty-portal.xml", "/root/users/" + userdate.getUsername(),"default.ptl");
			
			
			
			
			
//			//create Icons
//			String[] labels = new String[]{"Explorer", "Designer", "UserInfo", "Business", "Contacts", "Events", "Youtube", "Settings", "Logout"};
//			for(String label : labels){
//				Directory app = SpringUtil.getRepositoryService().getDirectory("/root/users/" + user.getUsername() + "/Applications", user.getUsername());
//				BinaryFile bf = app.createFile(label + ".app", BinaryFile.class);//new BinaryFile();
//				//bf.setName(label + ".app");
//				bf.makeOwner(user.getUsername());
//				
//				bf.write(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/webos/" + label + ".app"));
//				SpringUtil.getRepositoryService().update(app,  user.getUsername());
//				
//				
//				Directory desktop = SpringUtil.getRepositoryService().getDirectory("/root/users/" + user.getUsername() + "/Desktop", user.getUsername());
//				//Shortcut shortcut = new Shortcut();
//				//shortcut.setName(bf.getName());
//				Shortcut shortcut = desktop.createFile(bf.getName(), Shortcut.class);
//				shortcut.setReference(bf.getAbsolutePath());
//				shortcut.makeOwner(user.getUsername());
//				SpringUtil.getRepositoryService().update(desktop, user.getUsername());
//			}
			return user;
			
			
		}catch(Exception e){
			throw new UIException(e);
			//e.printStackTrace();
		}
	}

	public CollectedUserDate getUserdate() {
		return userdate;
	}

	public void setUserdate(CollectedUserDate userdate) {
		this.userdate = userdate;
	}
	
	
	

}
