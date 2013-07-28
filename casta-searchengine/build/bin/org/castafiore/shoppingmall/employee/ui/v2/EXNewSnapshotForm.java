package org.castafiore.shoppingmall.employee.ui.v2;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.searchengine.back.EXWindow;
import org.castafiore.shoppingmall.employee.ui.model.LoaderUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXNewSnapshotForm extends EXDynaformPanel implements Event{

	public EXNewSnapshotForm(String name) {
		super(name, "Create new snapshot");
		addField("Label :", new EXInput("label"));
		addField("From :", new EXDatePicker("from"));
		addField("To :", new EXDatePicker("to"));
		
		addButton(new EXButton("save", "Save"));
		addButton(new EXButton("cancel", "Cancel"));
		
		getDescendentByName("save").addEvent(this, CLICK);
		getDescendentByName("cancel").addEvent(CLOSE_EVENT, CLICK);
		
		setStyle("z-index", "3000").setStyle("width", "600px");
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}
	
	
	public void export(Calendar start, Calendar enDate, String label)throws Exception{
		Workbook wb = LoaderUtil.exp(start, enDate).getWorkbook();
		Directory root = (Directory)SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getLoggedOrganization()+"/timesheet2", Util.getRemoteUser());
		
		String name = "orig.xls";
		
		
		Article article = root.createFile(System.currentTimeMillis() + "", Article.class);
		article.setTitle(label);
		article.setSummary(start.getTimeInMillis() + "");
		article.setDetail(enDate.getTimeInMillis() + "");
		
		
		BinaryFile fDel = article.createFile(name, BinaryFile.class);
			
		OutputStream fout = fDel.getOutputStream();
		wb.write(fout);
		fout.flush();
		fout.close();
		article.save();

		SnapshotsTableModel model = (SnapshotsTableModel)getAncestorOfType(EXWindow.class). getDescendentOfType(EXTable.class).getModel();
		model.init();
		getAncestorOfType(EXWindow.class). getDescendentOfType(EXTable.class).refresh();
		remove();
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		
		try{
			Date st = (Date) getField("from").getValue();
			Date ed = (Date) getField("to").getValue();
			String label = getField("label").getValue().toString();

			if (st.getTime() < ed.getTime()) {
				Calendar cst = Calendar.getInstance();
				cst.setTime(st);
				Calendar ced = Calendar.getInstance();
				ced.setTime(ed);
				cst.setTime(st);
				export(cst, ced, label);
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
