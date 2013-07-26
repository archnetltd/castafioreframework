package org.castafiore.searchengine.back;

import org.apache.log4j.chainsaw.Main;
import org.castafiore.accounting.ui.EXAccounting;
import org.castafiore.agenda.EXAgendaNG;
import org.castafiore.catalogue.Product;
import org.castafiore.community.ui.EXCommunity;
import org.castafiore.designable.EXCatalogue;
import org.castafiore.designer.EXDesigner;
import org.castafiore.ecm.ui.fileexplorer.EXFileExplorer;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.logs.Log;
import org.castafiore.security.logs.Logger;
import org.castafiore.shoppingmall.checkout.EXOrdersList;
import org.castafiore.shoppingmall.crm.EXCrmPanel;
import org.castafiore.shoppingmall.employee.ui.v2.EXTimesheetV2;
import org.castafiore.shoppingmall.merchant.PlanViolationException;
import org.castafiore.shoppingmall.orders.EXOrdersPanel;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.shoppingmall.pos.EXPurchaseOrdersPanel;
import org.castafiore.shoppingmall.product.ui.EXProductsPanel;
import org.castafiore.shoppingmall.product.ui.EXProfocusEvent;
import org.castafiore.shoppingmall.relationship.EXRelationshipManager;
import org.castafiore.shoppingmall.user.ui.EXShopSettings;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.workflow.FlexibleWorkflow;

public class OSApplicationRegistryImpl implements OSApplicationRegistry{
	
	
	public void showQuotation(Container me){
		EXCatalogue catalogue = new EXCatalogue("cal");
		EXPanel panel = new EXPanel( "quotation", "Make a quotation");
		panel.setBody(catalogue);
		me.getAncestorOfType(PopupContainer.class).addPopup(panel);
		
		
	}

