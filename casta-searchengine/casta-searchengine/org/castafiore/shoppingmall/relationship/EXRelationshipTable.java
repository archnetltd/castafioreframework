package org.castafiore.shoppingmall.relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.community.ui.relationships.EXOrganizationDashBoard;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Restrictions;

public class EXRelationshipTable extends EXTable implements TableModel, CellRenderer, Event {
	
	private String firstOrganization;
	
	private List<Merchant> related = new ArrayList<Merchant>();
	
	private String[] labels = new String[]{"username", "Company Name", "eMail"};

	public EXRelationshipTable(String name,	String firstOrganization) {
		super(name, null);
		
		this.firstOrganization = firstOrganization;
		
		QueryParameters params = new QueryParameters().setEntity(Merchant.class);
		List<String> usernames = SpringUtil.getRelationshipManager().getRelatedOrganizations(this.firstOrganization);
		if(usernames.size() ==0){
			usernames.add("-fo");
		}
		params.addRestriction(Restrictions.in("username", usernames));
		
		List l = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		related = l;
		setModel(this);
		setCellRenderer(this);
	}

	
	public void filter(String relation){
		QueryParameters params = new QueryParameters().setEntity(Merchant.class);
		List<String> usernames = SpringUtil.getRelationshipManager().getRelatedOrganizations(this.firstOrganization,relation);
		if(usernames.size() ==0){
			usernames.add("-fo");
		}
		params.addRestriction(Restrictions.in("username", usernames));
		
		List l = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		related = l;
		if(getAncestorOfType(EXPagineableTable.class) == null){
			refresh();
		}else{
			getAncestorOfType(EXPagineableTable.class).refresh();
		}
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return labels[index];
	}

	@Override
	public int getRowCount() {
		return related.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = (getRowsPerPage()*page) + row;
		return related.get(rrow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Merchant m = (Merchant)model.getValueAt(column, row, page);
		if(column == 0){
			return new EXContainer("", "span").setText(m.getUsername());
		}else if(column == 1){
			return new EXContainer("company", "a").addEvent(this, CLICK).setAttribute("href", "#").setAttribute("organization", m.getUsername()).setText(m.getCompanyName());
		}else {
			return new EXContainer("", "span").setText(m.getEmail());
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		Merchant m = (Merchant)model.getValueAt(column, row, page);
		if(column == 0){
			component.setText(m.getUsername());
		}else if(column == 1){
			component.setAttribute("organization", m.getUsername()).setText(m.getCompanyName());
		}else {
			component.setText(m.getEmail());
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		//EXOrganizationDashBoard dash = new EXOrganizationDashBoard("",container.getAttribute("organization") );
		Merchant m = MallUtil.getMerchant(container.getAttribute("organization"));
		EXPanel panel = new EXPanel("oroo", "Organization dashboard");
		panel.setStyle("width", "850px");
		panel.setBody(new EXCompanyCard("cc", m));
		container.getAncestorOfType(PopupContainer.class).addPopup(panel);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	

}
