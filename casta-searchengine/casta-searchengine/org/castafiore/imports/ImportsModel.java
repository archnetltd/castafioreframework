package org.castafiore.imports;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.inventory.orders.OrderService;
import org.castafiore.shoppingmall.imports.Importer;
import org.castafiore.shoppingmall.imports.OrderImporterTemplateV2;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.BinaryFile;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.io.Resource;

public class ImportsModel implements TableModel, CellRenderer, Event{

	
	private String[] label = new String[]{"Date", "File Name", "Sheets", "Log"};
	
	
	private List<BinaryFile> files = new ArrayList<BinaryFile>();
	
	
	public ImportsModel(){
		QueryParameters params = new QueryParameters();
		params.setEntity(BinaryFile.class).addRestriction(Restrictions.eq("parent.absolutePath", "/root/users/" + Util.getLoggedOrganization() + "/imports"));
		files = (List)SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		for(BinaryFile f : files){
			f.getFiles();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return label.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return label[index];
	}

	@Override
	public int getRowCount() {
		return files.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int realRow = (getRowsPerPage()*page) + row;
		return files.get(realRow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		
		try{
		BinaryFile bf = (BinaryFile)getValueAt(column, row, page);
		int realRow = (page*getRowsPerPage()) + row;
		Container c = new EXContainer("s", "span");
		if(column == 0){
			return c.setText(new SimpleDateFormat("dd MMM yyyy").format(bf.getDateCreated().getTime()));
		}else if(column == 1){
			return c.setText(bf.getName());
		}else if(column == 2){
			try{
				c = new EXContainer("s", "div");
				Workbook wb = new HSSFWorkbook(bf.getInputStream());
				
				for(int i = 0; i < wb.getNumberOfSheets(); i++){
					String sheetName = wb.getSheetName(i);
					Container s =new EXContainer(sheetName, "a").setAttribute("row",realRow + "" ).setAttribute("href", "#").setText(sheetName).addEvent(this, Event.CLICK);
					 c.addChild(s).addChild(new EXContainer("", "span").setText(" | "));
					
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			return c;
			
		}else {
			c = new EXContainer("s", "a").setAttribute("href", ResourceUtil.getDownloadURL("ecm", bf.getAbsolutePath() + "/logs.txt")).setAttribute("target", "_blank").setText("View");
			return c;
		}
		}catch(Exception e){
			return new EXContainer("", "div").setText("??");
		}
		
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	private void import_( String pos,boolean promo, int row, String name){
		
		
		final BinaryFile bf = files.get(row);
		
		OrderImporterTemplateV2 imp = new OrderImporterTemplateV2();
		imp.setBatchSize(30);
		imp.setMerchant("elieandsons");
		imp.setFirstRow(1);
		imp.setSheet(name);
		imp.setExcelFile(new Resource() {
			
			@Override
			public InputStream getInputStream() throws IOException {
				try{
				return bf.getInputStream();
				}catch(Exception e){
					throw new IOException(e);
				}
			}
			
			@Override
			public long lastModified() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public boolean isReadable() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public boolean isOpen() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public URL getURL() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public URI getURI() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getFilename() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public File getFile() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean exists() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public Resource createRelative(String arg0) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		Importer<Row> importer = new Importer<Row>();
		importer.setImporterTemplate(imp);
		Map<String,String> ad = new HashMap<String, String>();
		ad.put("pointOfSale", pos);
		if(promo){
			ad.put("3monthpromotion", "true");
		}
		importer.doImport(ad);
		List<String> errors = imp.errors;
		
		BinaryFile log = bf.getFile("logs.txt", BinaryFile.class);
		if(log == null){
			bf.createFile("logs.txt", BinaryFile.class);
			bf.save();
		}
		
		StringBuilder b = new StringBuilder();
		for(String error : errors){
			b.append("Sheet: " + name + "->").append(error).append("\n");
		}
		try{
		log.append(b.toString().getBytes());
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("save")){
			String pos = container.getAncestorOfType(EXDynaformPanel.class).getField("pos").getValue().toString();
			boolean ispromo = container.getAncestorOfType(EXDynaformPanel.class).getDescendentOfType(EXCheckBox.class).isChecked();
			
			import_(pos,ispromo, Integer.parseInt(container.getAttribute("row")), container.getAttribute("name_"));
			return true;
		}
		try{
			EXDynaformPanel panel = new EXDynaformPanel("panel", "Specify additional info before Import");
			panel.setStyle("width", "500px");
			
			
			EXSelect posSelect = new EXSelect("pos", new OrderService().getPointOfSales());
			panel.addField("Point of sale :", posSelect);
			
			EXCheckBox promo = new EXCheckBox("promo",false);
			panel.addField("3 Months promotion :", promo);
			
			panel.addButton(new EXButton("save", "Import"));
			panel.addButton(new EXButton("close", "Cancel"));
			
			panel.getDescendentByName("save").addEvent(this, CLICK).setAttribute("row", container.getAttribute("row")).setAttribute("name_", container.getName());
			panel.getDescendentByName("close").addEvent(Panel.CLOSE_EVENT, Event.CLICK);
			container.getAncestorOfType(PopupContainer.class).addPopup(panel);
			
		}catch(Exception e){
			throw new UIException(e);
		}
		
		
		
		
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