	@Override
	public Panel getWindow(String appName) {
		
		String plan = MallUtil.getCurrentMerchant().getPlan();
		if(appName.equalsIgnoreCase("inventory")){
			EXWindow window = new EXWindow("inventory", "Product List");
			EXProductsPanel panel = new EXProductsPanel("gdffdfd", "Panel");
			window.setBody(panel);
			panel.showProductList(Product.STATE_PUBLISHED);
			window.setStyle("width", "900px");
			Logger.log("accessing " + appName, Log.INFO);
			return window;
		}else if(appName.equalsIgnoreCase("EventsAndDestination")){
			EXWindow window = new EXWindow("EventsAndDestination", "Events and Destinations");
			EXProfocusEvent panel = new EXProfocusEvent("gdffdfd", "Panel");
			window.setBody(panel);
			panel.showProductList(Product.STATE_PUBLISHED, "Events");
			window.setStyle("width", "900px");
			Logger.log("accessing " + appName, Log.INFO);
			return window;
		}else if(appName.equalsIgnoreCase("invoices")){
			if(plan.equalsIgnoreCase("free")){
				throw new PlanViolationException("Invoice management system is not available for free plan");
			}
			
			EXWindow window = new EXWindow("", "Manage orders");
			window.setBody(new EXOrdersPanel(""));
			window.getDescendentOfType(EXOrdersPanel.class).showOrderList();
			window.getDescendentOfType(EXOrdersList.class).filtreByState(11);
			window.setStyle("width", "1200px");
			Logger.log("accessing " + appName, Log.INFO);
			return window;
		}else if(appName.equalsIgnoreCase("crm")){
			EXWindow window = new EXWindow("crm", "Manage your customers");
			window.setBody(new EXCrmPanel(""));
			
			window.setStyle("width", "800px");
			Logger.log("accessing " + appName, Log.INFO);
			return window;
		}else if(appName.equalsIgnoreCase("fileexplorer")){

			EXFileExplorer explorer = SpringUtil.getBeanOfType(EXFileExplorer.class);
			explorer.setStartDir("/root/users/" + MallUtil.getCurrentUser().getUser().getUsername());
			explorer.init();
			ComponentUtil.metamorphoseExplorer(explorer);
			EXWindow window = new EXWindow("fileexplorer", "File Explorer");
			window.setBody(explorer);
			window.setStyle("width", "1000px");
			Logger.log("accessing " + appName, Log.INFO);
			return window;
		}else if(appName.equalsIgnoreCase("designer")){
			if(plan.equalsIgnoreCase("free")){
				 //throw new PlanViolationException("Online web site creator is not available for free plan");
			}
			EXDesigner des = new EXDesigner();
			EXWindow window = new EXWindow("designer", "Designer");
			window.getChildren().clear();
			window.setDraggable(false);
			window.setRendered(false);
			window.setStyleClass("");
			des.getChild("close").getEvents().clear();
			des.getChild("close").addEvent(EventDispatcher.DISPATCHER, Event.CLICK);
			window.setStyle("top", "0").setStyle("left", "0");
			window.addChild(des);
			window.setStyle("width", "1000px");
			try{
			des.open((BinaryFile)SpringUtil.getRepositoryService().getFile("/root/users/" + Util.getRemoteUser() + "/site.ptl", Util.getRemoteUser()));
			}catch(Exception e){
				
			}
			Logger.log("accessing " + appName, Log.INFO);
			return window;
		}else if(appName.equalsIgnoreCase("ShopSetting")){
			EXWindow window = new EXWindow("ShopSetting", "Shop settings");
			EXShopSettings setting = new EXShopSettings();
			
			setting.fill();
			window.setBody(setting);
			window.setStyle("width", "800px");
			Logger.log("accessing " + appName, Log.INFO);
			return window;
			
		}else if(appName.equalsIgnoreCase("accounting")){
			Logger.log("accessing " + appName, Log.INFO);
			EXWindow window = new EXWindow("accounting", "Accounting module");
			window.setStyle("width", "800px");
			window.setBody(new EXAccounting("acc"));
			return window;
			
		}else if(appName.equalsIgnoreCase("Organization")){
			EXCommunity comunity = new EXCommunity();
			EXWindow window = new EXWindow("Organization", "Organization Management system");
			window.setStyle("width", "900px");
			window.setBody(comunity);
			Logger.log("accessing " + appName, Log.INFO);
			return window;
		}else if(appName.equalsIgnoreCase("Maxxin")){
			//return new EXWeeksPanel("weeks");
			try{
				Logger.log("accessing " + appName, Log.INFO);
			return (Panel)Thread.currentThread().getContextClassLoader().loadClass("org.castafiore.maxxin.EXWeeksPanel").newInstance();
			}catch(Exception e){
				throw new UIException(e);
			}
		}else if(appName.equalsIgnoreCase("workflow")){ 
			try{
			EXDesigner des =   (EXDesigner)Thread.currentThread().getContextClassLoader().loadClass("org.castafiore.workflow.ui.EXWorkflowDesigner").newInstance();//new EXWorkflowDesigner();
			EXWindow window = new EXWindow("workflow", "Workflow Designer");
			window.getChildren().clear();
			window.setDraggable(false);
			window.setRendered(false);
			window.setStyleClass("");
			des.getChild("close").getEvents().clear();
			des.getChild("close").addEvent(EventDispatcher.DISPATCHER, Event.CLICK);
			window.setStyle("top", "0").setStyle("left", "0");
			window.addChild(des);
			window.setStyle("width", "1000px");
			Logger.log("accessing " + appName, Log.INFO);
			return window;
			}catch(Exception e){
				throw new RuntimeException(e);
			}
			
		}else if(appName.equalsIgnoreCase("agenda")){
			EXWindow panel = new EXWindow("agenda", "My agenda");
			panel.setStyle("width", "960px");
			panel.setBody(new EXAgendaNG("myAgenda").setStyle("width", "940px"));
			Logger.log("accessing " + appName, Log.INFO);
			return panel;
		}else if(appName.equalsIgnoreCase("Registry")){
			EXWindow r = new EXApplicationRegistry("registry");
			r.setStyle("width", "800px");
			Logger.log("accessing " + appName, Log.INFO);
			return  r;
		}else if(appName.equalsIgnoreCase("timesheet")){
			try{
			EXWindow panel = new EXTimesheetV2();//(EXWindow)Thread.currentThread().getContextClassLoader().loadClass("org.castafiore.oceancall.timesheet.EXTimesheetPanel").newInstance();
			//panel.setStyle("width", "1200px");
			Logger.log("accessing " + appName, Log.INFO);
			return panel;
			}catch(Exception e){
				throw new UIException(e);
			}
		}else if(appName.equalsIgnoreCase("relationship")){
			try{
				//EXWindow panel = new EXTimesheetV2();//(EXWindow)Thread.currentThread().getContextClassLoader().loadClass("org.castafiore.oceancall.timesheet.EXTimesheetPanel").newInstance();
				EXRelationshipManager p = new EXRelationshipManager("relationship");
				EXPanel panel = new EXWindow("ds", "Relationship Management system");
				panel.setStyle("width", "1000px");
				panel.setBody(p);
				//panel.setStyle("width", "1200px");
				Logger.log("accessing " + appName, Log.INFO);
				return panel;
			}catch(Exception e){
				throw new UIException(e);
			}
		}else if(appName.equalsIgnoreCase("pos")){
			try{
				//EXWindow panel = new EXTimesheetV2();//(EXWindow)Thread.currentThread().getContextClassLoader().loadClass("org.castafiore.oceancall.timesheet.EXTimesheetPanel").newInstance();
				//@PATCH
				OrdersWorkflow workflow;
				if(Util.getLoggedOrganization().equalsIgnoreCase("erevolution")){
					workflow = new FlexibleWorkflow("/root/users/erevolution/workflow");
				}else{
					if(SpringUtil.getRelationshipManager().hasRelationship("erevolution", Util.getLoggedOrganization(), "Tier 1 Agent")){
						workflow = new FlexibleWorkflow("/root/users/erevolution/Tier1Workflow");
					}else{
						workflow = new FlexibleWorkflow("/root/users/erevolution/Tier2Workflow");
					}
				}
				//@END-PATCH
				EXPurchaseOrdersPanel p = new EXPurchaseOrdersPanel("pos", workflow);
				EXPanel panel = new EXWindow("pos", "Purchase orders");
				panel.setStyle("width", "1000px");
				panel.setBody(p);
				//panel.setStyle("width", "1200px");
				Logger.log("accessing " + appName, Log.INFO);
				return panel;
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		
		EXWindow window = new EXWindow(appName, "This is a title");
		window.setBody(new OSLoginForm("OSLoginForm"));
		return window;
	}
	

}
