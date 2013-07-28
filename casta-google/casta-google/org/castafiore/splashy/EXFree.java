package org.castafiore.splashy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.castafiore.catalogue.Product;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.back.OSBarItem;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.splashy.templates.EXSplashyTemplatesApplication;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Link;
import org.hibernate.criterion.Restrictions;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EXFree extends EXXHTMLFragment implements Event{

	public EXFree(String name) {
		super(name, "templates/splashy/EXFree.xhtml");
		addClass("container_12");
		addChild(new EXInput("email").addEvent(this, BLUR));
		addChild(new EXPassword("password"));
		addChild(new EXInput("firstName"));
		addChild(new EXInput("lastName"));
		addChild(new EXInput("phone"));
		addChild(new EXInput("mobile"));
		addChild(new EXSelect("country", new DefaultDataModel<Object>().addItem("Mauritius", "Other")));
		addChild(new EXContainer("save","a").addClass("pro_btn").setText("Register").setAttribute("href", "#s").addEvent(this, CLICK));
		addChild(new EXContainer("back","a").addClass("pro_btn").setText("Select another template").setAttribute("href", "#s").addEvent(this, CLICK));
		addChild(new EXCheckBox("accept"));
		addChild(new EXContainer("message", "p").setStyleClass("pro_info pro_info_warning grid_6").setStyle("margin", "0 0 30px").setStyle("display", "none"));
		
	}
	
	public String getVal(String name){
		return ((StatefullComponent)getDescendentByName(name)).getValue().toString();
	}
	
	public User createCompany(Product product){
		try{
			
			User user = new User();
			user.setUsername(getVal("email"));
			user.setPassword(getVal("password"));
			user.setFirstName(getVal("firstName"));
			user.setLastName(getVal("lastName"));
			user.setEmail(getVal("email"));
			user.setMobile(getVal("mobile"));
			user.setPhone(getVal("phone"));
			user.setEnabled(true);
			user.setMerchant(true);
			user.setOrganization(user.getUsername());
			SecurityService service = SpringUtil.getSecurityService();
			
			
		
			service.registerUser(user);
			
			service.assignSecurity(user.getUsername(), "member", "users", user.getUsername(), true);
			service.assignSecurity(user.getUsername(), "member", "merchants", user.getUsername(), true);
			
			service.login(user.getUsername(), user.getPassword());
			
			Merchant merchant = MallUtil.getCurrentMall().createMerchant(user.getUsername());//new Merchant();
			merchant.setEmail(getVal("email"));
			merchant.setMobilePhone(getVal("mobile"));
			merchant.setUsername(user.getUsername());
			merchant.setCountry(getVal("country"));
			merchant.setStatus(12);
			
			//should we generate a site? or genetate a site based on the template selected
			
			
			for(BinaryFile ptl : product.getFiles(BinaryFile.class).toList()){
				if(ptl.getName().endsWith(".ptl")){
					useTemplate(ptl, user.getUsername());
					break;
				}
			}
			//generateSite( user.getUsername());
			
			Directory apps = merchant.createFile("MyApplications", Directory.class);
			
			
			Map<String, OSBarItem> myapps = SpringUtil.getApplicationContext().getBeansOfType(OSBarItem.class);
			Iterator<String> iter = myapps.keySet().iterator();
			
			List<String> freeApps = new ArrayList<String>();
			freeApps.add("ShopSetting");
			freeApps.add("Inventory");
			freeApps.add("Designer");
			freeApps.add("FileExplorer");
			freeApps.add("Organization");
			freeApps.add("Registry");
			
			while(iter.hasNext()){
				OSBarItem item = myapps.get(iter.next());
				if(freeApps.contains(item.getAppName())){
					try{
					Article app = apps.createFile(item.getAppName(), Article.class);
					app.setTitle(item.getTitle());
					app.setSummary(item.getIcon());
					}catch(Exception e){
						
					}
				}
			}
			
			
			merchant.save();
			
			createSampleProducts(user);
			
			if(StringUtil.isNotEmpty(merchant.getEmail())){
				sendMail(merchant.getEmail(), merchant.getUsername(), user.getPassword());
			}
			
			return user;
			
			
		}catch(Exception e){
			return null;
		}
	}
	
	private void createSampleProducts(User user)throws Exception{
		Class f = Class.forName("org.castafiore.catalogue.Product");
		QueryParameters param = 	new QueryParameters().setEntity(f).addRestriction(Restrictions.like("code", "TST%")).addRestriction(Restrictions.eq("providedBy", "archnetltd"));
		
		List products = SpringUtil.getRepositoryService().executeQuery(param, Util.getRemoteUser());
		
		Directory parent = SpringUtil.getRepositoryService().getDirectory(ShoppingMallMerchant.PRODUCT_DRAFT_DIR.replace("$user", user.getUsername()),Util.getRemoteUser());
		
		
		
		
		for(Object o : products){
			Product p = (Product)o;
			Product np = parent.createFile(p.getName(), Product.class);
			np.makeOwner(user.getUsername());
			np.setProvidedBy(user.getUsername());
			np.setCategory(p.getCategory());
			np.setCategory_1(p.getCategory_1());
			np.setCategory_3(p.getCategory_3());
			np.setCategory_4(p.getCategory_4());
			np.setCode(p.getCode().replace("TST", ""));
			np.setCommentable(p.isCommentable());
			np.setContainsText(p.getContainsText());
			np.setCostPrice(p.getCostPrice());
			np.setCurrency(p.getCurrency());
			np.setCurrentQty(p.getCurrentQty());
			np.setDetail(p.getDetail());
			np.setHeight(p.getHeight());
			np.setLength(p.getLength());
			np.setMade(p.getMade());
			np.setReorderLevel(p.getReorderLevel());
			np.setStatus(Product.STATE_PUBLISHED);
			np.setCategory(p.getCategory());
			np.setSubCategory_1(p.getSubCategory_1());
			np.setSubCategory_3(p.getSubCategory_3());
			np.setSubCategory_4(p.getSubCategory_4());
			np.setSummary(p.getSummary());
			np.setTaxRate(p.getTaxRate());
			np.setTitle("Demo - " +p.getTitle());
			np.setTotalPrice(p.getTotalPrice());
			np.setWeight(p.getWeight());
			np.setWidth(p.getWidth());
			
			for(Link l : p.getImages().toList()){
				Link nl = np.createFile(l.getName(), Link.class);
				nl.setUrl(l.getUrl());
			}
			parent.save();
		}
	}
	
	
	private static void useTemplate(BinaryFile ptl, String byUser)throws Exception{
		DesignableUtil.copyProject(ptl, "/root/users/" + byUser);
	}
	
	
	private static BinaryFile copyFile(Directory fDest, byte[] data, String name)throws Exception{
		BinaryFile bf = fDest.createFile(name,BinaryFile.class);
		bf.write(data);
		return bf;
	}
	public static BinaryFile generateSite(String username )throws Exception{
		String portalName = "site.ptl";
		RepositoryService service = SpringUtil.getRepositoryService();
		String rootDir = "/root/users/" + username;
		Directory dir = service.getDirectory(rootDir ,username);
		
		Directory portalData = dir.createFile(portalName.replace(".ptl", "") + "_files", Directory.class);
		portalData.makeOwner(username);
		
		Directory templates = portalData.createFile("templates", Directory.class);
		templates.makeOwner(username);

		//copy templates
		String[] files = new String[]{"Copyright.xhtml", "Article.xhtml", "TitleWithImage.xhtml", "Products.xhtml", "TitleWithList.xhtml", "TitleWithParagraph.xhtml"};
		for(String  f : files){
			byte[] bytes = IOUtil.getStreamContentAsBytes(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/splashy/" +f));
			copyFile(templates, bytes, f).getAbsolutePath();
			//copyFile(templates, data, name)
		}
		
		//copy portal
		String emptyPortal = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/splashy/splashy.xml"));
		emptyPortal = emptyPortal.replace("${username}", username);
		
		BinaryFile ptl =copyFile(dir, emptyPortal.getBytes(), portalName);
		Directory pages = ptl.createFile("pages", Directory.class);
		pages.makeOwner(username);
		
		BinaryFile css = ptl.createFile("site.css", BinaryFile.class);
		css.write(" ".getBytes());

		//save home page
		String home = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/splashy/home.xml"));
		home = home.replace("${username}", username);
		copyFile(pages,home.getBytes(), "home");
		
		dir.save();
		return ptl;
	}
	
	private void sendMail(String to, String username, String password){
		try{
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject("Registration information for Web Splashy.com");
			helper.setFrom("kureem@gmail.com");
			helper.setTo(new String[]{to,"kureem@gmail.com"});
			String content = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/splashy/mail.xhtml"));
			content = content.replace("${username}", username);
			content = content.replace("${password}", username);
			helper.setText(content, true);
			sender.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setMessage(String message){
		getChild("message").setText(message).setStyle("display", "block");
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("back")){
			getAncestorOfType(EXSplashyTemplatesApplication.class).list();
		}
		
		if(container.getName().equalsIgnoreCase("email")){
			String email = getVal("email");
			if(StringUtil.isNotEmpty(email)){
				try{
				SpringUtil.getSecurityService().loadUserByUsername(email);
				setMessage("It seems that you have already registered.<br>Please click here if you forgot your password or choose another email");
				request.put("iid", container.getId());
				}catch(UsernameNotFoundException unfe){
					
				}
				
			}else{
				setMessage("Please enter an email");
				request.put("iid", container.getId());
			}
		}else if(container.getName().equalsIgnoreCase("password")){
			if(!StringUtil.isNotEmpty(getVal("password"))){
				setMessage("Please enter a password");
				request.put("iid", container.getId());
			}
		}else if(container.getName().equalsIgnoreCase("save")){
			if(getDescendentOfType(EXCheckBox.class).isChecked()){
				String select = getAncestorOfType(EXWebServletAwareApplication.class).getRequest().getSession().getAttribute("selectedTemplate").toString();
				
				Product p = (Product) SpringUtil.getRepositoryService().getFile(select, Util.getRemoteUser());
				createCompany(p);
				getAncestorOfType(EXSplashyTemplatesApplication.class).inviteFriends();
				
				//request.put("redirect", "os.html");
				//getAncestorOfType(EXSplashyTemplatesApplication.class).edit(p);
				
				//
//				getChild("message").setStyle("display", "block").setStyleClass("pro_info pro_info_success grid_6").setText("Congratulations. You have been successfully registered!");
//				Container root = getRoot();
//				root.getChildren().clear();
//				root.setRendered(false);
//				
//				root.addChild(new EXXHTMLFragment("success", "templates/splashy/EXSuccess.xhtml").addChild(new EXContainer("website", "a").setAttribute("target", "_blank").setText("Click Here").setAttribute("href", "site.jsp?m=" + getVal("email"))));
			}else{
				setMessage("You are requested to accept the terms and condition to register for our service");
			}
		}
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("iid")){
			container.mergeCommand(new ClientProxy("#" + request.get("iid")).addMethod("focus"));
		}
		if(request.containsKey("redirect")){
			container.redirectTo("os.html");
		}
		
	}

}
