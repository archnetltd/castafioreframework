package org.castafiore.facebook.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jodd.servlet.ServletAction;

import org.castafiore.facebook.FacebookGraphAPIClient;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.RefreshSentive;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.StringUtil;

public class EXFriendsList extends EXContainer implements TableModel, CellRenderer, RefreshSentive{

	private List<Map<String,String>> friends = new ArrayList<Map<String,String>>();
	
	public EXFriendsList(String name) {
		super(name, "div");
		addChild(new EXContainer("sd", "a").setAttribute("href", "https://www.facebook.com/dialog/oauth/?client_id=180567415418654&redirect_uri=http://www.emallofmauritius.com/templates.html?facebook=true").setText("Invite friends from facebook"));
		
		
	}

	private String[] cols = new String[]{"Friends"};
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return cols.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return cols[index];
	}

	@Override
	public int getRowCount() {
		return friends.size();
	}

	@Override
	public int getRowsPerPage() {
		return 5;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = row + (getRowsPerPage() *page);
		return friends.get(rrow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		
		
		Map<String,String> f = (Map<String,String>)model.getValueAt(column, row, page);
		
		
		return new EXFriend("", f);
		
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		
		Map<String,String> f = (Map<String,String>)model.getValueAt(column, row, page);
		
		
			component.getAncestorOfType(EXFriend.class).setFriend(f);
		
	}

	@Override
	public void onRefresh() {
		Object code = getAncestorOfType(EXWebServletAwareApplication.class).getRequest().getAttribute("code");
		
		if(StringUtil.isNotEmpty(code)){
			this.getChildren().clear();
			setRendered(false);
			
			
			
			String scode = code.toString();
			try{
				String fql = "select uid, first_name, last_name, pic  from user  where uid in (select uid2 from friend WHERE uid1 = me() )";
				friends = SpringUtil.getBeanOfType(FacebookGraphAPIClient.class).executeFql(fql, scode,"180567415418654", "ef8be0651a0d0aa94a51ec454b9a9165","http://www.emallofmauritius.com/templates.html?facebook=true" );
				EXTable t = new EXTable("ts", this,this);
				EXPagineableTable ptable = new EXPagineableTable("as", t);
				addChild(ptable);
				t.setStyle("width", "450px").setStyle("margin", "auto");
				setStyle("width", "450px").setStyle("margin", "auto");
			}catch(Exception e){
				this.getChildren().clear();
				try{
					String s = SpringUtil.getBeanOfType(FacebookGraphAPIClient.class).getAuthorizationUrl("180567415418654", "ef8be0651a0d0aa94a51ec454b9a9165", "http://www.emallofmauritius.com/templates.html?facebook=true");
				addChild(new EXContainer("sd", "a").setAttribute("href", s).setText("Invite friends from facebook"));
				}catch(Exception ee){
					throw new UIException(e);
				}
			}
			
			
			
			//getDescendentOfType(EXPagineableTable.class).refresh();
		}else{
			this.getChildren().clear();
			try{
				String s = SpringUtil.getBeanOfType(FacebookGraphAPIClient.class).getAuthorizationUrl("180567415418654", "ef8be0651a0d0aa94a51ec454b9a9165", "http://www.emallofmauritius.com/templates.html?facebook=true");
			addChild(new EXContainer("sd", "a").setAttribute("href", s).setText("Invite friends from facebook"));
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		
	}

}
