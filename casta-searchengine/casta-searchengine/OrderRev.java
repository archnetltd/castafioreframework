import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.EXCatalogue;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.product.EXProduct;
import org.castafiore.designable.product.EXProductItem;
import org.castafiore.designable.product.EXProductResultBar;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;


class OrderRev {

		
	void showDetail(Container mm){

		EXCatalogue catalogue = new EXCatalogue("cal");
				catalogue.search("recent", "test", "timbarafrica");
				EXPanel panel = new EXPanel( "quotation", "Make a quotation");
				panel.setStyle("z-index", "3000");
				panel.setStyle("width", "636px");
				catalogue.setStyle("width", "440px");
				EXMiniCart cart = new EXMiniCart("minicart");
				cart.getDescendentByName("checkout").getEvents().clear();
				cart.getDescendentByName("checkout").setText("Send Quotation").addEvent(new Event(){

					@Override
					public void ClientAction(ClientProxy container) {
						container.mask().makeServerRequest(this);
						
					}

					@Override
					public boolean ServerAction(Container container,
							Map<String, String> request) throws UIException {
						return true;
					}

					@Override
					public void Success(ClientProxy container,
							Map<String, String> request) throws UIException {
						// TODO Auto-generated method stub
						
					}
					
				}, Event.CLICK);
				
				
				panel.setBody(catalogue.setStyle("float", "left"));
				panel.getBody().getParent().addChild(cart.setStyle("width", "150px").setStyle("float", "left"));
				catalogue.getDescendentOfType(EXProductResultBar.class).setDisplay(false);
				ComponentUtil.iterateOverDescendentsOfType(catalogue, EXProductItem.class, new ComponentVisitor() {
					
					@Override
					public void doVisit(Container c) {
		c.getDescendentByName("price").setDisplay(false);
		c.getDescendentByName("addToCart").setText("Selecter produit");
						c.getDescendentByName("productImageLink").getEvents().clear();
						c.getDescendentByName("productImageLink").addEvent(new Event() {
							
							@Override
							public void Success(ClientProxy container, Map<String, String> request)
									throws UIException {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public boolean ServerAction(Container container, Map<String, String> request)
									throws UIException {
								EXPanel pan = new EXPanel("s", "Product Detail");
		pan.setStyle("width", "730px").setStyle("z-index", "4000");
								
								EXProduct p = new EXProduct("");
								pan.setBody(p);
								String path = container.getAncestorOfType(EXProductItem.class).getAttribute("path");
								Product product = (Product)SpringUtil.getRepositoryService().getFile(path, "timbarafrica");
								p.setProduct(product);
								container.getAncestorOfType(EXPanel.class).getBody().getParent().addChild(pan);
		p.getDescendentByName("continueShopping").setStyle("display", "none");
								p.getDescendentByName("addToCart").setDisplay(false);
								
								return true;

							}
							
							@Override
							public void ClientAction(ClientProxy container) {
								container.mask().makeServerRequest(this);
								
							}
						},  Event.CLICK);
						
					}
				});


				mm.getParent().addChild(panel);

		}



		
		
	}
