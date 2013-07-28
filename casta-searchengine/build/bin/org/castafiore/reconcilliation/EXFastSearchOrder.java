package org.castafiore.reconcilliation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.castafiore.persistence.Dao;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.orders.OrdersUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.AutoCompleteSource;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.js.JArray;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXFastSearchOrder extends EXPanel implements Event, TableModel,CellRenderer{
	//private List<MerchantSubscription> subscribers = new ArrayList<MerchantSubscription>();
	
	//private List<Map<String,String>> allorders = new ArrayList<Map<String,String>>();

	private List<Map<String,String>> filtered = new ArrayList<Map<String,String>>();
	
	public EXFastSearchOrder(String name, ReconcilationDTO dto) {
		super(name, "Quickly search orders");
		
		//allorders = ReconciliationUtil.loadOrdersValues();
		Container tb = addSearch();
		
		
		Container body = new EXContainer("bb", "div");
		setBody(body);
		body.addChild(tb);
		
//		for(Map<String,String> d : allorders){
//			String s = d.toString().toLowerCase();
//			if(s.contains(dto.getRefNumber().toLowerCase()) || s.contains(dto.getAccountNumber().toLowerCase()) || s.contains(dto.getBank().toLowerCase())){
//				filtered.add(d);
//			}else{
//				String[] parts = StringUtil.split(dto.getName(), " ");
//				for(String part : parts){
//					if(part.length() > 4 && s.contains(part.toLowerCase())){
//						filtered.add(d);
//						break;
//					}
//				}
//			}
//			
//		}
		
		filter(dto);
		
		EXTable table = new EXTable("tn", this);
		EXPagineableTable ptable = new EXPagineableTable("pt", table);
		body.addChild(ptable);
		table.setCellRenderer(this);
		setStyle("width", "700px").setStyle("z-index", "6000");
	}
	
	
	private void filter(ReconcilationDTO dto){
		String like = "select value from WFS_FILE where (value like '%-:;:-accountNumber:" +  dto.getAccountNumber() + "-:;:-%' or ";
		like = like + " value like '%-:;:-bankName:" +  dto.getBank() + "-:;:-%' or ";
		like = like + " value like '%-:;:-code:" +  dto.getRefNumber() + "-:;:-%') and ";
		like = like + " dtype='Value' and name ='applicationForm'";
		
		
		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(like).setMaxResults(100).list();
		filtered.clear();
		for(Object s : l){
			filtered.add(BillingInformation.buildMap(s.toString()));
		}
		try{
		getDescendentOfType(EXTable.class).setModel(this);
		getDescendentOfType(EXPagineableTable.class).refresh();
		}catch(Exception e){
			
		}
	}
	
	private void filter(String term){
		String like = "select value from WFS_FILE where value like '%"+term+"%' and ";
		
		like = like + " dtype='Value' and name ='applicationForm'";
		
		
		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(like).setMaxResults(100).list();
		filtered.clear();
		for(Object s : l){
			filtered.add(BillingInformation.buildMap(s.toString()));
		}
		
		getDescendentOfType(EXTable.class).setModel(this);
		getDescendentOfType(EXPagineableTable.class).refresh();
	}
	
	private Container addSearch(){
		
		
		//Collections.sort(dict);
			
		EXToolBar tb = new EXToolBar("tb");
		Container  complete = new EXAutoComplete("searchInput", "").setSource(new AutoCompleteSource() {
			
			@Override
			public JArray getSource(String param) {
				String query = "select subscriber, firstName, lastName, company, phone, mobile , fax, email, addressLine1, addressLine2, city, country, zipPostalCode, nic from WFS_FILE where dtype='MerchantSubscription'";
				JArray dict = new JArray();
				try{
					BasicDataSource ds = SpringUtil.getBeanOfType(BasicDataSource.class);
					Connection con = ds.getConnection();
					ResultSet rs = con.prepareStatement(query).executeQuery();
					while(rs.next()){
						for(int i =1; i <= 14; i++){
							String s = rs.getString(i);
							if(s != null && s.trim().length() > 0 && !dict.contains(s.trim())){
								dict.add(s);
							}
							
							
						}
						
					}

				}catch(Exception e){
					throw new UIException(e);
				}
				return dict;
			}
		}).setStyle("margin-left", "30px").setStyle("width", "300px").setStyle("float", "left");
		tb.addChild(complete);
		Container search = new EXContainer("search", "a").setStyle("float", "left").setAttribute("href", "#").setText("<img src='icons-2/fugue/icons/binocular.png'></img>").addEvent(this, CLICK);
		tb.addChild(search);
		
		
		return tb;
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equals("chq")){
			
			
			String txt = container.getText(false);
			container.setText("");
			EXInput in = new EXInput("ss");
			in.setValue(txt);
			container.addChild(in);
			in.addEvent(this, BLUR);
			container.getEvents().clear();
			container.setRendered(false);
			return true;
		}else if(container.getName().equalsIgnoreCase("ss")){
			String value = ((EXInput)container).getValue().toString();
			if(StringUtil.isNotEmpty(value.trim())){
				Order o = OrdersUtil.getOrderByCode(container.getParent().getAttribute("path"));//(Order)SpringUtil.getRepositoryService().getFile(container.getParent().getAttribute("path"), Util.getRemoteUser());
				o.setChequeNo(value);
				o.save();
				Container p = container.getParent();
				p.getChildren().clear();
				p.setRendered(false);
				p.addEvent(this, DOUBLE_CLICK);
				p.setText(value);
			}
			return true;
		}
		
		String txt = getDescendentOfType(EXAutoComplete.class).getValue().toString().toLowerCase();
		
		filter(txt);
		
		
		
		
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
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
		return filtered.size();
	}


	@Override
	public int getRowsPerPage() {
		return 10;
	}


	@Override
	public Object getValueAt(int col, int row, int page) {
		int realRow = (page*getRowsPerPage()) + row;
		
		
		Map<String, String> sb = filtered.get(realRow);
		//Map<String,String> o = ReconciliationUtil.searchFromCode(allorders, sb.getSubscriber());
		if(col == 0){
			return sb.get("fsNumber");
		}else if(col ==1){
			return sb.get("cfullName") + " " + sb.get("cSurname");
		}else if(col == 2){
			String ch = sb.get("accountNumber");
			if(ch== null || !StringUtil.isNotEmpty(ch.toString())){
				ch = "No Account Number";
			}
			return ch + "|-|" +sb.get("fsNumber");
		}else if(col == 3){
			return sb.get("bankName");
		}else{
			return sb.get("monthlyInstallment");
		}
	}
	
	
	


	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	private String[] labels = new String[]{"Invoice.", "Customer","Bank acc.", "Bank", "Installment"};
	
	


	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Object o = model.getValueAt(column, row, page);
		String s = "";
		if(o!= null){
			s = o.toString();
		}
		
		
		Container ss = new EXContainer("", "span");
		
		if(column == 2){
			Container span = new EXContainer("chq", "span");
			span.addEvent(this, DOUBLE_CLICK);
			String[] as = StringUtils.splitByWholeSeparator(s, "|-|");
			span.setText(as[0]);
			span.setAttribute("path", as[1]);
			return span;
			
		}else{
			ss.setText(s);
			return ss;
		}
		//return span;
	}


	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		String s = model.getValueAt(column, row, page).toString();
		
		
		Container ss = new EXContainer("", "span");
		
		if(column == 2){
			//Container span = new EXContainer("chq", "span");
			//span.addEvent(this, DOUBLE_CLICK);
			String[] as = StringUtil.split(s,"|-|");
			component.setText(as[0]);
			component.setAttribute("path", as[1]);
			
			
		}else{
			component.setText(s);
			//return ss;
		}
		
	}
	

}
