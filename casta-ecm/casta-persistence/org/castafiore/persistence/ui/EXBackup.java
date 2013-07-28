package org.castafiore.persistence.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.castafiore.persistence.DatabaseDumper;
import org.castafiore.persistence.DatabaseImporter;
import org.castafiore.resource.FileData;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ChannelUtil;
import org.castafiore.utils.StringUtil;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;

public class EXBackup extends EXPanel implements TableModel, CellRenderer, Event{

	private String[] cols = new String[]{"Date", "Created By","Download","Size", "Restore"};

	//public static String backupdir = "C:\\apache-tomcat-6.0.18\\webapps\\casta-ui\\backup";	
	//public static Object factory = new PostgresqlDataTypeFactory();
	
	public static String backupdir = "/usr/local/software/tomcat6/webapps/elie/backup";	
	public static Object factory = new MySqlDataTypeFactory();
	
	
	
	private List<File> files = new ArrayList<File>();
	
	public EXBackup(String name) {
		super(name, "Backup and restore");
		
		
		getBodyContainer().addChild(new EXContainer("msg", "h5").setStyle("padding", "30px").addClass("ui-state-error").setText("When creating a backup, the data will be saved on the server itself. You can then download the data and store it in a safe place."));
		
		Container backup = new EXContainer("backup", "button").setText("Create Backup");
		backup.addEvent(this, CLICK);
		getBodyContainer().addChild(backup);
		
		Container upload = new  EXContainer("upload", "button").setText("Upload Backup");
		upload.addEvent(this, CLICK);
		getBodyContainer().addChild(upload);
		
		ref();
		EXTable table = new EXTable("bb", this);
		table.setCellRenderer(this);
		EXPagineableTable pTable = new EXPagineableTable("pbb", table);
		getBodyContainer().setStyle("padding", "15px").addChild(pTable.setStyle("clear", "both"));
		setStyle("width", "700px");
		
	}
	
	public void ref(){
		File[] ffs = new File(backupdir).listFiles();
		files.clear();
		for(File f : ffs){
			if(!f.isDirectory()){
				files.add(f);
			}
		}
		
		
	}
	
	public void refTable(){
		getDescendentOfType(EXTable.class).setModel(this);
		getDescendentOfType(EXPagineableTable.class).refresh();
	}

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
		return files.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int realRow = (page*getRowsPerPage()) + row;
		return files.get(realRow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		File file = (File)model.getValueAt(column, row, page);
		
		Container span = new EXContainer("", "span");
		if(column == 0){
			Date d = new Date(file.lastModified());
			span.setText(new SimpleDateFormat("dd/MM/yyyy").format(d));
			return span;
		}else if(column == 1){
			//String user = StringUtil.split(file.getName(), "-")[1];
			span.setText("elieandsons");
			return span;
		}else if(column == 2){
			span= new EXContainer("", "a");
			span.setText("Download");
			span.setAttribute("href", "/elie/backup/" + file.getName());
			span.setAttribute("target", "_blank");
			return span;
		}else if(column == 3){
			span.setText(file.length() + "");
			return span;
		}else{
			span = new EXContainer("restore", "button").setText("Restore").setAttribute("file", file.getAbsolutePath()).addEvent(this, CLICK);
			return span;
		}
	}

	@Override
	public void onChangePage(Container span, int row, int column,
			int page, TableModel model, EXTable table) {
		File file = (File)model.getValueAt(column, row, page);

		if(column == 0){
			Date d = new Date(file.lastModified());
			span.setText(new SimpleDateFormat("dd/MM/yyyy").format(d));
			
		}else if(column == 1){
			String user = StringUtil.split(file.getName(), "::")[1].replace(".sql", "");
			span.setText(user);
			
		}else if(column == 2){
			span.setAttribute("href", "/elie/backup/" + file.getName());
		}else if(column == 3){
			span.setText(file.length() + "");
		}else{
			span.setAttribute("file", file.getAbsolutePath());
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("backup")){
//			String fName = System.currentTimeMillis() + "::" + Util.getRemoteUser();
//			DumpAndRestor.dump("eliensonsv2", backupdir + "/" + fName + ".sql" );
//			ref();
//			refTable();
			
			Thread t = new Thread(new DatabaseDumper());
			t.start();
			container.setText("Backup in progress...");
			container.getParent().getDescendentByName("msg").setText("Backing up of database in progress.<br> It will take about 1/2 and hours to properly backup the database. <br>You can continue working in the meantime<br>Please log out and then log back in to view result in table below");
			
		}else if (container.getName().equalsIgnoreCase("upload")){
			//restor comes here
			
			EXDynaformPanel panel = new EXDynaformPanel("up", "Upload backup data");
			panel.setStyle("width", "500px");
			panel.addField("Upload content :", new EXUpload("content"));
			panel.addButton(new EXButton("save", "Save"));
			panel.addButton(new EXButton("cancel", "Cancel"));
			panel.getDescendentByName("save").addEvent(this, CLICK);
			panel.getDescendentByName("cancel").addEvent(CLOSE_EVENT, CLICK);
			getAncestorOfType(PopupContainer.class).addPopup(panel);
			panel.setStyle("z-index", "4000");
			
			
		}else if(container.getName().equals("save")){
			try{
			FileData data = (FileData)container.getAncestorOfType(EXDynaformPanel.class).getField("content").getValue();
			FileOutputStream out = new FileOutputStream(backupdir + "/" + data.getName());
			ChannelUtil.TransferData(data.getInputStream(), out);
			ref();
			refTable();
			}catch(Exception e){
				throw new UIException(e);
			}
		}else if(container.getName().equals("restore")){
			try{
				String path = container.getAttribute("file");
				String sDir = path.replace(".zip", "");
				File dir = new File(sDir);
				if(!dir.exists()){
					dir.mkdir();
					ZipFile zip = new ZipFile(new File(path));
					Enumeration entries = zip.entries();
					while(entries.hasMoreElements()) {
						ZipEntry entry = (ZipEntry)entries.nextElement();
						if(!entry.isDirectory()){
							String fName = sDir + "/" + entry.getName();
							FileOutputStream fout = new FileOutputStream(fName);
							ChannelUtil.TransferData(zip.getInputStream(entry), fout);
						}
					}
				}
				DatabaseImporter importer = new DatabaseImporter(sDir);
				Thread t = new Thread(importer);
				t.start();
				container.getAncestorOfType(EXPanel.class).getDescendentByName("msg").setText("Restoring of database in progress.<br> It will take about 1/2 and hours to properly restore the database. <br>You can continue working in the meantime or close your browser<br>");
				//importer.run()
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
