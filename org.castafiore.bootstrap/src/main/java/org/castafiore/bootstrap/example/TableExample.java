package org.castafiore.bootstrap.example;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.bootstrap.demo.BSExample;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.table.EXPagineableTable;
import org.castafiore.ui.ex.table.EXTable;
import org.castafiore.ui.ex.table.TableModel;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;

public class TableExample extends EXContainer {
	
	private List<Invoice> data = new ArrayList<TableExample.Invoice>();
	
	private final static String[] COL_NAMES = new String[]{"Inv", "Date", "Name", "Amount", "Price", "Cost", "Note"};

	public TableExample() {
		super("TableExample", "div");
		createExample1();
	}
	
	public void createExample1(){
		data = loadData();
		EXTable table = new EXTable("table", new MyTableModel());
		EXPagineableTable ptable = new EXPagineableTable("ptable", table);
		
		BSExample example = new BSExample("Table");
		example.setExample(ptable, IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("samples/TableExample1.txt")));
		addChild(example);
	}
	
	public class MyTableModel implements TableModel{

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public int getColumnCount() {
			return COL_NAMES.length;
		}

		@Override
		public int getRowsPerPage() {
			return 10;
		}

		@Override
		public String getColumnNameAt(int index) {
			return COL_NAMES[index];
		}

		@Override
		public Object getValueAt(int col, int row, int page) {
			Invoice inv = data.get((page* getRowsPerPage()) + row);
			if(col == 0){
				return inv.inv;
			}else if(col == 1){
				return inv.date;
			}else if(col == 2){
				return inv.name;
			}else if(col == 3){
				return inv.amount;
			}else if(col == 4){
				return inv.price;
			}else if(col==5){
				return inv.cost;
			}else if(col == 6){
				return inv.note;
			}else{
				return "?";
			}
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
		
	}
	
	private List<Invoice> loadData(){
		
		List<Invoice> data = new ArrayList<TableExample.Invoice>();
		
		String json = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("samples/data.txt"));
		json = json.replace("\"", "").replace("},", "|");
		String[]  sections = StringUtil.split(json, "|");
		for(String sec : sections){
			sec = sec.replace("]", "").replace("[", "").replace("}", "").replace("{", "");
			String[] kvs = StringUtil.split(sec, ",");
			Invoice inv = new Invoice();
			for(String skv : kvs){
				String[] parts = StringUtil.split(skv, ":");
				if(parts.length == 2){
					String key = parts[0];
					String value = parts[1];
				
					if(key.equalsIgnoreCase("inv")){
						inv.inv = value;
					}else if(key.equalsIgnoreCase("date")){
						try{
							inv.date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
						}catch(Exception e){
							
						}
					}else if(key.equalsIgnoreCase("name")){
						inv.name = value;
					}else if(key.equalsIgnoreCase("note")){
						inv.note = value;
					}else if(key.equalsIgnoreCase("amount")){
						inv.amount = new BigDecimal(value);
					}else if(key.equalsIgnoreCase("price")){
						inv.price = new BigDecimal(value);
					}
				}
			}
			data.add(inv);
			
		}
		return data;
	}
	
	
	public class Invoice{
		private String inv;
		
		private Date date;
		
		private String name;
		
		private String note;
		
		private BigDecimal amount;
		
		private BigDecimal price;
		
		private BigDecimal cost;

		public String getInv() {
			return inv;
		}

		public void setInv(String inv) {
			this.inv = inv;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public BigDecimal getCost() {
			return cost;
		}

		public void setCost(BigDecimal cost) {
			this.cost = cost;
		}
		
		
		
	}

}
