package org.castafiore.sample.integrations;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.panel.EXPanel;

public class FlexGrid extends EXApplication{

	public FlexGrid() {
		super("flexgrid");
	}
	
	public void initApp(){
		EXFlexGrid grid = new EXFlexGrid("flexgrid");
		grid.addColumn("iso", "ISO", 40, true, "center")
		.addColumn("name", "Name", 180, true, "left")
		.addColumn("printable_name", "Printable Name", 120, true, "left")
		.addColumn("iso3", "ISO3", 40, true, "center")
		.addColumn("numcode", "Number Code", 80, true, "right");
		
		grid.setDefaultSort("iso", "asc");
		grid.setUsePager(true);
		grid.setUseRp(15);
		grid.setShowTableToggleBtn(false);
		grid.setWidth(700).setHeight(200);
		grid.addButton("Edit", "edit").addButton("Delete", "delete");
		grid.setListener(new FlexiGridListener() {
			
			@Override
			public void execute(String btnName, String id, EXFlexGrid source) {
				System.out.println(btnName + ":" + id);
			}
		});
		
		
		grid.setDataSource(new FlexiGridDataSource() {
			
			@Override
			public int getSize() {
				return 500;
			}
			
			@Override
			public List<FlexiGridDataRow> getPage(int page, int pageSize, String sort,
					String order, String query) {
				List<FlexiGridDataRow> rows = new ArrayList<FlexiGridDataRow>(pageSize);
				for(int i = 0; i < pageSize;i++){
					FlexiGridDataRow r = new FlexiGridDataRow((((page-1) * pageSize) + i) + "");
					r.put("iso", "iso " + r.getId());
					r.put("name", "name " + r.getId());
					r.put("printable_name", "printable_name " + r.getId());
					r.put("iso3", "iso3 " + r.getId());
					r.put("numcode", "numcode " + r.getId());
					rows.add(r);
				}
				
				return rows;
				
			}
		});
		
		
		EXPanel panel = new EXPanel("panel");
		panel.setBody(grid);
		panel.setStyle("width", "700px");
		addChild(panel);
	}

}
