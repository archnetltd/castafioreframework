package org.castafiore.reconcilliation.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.castafiore.persistence.Dao;
import org.castafiore.reconcilliation.ReconcilationDTO;
import org.castafiore.reconcilliation.ReconciliationUtil;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.orders.OrdersUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.AutoCompleteSource;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.js.JArray;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class ReconciliationMatcher extends EXContainer implements Event{
	private ReconcilationDTO dto_ ;
	public ReconciliationMatcher(String name, ReconcilationDTO dto) {
		super(name, "div");
		this.dto_ = dto;
		EXGrid grid = new EXGrid("gr", 2, 2);
		
		addChild(grid);
		
		EXFieldSet when = new EXFieldSet("when", "When", false);
		when.addField("Invoice Code = ", new EXLabel("invoice",dto.getRefNumber()));
		when.addField("Amount = ", new EXLabel("amount", dto.getAmount()));
		when.addField("Bank Acc = ", new EXLabel("bankAcc", dto.getAccountNumber()));
		when.addField("Bank Name = ", new EXLabel("bankName", dto.getBank()));
		when.addField("Name = ", new EXLabel("name", dto.getName()));
		
		
		when.setStyle("width", "450px");
		grid.getCell(0, 0).addChild(when);
		List<String> banks = new ArrayList<String>();
		banks.add("MCB");
		banks.add("State Bank");
		banks.add("Barklays");
		banks.add("Hong Kong Bank");
		banks.add("Bank of Baroda");
		banks.add("Other");
		
		
		
		
		
		EXFieldSet dothis = new EXFieldSet("dothis", "Create a ledger entry with", false);
		dothis.setAttribute("index", "0");
		dothis.addField("Invoice Code = ", new EXAutoComplete("invoice","").setSource(new AutoCompleteSource() {
			
			@Override
			public JArray getSource(String param) {
				String sql = "select distinct code from WFS_FILE where dtype='Order' and absolutePath like '/root/users/" + Util.getLoggedOrganization() + "%' and code like '"+param+"%'";
				JArray array = new JArray();
				List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).setMaxResults(10).list();
				for(Object o : l){
					array.add(o.toString());
				}
				return array;
			}
		}));
		dothis.addField("Amount = ", new EXInput("amount"));
		dothis.addField("Bank Acc = ", new EXInput("bankAcc"));
		dothis.addField("Bank Name = ", new EXAutoComplete("bankName","", banks));
		dothis.addField("Name = ", new EXInput("name"));
		setStyle("z-index", "6000");
		dothis.setStyle("width", "300px");
		grid.getCell(1, 0).addChild(dothis).setStyle("vertical-align", "top");
		
		grid.getCell(1, 0).addChild(new EXButton("moreEntry", "Create more Legder entry").addEvent(this, CLICK)).setStyle("vertical-align", "top");
		
		dothis.getDescendentByName("invoice").addEvent(this, BLUR);
		
		grid.getCell(0, 1).setAttribute("colspan", "2").setStyle("text-align", "center");
		grid.getCell(1, 1).remove();
		
		grid.getCell(0, 1).addChild(new EXButton("save", "Save this rule").setStyle("margin", "auto").setStyle("float", "none").addEvent(this, CLICK));
	}
	
	
	public List<String> getInvoiceCodes(){
		String sql = "select distinct code from WFS_FILE where dtype='Order' and absolutePath like '/root/users/" + Util.getLoggedOrganization() + "%'";
		
		List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).list();
		return l;
	}
	
	
	public void saveRule()throws Exception{
		//Workbook wb = getExcelSheet();
		Sheet sheet =  ReconciliationUtil.getMatchSheet();//wb.getSheetAt(0);
		//EXFieldSet dothis = (EXFieldSet)getDescendentByName("dothis");
		
		final Row r;
		if(sheet.getRow(0) == null)
			r = sheet.createRow(0);
		else
			r =sheet.createRow(sheet.getLastRowNum() + 1);
		
		String original = dto_.getOriginalLine();
		String ref = dto_.getRefNumber();
		String amount = dto_.getAmount();
		String bank = dto_.getBank();
		String acc = dto_.getAccountNumber();
		String name = dto_.getName();
		
		if(StringUtil.isNotEmpty(original) && !original.equalsIgnoreCase("??")){
			r.createCell(0).setCellValue(original);
		}else{
			r.createCell(0).setCellValue("");
		}
		
		if(StringUtil.isNotEmpty(ref) && ! ref.equalsIgnoreCase("??")){
			r.createCell(1).setCellValue(ref);
		}else{
			r.createCell(1).setCellValue("");
		}
		
		if(StringUtil.isNotEmpty(amount) && ! amount.equalsIgnoreCase("??")){
			r.createCell(2).setCellValue(amount);
		}else{
			r.createCell(2).setCellValue("");
		}
		
		if(StringUtil.isNotEmpty(bank) && ! bank.equalsIgnoreCase("??")){
			r.createCell(3).setCellValue(bank);
		}else{
			r.createCell(3).setCellValue("");
		}
		
		if(StringUtil.isNotEmpty(acc) && ! acc.equalsIgnoreCase("??")){
			r.createCell(4).setCellValue(acc);
		}else{
			r.createCell(4).setCellValue("");
		}
		
		if(StringUtil.isNotEmpty(name) && !name.equalsIgnoreCase("??")){
			r.createCell(5).setCellValue(name);
		}else{
			r.createCell(5).setCellValue("");
		}
		
		
		ComponentUtil.iterateOverDescendentsOfType(this, EXFieldSet.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				
				if(c.getName().equalsIgnoreCase("dothis")){
					
					EXFieldSet dothis =(EXFieldSet)c;
					int index = Integer.parseInt(c.getAttribute("index")) +1;
					r.createCell((index *5) + 1).setCellValue(dothis.getField("invoice").getValue().toString());
					r.createCell((index *5) + 2).setCellValue(dothis.getField("amount").getValue().toString());
					r.createCell((index *5) + 3).setCellValue(dothis.getField("bankName").getValue().toString());
					r.createCell((index *5) + 4).setCellValue(dothis.getField("bankAcc").getValue().toString());
					r.createCell((index *5) + 5).setCellValue(dothis.getField("name").getValue().toString());
				}
				
			}
		});
		
		
		
