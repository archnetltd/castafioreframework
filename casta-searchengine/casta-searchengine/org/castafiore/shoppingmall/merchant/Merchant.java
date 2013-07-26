package org.castafiore.shoppingmall.merchant;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.Entity;

import org.castafiore.accounting.CashBook;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.back.OSApplicationRegistry;
import org.castafiore.searchengine.back.OSBarItem;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.DeliveryOptions;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.crm.newsletter.Newsletter;
import org.castafiore.shoppingmall.employee.Employee;
import org.castafiore.shoppingmall.project.Project;
import org.castafiore.shoppingmall.user.ShoppingMallUserImpl;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.FileAlreadyExistException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.iterator.SimpleFileIterator;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Value;
import org.hibernate.annotations.Type;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

@Entity
public class Merchant extends Article {

	private String code;

	@Type(type = "text")
	private String username;

	// company email
	private String email;

	// company phone
	private String phone;

	// company mobile phone
	private String mobilePhone;

	private String fax;

	// company name as on Business registration card
	@Type(type = "text")
	private String companyName;

	// company name as on Business registration card
	@Type(type = "text")
	private String businessRegistrationNumber;

	@Type(type = "text")
	private String addressLine1;

	@Type(type = "text")
	private String addressLine2;

	@Type(type = "text")
	private String city;

	@Type(type = "text")
	private String logoUrl;

	@Type(type = "text")
	private String bannerUrl;

	@Type(type = "text")
	private String website;

	@Type(type = "text")
	private String vatRegistrationNumber;

	@Type(type = "text")
	private String nature;

	@Type(type = "text")
	private String category;

	@Type(type = "text")
	private String category_1;

	@Type(type = "text")
	private String category_2;

	@Type(type = "text")
	private String category_3;

	@Type(type = "text")
	private String category_4;

	@Type(type = "text")
	private String subCategory;

	@Type(type = "text")
	private String subCategory_1;

	@Type(type = "text")
	private String subCategory_2;

	@Type(type = "text")
	private String subCategory_3;

	@Type(type = "text")
	private String subCategory_4;

	// private BigDecimal creditsToRedeem;

	private String plan = "free";

	private String currency = "MUR";

	@Type(type = "text")
	private String country;

	private String zipPostalCode;

	private String language = Locale.getDefault().getLanguage();

	public String getPlan() {
		 if(plan == null){
		 return "corportate";
		 }
		 return plan;

		//return "corporate";
	}

