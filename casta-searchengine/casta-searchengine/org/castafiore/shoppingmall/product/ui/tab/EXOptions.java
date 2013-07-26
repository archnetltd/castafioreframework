package org.castafiore.shoppingmall.product.ui.tab;

import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.ui.EXMiniConfig;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;

public class EXOptions extends EXAbstractProductTabContent implements Event
{
	public EXOptions() {
		
//		addChild(new EXContainer("addOption", "button").setText("Add New").addEvent(this, CLICK));
//		addChild(new EXContainer("error", "p").addClass("error"));
//		
//		String plan = MallUtil.getCurrentMerchant().getPlan();
//		if(plan.equalsIgnoreCase("free")){
//			setTemplateLocation("templates/product/EXOptionsFree.xhtml");
//			getChild("error").setText("Cart options is not available for the free plan. <br>Please upgrade to the professional plan to be able to add cart options to products");
//		}else{
			//addChild(new EXProductOption());
//		}
		try{
			EXDesigner des = new EXDesigner();
			des.setTemplateLocation("designer/sliced/minidesigner.xhtml");
			des.setName("EXProductOption");
			des.addChild(new EXMiniConfig("miniConfigs"));
			addChild(des);
			
		}catch(Exception e){
			throw new UIException(e);
		}
		
		
	}

	@Override
	public void fillProduct(Product product) {
//		getChild("error").setText("");
//		getChild("error").setDisplay(false);
//		EXProductOption opt = getDescendentOfType(EXProductOption.class);
//		if(opt != null){
//			opt.fillProduct(product);
//		}
		
		try{
			BinaryFile bf = product.getOption();
			PortalContainer pc = getDescendentOfType(EXDesigner.class).getRootLayout();
			String xml = DesignableUtil.generateXML(pc, null);
			bf.write(xml.getBytes());
			bf.getParent().save();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
	public void setError(String error){
		getChild("error").setDisplay(true).setText(error);
	}

	@Override
	public Container setProduct(Product product) {
//		EXProductOption options = getDescendentOfType(EXProductOption.class);
//		if(options != null)
//			options.setProduct(product);
//		return this;
		
		try{
			if(product == null){
				getDescendentOfType(EXDesigner.class).open( (PortalContainer)DesignableUtil.buildContainer(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/product/ui/tab/productoption.xml"), false));
				return this;
			}
			
			product = (Product)SpringUtil.getRepositoryService().getFile(product.getAbsolutePath(), Util.getRemoteUser());
			BinaryFile bf = product.getOption();
			getDescendentOfType(EXDesigner.class).open(bf);
			
	
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return this;
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		container.getParent().getDescendentOfType(EXProductOption.class).addRawLine();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