//		BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile("/root/users/" + Util.getLoggedOrganization() + "/reconcilationrules.xls", Util.getRemoteUser());
//		
//		OutputStream out = bf.getOutputStream();
//		wb.write(out);
//		out.flush();
//		out.close();
		
		ReconciliationUtil.saveMatchSheet(sheet.getWorkbook());
	}
	
	


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			
			if(container.getName().equalsIgnoreCase("moreEntry")){
				
				List<String> banks = new ArrayList<String>();
				banks.add("MCB");
				banks.add("State Bank");
				banks.add("Barklays");
				banks.add("Hong Kong Bank");
				banks.add("Bank of Baroda");
				banks.add("Other");
				EXFieldSet dothis = new EXFieldSet("dothis", "Create a new ledger entry with", false);
				dothis.addField("Invoice Code = ", new EXAutoComplete("invoice","").setSource(new AutoCompleteSource() {
					
					@Override
					public JArray getSource(String param) {
						String sql = "select distinct code from WFS_FILE where dtype='Order' and absolutePath like '/root/users/" + Util.getLoggedOrganization() + "%' and code like '"+param+"%'";
						JArray array = new JArray();
						List l = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createSQLQuery(sql).setMaxResults(10).list();
						for(Object o : l){
							array.add(o.toString());
						}
						return array;
					}
				}));
				dothis.addField("Amount = ", new EXInput("amount"));
				dothis.addField("Bank Acc = ", new EXInput("bankAcc"));
				dothis.addField("Bank Name = ", new EXAutoComplete("bankName","", banks));
				dothis.addField("Name = ", new EXInput("name"));
				setStyle("z-index", "6000");
				dothis.setStyle("width", "300px");
				getDescendentOfType(EXGrid.class).getCell(1, 0).addChild(dothis).setStyle("vertical-align", "top");
				dothis.setAttribute("index", (dothis.getParent().getChildren().size() -2) + "");
				return true;
			}
			
			
			if(container.getName().equalsIgnoreCase("invoice")){
				
				try{
				String invoice = container.getDescendentOfType(StatefullComponent.class).getValue().toString();
				Order o = OrdersUtil.getOrderByCode(invoice);
				Map<String,String> data = o.getBillingInformation().getOtherProperties();
				container.getAncestorOfType(EXFieldSet.class).getField("amount").setValue(o.getInstallment().toPlainString());
				container.getAncestorOfType(EXFieldSet.class).getField("name").setValue(o.getBillingInformation().getFirstName() + " " + o.getBillingInformation().getLastName());
				container.getAncestorOfType(EXFieldSet.class).getField("bankName").setValue(data.get("bankName"));
				container.getAncestorOfType(EXFieldSet.class).getField("bankAcc").setValue(data.get("accountNumber"));
				}catch(Exception e){
					
				}
				return true;
				
			}
			
			
			saveRule();
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
