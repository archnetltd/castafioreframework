package org.castafiore.shoppingmall.user;

import java.io.Serializable;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.contact.Contact;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.merchant.MerchantSubscription;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.wfs.types.Comment;
import org.castafiore.wfs.types.Message;
/**
 * a user can
 * 	buy product
 * 	search product
 * 	has friends who are shopping mall users
 * 	has messages
 * 	send message
 * 	has added comments
 * 	
 * a user is a 
 * 	member of the platform
 * 	has read rights on 
 * 	
 * 	
 * @author kureem
 *
 */
public interface ShoppingMallUser extends Serializable{
	
	public final static String E_SHOP_DIR = "/root/users/$user/Applications/e-Shop";
	
	public final static String USER_FAVORIT_DIR = E_SHOP_DIR + "/Favorite";
	
	public final static String MESSAGES_DIR = E_SHOP_DIR +"/Messages";
	
	public final static String CONTACTS_DIR = E_SHOP_DIR +"/Contacts";
	
	public final static String ORDERS_DIR = E_SHOP_DIR + "/Orders";
	
	public final static String DEFAULT_CONTACT_CATEGORY = "Default";
	
	/**
	 * return my friends
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	public List<Contact> getContacts(String category,int startPage, int pageSize);
	
	public Contact createContact(String category, User user);
	
	public List<String> getContactCategories();
	
	
	
	public List<Message> getDraftMessages(int startPage, int pageSize);
	
	/**
	 * sends an invitation to the specified username
	 * @param username
	 */
	//public void sendInvitation(String username);
	
	
	/**
	 * accepts the invitation message.
	 * This means you have become a contact to the author of the invitation message
	 * @param invitationMessage
	 */
	//public void acceptInvitation(Message invitationMessage);
	
	
	/**
	 * return messages sent to me
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	public  List<Message> getPersonalMessages(int startPage, int pageSize);
	
	/**
	 * return messages shared with me
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	public  List<Message> getSharedMessages(int startPage, int pageSize);
	
	/**
	 * return message I have sent
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	public List<Message> getSentMessages(int startPage, int pageSize);
	
	
	/**
	 * sends a message
	 * @param message
	 */
	public Message createMessage(String name);
	
	
	/**
	 * return comments I added
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	public List<Comment> getAddedComments(int startPage, int pageSize);
	
	/**
	 * return me
	 * @return
	 */
	public User getUser();
	
	
	/**
	 * check if I am anonymous
	 * @return
	 */
	public boolean isAnonymous();
	

	
	/**
	 * 
	 * @return return my favorite
	 */
	public List<Product> getFavorite();
	
	/**
	 * adds to favorite of current user
	 * @param productPath
	 */
	public void addToFavorite(String productPath);
	
	
	/**
	 * checks if this user is a merchant
	 * @return
	 */
	public boolean isMerchant();
	
	/**
	 * returns the merchant wrapper
	 * @return
	 */
	public ShoppingMallMerchant getMerchant();
	
	
	
	
	
	//public void confirmOrder(Order order);
	
	
	public List<Order> getInvoices();
	
	
	
	public List<MerchantSubscription> getSubscriptions();
}
