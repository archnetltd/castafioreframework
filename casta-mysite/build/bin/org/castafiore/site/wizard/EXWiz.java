package org.castafiore.site.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.castafiore.catalogue.Product;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.back.OSBarItem;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ChannelUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.webwizard.WebWizardService;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Link;
import org.hibernate.criterion.Restrictions;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EXWiz extends EXXHTMLFragment implements Event{

	public EXWiz(String name) {
		super(name, "webos/gs/EXWiz.xhtml");
		addChild(new EXContainer("body", "div").addClass("ui-widget-content").addChild(new EXUserInfo("userinfo")));
		
		addChild(new EXButton("next", "Next").addEvent(this, Event.CLICK).setStyle("float", "right"));
		addChild(new EXButton("back", "Back").addEvent(this, Event.CLICK).setStyle("float", "left"));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}
	
	public String getVal(String name){
		return ((EXInput)getDescendentByName(name)).getValue().toString();
	}
	
	
	private void sendMail(String to, String username, String password){
		try{
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject("Registration information for emall of mauritius (ArchNet ltd)");
			helper.setFrom("contact@archnetltd.com");
			helper.setTo(to);
			String content = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.xhtml"));
			content = content.replace("${username}", username);
			content = content.replace("${password}", username);
			helper.setText(content, true);
			sender.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public User createCompany(){
		try{
			
			User user = new User();
			user.setUsername(getVal("cousername"));
			user.setPassword(getVal("copassword"));
			user.setFirstName(getVal("firstName"));
			user.setLastName(getVal("lastName"));
			user.setEmail(getVal("email"));
			user.setMobile(getVal("phone"));
			user.setPhone(getVal("phone"));
			user.setFax(getVal("fax"));
			user.setEnabled(true);
			user.setMerchant(true);
			user.setOrganization(user.getUsername());
			SecurityService service = SpringUtil.getSecurityService();
			
			
		
			service.registerUser(user);
			
			service.assignSecurity(user.getUsername(), "member", "users", user.getUsername(), true);
			service.assignSecurity(user.getUsername(), "member", "merchants", user.getUsername(), true);
			
			//service.login(userdate.getUsername(), userdate.getPassword());
			
			Merchant merchant = MallUtil.getCurrentMall().createMerchant(user.getUsername());//new Merchant();
			merchant.setTitle(getVal("companyName"));
			merchant.setEmail(getVal("email"));
			merchant.setMobilePhone(getVal("phone"));
			merchant.setUsername(user.getUsername());
			merchant.setCompanyName(getVal("companyName"));
			merchant.setAddressLine1(getVal("addressLine1"));
			merchant.setAddressLine2(getVal("addressLine2"));
			merchant.setCity(getVal("city"));
			//merchant.setWebsite("http://www.emallofmauritius.com/ecommerce.jsp?m=" + user.getUsername());
			merchant.setStatus(12);
			//merchant.setBannerUrl(bannerUrl);
			
			
			
			List<Container> cats = getDescendentByName("selectedCategories").getChildren();
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
			
			
			
			EXTextures tes = getDescendentOfType(EXTextures.class);
			String banner = getDescendentOfType(EXSelectBanner.class).getUrl();
			String menu = getDescendentOfType(EXSelectMenu.class).getSelected();
			
			DesignableUtil.generateSite(getRoot(), user.getUsername(),tes.getLogo(), tes.getBodyTexture(),banner,menu, merchant.getTitle());
			
			merchant.setLogoUrl(tes.getLogo());
			
			
Directory apps = merchant.createFile("MyApplications", Directory.class);
			
			
			Map<String, OSBarItem> myapps = SpringUtil.getApplicationContext().getBeansOfType(OSBarItem.class);
			Iterator<String> iter = myapps.keySet().iterator();
			while(iter.hasNext()){
				OSBarItem item = myapps.get(iter.next());
				Article app = apps.createFile(item.getAppName(), Article.class);
				app.setTitle(item.getTitle());
				app.setSummary(item.getIcon());
			}
			
			
			merchant.save();
			
			//createWorkflow(merchant.getUsername());
			//createSampleProducts(user);
			
			if(StringUtil.isNotEmpty(merchant.getEmail())){
				sendMail(merchant.getEmail(), merchant.getUsername(), user.getPassword());
			}
			
			return user;
			
			
		}catch(Exception e){
			throw new UIException(e);
			//e.printStackTrace();
		}
	}
	
	
	private void createWorkflow(String username)throws Exception{
		String dir = SpringUtil.getBeanOfType(WebWizardService.class).getWorkflowDir();
		
		Directory u = SpringUtil.getRepositoryService().getDirectory("/root/users/" + username, Util.getRemoteUser());
		Directory w = u.createFile("workflow", Directory.class);
		
		
		
		for(File f : new File(dir).listFiles()){
			if(!f.isDirectory()){
				BinaryFile bf = w.createFile(f.getName(), BinaryFile.class);
				InputStream in = new FileInputStream(f);
				
				OutputStream out = bf.getOutputStream();
				ChannelUtil.TransferData(in, out);
			}
		}
		u.save();
	}
	
	private void createSampleProducts(User user)throws Exception{
		Class f = Class.forName("org.castafiore.catalogue.Product");
		QueryParameters param = 	new QueryParameters().setEntity(f).addRestriction(Restrictions.eq("providedBy", SpringUtil.getBeanOfType(WebWizardService.class).getTemplateUsername()));
		
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
			np.setCode(p.getCode());
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
			np.setTitle(p.getTitle());
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
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("next")){
			
			if(getChild("body").getChildren().size() == 1){
				if(getDescendentOfType(EXUserInfo.class).validate()){
					getChild("body").getChildByIndex(0).setDisplay(false);
					getChild("body").addChild(new EXTextures("textures"));
					getChild("next").setText("Next");
				}
			}else if(getChild("body").getChildren().size() == 2){
				getChild("body").getChildByIndex(1).setDisplay(false);
				getChild("body").addChild(new EXSelectBanner("selectbanner"));
				getChild("next").setText("Next");
			}else if(getChild("body").getChildren().size() == 3){
				getChild("body").getChildByIndex(2).setDisplay(false);
				getChild("body").addChild(new EXSelectMenu("selectMenus"));
				getChild("next").setText("Next");
			}else if(getChild("body").getChildren().size() == 4){
				getChild("body").getChildByIndex(3).setDisplay(false);
				getChild("body").addChild(new EXCategories("cats"));
				getChild("next").setText("Finish");
			}else if(getChild("body").getChildren().size() == 5){
				if(getDescendentOfType(EXCategories.class).validate()){
					User u =createCompany();
					getChild("body").getChildByIndex(4).setDisplay(false);
					getChild("body").addChild(new EXFinal("final",u.getUsername()));
					getChild("next").setDisplay(false);
					getChild("back").setDisplay(false);
					
				}
			}
			
		}else if(container.getName().equals("back")){
			if(getChild("body").getChildren().size() == 2){
				//validate textures done
				getChild("body").getChildByIndex(1).remove();
				getChild("body").getChildByIndex(0).setDisplay(true);
				getChild("next").setText("Next");
			}else if(getChild("body").getChildren().size() == 3){
					getChild("body").getChildByIndex(2).remove();
					getChild("body").getChildByIndex(1).setDisplay(true);
					getChild("next").setText("Next");
			}
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
