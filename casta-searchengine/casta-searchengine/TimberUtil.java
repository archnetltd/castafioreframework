import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.EXCatalogue;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.checkout.EXOrderReview;
import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designable.product.EXProduct;
import org.castafiore.designable.product.EXProductItem;
import org.castafiore.designable.product.EXProductResultBar;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.delivery.Delivery;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.merchant.ShoppingMallMerchant;
import org.castafiore.shoppingmall.orders.OrdersUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


public class TimberUtil {
	
	public static void exec(Container mm){
		EXCatalogue catalogue = new EXCatalogue("cal");
		catalogue.search("recent", "test", "timbarafrica");
		EXPanel panel = new EXPanel( "quotation", "Demander une quotation");
		
		catalogue.setStyle("width", "440px").setStyle("margin", "10px").addClass("cart-widget");
		EXMiniCart cart = new EXMiniCart("minicart");
		cart.getDescendentByName("checkout").getEvents().clear();
		cart.getDescendentByName("checkout").setText("Quotation").addEvent(new Event(){
			@Override
			public void ClientAction(ClientProxy container) {
				container.mask().makeServerRequest(this);
			}
			@Override
			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				EXDynaformPanel gg = new EXDynaformPanel("", "Veuillez vous enregistrer");
				gg.setStyle("width", "600px");
				
				gg.addField("First Name :", new EXInput("firstName"));
				gg.addField("Last Name :", new EXInput("lastName"));
				gg.addField("Phone :", new EXInput("phone"));
				gg.addField("Mobile :", new EXInput("mobile"));
				gg.addField("Email :", new EXInput("email"));
				
				gg.getDescendentByName("firstName").setStyle("width", "460px");
				gg.getDescendentByName("lastName").setStyle("width", "460px");
				gg.getDescendentByName("phone").setStyle("width", "460px");
				gg.getDescendentByName("mobile").setStyle("width", "460px");
				gg.getDescendentByName("email").setStyle("width", "460px");
				
				EXButton close = new EXButton("close", "Fermer");
				close.addEvent(gg.CLOSE_EVENT, Event.CLICK);
				gg.addButton(close);
				EXButton save = new EXButton("save", "Quotation");
				save.addEvent(new Event(){
					public void ClientAction(ClientProxy c) {c.mask().makeServerRequest(this);}
					public boolean ServerAction(Container c, Map<String, String> r)	throws UIException {
						EXDynaformPanel pppp = c.getAncestorOfType(EXDynaformPanel.class);
						pppp.getDescendentByName("save").setDisplay(false);

						String firstName=pppp.getField("firstName").getValue().toString();
						String lastName=pppp.getField("lastName").getValue().toString() ;
						String tel=pppp.getField("phone").getValue().toString();
						String mobile=pppp.getField("mobile").getValue().toString();
						String email=pppp.getField("email").getValue().toString();
						EXOrderReview rv = new EXOrderReview("",c.getRoot().getDescendentOfType(EXMiniCart.class), 0);
						c.getAncestorOfType(EXDynaformPanel.class).setBody(rv);
						rv.getDescendentByName("continue").setDisplay(false);
						rv.getDescendentByName("back").setDisplay(false);
						Container t =rv.getChildByIndex(1);
						Container trH =t.getDescendentByName("header");
						trH.getChildByIndex(0).remove();
						trH.getChildByIndex(1).remove();
						trH.getChildByIndex(1).remove();
						trH.getChildByIndex(1).remove();
						trH.getChildByIndex(0).setText("Quotation").setStyle("text-align", "center");
						Container tbody =t.getDescendentByName("tbody");
						for(Container trow : tbody.getChildren()){
							trow.getChildByIndex(0).remove();
							trow.getChildByIndex(1).remove();
							trow.getChildByIndex(1).remove();
							trow.getChildByIndex(1).remove();
							trow.getChildByIndex(0).setStyle("text-align", "center");
						}

						String username = "timbarafrica";
						EXMiniCart c1= c.getRoot().getDescendentOfType(EXMiniCart.class);
						Merchant merchant = MallUtil.getMerchant(username);

						ShoppingMallMerchant m1 = merchant.getManager();
						Order order1 = m1.createOrder(username);
						order1.setDateOfTransaction(new Date());
						order1.setPointOfSale("Internet");
						order1.setSource("Internet");
						c1.createItems(order1);
						order1.setOwner(username);

						String icode = StringUtil.nextString(10);
						order1.setCode(icode);

						BillingInformation bif1 = order1.createFile("billing", BillingInformation.class);
						bif1.setCountry("Mauritius");
						bif1.setEmail(email);
						bif1.setFirstName(firstName);
						bif1.setLastName(lastName);
						bif1.setPhone(tel);
						bif1.setMobile(mobile);
						bif1.setUsername(order1.getCode());
 
						Delivery delivery = order1.createFile("delivery", Delivery.class);
						BigDecimal deliveryPrice = new BigDecimal(0);

						delivery.setPrice(deliveryPrice);
						delivery.setWeight(new BigDecimal(0));

						order1.setStatus(11);
						BigDecimal weight = new BigDecimal(0);
						delivery.setWeight(weight);
						order1.update();

						merchant.subscribe(bif1, icode);
						order1.setOrderedBy(bif1.getUsername());
						order1.save();
						try{
							Map<String, String> rep = new HashMap<String, String>();
							rep.put("#bfirstName", bif1.getFirstName());
							rep.put("#blastName", bif1.getLastName());
							rep.put("#baddline1", bif1.getAddressLine1());
							rep.put("#baddline1", bif1.getAddressLine2());
							rep.put("#bphone", bif1.getPhone());
							rep.put("#bmobile", bif1.getMobile());
							rep.put("#bemail", bif1.getEmail());
							rep.put("#invoicenumber",order1.getCode());
							rep.put("#bzipcode"," ");
							rep.put("#date", new SimpleDateFormat("dd/MMM/yyyy").format(order1.getDateOfTransaction()));
							rep.put("#subtotal", StringUtil.toCurrency("MUR",order1.getSubTotal()));
							rep.put("#tax", StringUtil.toCurrency("MUR",order1.getSubTotal()));
							rep.put("#total", StringUtil.toCurrency("MUR",order1.getTotal()));
							rep.put("#merchant", merchant.getCompanyName());
							String orderLine = "";
							for(CartItem iit : c1.getItems()){
								
								String tp = "<tr><td>#{title}</td><td>#{bois}</td><td>#{unite}</td><td>#{quantite}</td><td>#{total}</td></tr>";
								Map oppp = iit.getOptionMap();
								tp = tp.replace("#{title}", iit.getTitle()).replace("#{bois}", oppp.get("bois").toString());
								tp = tp.replace("#{unite}", oppp.get("unites").toString()).replace("#{quantite}", oppp.get("quantity").toString());
								tp = tp.replace("#{total}", iit.getTotalPrice().toPlainString());
								orderLine = orderLine + tp;
							}
							
							rep.put("#orderlines", orderLine);
							String content = ResourceUtil.getTemplate(ResourceUtil.getDownloadURL("ecm", "/root/users/timbarafrica/template/Order.xhtml"), c.getRoot());
							content = OrdersUtil.compile(content, rep);
							JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
							MimeMessage message = sender.createMimeMessage();
							MimeMessageHelper helper = new MimeMessageHelper(message, true);
							helper.setSubject("Quotation Timber Africa");
							helper.setFrom(merchant.getEmail());
							helper.setTo(email);
							helper.setText(content, true);
							sender.send(message);
							
							
							JavaMailSender sender1 = SpringUtil.getBeanOfType(JavaMailSender.class);
							MimeMessage message1 = sender1.createMimeMessage();
							MimeMessageHelper helper1 = new MimeMessageHelper(message1, true);
							helper1.setSubject("Quotation Timber Africa");
							helper1.setFrom(merchant.getEmail());
							
							helper1.setText(content, true);
							//sender1.send(message1);

							helper1.setTo(merchant.getEmail());
							sender1.send(message1);
						}catch(Exception e){
							e.printStackTrace();
							throw new UIException(e);
						}
						c1.setItems(new ArrayList());
						return true;
					}
					public void Success(ClientProxy c, Map r)throws UIException {}


				}, Event.CLICK);
				gg.addButton(save);
				gg.setDraggable(false);
				gg.setStyle("float", "left");
				container.getRoot().getDescendentOfType(PageContainer.class).setPage(gg);
				//container.getAncestorOfType(PopupContainer.class).addPopup(gg.setStyle("z-index", "4000"));
				//gg.setStyle("top", var)
				return true;
			}

			@Override
			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {}
			
		}, Event.CLICK);
		
