package org.castafiore.accounting.ui;

import org.castafiore.accounting.CashBook;
import org.castafiore.searchengine.MallUtil;

import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXScrollableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.ex.tree.TreeNode;
import org.castafiore.ui.menu.EXMenu;
import org.castafiore.ui.menu.EXMenuItem;
import org.castafiore.ui.menu.MenuListener;
import org.castafiore.ui.menu.MutableMenuTreeNode;

public class EXAccounting extends EXContainer implements MenuListener, PopupContainer{

	public EXAccounting(String name) {
		super(name, "div");
		
		addChild(new EXOverlayPopupPlaceHolder("popup"));
		EXToolBar toolbar = new EXToolBar("accountsToolBar");
		
		EXMenu accounts = new EXMenu("accountsMenu", getAccountsNode());
		accounts.addMenuListener(this);
		toolbar.addItem(accounts);
		
		EXMenu cash = new EXMenu("cashBookMenu", getCashBookNodes());
		cash.addMenuListener(this);
		toolbar.addItem(cash);
		
		
		addChild(toolbar);
		
		addChild(new EXContainer("body", "div"));
	}
	
	
	private TreeNode<EXMenuItem> getCashBookNodes(){
		MutableMenuTreeNode root = new MutableMenuTreeNode(null, new EXMenuItem("cashbookroot", "CashBook"));
		root.addChild(new EXMenuItem("cashBook", "Cash Book"));
		root.addChild(new EXMenuItem("newEntry", "New entry"));
		root.addChild(new EXMenuItem("exportCashBook", "Export Cash Book"));
		
		return root;
	}
	
	private TreeNode<EXMenuItem> getAccountsNode(){
		MutableMenuTreeNode root = new MutableMenuTreeNode(null, new EXMenuItem("accounts", "Accounts"));
		root.addChild(new EXMenuItem("accountList", "Account List"));
		root.addChild(new EXMenuItem("newAccount", "New Account"));
		root.addChild(new EXMenuItem("exportAccount", "Export Accounts"));
		
		return root;
		
		
	}
	
	
	public void accountList(Container source){
		//EXWindow panel = source.getAncestorOfType(EXWindow.class);
		Container body = getChild("body");
		body.getChildren().clear();
		body.setRendered(false);

		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		EXTable table =new EXTable("taleList", new AccountModel(book.getAbsolutePath()));
		table.setStyle("width", "100%");
		table.setCellRenderer(new AccountCellRenderer());
		
		EXScrollableTable pTable = new EXScrollableTable("tableList", table);
		
		//EXPagineableTable pTable = new EXPagineableTable("tableList", table);
		pTable.setStyle("width", "100%").setStyle("height", "410px");
		body.addChild(pTable);
		
	}
	
	public void newAccount(Container source){
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		BAccountForm form = new BAccountForm("BAccountForm",book.getAbsolutePath());
		form.New();
		source.getAncestorOfType(PopupContainer.class).addPopup(form.setStyle("z-index", "3000"));
	}
	
	public void cashBook(Container source){
		
		Container body = getChild("body");
		body.getChildren().clear();
		body.setRendered(false);
		
		
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		EXTable table =new EXTable("cashBook", new CashBookEntryModel(book.getAbsolutePath()));
		table.setStyle("width", "100%");
		table.setCellRenderer(new CashBookEntryCellRenderer());
		EXScrollableTable pTable = new EXScrollableTable("tableList", table);
		pTable.setStyle("width", "100%").setStyle("height", "410px");
		body.addChild(pTable);
			
	}
	
	
	public void newEntry(Container source){
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		BCashBookEntryForm form = new BCashBookEntryForm("BCashBookEntryForm", book.getAbsolutePath());
		form.New();
		source.getAncestorOfType(PopupContainer.class).addPopup(form);
	}
	
	public void exportAccount(Container source){
		Container body = getChild("body");
		body.getChildren().clear();
		body.setRendered(false);
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		EXExportAccount acc = new EXExportAccount("exs", book.getAbsolutePath());
		body.addChild(acc);
	}
	
	
	public void exportCashBook(Container source){
		Container body = getChild("body");
		body.getChildren().clear();
		body.setRendered(false);
		CashBook book = MallUtil.getCurrentMerchant().createCashBook("DefaultCashBook");
		EXExportCashBook acc = new EXExportCashBook("exs", book.getAbsolutePath());
		body.addChild(acc);
	}


	@Override
	public void onSelectItem(EXMenuItem menuItem, EXMenu menu) {
		try{
			getClass().getMethod(menuItem.getName(), Container.class).invoke(this, menuItem);
			}catch(Exception e){
				throw new UIException(e);
			}
		
	}


	@Override
	public void addPopup(Container popup) {
		getChild("popup").addChild(popup);
		
	}

}
