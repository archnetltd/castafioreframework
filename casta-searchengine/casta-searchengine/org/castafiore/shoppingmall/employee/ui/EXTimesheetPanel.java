/**
 * 
 */
package org.castafiore.shoppingmall.employee.ui;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.KeyValuePair;
import org.castafiore.searchengine.back.EXWindow;
import org.castafiore.shoppingmall.employee.ui.model.LoaderUtil;
import org.castafiore.shoppingmall.employee.ui.tables.MyTimesheetCellRenderer;
import org.castafiore.shoppingmall.employee.ui.tables.MyTimesheetTableModel;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.EXTableWithExport;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

/**
 * @author acer
 * 
 */
public class EXTimesheetPanel extends EXWindow implements Event {

	public EXTimesheetPanel() throws Exception {
		this("Timesheet");
	}

	/**
	 * @param name
	 * @param title
	 */
	public EXTimesheetPanel(String name) throws Exception {
		super(name, "Timesheet");

		Container body = new EXContainer("bo", "div")
				.addClass("ui-widget-content");
		EXFieldSet fieldset = new EXFieldSet("field", "My timesheet", false);
		fieldset.setStyle("width", "500px");
		fieldset.addField("From :", new EXDatePicker("from"));
		fieldset.addField("To : ", new EXDatePicker("to"));
		body.addChild(fieldset);

		//List<KeyValuePair> data = getUsers();
//		// DefaultDataModel<Object> model = new DefaultDataModel<Object>(data);
//
//		List<String> sdat = new ArrayList<String>(data.size());
//		for (Object o : data) {
//			sdat.add(o.toString());
//		}
//
//		EXAutoComplete users = new EXAutoComplete("users", sdat.get(0), sdat);
//		// EXSelect users = new EXSelect("", model);
//		fieldset.addField("Utilisateur :", users);
//		users.setStyle("width", "380px");

		
//		Container generate = new EXContainer("generate", "button").setText(
//				"Import from Pointer device").addEvent(this, CLICK);
//		body.addChild(generate.setStyle("display", "block"));
//
//		
//
//		Container detailView = new EXContainer("detail", "button").setText(
//				"Detail View").addEvent(this, CLICK);
//		body.addChild(detailView.setStyle("display", "block"));

		Container export = new EXContainer("export", "button").setText("Export to excel").addEvent(this, CLICK);
		body.addChild(export.setStyle("display", "block"));

//		Calendar st = Calendar.getInstance();
//		Calendar ed = Calendar.getInstance();
//		ed.add(Calendar.DATE, 2);
//		EXTable table = new EXTable("timesheet",
//				new MyTimesheetTableModel(st, ed, data.get(0).getKey()));
//		table.setCellRenderer(new MyTimesheetCellRenderer(data.get(0).getKey()));
//		table.setStyle("display", "block");
//		body.addChild(table);
		setBody(body);
		//getDescendentByName("from").addEvent(this, BLUR);
		//getDescendentByName("to").addEvent(this, BLUR);
		setStyle("width", "522px");
	}
	

public  Workbook saveAndExportToExcel(Calendar start, Calendar end)throws Exception{
		
		Workbook wb = new HSSFWorkbook();
//		Sheet s = wb.createSheet();
//		List<KeyValuePair> kvs = getUsers();
//		
//		Connection con = getConnection();
//		
//		
//		
//		Date ed = end.getTime();
//		int rows = 0;
//		for(KeyValuePair kv : kvs){
//			String userid = kv.getKey();
//			String query = "SELECT Min(DateTime) AS MinOfDateTime, Max(DateTime) AS MaxOfDateTime FROM PERSONALLOG where FingerPrintID=" + userid + " and DateLog = ?";
//			PreparedStatement stmt = con.prepareStatement(query);
//			Date sta = start.getTime();
//			Calendar csta = Calendar.getInstance();
//			csta.setTime(sta);
//			
//			Directory timesheet = SpringUtil.getRepositoryService().getDirectory("/root/users/"+Util.getLoggedOrganization()+"/timesheet", Util.getRemoteUser());
//			
//			Directory myt = timesheet.getFile(userid, Directory.class);
//			if(myt == null){
//				myt = timesheet.createFile(userid, Directory.class);
//				timesheet.save();
//			}
//			
//			Row r = s.createRow(rows);
//			r.createCell(0).setCellValue(kv.getKey());
//			r.createCell(1).setCellValue(kv.getValue());
//			int cols = 2;
//			while(true){
//				
//				long[] entry = getEntryExit(Double.parseDouble(kv.getKey()), csta, con, stmt);
//				if(entry == null){
//					entry = new long[2];
//					entry[0] = 0;
//					entry[1] = 0;
//				}
//				
//				saveEntry(Double.parseDouble(userid), entry[0], entry[1], myt);
//				
//				Calendar cal1 = Calendar.getInstance();
//				cal1.setTimeInMillis(entry[0]);
//				
//				r.createCell(cols).setCellValue(cal1);
//				cols = cols +1;
//				
//				Calendar cal2 = Calendar.getInstance();
//				cal2.setTimeInMillis(entry[1]);
//				r.createCell(cols).setCellValue(cal2);
//				cols=cols+1;
//				
//				csta.add(Calendar.DATE, 1);
//				if(csta.getTime().getTime() > ed.getTime())
//					break;
//				
//			}
//			rows++;
//		}
		
		return wb;
	}
	

