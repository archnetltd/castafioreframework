package org.castafiore.shoppingmall.relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Restrictions;

public class EXFastSearchRelated extends EXPanel implements TableModel, CellRenderer, Event{
	
	private List<Merchant> merchants_;

	private String[] labels = new String[]{"Company Name"};
	private OnSelectItemHandler handler;
	
	private List<String> related;
	public EXFastSearchRelated(String name, String org, String relationship, OnSelectItemHandler handler) {
		super(name, "");
		
		Container body = new EXContainer("", "div");
		
		body.addChild(new EXLabel("lblSearch", "Filter : "));
		body.addChild(new EXAutoComplete("search", "",init(org,relationship)).addEvent(this, BLUR));
		this.handler = handler;
		
		
		EXPagineableTable ptable = new EXPagineableTable("", new EXTable("table",this,this));
		body.addChild(ptable);
		setBody(body);
		getDescendentOfType(EXTable.class).removeClass("EXGrid");
		setStyle("width", "450px");
	}
	
	
	private List<String> init(String org, String relationsip){
		 related = SpringUtil.getRelationshipManager().getRelatedOrganizations(org, relationsip);
		
		List merchants = SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Merchant.class).addRestriction(Restrictions.in("username", related)), Util.getRemoteUser());
		this.merchants_ = merchants;
		
		List<String> dict = new ArrayList<String>();
		
		for(Merchant m : merchants_){
			String[] as = StringUtil.split(m.getCompanyName(), " ");
			if(as != null && as.length > 0){
				for(String s : as){
					if(s.length() > 4){
						dict.add(s);
					}
				}
			}
		}
		
		return dict;
		
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
		return merchants_.size();
	}


	@Override
	public int getRowsPerPage() {
		return 7;
	}


	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrrow = row + (page*getRowsPerPage());
		Merchant m = merchants_.get(rrrow);
		return m;
	}


	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Merchant m = (Merchant)model.getValueAt(column, row, page);
		EXButton button = new EXButton("", m.getCompanyName());
		button.setAttribute("path", m.getAbsolutePath());
		button.removeClass("ui-corner-all");
		button.setStyle("width", "100%");
		return button.addEvent(this, CLICK);
	}


	@Override
	public void onChangePage(Container button, int row, int column,
			int page, TableModel model, EXTable table) {
		Merchant m = (Merchant)model.getValueAt(column, row, page);
		//EXButton button = new EXButton(m.getName(), m.getCompanyName());
		button.setText(m.getCompanyName());
		button.setAttribute("path", m.getAbsolutePath());
		
		
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("search")){
			String term = container.getDescendentOfType(StatefullComponent.class).getValue().toString();
			List merchants = SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Merchant.class).addRestriction(Restrictions.like("companyName", "%"+term+"%")).addRestriction(Restrictions.in("username", related)), Util.getRemoteUser());
			this.merchants_ = merchants;
			getDescendentOfType(EXPagineableTable.class).refresh();
			return true;
		}
		
		String path = container.getAttribute("path");
		Merchant m = (Merchant)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		
		this.handler.selectItem(m);
		remove();
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
