package com.eliensons.ui;

import org.castafiore.community.ui.EXCommunity;
import org.castafiore.designer.EXDesigner;
import org.castafiore.ecm.ui.fileexplorer.EXFileExplorer;
import org.castafiore.imports.EXImportContractsPanel;
import org.castafiore.reconcilliation.EXReconciliationPanel;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.back.EXApplicationRegistry;
import org.castafiore.searchengine.back.EXWindow;
import org.castafiore.searchengine.back.OSApplicationRegistryImpl;
import org.castafiore.searchengine.back.OSLoginForm;
import org.castafiore.security.logs.Log;
import org.castafiore.security.logs.Logger;
import org.castafiore.shoppingmall.crm.EXCRM;
import org.castafiore.shoppingmall.orders.EXOrdersPanel;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.shoppingmall.pos.EXPurchaseOrdersPanel;
import org.castafiore.shoppingmall.relationship.EXRelationshipManager;
import org.castafiore.shoppingmall.reports.EXReportsPanel;
import org.castafiore.shoppingmall.user.ui.EXShopSettings;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;

import com.eliensons.ui.plans.EXPlansPanel;
import com.eliensons.ui.sales.EXElieNSonsSales;

public class ElienSonsApplicationRegistry extends OSApplicationRegistryImpl {

	@Override
	public Panel getWindow(String appName) {

		if (appName.equalsIgnoreCase("inventory")) {
			return super.getWindow(appName);
		} else if (appName.equalsIgnoreCase("invoices")) {

			EXWindow window = new EXWindow("invoices", "Manage orders");
			window.setBody(new EXPlansPanel("", "List of orders"));
			window.getDescendentOfType(EXOrdersPanel.class).showOrderList();
			window.setStyle("width", "900px");
			return window;
		} else if (appName.equalsIgnoreCase("crm")) {
			EXPanel panel = new EXCRM("crm");
			panel.setStyle("z-index", "2000");
			panel.setStyle("width", "100%").setStyle("top", "0px")
					.setStyle("left", "0px");
			return panel;
		} else if (appName.equalsIgnoreCase("fileexplorer")) {

			EXFileExplorer explorer = SpringUtil
					.getBeanOfType(EXFileExplorer.class);
			explorer.setStartDir("/root/users/"
					+ MallUtil.getCurrentUser().getUser().getUsername());
			explorer.init();
			ComponentUtil.metamorphoseExplorer(explorer);
			EXWindow window = new EXWindow("fileExplorer", "File Explorer");
			window.setBody(explorer);
			window.setStyle("width", "1000px");
			return window;
		} else if (appName.equalsIgnoreCase("designer")) {
			EXDesigner des = new EXDesigner();
			EXWindow window = new EXWindow("designer", "Designer");
			window.getChildren().clear();
			window.setDraggable(false);
			window.setRendered(false);
			window.setStyleClass("");
			des.getChild("close").getEvents().clear();
			des.getChild("close").addEvent(EventDispatcher.DISPATCHER,
					Event.CLICK);
			window.setStyle("top", "0").setStyle("left", "0");
			window.addChild(des);
			window.setStyle("width", "1000px");
			try {
				des.open((BinaryFile) SpringUtil.getRepositoryService()
						.getFile(
								"/root/users/" + Util.getRemoteUser()
										+ "/ecommerce.ptl",
								Util.getRemoteUser()));
			} catch (Exception e) {

			}
			return window;
		} else if (appName.equalsIgnoreCase("ShopSetting")) {
			EXWindow window = new EXWindow("ShopSetting", "Shop settings");
			EXShopSettings setting = new EXShopSettings();

			setting.fill();
			window.setBody(setting);
			window.setStyle("width", "800px");
			return window;
		} else if (appName.equalsIgnoreCase("accounting")) {
			return super.getWindow(appName);

		} else if (appName.equalsIgnoreCase("PlanSales")) {
			EXWindow window = new EXWindow("PlanSales", "Manage orders");
			window.setStyle("width", "1000px");
			window.setCloseButtonEvent(EXWindow.HIDE_EVENT);
			try {
				window.setBody(new EXElieNSonsSales("sales"));
			} catch (Exception e) {
				throw new UIException(e);
			}

			return window;
		} else if (appName.equalsIgnoreCase("Organization")) {
			EXCommunity comunity = new EXCommunity();
			EXWindow window = new EXWindow("Organization", "Organization Management system");
			window.setBody(comunity);
			window.setStyle("width", "1000px");
			return window;
		} else if (appName.equalsIgnoreCase("reports")) {
			EXWindow window = new EXWindow("", "Reports");
			window.setStyle("width", "1000px");
			try {
				window.setBody(new EXReportsPanel(""));
				window.setCloseButtonEvent(EXWindow.HIDE_EVENT);
			} catch (Exception e) {
				throw new UIException(e);
			}
			return window;
		} else if (appName.equalsIgnoreCase("Reconciliation")) {
			return new EXReconciliationPanel("Reconciliation");
		} else if (appName.equalsIgnoreCase("Registry")) {
			return new EXApplicationRegistry("Registry");
		} else if (appName.equalsIgnoreCase("Backup")) {
			return new EXImportContractsPanel("Backup");
		} else if (appName.equalsIgnoreCase("relationship")) {
			try {
				
				EXRelationshipManager p = new EXRelationshipManager("relationship");
				EXPanel panel = new EXWindow("ds",
						"Relationship Management system");
				panel.setStyle("width", "1000px");
				panel.setBody(p);
				Logger.log("accessing " + appName, Log.INFO);
				return panel;
			} catch (Exception e) {
				throw new UIException(e);
			}
		} else if (appName.equalsIgnoreCase("pos")) {
			try {

				EXPurchaseOrdersPanel p = new EXPurchaseOrdersPanel("pos",
						SpringUtil.getBeanOfType(OrdersWorkflow.class));
				EXPanel panel = new EXWindow("ds", "Point of sale");
				panel.setStyle("width", "1000px");
				panel.setBody(p);

				Logger.log("accessing " + appName, Log.INFO);
				return panel;
			} catch (Exception e) {
				throw new UIException(e);
			}
		}

		EXWindow window = new EXWindow(appName, "This is a title");
		window.setBody(new OSLoginForm("OSLoginForm"));
		return window;
	}