	public String getLanguage() {
		if (language == null) {
			language = Locale.getDefault().getLanguage();
		}
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCurrency() {
		if (currency == null) {
			currency = "MUR";
		}
		return currency;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipPostalCode() {
		return zipPostalCode;
	}

	public void setZipPostalCode(String zipPostalCode) {
		this.zipPostalCode = zipPostalCode;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public DeliveryOptions getDeliveryOptions() {
		DeliveryOptions options = (DeliveryOptions) getFile("deliveryOptions");
		if (options == null) {
			options = createFile("deliveryOptions", DeliveryOptions.class);
			options.setUps(false);
			options.setTransportPayer("Shipper");
			save();
		}
		return options;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public boolean isUps() {
		DeliveryOptions options = (DeliveryOptions) getFile("deliveryOptions");
		if (options == null) {
			return false;
		} else {
			return options.isUps();
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory_1() {
		return category_1;
	}

	public void setCategory_1(String category_1) {
		this.category_1 = category_1;
	}

	public String getCategory_2() {
		return category_2;
	}

	public void setCategory_2(String category_2) {
		this.category_2 = category_2;
	}

	public String getCategory_3() {
		return category_3;
	}

	public void setCategory_3(String category_3) {
		this.category_3 = category_3;
	}

	public String getCategory_4() {
		return category_4;
	}

	public void setCategory_4(String category_4) {
		this.category_4 = category_4;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getSubCategory_1() {
		return subCategory_1;
	}

	public void setSubCategory_1(String subCategory_1) {
		this.subCategory_1 = subCategory_1;
	}

	public String getSubCategory_2() {
		return subCategory_2;
	}

	public void setSubCategory_2(String subCategory_2) {
		this.subCategory_2 = subCategory_2;
	}

	public String getSubCategory_3() {
		return subCategory_3;
	}

	public void setSubCategory_3(String subCategory_3) {
		this.subCategory_3 = subCategory_3;
	}

	public String getSubCategory_4() {
		return subCategory_4;
	}

	public void setSubCategory_4(String subCategory_4) {
		this.subCategory_4 = subCategory_4;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getVatRegistrationNumber() {
		return vatRegistrationNumber;
	}

	public void setVatRegistrationNumber(String vatRegistrationNumber) {
		this.vatRegistrationNumber = vatRegistrationNumber;
	}

	public Merchant() {
		super();
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBusinessRegistrationNumber() {
		return businessRegistrationNumber;
	}

	public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
		this.businessRegistrationNumber = businessRegistrationNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public MerchantSubscription subscribe(String usernamee) {
		try {

			FileIterator<Directory> cats = getSubscriptionCategories();
			Directory dir = cats.get(0);

			MerchantSubscription ms = dir.createFile(usernamee,
					MerchantSubscription.class);
			User u = SpringUtil.getSecurityService().loadUserByUsername(
					usernamee);
			ms.setSubscriber(usernamee);
			ms.setAddressLine1(u.getDefaultAddress().getLine1());
			ms.setAddressLine2(u.getDefaultAddress().getLine2());
			ms.setCity(u.getDefaultAddress().getCity());
			// ms.setCompany()
			ms.setCountry(u.getDefaultAddress().getCountry());
			ms.setEmail(u.getEmail());
			ms.setFax(u.getFax());
			ms.setFirstName(u.getFirstName());
			ms.setLastName(u.getLastName());
			ms.setMobile(u.getMobile());
			ms.setPhone(u.getPhone());
			ms.setZipPostalCode(u.getDefaultAddress().getPostalCode());
			ms.setMerchantUsername(this.username);
			ms.setOwner(this.username);
			ms.setTitle(u.getTitle());
			ms.setGender(u.getGender());
			ms.setMaritalStatus(u.getMaritalStatus());
			ms.setNic(u.getNic());
			ms.setDateOfBirth(u.getDateOfBirth());
			save();
			return ms;
		} catch (FileAlreadyExistException fae) {
			return (MerchantSubscription) getFile(username);
		}
	}

	public MerchantSubscription subscribe(BillingInformation bi, String username) {
		try {

			FileIterator<Directory> cats = getSubscriptionCategories();
			Directory dir = cats.get(0);
			// String gen = StringUtil.nextString(10);
			// while(true){
			// try{
			// User t = SpringUtil.getSecurityService().loadUserByUsername(gen);
			// if(t != null){
			// gen = StringUtil.nextString(10);
			// }
			// }catch(Exception e){
			// break;
			// }
			// }
			bi.setUsername(username);
			MerchantSubscription ms = dir.createFile(bi.getUsername(),
					MerchantSubscription.class);
			ms.setAddressLine1(bi.getAddressLine1());
			ms.setAddressLine2(bi.getAddressLine2());
			ms.setCity(bi.getCity());
			ms.setCompany(bi.getCompany());
			ms.setCountry(bi.getCountry());
			ms.setEmail(bi.getEmail());
			ms.setFax(bi.getFax());
			ms.setFirstName(bi.getFirstName());
			ms.setLastName(bi.getLastName());
			ms.setMobile(bi.getMobile());
			ms.setPhone(bi.getPhone());
			ms.setZipPostalCode(bi.getZipPostalCode());
			ms.setMerchantUsername(this.username);
			ms.setOwner(this.username);
			ms.setTitle(bi.getTitle());
			ms.setGender(bi.getGender());
			ms.setMaritalStatus(bi.getMaritalStatus());
			ms.setSubscriber(username);
			try {
				ms.setContainsText(bi.getProperty("applicationForm"));
			} catch (Exception e) {

			}

			User u = new User();
			Address a = new Address();
			a.setCity(ms.getCity());
			a.setCountry(ms.getCountry());
			a.setDefaultAddress(true);
			a.setLine1(ms.getAddressLine1());
			a.setLine2(ms.getAddressLine2());
			a.setPostalCode(ms.getZipPostalCode());
			u.addAddress(a);
			u.setEmail(ms.getEmail());
			u.setFax(ms.getFax());
			u.setFirstName(u.getFirstName());
			u.setGender(ms.getGender());
			u.setLastName(ms.getLastName());
			u.setMaritalStatus(ms.getMaritalStatus());
			u.setMobile(ms.getMobile());
			// the subscribed user do not form part of the organization
			// u.setOrganization(Util.getLoggedOrganization());
			u.setPhone(ms.getPhone());
			u.setTitle(ms.getTitle());
			u.setDateOfBirth(ms.getDateOfBirth());
			u.setNic(ms.getNic());

			u.setUsername(username);
			u.setPassword(username);

			SpringUtil.getSecurityService().registerUser(u);

			return ms;
		} catch (FileAlreadyExistException fae) {
			return (MerchantSubscription) getFile(username);
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

	public void unSubscribe(String username) {
		MerchantSubscription subs = getSubscription(username);
		if (subs != null) {
			Directory parent = subs.getParent();
			subs.remove();
			parent.save();
		}
	}

	public MerchantSubscription getSubscription(String username) {
		if(username == null){
			return null;
		}
		List result = SpringUtil.getRepositoryService()
				.executeQuery(
						new QueryParameters().setEntity(
								MerchantSubscription.class).addRestriction(
								Restrictions.eq("subscriber", username))
								.addRestriction(
										Restrictions.eq("merchantUsername",
												getUsername())),
						Util.getRemoteUser());
		if (result.size() > 0) {
			return (MerchantSubscription) result.get(0);
		} else {
			return null;
		}
	}

	public CashBook createCashBook(String name) {
		File f = getFile(name);
		if (f == null) {
			CashBook cb = createFile(name, CashBook.class);
			cb.setTitle(name);
			return cb;
		}
		return (CashBook) f;

	}

	public CashBook getCashBook(String name) {
		File f = getFile(name);
		if (f == null) {
			return null;
		}
		return (CashBook) f;
	}

	public boolean hasCashBook(String name) {
		if (getFile(name) != null) {
			return true;
		}
		return false;
	}

	public FileIterator<MerchantSubscription> getSubscriptions(String category) {
		for (Directory cat : getSubscriptionCategories().toList()) {
			if (cat.getName() == null) {
				cat.setName("Default");
			}
			if (cat.getName().equals(category)) {
				return cat.getFiles(MerchantSubscription.class);
			}
		}
		return null;
	}

	public List<MerchantSubscription> searchSubscriptions(String text) {
		// search users with stuffs
		// from merchanSubscription where firstName like '', lastName
		// like'',.......

		if (StringUtil.isNotEmpty(text.trim())) {
			// QueryParameters params = new
			// QueryParameters().setEntity(MerchantSubscription.class).addRestriction(Restrictions.or(gl("lastName",
			// text), Restrictions.or(gl("firstName", text),
			// Restrictions.or(gl("addressLine1",
			// text),Restrictions.or(gl("addressLine2", text),
			// Restrictions.or(gl("city", text), gl("nic", "text")) )))));
			String hql = "from MerchantSubscription where lastName like '%"
					+ text + "%' or firstName like '%" + text + "%'";

			hql = appendOr(hql, text, "title");
			hql = appendOr(hql, text, "subscriber");
			hql = appendOr(hql, text, "addressLine1");
			hql = appendOr(hql, text, "addressLine2");
			hql = appendOr(hql, text, "city");
			hql = appendOr(hql, text, "country");
			hql = appendOr(hql, text, "zipPostalCode");
			hql = appendOr(hql, text, "containsText");

			List result = SpringUtil.getBeanOfType(Dao.class)
					.getReadOnlySession().createQuery(hql).list();// SpringUtil.getRepositoryService().executeQuery(params,
																	// Util.getRemoteUser());

			return result;
		} else {
			return getSubscribers("Default");
		}

		// search orders containing stuffs
	}

	private String appendOr(String hql, String text, String field) {
		hql = hql + " or " + field + " like'%" + text + "%'";
		return hql;

	}

	private Criterion gl(String field, String text) {
		return Restrictions.ilike(field, "%" + text + "%");
	}

	public void addCategory(String category) {
		Directory dir = (Directory) getFile("subscriptionCategories");
		if (dir == null) {
			dir = createFile("subscriptionCategories", Directory.class);
			dir.createFile("Default", Directory.class);

		}
		if (dir.getFile(category) == null)
			dir.createFile(category, Directory.class);
		save();
	}

	public FileIterator<Directory> getSubscriptionCategories() {
		Directory dir = (Directory) getFile("subscriptionCategories");
		if (dir == null) {
			dir = createFile("subscriptionCategories", Directory.class);
			dir.createFile("Default", Directory.class);
			save();
		}
		return dir.getFiles(Directory.class);

	}

	public Directory getSubscriptionCategory(String category) {
		Directory dir = (Directory) getFile("subscriptionCategories");
		if (dir == null) {
			dir = createFile("subscriptionCategories", Directory.class);
			dir.createFile("Default", Directory.class);
			save();
		}
		return (Directory) dir.getFile(category);

	}

	public FileIterator<Project> getProjects() {
		Directory projectDir = getFile("projects", Directory.class);
		if (projectDir != null) {
			return projectDir.getFiles(Project.class);
		} else {
			projectDir = createFile("projects", Directory.class);
			projectDir.createFile("Working", Project.class).setTitle("Working");
			projectDir.createFile("Sick Leaves", Project.class).setTitle(
					"Sick Leaves");
			projectDir.createFile("Local Leaves", Project.class).setTitle(
					"Local Leaves");

			projectDir.save();

			return getProjects();

		}
	}

	public Project createProject(String name, String title, String description) {
		Project p = createFileInDir(name, Project.class, "projects");
		p.setTitle(title);
		p.setSummary(description);
		return p;
	}

	public List<MerchantSubscription> getSubscribers(String cat) {
		FileIterator<MerchantSubscription> subss = getSubscriptions(cat);
		return subss.toList();
		// List<String> usernames = new ArrayList<String>(subss.count());
		//		
		// while(subss.hasNext()){
		// usernames.add(subss.next().getSubscriber());
		// }
		//		
		// return SpringUtil.getSecurityService().loadUsers(usernames);
	}

	public List<MerchantSubscription> getSubscribers(List<String> cats) {
		List<String> usernames = new ArrayList<String>();
		List<MerchantSubscription> result = new ArrayList<MerchantSubscription>();
		for (String cat : cats) {
			FileIterator<MerchantSubscription> subss = getSubscriptions(cat);
			result.addAll(subss.toList());
			// while(subss.hasNext()){
			// usernames.add(subss.next().getSubscriber());
			// }
		}

		// return SpringUtil.getSecurityService().loadUsers(usernames);
		return result;
	}

	public Newsletter createNewsletter(String name) {
		Directory newsletters = (Directory) getFile("newsletters");
		if (newsletters == null) {
			newsletters = createFile("newsletters", Directory.class);
			save();
		}

		return newsletters.createFile(name, Newsletter.class);
	}

	public FileIterator<Newsletter> getNewsletters() {
		Directory newsletters = (Directory) getFile("newsletters");
		if (newsletters != null) {
			return newsletters.getFiles(Newsletter.class);
		}
		FileIterator result = new SimpleFileIterator(new ArrayList<File>());
		return result;
	}

	public FileIterator<Newsletter> getNewsletters(final int state) {
		Directory newsletters = (Directory) getFile("newsletters");
		if (newsletters != null) {
			FileIterator result = getFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					if ((pathname instanceof Newsletter)
							&& pathname.getStatus() == state) {
						return true;
					} else {
						return false;
					}
				}
			});
			return result;
		}
		FileIterator result = new SimpleFileIterator(new ArrayList<File>());
		return result;
	}

	public FileIterator<BinaryFile> getOptionTemplates() {
		Directory optionTemplates = (Directory) getFile("optionTemplates");
		if (optionTemplates != null) {
			return optionTemplates.getFiles(BinaryFile.class);
		}
		FileIterator result = new SimpleFileIterator(new ArrayList<File>());
		return result;
	}

	public void addOptionTemplate(String name, InputStream in) {
		try {
			Directory optionTemplates = (Directory) getFile("optionTemplates");
			if (optionTemplates == null) {
				optionTemplates = createFile("optionTemplates", Directory.class);
			}

			BinaryFile bf = optionTemplates.createFile(name, BinaryFile.class);
			bf.write(in);
			save();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public ShoppingMallMerchant getManager() {
		ShoppingMallMerchantImpl manager = new ShoppingMallMerchantImpl(
				SpringUtil.getRepositoryService(), new ShoppingMallUserImpl(
						SpringUtil.getRepositoryService(), SpringUtil
								.getSecurityService().loadUserByUsername(
										this.username)));

		return manager;
	}

	public FileIterator<Employee> getEmployees() {
		Directory employees = (Directory) getFile("employees");
		if (employees != null) {
			return employees.getFiles(Employee.class);
		} else {
			FileIterator f = new SimpleFileIterator(new ArrayList());
			return f;
		}
	}

	private void fillEmployee(User user, String tan, String ssnumber,
			String bank, String accountNumber, BigDecimal basic, String nic,
			String job, String department, Employee e) {
		e.setSubscriber(user.getUsername());
		e.setDateOfBirth(user.getDateOfBirth());
		e.setEmail(user.getEmail());
		e.setFax(user.getFax());
		e.setFirstName(user.getFirstName());
		e.setLastName(user.getLastName());
		e.setGender(user.getGender());
		e.setMaritalStatus(user.getMaritalStatus());
		e.setMobile(user.getMobile());
		e.setNic(user.getNic());
		e.setPhone(user.getPhone());
		e.setTitle(user.getTitle());

		e.setTan(tan);
		e.setSSNumber(ssnumber);
		e.setBank(bank);
		e.setBankAccountNumber(accountNumber);
		e.setBasicSalary(basic);
		e.setNic(nic);
		e.setDepartment(department);
		e.setJob(job);
		Address add = user.getDefaultAddress();
		if (add != null) {
			e.setAddressLine1(add.getLine1());
			e.setAddressLine2(add.getLine2());
			e.setCity(add.getCity());
			e.setCountry(add.getCountry());
			e.setTitle(add.getPostalCode());
		}
	}

	public Employee hireUser(User user, String tan, String ssnumber,
			String bank, String accountNumber, BigDecimal basic, String nic,
			String job, String department) {
		user.setOrganization(getUsername());
		Directory employees = (Directory) getFile("employees");
		if (employees == null) {
			employees = createFile("employees", Directory.class);
			Employee e = employees.createFile(user.getUsername(),
					Employee.class);

			fillEmployee(user, tan, ssnumber, bank, accountNumber, basic, nic,
					job, department, e);
			employees.save();
			return e;
		} else {
			Employee e = employees.createFile(user.getUsername(),
					Employee.class);
			fillEmployee(user, tan, ssnumber, bank, accountNumber, basic, nic,
					job, department, e);
			e.save();
			return e;
		}
	}

	public Employee getEmployee(String username) {
		QueryParameters params = new QueryParameters();
		params.setEntity(Employee.class).addRestriction(
				Restrictions.eq("subscriber", this.username)).addSearchDir(
				getAbsolutePath());

		List<File> files = SpringUtil.getRepositoryService().executeQuery(
				params, Util.getRemoteUser());
		if (files.size() != 0) {
			return (Employee) files.get(0);
		}
		return null;
	}

	public List<OSBarItem> getMyApps() {
		Directory dir = getFile("MyApplications", Directory.class);
		List<OSBarItem> result = new ArrayList<OSBarItem>();
		if (dir != null) {
			List<Article> arts = dir.getFiles(Article.class).toList();
			for (Article art : arts) {
				OSBarItem item = new OSBarItem(art.getTitle(),
						art.getSummary(), art.getName());
				item.setPermission(art.getReadPermissions());
				item.setOsApplicationRegistry(SpringUtil
						.getBeanOfType(OSApplicationRegistry.class));
				result.add(item);
			}
		} else {
			OSBarItem item = new OSBarItem("File Explorer",
					"osicons/explorer.png", "FileExplorer");
			item.setOsApplicationRegistry(SpringUtil
					.getBeanOfType(OSApplicationRegistry.class));

			OSBarItem settings = new OSBarItem("Shop Setting",
					"osicons/settings.png", "ShopSetting");
			settings.setOsApplicationRegistry(SpringUtil
					.getBeanOfType(OSApplicationRegistry.class));
			result.add(settings);
			result.add(item);
		}
		return result;
	}

	public void saveApp(String name, String title, String icon,
			String permissions) {
		Directory dir = getFile("MyApplications", Directory.class);
		if (dir != null) {
			Article art = dir.getFile(name, Article.class);
			art.setTitle(title);
			art.setSummary(icon);
			art.setReadPermissions(permissions);
			art.save();
		}

	}

	public Integer nextSequenceValue(String sequence) {
		// Merchant merchant = getCurrentMerchant();
		Directory sequences = (Directory) getFile("sequences");
		Value val = null;
		if (sequences == null) {
			sequences = createFile("sequences", Directory.class);
		} else {
			val = sequences.getFile(sequence, Value.class);
			if (val != null) {
				val.setString((Integer.parseInt(val.getString()) + 1) + "");
			}
		}

		if (val == null) {
			val = sequences.createFile(sequence, Value.class);
			val.setString("1");
		}

		save();
		return Integer.parseInt(val.getString());

	}

	public String getPreference(String key, String sdefault) {

		Directory preferences = getFile("preferences", Directory.class);
		if (preferences == null) {
			return sdefault;
		} else {
			String p = preferences.getProperty(key);
			if (p == null) {
				return sdefault;
			} else {
				return p;
			}
		}
	}

	public void setPreference(String key, String value) {
		Directory preferences = getFile("preferences", Directory.class);
		if (preferences == null) {
			preferences = createFile("preferences", Directory.class);
		}

		preferences.setProperty(key, value);
	}
	
	public List<Merchant> getRelated(){
		QueryParameters params = new QueryParameters().setEntity(Merchant.class).addRestriction(Restrictions.in("username", SpringUtil.getRelationshipManager().getRelatedOrganizations(this.username)));
		List l = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		return l;	
	}
	
	
	public List<Order> getPurchaseOrders(){
		List r = SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.eq("orderedBy", username)), Util.getRemoteUser());
		return r;
	}
	
	
	public int countOrders(int status){
		QueryParameters params = new QueryParameters().setEntity(Order.class).addSearchDir("/root/users/" + username).addRestriction(Restrictions.eq("status", status));
		return SpringUtil.getRepositoryService().countRows(params, Util.getRemoteUser());
	}

}