		panel.setBody(catalogue.setStyle("float", "left"));
		panel.getBody().getParent().addChild(cart.setStyle("width", "150px").setStyle("float", "left"));
		catalogue.setStyle("background", "#100700");	catalogue.getDescendentOfType(EXProductResultBar.class).setDisplay(false);
		ComponentUtil.iterateOverDescendentsOfType(catalogue, EXProductItem.class, new ComponentVisitor() {
			@Override
			public void doVisit(Container c) {
				c.getDescendentByName("price").setDisplay(false);
				c.getDescendentByName("addToCart").setStyle("display", "none");
				c.getDescendentByName("productImageLink").getEvents().clear();
				c.getDescendentByName("productImageLink").addEvent(new Event() {
					public void Success(ClientProxy container, Map<String, String> request)throws UIException {}
					public boolean ServerAction(Container container, Map<String, String> request)throws UIException {
						//EXPanel pan = new EXPanel("s", "Product Detail");
						//pan.setStyle("width", "730px").setStyle("z-index", "4000").setStyle("clear", "both");
						
						EXProduct p = new EXProduct("");
						
						//pan.setBody(p);
						String path = container.getAncestorOfType(EXProductItem.class).getAttribute("path");
						Product product = (Product)SpringUtil.getRepositoryService().getFile(path, "timbarafrica");
						p.setProduct(product);
						container.getRoot().getDescendentOfType(PageContainer.class).setPage(p.setStyle("float", "left"));
						//container.getAncestorOfType(PopupContainer.class).addPopup(pan);
						//p.getDescendentByName("continueShopping").setStyle("display", "none");
						p.getDescendentByName("addToCart").setText("Ajouter item").setStyle("float", "right");
						p.getDescendentOfType(EXDynaformPanel.class).setStyle("height", "100px").setStyle("margin-bottom", "10px");
						//pan.setStyle("top", "300px");
						return true;
					}
					
					@Override
					public void ClientAction(ClientProxy container) {
						container.mask().makeServerRequest(this);
					}
				},  Event.CLICK);
			}
		});
		Container c = panel.getBody().getParent();
		c.setStyle("float", "left");
		mm.getRoot().getDescendentOfType(PageContainer.class).setPage(c);
		//mm.getParent().addChild(panel);
	}

	

}