	// public EXWindow showAccounts(){
	//
	// EXWindow window = super.showAccounts();
	// EXButton reconcilliate = new EXButton("reconcilliate", "Reconciliation");
	// //toolbar.addItem(accountList);
	// reconcilliate.setStyleClass("");
	// reconcilliate.addEvent(this, Event.CLICK);
	// window.getDescendentOfType(EXToolBar.class).addItem(reconcilliate);
	// window.setStyle("width", "650px");
	// return window;
	//
	// }

	// public void reconcilliate(Container source){
	// EXDynaformPanel panel = new EXDynaformPanel("", "Bank reconciliation");
	// panel.addField("Upload file :", new EXUpload("upload"));
	// //panel.addField("Choose bank:", new EXSelect("bank", new
	// DefaultDataModel<Object>().addItem("MCB", "State Bank")));
	//
	// panel.addButton(new EXButton("reconc", "Reconciliate"));
	// panel.addButton(new EXButton("cancel", "Cancel"));
	// panel.setStyle("z-index", "3000");
	// panel.setStyle("width", "900px");
	// source.getAncestorOfType(PopupContainer.class).addPopup(panel);
	// panel.getDescendentByName("cancel").addEvent(Panel.CLOSE_EVENT, CLICK);
	// panel.getDescendentByName("reconc").addEvent(this, CLICK);
	// }
	//
	// public void reconc(Container source){
	// try{
	// EXDynaformPanel panel = source.getAncestorOfType(EXDynaformPanel.class);
	// InputStream in =
	// panel.getDescendentOfType(EXUpload.class).getFile().getInputStream();
	// List<Order> orders = ElieNSonsUtil.loadOrders();
	// List<ReconcilationDTO> rs = ElieNSonsUtil.analyse(in, orders);
	// ReconciliationModel model = new ReconciliationModel(rs, orders);
	// EXTable table = new EXTable("", model);
	// table.setColumnModel(this);
	// table.setCellRenderer(model);
	// EXPagineableTable pTable = new EXPagineableTable("", table);
	// panel.getBody().getParent().addChild(pTable);
	// }catch(Exception e){
	// throw new UIException(e);
	// }
	// }
	//
	//
	// public void saveReconcilliated(Container c){
	// CashBook cb =
	// MallUtil.getCurrentMerchant().getCashBook("DefaultCashBook");
	// EXTable table = c.getAncestorOfType(EXTable.class);
	// int page =table.getCurrentPage();
	// List<ReconcilationDTO> toremove = new ArrayList<ReconcilationDTO>();
	// ReconciliationModel model = (ReconciliationModel)table.getModel();
	// for(int i = 0; i <model.getRowsPerPage(); i++ ){
	// ReconcilationDTO dto = (ReconcilationDTO)model.getValueAt(0, i, page);
	// if(dto.isOk()){
	// ElieNSonsUtil.createItem(dto, cb);
	// toremove.add(dto);
	// }
	// }
	//
	//
	// model.refresh(toremove);
	//
	//
	// table.setModel(model);
	// table.getAncestorOfType(EXPagineableTable.class).refresh();
	//
	// }
	//
	//
	//
	// public void ClientAction(ClientProxy container) {
	// container.mask(container.getAncestorOfType(EXWindow.class));
	// container.makeServerRequest(this);
	//
	// }
	//
	// public boolean ServerAction(Container container,
	// Map<String, String> request) throws UIException {
	//
	//
	// try{
	// getClass().getMethod(container.getName(), Container.class).invoke(this,
	// container);
	// }catch(Exception e){
	// throw new UIException(e);
	// }
	// return true;
	// }
	//
	//
	// @Override
	// public void Success(ClientProxy container, Map<String, String> request)
	// throws UIException {
	// // TODO Auto-generated method stub
	//
	// }

	// @Override
	// public EXContainer getColumnAt(int index, EXTable table, TableModel
	// model) {
	// if(index == 6){
	// EXButton button = new EXButton("saveReconcilliated", "Save");
	// button.addEvent(this, CLICK);
	// return button;
	// }else{
	// EXContainer column = new EXContainer("" + index, "th");
	// column.addClass("ui-widget-header");
	// column.setText(model.getColumnNameAt(index));
	// return column;
	// }
	// }
}