	private List<KeyValuePair> getUsers() throws Exception {

		return OCUtil.geFingerSheet();
	}
	public void export(Calendar start, Calendar enDate)throws Exception{
		Workbook wb = LoaderUtil.exp(start, enDate).getWorkbook();
		Directory article = (Directory)SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getLoggedOrganization(), Util.getRemoteUser());
		
		String name = "export-" + System.currentTimeMillis() + ".xls";
		
		BinaryFile fDel = article.createFile(name, BinaryFile.class);
			
		OutputStream fout = fDel.getOutputStream();
			 wb.write(fout);
			 fout.flush();
			 fout.close();
		article.save();

		EXPanel panel = new EXPanel("sa");
		Container body = new EXContainer("bd", "div");
		
		Container del = new EXContainer("del", "a").setText("Telecharger").setAttribute("href", ResourceUtil.getDownloadURL("ecm", fDel.getAbsolutePath())).setAttribute("target", "_blank");
		panel.setBody(body);
		body.addChild(del);
		
		panel.setStyle("z-index", "6000").setStyle("width", "400px");
		getAncestorOfType(PopupContainer.class).addPopup(panel);
	}
	
	private KeyValuePair getUser(String name)throws Exception{
		List<KeyValuePair> data = getUsers();
		// DefaultDataModel<Object> model = new DefaultDataModel<Object>(data);

		
		for (KeyValuePair o : data) {
			//sdat.add(o.toString());
			if(o.getValue().equalsIgnoreCase(name)){
				return o;
			}
		}
		
		return null;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);

	}
	

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {

		try{
		Date st = (Date) getDescendentOfType(EXFieldSet.class).getField("from").getValue();
		Date ed = (Date) getDescendentOfType(EXFieldSet.class).getField("to").getValue();
		
		//KeyValuePair user = getUser(getDescendentOfType(EXAutoComplete.class).getValue().toString());

		if (st.getTime() < ed.getTime()) {
			Calendar cst = Calendar.getInstance();
			cst.setTime(st);

			Calendar ced = Calendar.getInstance();
			ced.setTime(ed);

			cst.setTime(st);
			//getDescendentOfType(EXTable.class).setModel(new MyTimesheetTableModel(cst, ced, user.getKey()));
			//getDescendentOfType(EXTable.class).setCellRenderer(new MyTimesheetCellRenderer(user.getKey()));
			export(cst, ced);
		}
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
