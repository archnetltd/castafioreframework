package com.eliensons.ui.sales;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.inventory.orders.OrderService;
import org.castafiore.persistence.Dao;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableColumnModel;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.FileAlreadyExistException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Value;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.eliensons.ui.ElieNSonsUtil;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;



public class EXElieNSonsPreprintInvoice extends EXXHTMLFragment implements TableModel, CellRenderer, Event, TableColumnModel{
	
	private List<Value> codes = null;

	public EXElieNSonsPreprintInvoice(String name) {
		super(name, "templates/EXElieAndSonsPreprintInvoice.xhtml");
		addChild(new EXButton("generate", "Generate").addEvent(this, Event.CLICK));
		addChild(new EXInput("toGenerate"));
		addChild(new EXInput("toStart"));
		
		try{
		
		
		
		
		EXSelect posSelect = new EXSelect("posToGenerate", new OrderService().getPointOfSales());
		addChild(posSelect);
		}catch(Exception e){
			throw new UIException(e);
		}
		
		
		EXPagineableTable ptable = new EXPagineableTable("codes", new EXTable("codes_table", this));
		addChild(ptable.setStyle("clear", "left"));
		getDescendentOfType(EXTable.class).setCellRenderer(this);
		getDescendentOfType(EXTable.class).setColumnModel(this);
		addChild(new EXButton("delete", "Delete Selected").addEvent(this, Event.CLICK));
		addChild(new EXButton("print", "Print selected").addEvent(this, Event.CLICK));
		addChild(new EXContainer("printed", "div"));
		init();
	}
	
	private void init(){
		int cur = 0;
		Calendar cal = Calendar.getInstance();
		String currentYear = (cal.get(Calendar.YEAR) - 2000) + "";
		Merchant merchant = MallUtil.getCurrentMerchant();
		Directory codes = (Directory)merchant.getFile("invoiceCodes");
		if(codes == null){
			codes = merchant.createFile("invoiceCodes", Directory.class);
		}else{
			QueryParameters params = new QueryParameters();
			params.setEntity(Value.class).addSearchDir(codes.getAbsolutePath()).addOrder(Order.desc("value")).setFirstResult(0).setMaxResults(1);
			
			
			List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
			if(result.size() > 0){
				String name =((Value)result.get(0)).getName();
				if(name.endsWith(currentYear)){
					String[] parts = StringUtil.split(name, "/");
					cur = Integer.parseInt(parts[1]);
					
				}
			}
		}
		
		((EXInput)getDescendentByName("toStart")).setValue(cur + "");
		((EXInput)getDescendentByName("toGenerate")).setValue(30);
	}
	
	public void generate(int amount, String pos){
		int cur = 0;
		Calendar cal = Calendar.getInstance();
		String currentYear = (cal.get(Calendar.YEAR) - 2000) + "";
		Merchant merchant = MallUtil.getCurrentMerchant();
		Directory codes = (Directory)merchant.getFile("invoiceCodes");
		cur = Integer.parseInt(((EXInput)getDescendentByName("toStart")).getValue().toString());
		for(int i =1; i <= amount; i++){
			while(true){
				String ss = (cur + i) + "";
				for(int l = ss.length(); l <=4; l++){
					ss = "0" + ss;
				}
			
				try{
					Value value = codes.createFile("FS" + ss + "/" + currentYear, Value.class);
					value.setString(currentYear + ss + "@" + pos);
					break;
					
				}catch(FileAlreadyExistException fae){
					cur = cur+1;
				}
			}
		}
		merchant.save();
	}
	
	
	
	public List<Value> getCodes(){
		if(codes == null){
			Merchant merchant = MallUtil.getCurrentMerchant();
			Directory dcodes = (Directory)merchant.getFile("invoiceCodes");
			if(dcodes != null){
				QueryParameters params = new QueryParameters();
				params.setEntity(Value.class).addSearchDir(dcodes.getAbsolutePath()).addOrder(Order.desc("value"));	
				List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
				codes = result;
			}else{
				codes =new  ArrayList<Value>();
			}
		}
		return codes;
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}


	@Override
	public int getColumnCount() {
		return 4;
	}


	@Override
	public String getColumnNameAt(int index) {
		if(index == 0){
			return "";
		}else if(index == 1){
			return "Codes";
		}else if(index == 2){
			return "POS";
		}else{
			return "Status";
		}
	}


	@Override
	public int getRowCount() {
		return getCodes().size();
	}


