/*
 * 
 */
package org.castafiore.ui.ex.form.table;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXTableWithExport extends EXContainer{

	public EXTableWithExport(String name, TableModel model) {
		super(name, "div");
		EXTable table = new EXTable("table_" + name, model);
		EXPagineableTable pTable = new EXPagineableTable("", table);
		addChild(pTable);
		try{
		
		Container c = new EXContainer("", "fieldset");
		addChild(c);
		c.addChild(new EXContainer("legend", "legend").setText("Export table"));
		
		c.addChild(new EXContainer("excel", "a").setAttribute("href", "export/?type=excel&app=" + getRoot().getName() + "&compid=" + table.getId()).setAttribute("target", "_blank").setText("<img src='blueprint/excel.png'></img>").setAttribute("title", "Export to excel"));
		
		c.addChild(new EXContainer("pdf", "a").setAttribute("href", "export/?type=pdf&app=" + getRoot().getName() + "&compid=" + table.getId()).setAttribute("target", "_blank").setText("<img src='blueprint/pdf.png'></img>").setAttribute("title", "Export to pdf"));
		
		c.addChild(new EXContainer("chart", "a").setAttribute("href", "export/?type=chart&app=" + getRoot().getName() + "&compid=" + table.getId()).setAttribute("target", "_blank").setText("<img src='blueprint/chart.png'></img>").setAttribute("title", "Generate a bar chart"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public EXTable getTable(){
		return getDescendentOfType(EXTable.class);
	}

}
