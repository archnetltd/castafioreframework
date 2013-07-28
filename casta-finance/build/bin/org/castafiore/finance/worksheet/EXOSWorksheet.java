package org.castafiore.finance.worksheet;

import org.castafiore.finance.worksheet.inputs.EXOSStringInput;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXOSWorksheet extends EXContainer implements OSWorksheetCellRenderer, OSWorksheetColumnModel, OSWorksheetColumnRenderer, OSWorksheetDataModel {
	
	
	private OSWorksheetColumnRenderer columnRenderer;
	
	private OSWorksheetDataModel dataModel;
	
	private OSWorksheetCellRenderer cellRenderer;
	
	private OSWorksheetColumnModel columnModel;
	
	private int currentPage = 0;
	
	private final static String[] DEF_TITLES = new String[]{"A", "B", "C", "D", "E", "F"};

	public EXOSWorksheet(String name) {
		super(name, "div");
		addClass("worksheet").addClass("span-14");
		build();
		
	}

	
	/**
	 * adds an empty row in the specified position of the worksheet
	 * @param position
	 */
	public void addEmptyRow(int position){
		EXOSWorksheetRow row = new EXOSWorksheetRow(columnModel);
		if(position == -1){
			addChild(row);
			row.getChildByIndex(0).setText((getChildren().size()-2) + "");
		}else
			addChildAt(row, position +1);
	}
	
	public void build(){
		this.getChildren().clear();
		this.setRendered(false);
		
		
		if(this.columnModel == null){
			this.columnModel = this;
		}
		
		if(this.cellRenderer == null){
			cellRenderer = this;
		}
		
		if(this.columnRenderer == null){
			columnRenderer = this;
		}
		if(this.dataModel == null){
			this.dataModel = this;
		}
		
		Container head = new EXContainer("head", "div").addClass("head");
		addChild(head);
		int colSize = columnModel.getSize();
		head.addChild(new EXContainer("left-margin", "div").addClass("empty").addClass("span-1").addClass("margin-left"));
		for(int i = 0; i < colSize; i++){
			String title = columnModel.getTitle(i);
			WorksheetCellSpan span = columnModel.getSpan(i);
			Container header = columnRenderer.getColumnCell(title, i,false);
			if(span == null){
				span = WorksheetCellSpan.span_1;
			}
			if(header != null){
				header.addClass(span.toString().replace('_', '-'));
				head.addChild(header);
			}
		}
		head.addChild(new EXContainer("right-margin", "div").addClass("empty").addClass("span-1").addClass("margin-right"));
		
		int rows = dataModel.size();
		
		for(int i = 0; i < rows; i ++){
			EXOSWorksheetRow row = new EXOSWorksheetRow( i, columnModel, dataModel, cellRenderer);
			//row.getChildByIndex(0).setText(text)
			addChild(row);
			row.getChildByIndex(0).setText((getChildren().size()-2) + "");
		}
		
		
	}
	
	
	public OSWorksheetColumnRenderer getColumnRenderer() {
		return columnRenderer;
	}

	public void setColumnRenderer(OSWorksheetColumnRenderer columnRenderer) {
		this.columnRenderer = columnRenderer;
	}

	public OSWorksheetDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(OSWorksheetDataModel dataModel) {
		this.dataModel = dataModel;
	}

	public OSWorksheetCellRenderer getCellRenderer() {
		return cellRenderer;
	}

	public void setCellRenderer(OSWorksheetCellRenderer cellRenderer) {
		this.cellRenderer = cellRenderer;
	}

	public OSWorksheetColumnModel getColumnModel() {
		return columnModel;
	}

	public void setColumnModel(OSWorksheetColumnModel columnModel) {
		this.columnModel = columnModel;
	}


	@Override
	public OSWorksheetCell getCell(String title, int colIndex, int rowIndex,
			Object value) {
		return new EXOSStringInput("", value.toString());
	}


	@Override
	public OSWorksheetTypes getDefaultType(int index) {
		return OSWorksheetTypes.STRING;
	}


	@Override
	public int getSize() {
		return DEF_TITLES.length;
	}


	@Override
	public WorksheetCellSpan getSpan(int index) {
		return WorksheetCellSpan.span_2;
	}


	@Override
	public String getTitle(int index) {
		return DEF_TITLES[index];
	}


	@Override
	public Container getColumnCell(String label, int index,
			 boolean onPageChange) {
		return new EXContainer("", "div").setText(label);
		
	}


	@Override
	public Object getValueAt(String title, int colIndex, int rowIndex,
			OSWorksheetTypes defaultType) {
		return "";
	}


	@Override
	public int size() {
		return 20;
	}


	@Override
	public boolean scrollable() {
		return true;
	}

	
	

	public void addPage() {
		for(int i = 0; i < 5; i ++){
			this.addEmptyRow(-1);
		}
		
	}












	
	
	

}
