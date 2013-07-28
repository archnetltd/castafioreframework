package org.erevolution.workflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.EXUpload;
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

public class ErevolutionUtil {

	private void sendMail(String to, String username, String password){
		try{
			JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject("Registration information for eRevolution");
			helper.setFrom("contact@erevolutiongroup.com");
			helper.setTo(to);
			String content = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/erevolution/registration/mail.xhtml"));
			content = content.replace("${username}", username);
			content = content.replace("${password}", password);
			helper.setText(content, true);
			sender.send(message);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static String getVal(String name,Container c){
		return ((StatefullComponent)c.getDescendentByName(name)).getValue().toString();
	}
	
	
	public static void exportExcel(Container c)throws Exception{
		Workbook wb = new HSSFWorkbook(new FileInputStream(new File("/usr/local/software/agents.xls")));
		Sheet s = wb.getSheetAt(0);
		for(int i =4; i <= s.getLastRowNum();i++){
			Row r = s.getRow(i);
			createCompany(r, c);
		}
	}
	
	public static void createCompany(Row row, Container c)throws Exception{
		try{
			
			String accountNumber = row.getCell(1).getStringCellValue();
			String name = row.getCell(2).getStringCellValue();
			String country = "Mozambique";
			String province = row.getCell(5).getStringCellValue();
			String city = row.getCell(4).getStringCellValue();
			String addLine1 = row.getCell(7).getStringCellValue();
			String type = row.getCell(3).getStringCellValue();
			String[] as = StringUtil.split(name, " ");
			String lastName = as[as.length-1];
			String firstName = name.replace(lastName, "").trim();
			String tier = "tier1";
			if(type.equalsIgnoreCase("Merchant")){
				tier = "tier2";
			}
			
			User user = new User();
			user.setUsername(accountNumber);
			user.setPassword(accountNumber);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(name.toLowerCase().replace(" ", ".") + "@erevolutiongroup.com");
			user.setMobile("");
			user.setPhone("");
			user.setFax("");
			user.setEnabled(true);
			user.setMerchant(true);
			user.setOrganization(user.getUsername());
			SecurityService service = SpringUtil.getSecurityService();
			
			
		
			service.registerUser(user);
			
			service.assignSecurity(user.getUsername(), "member", "users", user.getUsername(), true);
			service.assignSecurity(user.getUsername(), tier, "erevolution", user.getUsername(), true);
			
			
			
			Merchant merchant = MallUtil.getCurrentMall().createMerchant(user.getUsername());//new Merchant();
			merchant.setTitle(user.getOrganization());
			merchant.setEmail(user.getEmail());
			merchant.setMobilePhone(user.getPhone());
			merchant.setUsername(user.getOrganization());
			merchant.setCompanyName(user.getFirstName());
			merchant.setAddressLine1(addLine1);
			merchant.setAddressLine2(province);
			merchant.setCity(city);
			merchant.setCountry(country);
			merchant.setCurrency("MZN");
			merchant.setStatus(12);
			

			String banner = "https://lh5.googleusercontent.com/-0YGBUCiP0Uc/TX4kidYNsqI/AAAAAAAAABQ/0mqFEinzrUg/4.jpg";
			merchant.setBannerUrl(banner);
			String menu = "TabMenuI";
			
			String texture = "webos/gs/textures/4.jpg";
				
			String logo = "webos/gs/logos/logo043.png";
			DesignableUtil.generateSite(c.getRoot(), user.getUsername(),logo,texture,banner,menu, merchant.getTitle());
			
			merchant.setLogoUrl(logo);
			
			
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
			
			createWorkflow(merchant.getUsername(),tier);
			createSampleProducts(user);
			
			
			
			
		}catch(Exception e){
			throw new UIException(e);
			//e.printStackTrace();
		}
	}
	
	public User createCompany(Container c, String tier){
		try{
			
			User user = new User();
			user.setUsername(getVal("mobile",c).replace("+", "").replace(" ", ""));
			user.setPassword(getVal("password",c));
			user.setFirstName(getVal("firstName",c));
			user.setLastName(getVal("lastName",c));
			user.setEmail(getVal("email",c));
			user.setMobile(getVal("mobile",c));
			user.setPhone(getVal("phone",c));
			user.setFax(getVal("fax",c));
			user.setEnabled(true);
			user.setMerchant(true);
			user.setOrganization(user.getUsername());
			SecurityService service = SpringUtil.getSecurityService();
			
			
		
			service.registerUser(user);
			
			service.assignSecurity(user.getUsername(), "member", "users", user.getUsername(), true);
			service.assignSecurity(user.getUsername(), tier, "erevolution", user.getUsername(), true);
			
			
			
			Merchant merchant = MallUtil.getCurrentMall().createMerchant(user.getUsername());//new Merchant();
			merchant.setTitle(getVal("companyName",c));
			merchant.setEmail(getVal("email",c));
			merchant.setMobilePhone(getVal("phone",c));
			merchant.setUsername(user.getUsername());
			merchant.setCompanyName(getVal("companyName",c));
			merchant.setAddressLine1(getVal("addressLine1",c));
			merchant.setAddressLine2(getVal("addressLine2",c));
			merchant.setCity(getVal("city",c));
			
			merchant.setStatus(12);
			

			String banner = "https://lh5.googleusercontent.com/-0YGBUCiP0Uc/TX4kidYNsqI/AAAAAAAAABQ/0mqFEinzrUg/4.jpg";
			merchant.setBannerUrl(banner);
			String menu = "TabMenuI";
			
			String texture = "webos/gs/textures/4.jpg";
				
			String logo = "webos/gs/logos/logo043.png";
			DesignableUtil.generateSite(c.getRoot(), user.getUsername(),logo,texture,banner,menu, merchant.getTitle());
			
			merchant.setLogoUrl(logo);
			
			
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
			
			createWorkflow(merchant.getUsername(),tier);
			createSampleProducts(user);
			
			if(StringUtil.isNotEmpty(merchant.getEmail())){
				sendMail(merchant.getEmail(), merchant.getUsername(), user.getPassword());
			}
			
			return user;
			
			
		}catch(Exception e){
			throw new UIException(e);
			//e.printStackTrace();
		}
	}
	
	
	private static void createWorkflow(String username,String tier)throws Exception{
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
	
	private static void createSampleProducts(User user)throws Exception{
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
	
}