	@Override
	public int getRowsPerPage() {
		return 20;
	}


	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = (getRowsPerPage()*page) + row;
		return getCodes().get(rrow);
	}


	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}


	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Value val = (Value)model.getValueAt(column, row, page);
		if(column == 0){
			if(val.getStatus() == Value.STATE_PUBLISHED){
				return new EXContainer("", "div");
			}else{
				Container cb = new EXCheckBox("").setAttribute("path", val.getAbsolutePath());
				return cb;
			}
			
		}else if(column == 1){
			return new EXContainer("", "span").setText(val.getName());
		}else if(column == 2){
			String s = val.getString();
			String pos = "Beau Bassin";
			if(StringUtil.isNotEmpty(s) && s.contains("@")){
				pos = StringUtil.split(s, "@")[1];
			}
			return new EXContainer("", "span").setText(pos);
		}else {
			if(val.getStatus() == Value.STATE_PUBLISHED){
				return new EXContainer("", "span").setText("Printed");
			}else{
				return new EXContainer("", "span").setText("New");
			}
		}
	}
	
	public void printItems(){
		final List<String> paths = new ArrayList<String>();
		ComponentUtil.iterateOverDescendentsOfType(getDescendentOfType(EXTable.class), EXCheckBox.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				EXCheckBox cb = (EXCheckBox)c;
				if(cb.isChecked()){
					paths.add(cb.getAttribute("path"));
				}
				
			}
		});
		
		
		QueryParameters params = new QueryParameters().setEntity(Value.class).addRestriction(Restrictions.in("absolutePath", paths));
		
		
		List<File> files = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		try{
		//Document document = new Document();
		Merchant m = MallUtil.getCurrentMerchant();
		BinaryFile ff = m.createFile("appforms_" + System.currentTimeMillis() + ".xls", BinaryFile.class);
		OutputStream fout =ff.getOutputStream();
		Workbook wb = new HSSFWorkbook(new ByteArrayInputStream(ResourceUtil.readUrlBinary("http://68.68.109.26/elie/applicationform.xls")));
		for(File f : files){
			
			Value val = (Value)f;
			String code = val.getName();
			
			Sheet sheet =wb.cloneSheet(0);
			sheet.getRow(7).getCell(2).setCellValue(code);
			f.setStatus(File.STATE_PUBLISHED);
			f.save();
		}
		wb.write(fout);
		fout.flush();
		fout.close();
		m.save();
		getChild("printed").setText("<label>Download PDF and print forms</label></br><a href='"+ResourceUtil.getDownloadURL("ecm", ff.getAbsolutePath())+"' target='_blank'>Click Here to download</a>");
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		Value val = (Value)model.getValueAt(column, row, page);
		if(column == 0){
			if(val.getStatus() == Value.STATE_PUBLISHED){
				if(component instanceof EXCheckBox){
					Container parent = component.getParent();
					component.remove();
					parent.addChild(new EXContainer("", "div"));
				}
				
			}else{
				
				if(!(component instanceof EXCheckBox)){
					Container cb = new EXCheckBox("").setAttribute("path", val.getAbsolutePath());
					Container parent = component.getParent();
					component.remove();
					parent.addChild(cb);
				}
			}
			
		}else if(column == 1){
			component.setText(val.getName());
		}else if(column == 2){
			String s = val.getString();
			String pos = "Beau Bassin";
			if(StringUtil.isNotEmpty(s) && s.contains("@")){
				pos = StringUtil.split(s, "@")[1];
			}
			component.setText(pos);
		}else {
			if(val.getStatus() == Value.STATE_PUBLISHED){
				component.setText("Printed");
			}else{
				component.setText("New");
			}
		}
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("selectAll")){
			
			final boolean isc = ((EXCheckBox)container).isChecked();
			ComponentUtil.iterateOverDescendentsOfType(getDescendentOfType(EXTable.class), EXCheckBox.class, new ComponentVisitor() {
				
				@Override
				public void doVisit(Container c) {
					EXCheckBox cb = (EXCheckBox)c;
					if(isc){
						if(cb.isChecked()){
							
						}else
						{
							cb.setChecked(true);
						}
					}else{
						if(cb.isChecked()){
							cb.setChecked(false);
						}else
						{
							
						}
					}
					
				}
			});
			return true;
		}
		
		if(container.getName().equals("delete")){
			final List<String> paths = new ArrayList<String>();
			ComponentUtil.iterateOverDescendentsOfType(getDescendentOfType(EXTable.class), EXCheckBox.class, new ComponentVisitor() {

				@Override
				public void doVisit(Container c) {
					EXCheckBox cb = (EXCheckBox)c;
					if(cb.isChecked()){
						paths.add(cb.getAttribute("path"));
					}
				}
			});
			
			Session session = SpringUtil.getBeanOfType(Dao.class).getSession();
			int i =session.createQuery("delete from " + Value.class.getName() + " where absolutePath in (:abb)").setParameterList("abb", paths).executeUpdate();
		}else if(container.getName().equals("print")){
			printItems();
		}else{
			try{
				String pos = getDescendentOfType(EXSelect.class).getValue().toString();
				int amount = Integer.parseInt(getDescendentOfType(EXInput.class).getValue().toString());
				generate(amount,pos);
			}catch(Exception e){
				getDescendentOfType(EXInput.class).addClass("ui-state-error");
			}
			
		}
		
		codes = null;
		getDescendentOfType(EXTable.class).setModel(this);
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public EXContainer getColumnAt(int index, EXTable table, TableModel model) {
		EXContainer column = new EXContainer("" + index, "th");
		column.addClass("ui-widget-header");
		if(index == 0){
			column.addChild(new EXCheckBox("selectall").addEvent(this, CLICK));
		}else{
			column.setText(model.getColumnNameAt(index));
		}
		return column;
	}
	
	
	

}
