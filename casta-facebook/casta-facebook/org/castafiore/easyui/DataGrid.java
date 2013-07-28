package org.castafiore.easyui;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.Table;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.mvc.CastafioreController;
import org.castafiore.utils.ResourceUtil;
import org.springframework.web.servlet.ModelAndView;

public class DataGrid extends EXContainer implements CastafioreController, Table {
	
	

	public DataGrid(String name) {
		super(name, "table");
		// TODO Auto-generated constructor stub
	}

	private TableModel model_;
	
	private int currentPage_=0;
	
	@Override
	public void setModel(TableModel model) {
		this.model_ = model;
		
		
	}

	@Override
	public TableModel getModel() {
		return model_;
	}

	@Override
	public int getPages() {
		int rows = this.model_.getRowCount();

		int rPerPage = this.model_.getRowsPerPage();

		int remainder = rows % rPerPage;

		int multiple = Math.round(rows / rPerPage);

		int pages = multiple;
 
		if (remainder != 0)
		{
			pages = pages + 1;
		}

		return pages;
	}

	@Override
	public int getCurrentPage() {
		return currentPage_;
	}

	@Override
	public void changePage(int page) {
		this.currentPage_ = page;
		//throw new UnsupportedOperationException("Server side change page not supported ");
		
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int page = Integer.parseInt(request.getParameter("page")) -1;
		int rows = Integer.parseInt(request.getParameter("rows"));
		
		int start = page*rows;
		int end = ((page+1)*rows);
		
		int total = model_.getRowCount();
		
		if(end > total){
			end = total;
		}
		JMap data = new JMap().put("total", total);
		JArray datas = new JArray();
		int c = 0;
		for(int i = start; i < end;i++){
			JMap row = new JMap();
			for(int j = 0; j < model_.getColumnCount();j++){
				row.put(j+"", model_.getValueAt(j, c, page).toString());
			}
			datas.add(row);
			c++;
		}
		data.put("rows", datas);
		
		ServletOutputStream out = response.getOutputStream();
		response.setHeader("Content-Type", "application/json");
		out.write(data.getJavascript().getBytes());
		out.flush();
		return null;
		
		
	}

	@Override
	public void onReady(ClientProxy proxy) {
		
		super.onReady(proxy);
		
		JMap map = new JMap();
		int columns = model_.getColumnCount();
		JArray cols = new JArray();
		for(int i = 0; i < columns; i++){
			Class<?> t = model_.getColumnClass(i);
			JMap colOpt = new JMap();
			colOpt.put("field", i+ "").put("title", model_.getColumnNameAt(i));
			
			if(t.isAssignableFrom(Boolean.class)){
				colOpt.put("checkbox", true);
			}
			cols.add(new JArray().add(colOpt));
		}
		
		map.put("columns", cols);
		map.put("url", ResourceUtil.getMethodUrl(this));
		
		map.put("pagination", true);
		map.put("showFooter", true);
		
		proxy.addMethod("datagrid", map);
	}
	
	

}
