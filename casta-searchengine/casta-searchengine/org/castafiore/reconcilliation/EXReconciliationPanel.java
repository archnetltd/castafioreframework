package org.castafiore.reconcilliation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.accounting.CashBook;
import org.castafiore.imports.EXImportContractsPanel;
import org.castafiore.reconcilliation.matching.EXMatchingPanel;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXReconciliationPanel extends EXPanel implements Event, PopupContainer{

	public EXReconciliationPanel(String name) {
		super(name, "Reconciliation statement Analyzer");
		EXToolBar tb = new EXToolBar("tb");
		EXIconButton btn = new EXIconButton("bew", "Add New", Icons.ICON_PLUSTHICK);
		btn.setAttribute("title", "Upload an new reconciliation file");
		tb.addItem(btn);
		btn.addEvent(this, CLICK);
		
		EXIconButton matchning = new EXIconButton("matchning", "Manage Matching Rules", Icons.ICON_COMMENT);
		matchning.setAttribute("title", "Manage matching rules");
		tb.addItem(matchning);
		matchning.addEvent(this, CLICK);
		
		Container body = new EXContainer("bd", "div");
		body.addChild(tb);
		//body.addChild(new EXOverlayPopupPlaceHolder("overlay"));
		
		setStyle("width", "700px");
		setBody(body);
		
		EXTable table = new EXTable("reclist",new ReconciliationListModel() );
		table.setCellRenderer(new ReconciliationListCellRenderer());
		body.addChild(table);
		setStyle("min-height", "500px");
		
		addChild(new EXOverlayPopupPlaceHolder("overlay"));
	}

	public void showNewPanel(){
		EXDynaformPanel panel = new EXDynaformPanel("addNew", "Please upload new File");
		panel.addField("New file :", new EXUpload("newfile"));
		EXButton btn = new EXButton("cancel", "Cancel");
		btn.addEvent(CLOSE_EVENT, CLICK);
		panel.addButton(btn);
		
		EXButton save = new EXButton("save", "Save");
		save.addEvent(this, CLICK);
		panel.addButton(save);
		panel.setStyle("width", "500px").setStyle("z-index", "4000");
		getAncestorOfType(PopupContainer.class).addPopup(panel);
		
	}
	
	
	public void showImportPanel(){
		getAncestorOfType(PopupContainer.class).addPopup(new EXImportContractsPanel("k").setStyle("width", "500px").setStyle("z-index", "4000"));
	}
	
	public void open(Directory dir, Container source)throws Exception{
		EXDynaformPanel panel = source.getAncestorOfType(EXDynaformPanel.class);
		
		if(panel == null){
			panel = new EXDynaformPanel("pn", "Reconciliation");
			panel.setStyle("width", "1000px");
			source.getAncestorOfType(PopupContainer.class).addPopup(panel);
		}
		panel.setStyle("width", new Var("$(document).width()")).setStyle("z-index", "4000");
		panel.setStyle("top", "0px").setStyle("left", "0px");
		BinaryFile bf = dir.getFile("ongoing-snapshot.txt", BinaryFile.class);
		InputStream fstream = bf.getInputStream();
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		List<ReconcilationDTO> dtos = new ArrayList<ReconcilationDTO>();
		while ((strLine = br.readLine()) != null) {
			dtos.add(ReconcilationDTO.toDto(strLine));
		}
		List<Map<String, String>> orders = ReconciliationUtil.loadOrdersValues();
		ReconciliationModel model = new ReconciliationModel(dtos, orders);
		EXTable table = new EXTable("", model);
		table.setCellRenderer(model);
		EXPagineableTable pTable = new EXPagineableTable("reconciliationTableee", table);
		Container body = new EXContainer("bd", "div");
		EXToolBar tb = new EXToolBar("tb");
		EXIconButton btn= new EXIconButton("saveState", "Save current State");
		
		
		EXIconButton performMatching= new EXIconButton("performMatching", "Perform Matching");
		EXIconButton analyse= new EXIconButton("analyse", "Analyse");
		analyse.addEvent(this, CLICK);
		performMatching.addEvent(this, CLICK);
		
		
	//	EXIconButton export= new EXIconButton("export", "Export to ledger");
		//export.setAttribute("title", "Export selected records to ledger");
		//export.addEvent(this, CLICK);
		
		setAttribute("path", dir.getAbsolutePath());
		btn.addEvent(this, Event.CLICK);
		btn.setAttribute("path", dir.getAbsolutePath());
		tb.addItem(btn);
		tb.addItem(analyse);
		tb.addItem(performMatching);
	//	tb.addItem(export);
		body.addChild(tb);
		body.addChild(pTable);
		panel.setBody(body);
		//export.setStyle("float", "right");
		
		Container stats = new EXContainer("stats", "div").setStyle("float", "left").setStyle("display", "inline");
		tb.addChild(stats);
		stats.addChild(new EXContainer("totalRecondReconc","a").setAttribute("target","_blank").setAttribute("href", ResourceUtil.getMethodUrl(this, "exportByStatus_xls", "status", "Total")).setText("5567 Records to reconcile").setStyle("display", "block"));
		stats.addChild(new EXContainer("automaticReconc","a").setAttribute("target","_blank").setAttribute("href", ResourceUtil.getMethodUrl(this, "exportByStatus_xls", "status", "Automatic")).setText( "300 reconciled automatically").setStyle("display", "block"));
		stats.addChild(new EXContainer("matchingReconc","a").setAttribute("target","_blank").setAttribute("href", ResourceUtil.getMethodUrl(this, "exportByStatus_xls", "status", "Matched")).setText( "50 reconciled after matching with rules").setStyle("display", "block"));
		stats.addChild(new EXContainer("manualReconc", "a").setAttribute("target","_blank").setAttribute("href", ResourceUtil.getMethodUrl(this, "exportByStatus_xls", "status", "Manual")).setText("50 reconciled after matching with rules").setStyle("display", "block"));
		stats.addChild(new EXContainer("notReconc","a").setAttribute("target","_blank").setAttribute("href", ResourceUtil.getMethodUrl(this, "exportByStatus_xls", "status", "Not")).setText("55 Not reconciled yet").setStyle("display", "block"));
		
		
		tb.addChild(new EXButton("exp_Automatic", "Export all automatic Reconcile"));
		tb.addChild(new EXButton("exp_Matched", "Export all matching Reconcile"));
		tb.addChild(new EXButton("exp_Manual", "Export all manual Reconcile"));
		
		tb.getChild("exp_Automatic").addEvent(this, CLICK);
		tb.getChild("exp_Manual").addEvent(this, CLICK);
		tb.getChild("exp_Matched").addEvent(this, CLICK);
		model.updateStats(this);
	}
	
	public void performMatching(){
		EXPagineableTable ptable = getDescendentByName("reconciliationTableee").getDescendentOfType(EXPagineableTable.class);
		ReconciliationModel model =  (ReconciliationModel)ptable.getDescendentOfType(EXTable.class).getModel();
		model.refresh(new ArrayList<ReconcilationDTO>());
		getDescendentOfType(EXPagineableTable.class).refresh();
		model.updateStats(this);
	}
	
	
	public void saveState(ReconciliationModel model, String path)throws Exception{
		
		Directory dir = SpringUtil.getRepositoryService().getDirectory(path, Util.getRemoteUser());
		List<BinaryFile> bfs = dir.getFiles(BinaryFile.class).toList();
		BinaryFile bf = null;
		for(BinaryFile b : bfs){
			if(b.getName().startsWith("ongoing")){
				bf = b;
				break;
			}
		}
		if(bf == null){
			bf = dir.createFile("ongoing-snapshot.txt", BinaryFile.class);
		}
		String lines = model.saveState();
		bf.write(lines.getBytes());
		bf.save();
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);		
	}
	
	public InputStream exportByStatus_xls(String status)throws Exception{
		EXTable table =  getDescendentOfType(EXDynaformPanel.class).getDescendentOfType(EXTable.class);
		ReconciliationModel model = ((ReconciliationModel)table.getModel());
		
		Workbook wb = new HSSFWorkbook();
		Sheet s = wb.createSheet(status);
		Row head = s.createRow(0);
		head.createCell(0).setCellValue("Original String");
		
		for(ReconcilationDTO dto : model.table){
			if(status.equalsIgnoreCase(dto.getStatus()) || status.equalsIgnoreCase("Total") || (status.equalsIgnoreCase("Not") && !dto.isOk())){
				Row rr = s.createRow(s.getLastRowNum()+1);
				rr.createCell(0).setCellValue(dto.getOriginalLine());
				String[] as = StringUtil.split(dto.getOriginalLine(), "|");
				for(String sss : as){
					rr.createCell(rr.getLastCellNum() + 1).setCellValue(sss);
				}
			}
		}
		
		String dir = ResourceUtil.getDirToWrite();
		String fName = System.currentTimeMillis() + ".xls";
		FileOutputStream fout = new FileOutputStream(new File(dir + "/" + fName));
		wb.write(fout);
		fout.flush();
		fout.close();
		return new FileInputStream(new File(dir + "/" + fName));
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("analyse")){
			EXPagineableTable ptable = getDescendentByName("reconciliationTableee").getDescendentOfType(EXPagineableTable.class);
			ReconciliationModel model =  (ReconciliationModel)ptable.getDescendentOfType(EXTable.class).getModel();
			ReconciliationUtil.analyseAll(model.table);
			model.refresh(new ArrayList<ReconcilationDTO>());
			getDescendentOfType(EXPagineableTable.class).refresh();
			model.updateStats(this);
			return true;
		}
		
		if(container.getName().equalsIgnoreCase("matchning")){
			try{
			EXMatchingPanel panel = new EXMatchingPanel("matchningPanel");
			panel.setStyle("width", "900px");
			container.getAncestorOfType(PopupContainer.class).addPopup(panel.setStyle("z-index", "7000"));
			return true;
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		
		if(container.getName().equalsIgnoreCase("performMatching")){
			performMatching();
			return true;
		}
		
		if(container.getName().equalsIgnoreCase("importContracts")){
			showImportPanel();
			return true;
		}
		
		try{
			
			RepositoryService service = SpringUtil.getRepositoryService();
			if(container.getName().equalsIgnoreCase("bew")){
				showNewPanel();
			}else if(container.getName().equalsIgnoreCase("save")){
				
				String path = "/root/users/" + MallUtil.getCurrentMerchant().getUsername() + "/reconciliation";
				Directory recon = null;
				if(!service.itemExists(path)){
					Directory user = service.getDirectory("/root/users/"+ MallUtil.getCurrentMerchant().getUsername(), Util.getRemoteUser());
					recon =user.createFile("reconciliation", Directory.class);
					user.save();
				}else{
					recon = service.getDirectory(path, Util.getRemoteUser());
				}
				
				BinaryFile bf = (BinaryFile)container.getAncestorOfType(EXDynaformPanel.class).getField("newfile").getValue();
				//InputStream in = bf.getInputStream();
				Directory nn = recon.createFile(new Date().getTime() + "", Directory.class);
				nn.save();
				bf.putInto(nn.getAbsolutePath());
				bf.save();
				
				List<Map<String,String>> allOrders = ReconciliationUtil.loadOrdersValues();
				List<ReconcilationDTO> table_ = ReconciliationUtil.analyse(bf.getInputStream(), allOrders);
				ReconciliationModel model= new ReconciliationModel(table_, allOrders); //showReconciliationList(bf.getInputStream(), container, nn.getAbsolutePath());
				
				BinaryFile ongoing  = nn.createFile("ongoing-snapshot.txt", BinaryFile.class);
				ongoing.write(model.saveState().getBytes());
				ongoing.save();
				getDescendentOfType(EXTable.class).setModel(new ReconciliationListModel());
				container.getAncestorOfType(EXDynaformPanel.class).remove();
				
			}else if(container.getName().equalsIgnoreCase("saveState")){
				String path = container.getAttribute("path");
				ReconciliationModel model = ((ReconciliationModel)container.getAncestorOfType(EXDynaformPanel.class).getDescendentOfType(EXTable.class).getModel());
				saveState(model, path);
				//bf.wr
				return true;
			}else if(container.getName().startsWith("exp_")){
				//save state first
				EXTable table = container.getAncestorOfType(EXDynaformPanel.class).getDescendentOfType(EXTable.class);
				ReconciliationModel model = ((ReconciliationModel)table.getModel());
				String path = getAttribute("path");
				
				saveState(model, path);
				
				String status = container.getName().replace("exp_", "").trim();
				Sheet matchSheet=null;
				if(status.equalsIgnoreCase("Matched")){
					matchSheet = ReconciliationUtil.getMatchSheet();
				}
				CashBook cb = MallUtil.getCurrentMerchant().getCashBook("DefaultCashBook");
				List<ReconcilationDTO> toexp = new ArrayList<ReconcilationDTO>();
				for(ReconcilationDTO dto :  model.table){
					if(status.equalsIgnoreCase(dto.getStatus())){
						
						if(status.equalsIgnoreCase("Matched")){
							List<ReconcilationDTO> dtos  = ReconciliationUtil.getMatchDTO(matchSheet, dto);
							if(dtos != null){
								for(ReconcilationDTO d : dtos){
									ReconciliationUtil.createItem(d, cb);
								}
								toexp.add(dto);
							}
						}else{
							if(ReconciliationUtil.createItem(dto, cb)){
								toexp.add(dto);
							}
						}
					}
				}
				
				model.refresh(toexp);
				table.getAncestorOfType(EXPagineableTable.class).refresh();
				return true;
				
			}
			return true;
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addPopup(Container popup) {
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);
		//super.addPopup(popup);
	}
	
	

}
