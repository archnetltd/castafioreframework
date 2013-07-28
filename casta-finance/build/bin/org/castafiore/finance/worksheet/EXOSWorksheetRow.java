package org.castafiore.finance.worksheet;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXOSWorksheetRow extends EXContainer {

	public EXOSWorksheetRow( int rowIndex, OSWorksheetColumnModel columnModel, OSWorksheetDataModel dataModel, OSWorksheetCellRenderer cellRenderer) {
		super("r" + rowIndex, "div");
		addClass("sp-row");
		addClass("r" + rowIndex);

		addChild(new EXContainer("left-margin", "div").addClass("empty").addClass("span-1").addClass("margin-left"));
		for(int j = 0; j < columnModel.getSize(); j++){
			String title = columnModel.getTitle(j);
			Object value = dataModel.getValueAt(title, j,rowIndex, columnModel.getDefaultType(j));
			WorksheetCellSpan span = columnModel.getSpan(j);
			if(span == null){
				span = WorksheetCellSpan.span_1;
			}
			Container defCell = new EXOSWorksheetROCell("cell", value.toString(),cellRenderer.getCell(title, j, rowIndex, value) ).addClass(span.toString().replace('_', '-'));
			addChild(defCell);
		}
		addChild(new EXContainer("right-margin", "div").addClass("empty").addClass("span-1").addClass("margin-right"));
	}

	public EXOSWorksheetRow(OSWorksheetColumnModel columnModel) {
		super("rn", "div");
		addClass("sp-row");
		addClass("rn");

		addChild(new EXContainer("left-margin", "div").addClass("empty").addClass("span-1").addClass("margin-left"));
		for(int j = 0; j < columnModel.getSize(); j++){
			
			Object value = "";
			WorksheetCellSpan span = columnModel.getSpan(j);
			if(span == null){
				span = WorksheetCellSpan.span_1;
			}
			Container defCell = new EXOSWorksheetROCell("cell", value.toString() ).addClass(span.toString().replace('_', '-'));
			addChild(defCell);
		}
		addChild(new EXContainer("right-margin", "div").addClass("empty").addClass("span-1").addClass("margin-right"));
	}

}
