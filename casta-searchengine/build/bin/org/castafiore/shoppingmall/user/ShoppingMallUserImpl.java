package org.castafiore.shoppingmall.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.contact.Contact;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.merchant.MerchantSubscription;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchantImpl;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Comment;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Message;
import org.castafiore.wfs.types.Shortcut;
import org.hibernate.criterion.Restrictions;

public class ShoppingMallUserImpl implements ShoppingMallUser{
	
	protected RepositoryService repositoryService;

	protected User user;
	
	public ShoppingMallUserImpl(RepositoryService repositoryService, User user) {
		super();
		this.repositoryService = repositoryService;
		this.user = user;
	}
	
	

	@Override
	public List<Comment> getAddedComments(int startPage, int pageSize) {
		QueryParameters params = new QueryParameters().setEntity(Comment.class).addRestriction(Restrictions.eq("clazz",Comment.class.getName())).addRestriction(Restrictions.eq("author", user.getUsername())).setFirstResult(startPage*pageSize).setMaxResults(pageSize);
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());
		return result;
	}

	@Override
	public List<Contact> getContacts(String category,int startPage, int pageSize) {
		String dir = CONTACTS_DIR.replace("$user", user.getUsername()) + "/" + category;
		QueryParameters params = new QueryParameters().setEntity(Contact.class).addRestriction(Restrictions.like("absolutePath", dir + "/%")).setFirstResult(startPage*pageSize).setMaxResults(pageSize);
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());

		
		return result;
	}
	@Override
	public List<MerchantSubscription> getSubscriptions(){
		QueryParameters params = new QueryParameters().setEntity(MerchantSubscription.class).addRestriction(Restrictions.eq("subscriber", user.getUsername()));
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());
		return result;
	}
	
	public List<String> getContactCategories(){
		String dir = CONTACTS_DIR.replace("$user", user.getUsername());
		QueryParameters params = new QueryParameters().setEntity(Contact.class).
		addRestriction(Restrictions.like("absolutePath", dir + "/%"));
		List<File> result = repositoryService.executeQuery(params, Util.getRemoteUser());
		
		List<String> asResults = new ArrayList<String>();
		for(File f : result){
			asResults.add(f.getName());
		}
		return asResults;

		
	}

	public Contact createContact(String category, User user){
		String dir = CONTACTS_DIR.replace("$user", user.getUsername()) + "/" + category;
		Directory fDir = repositoryService.getDirectory(dir, getUser().getUsername());
		return fDir.createFile(user.getUsername(), Contact.class);
		//repositoryService.saveIn(dir, contact, user.getUsername());
	}
	@Override
	public List<Message> getPersonalMessages(int startPage, int pageSize) {
		QueryParameters params = new QueryParameters().setEntity(Message.class).addRestriction(Restrictions.ne("status", Article.STATE_DRAFT)).addRestriction(Restrictions.eq("destination", user.getUsername())).setFirstResult(startPage*pageSize).setMaxResults(pageSize);
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());
		
		return result;
	}

	@Override
	public List<Message> getSentMessages(int startPage, int pageSize) {
		QueryParameters params = new QueryParameters().setEntity(Message.class).addRestriction(Restrictions.ne("status", Article.STATE_DRAFT)).addRestriction(Restrictions.eq("author", user.getUsername())).setFirstResult(startPage*pageSize).setMaxResults(pageSize);
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());
		
		return result;
	}
	
	@Override
	public List<Message> getDraftMessages(int startPage, int pageSize) {
		QueryParameters params = new QueryParameters().setEntity(Message.class).addRestriction(Restrictions.eq("status", Article.STATE_DRAFT)).addRestriction(Restrictions.eq("author", user.getUsername())).setFirstResult(startPage*pageSize).setMaxResults(pageSize);
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());
		
		return result;
	}

	@Override
	public List<Message> getSharedMessages(int startPage, int pageSize) {
		QueryParameters params = new QueryParameters().setEntity(Message.class)
		.addRestriction(Restrictions.ne("author", user.getUsername()))
		.addRestriction(Restrictions.ne("owner", user.getUsername()))
		.addRestriction(Restrictions.ne("destination", user.getUsername()))
		.addRestriction(Restrictions.ne("status", Article.STATE_DRAFT))
		.setFirstResult(startPage*pageSize).setMaxResults(pageSize);
		List result = repositoryService.executeQuery(params, Util.getRemoteUser());
		return result;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void addToFavorite(String productPath) {
		Directory into = repositoryService.getDirectory(USER_FAVORIT_DIR.replace("$user", user.getUsername()), getUser().getUsername());
		Shortcut shortcut = into.createFile(ResourceUtil.getNameFromPath(productPath), Shortcut.class);//new Shortcut();
	//	shortcut.setName();
		shortcut.setReference(productPath);
		shortcut.makeOwner(Util.getRemoteUser());
		into.save();
		//repositoryService.update(into, Util.getRemoteUser());
		
		
	}

	
	@Override
	public List<Product> getFavorite() {
		List<Product> result = new ArrayList<Product>();
		
		Directory favDir = repositoryService.getDirectory(USER_FAVORIT_DIR.replace("$user", user.getUsername()),Util.getRemoteUser());
		FileIterator<Shortcut> iter = favDir.getFiles(Shortcut.class);
		while(iter.hasNext()){
			Shortcut sc = iter.next();
			try{
				result.add((Product)sc.getReferencedFile());
			}catch(Exception e){
				
			}
		}
		
		return result;
	}

	@Override
	public boolean isAnonymous() {
		return "anonymous".equals(user.getUsername());
	}



	



	@Override
	public Message createMessage(String name) {
		Directory parent = repositoryService.getDirectory(MESSAGES_DIR.replace("$user", getUser().getUsername()), getUser().getUsername());
		
		return parent.createFile(name, Message.class);
		//repositoryService.saveIn(, message, Util.getRemoteUser());
		
	}



//	@Override
//	public void acceptInvitation(Message invitationMessage) {
//		String contactName = invitationMessage.getDestination();
//		Contact contact = new Contact();
//		contact.setName(contactName);
//		contact.setUsername(contactName);
//		contact.setOwner(user.getUsername());
//		addContact(DEFAULT_CONTACT_CATEGORY, contact);
//		
//	}



//	@Override
//	public void sendInvitation(String username) {
//		String dir = MESSAGES_DIR.replace("$user", getUser().getUsername());
//		Directory parent = repositoryService.getDirectory(dir, getUser().getUsername());
//		Message message = parent.createFile("Invitation from " + user.getUsername(), Message.class);//new Message();
//		//message.setName("Invitation from " + user.getUsername());
//		message.setTitle(message.getName());
//		message.setSummary("Hello " + username + "\n" + user.toString() + " would like to add you in his contact list");
//		message.setDestination(username);
//		message.setAuthor(user.getUsername());
//		message.setOwner(user.getUsername());
//		repositoryService.update(parent, getUser().getUsername());
//		//sendMessage(message);
//		
//	}



	@Override
	public boolean isMerchant() {
		return user.isMerchant();
	}

	
	public ShoppingMallMerchant makeMerchant(){
		user.setMerchant(true);
		SpringUtil.getSecurityService().saveOrUpdateUser(user);
		return getMerchant();
	}


	@Override
	public ShoppingMallMerchant getMerchant() {
		if(isMerchant()){
			if(user.getOrganization() != null && user.getOrganization().equals(user.getUsername())){
				return new ShoppingMallMerchantImpl(repositoryService, this);
			}else{
				return new ShoppingMallMerchantImpl(repositoryService, new ShoppingMallUserImpl(repositoryService, SpringUtil.getSecurityService().loadUserByUsername(user.getOrganization())));
			}
		}else{
			throw new RuntimeException("User " + user.toString() + " is not a  merchant");
		}
		
	}



	
	
	
	
	public List<Order> getInvoices(){
		return repositoryService.getDirectory(ORDERS_DIR.replace("$user", getUser().getUsername()), Util.getRemoteUser()).getFiles(Order.class).toList();
	}



//	@Override
//	public void confirmOrder(Order order) {
//		order.setStatus(Order.PROCESSING);
//		order.save();
//		
//		
//		//EmailNotificationAgent mailSender = SpringUtil.getBeanOfType(EmailNotificationAgent.class);
////		Message message = order.createFile("confirmation_" +order.getName(), Message.class);
////		message.setTitle("New online order - " + order.getCode());
////		String msg ="New order from " + getUser().toString() + "\n" +
////				"Invoice number :" + order.getCode() + "\n" +
////				"Customer : " + getUser().toString() + "\n" +
////				"Mobile Phone :" + getUser().getMobile()+ "\n"; 
////		
////		message.setSummary(msg);
//		//User from = new User();
//		//User from = SpringUtil.getSecurityService().loadUserByUsername(order.getOrderedFrom());
//		//mailSender.sendNotification(message, from, getUser());
//		Merchant merchant = MallUtil.getMerchant(order.getOrderedFrom());
//		merchant.subscribe(order.getOrderedBy());
//		
//		
//		order.save();
//	}



	
}
