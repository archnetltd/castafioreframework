package org.castafiore.easyui.grid;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.ui.CastafioreController;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ResourceUtil;
import org.springframework.web.servlet.ModelAndView;

public class DataGrid extends EXContainer implements CastafioreController{

	private JMap options = new JMap();
	
	private DataGridColumnModel columnModel_;
	
	private DataGridModel model_;
	
	public DataGrid(String name) {
		super(name, "table");
	}
	
	
	public DataGrid setModel(DataGridModel model){
		this.model_ = model;
		return this;
	}
	
	public DataGrid setColumnModel(DataGridColumnModel model){
		this.columnModel_ = model;
		return this;
	}
	
	public DataGrid setFitColumns(boolean fit){
		options.put("fitColumns", fit);
		return this;
	}
	
	
	public DataGrid setResizeHandle(String handle){
		options.put("resizeHandle", handle);
		return this;
	}
	
	public DataGrid setAutoRowHeight(boolean auto){
		options.put("autoRowHeight", auto);
		return this;
	}
	
	public DataGrid setStriped(boolean strip){
		options.put("striped", strip);
		return this;
	}
	
	
	public DataGrid setNoWrap(boolean nowrap){
		options.put("nowrap", nowrap);
		return this;
	}
	
	public DataGrid setLoadMsg(String msg){
		options.put("loadMsg", msg);
		return this;
	}
	
	
	public DataGrid showPagination(boolean show){
		options.put("pagination", show);
		return this;
	}
	
	
	public DataGrid showRowNumbers(boolean show){
		options.put("rownumbers", show);
		return this;
	}
	
	public DataGrid setSingleSelect(boolean show){
		options.put("singleSelect", show);
		return this;
	}
	
	public DataGrid setCheckOnSelect(boolean show){
		options.put("checkOnSelect", show);
		return this;
	}
	
	public DataGrid setSelectOnCheck(boolean show){
		options.put("selectOnCheck", show);
		return this;
	}
	
	public DataGrid setPagePosition(String position){
		options.put("pagePosition", position);
		return this;
	}
	
	public DataGrid setPageNumber(int page){
		options.put("pageNumber", page);
		return this;
	}
	

	public DataGrid setPageSize(int size){
		options.put("pageSize", size);
		return this;
	}
	
	public DataGrid setPageList(int ...page){
		
		JArray opts = new JArray();
		for(int p :page){
			opts.add(p);
		}
		options.put("pageNumber", opts);
		return this;
	}
	
	public DataGrid showHeader(boolean show){
		options.put("showHeader", show);
		return this;
	}
	
	public DataGrid showFooter(boolean show){
		options.put("showFooter", show);
		return this;
	}
	
	public DataGrid setScrollbarSize(int size){
		options.put("scrollbarSize", size);
		return this;
	}
	
	
	
	public void onReady(ClientProxy proxy){
		int cols = columnModel_.size();
		JArray array = new JArray();
		
		for(int i =0; i< cols;i++){
			JMap m = new JMap();
			m.put("field", columnModel_.getName(i));
			m.put("title", columnModel_.getTitle(i));
			m.put("width", columnModel_.getWidth(i));
			m.put("align", columnModel_.getAlign(i));
			m.put("halign", columnModel_.getHAlign(i));
			m.put("sortable", columnModel_.isSortable(i));
//			m.put("order", columnModel_.getOrder(i));
			m.put("resizable", columnModel_.isResizable(i));
			m.put("hidden", columnModel_.isHidden(i));
			m.put("checkbox", columnModel_.isCheckBox(i));
			array.add(m);
//			if(columnModel_.isIdentityField(i)){
//				options.put("idField", columnModel_.getName(i));
//			}
			
		}
		JArray colOptions = new JArray();
		colOptions.add(array);
		options.put("columns", colOptions);
		String url = ResourceUtil.getMethodUrl(this);
		options.put("url", url);
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
		proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js", proxy.clone().addMethod("datagrid", options));
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int rows =  Integer.parseInt(request.getParameter("rows"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		int start = (page-1)*rows;
		int end = page*rows;
		int size = this.model_.size();
		if(end > size){
			end = size;
		}
		int cols = this.columnModel_.size();
		JArray data = new JArray();
		for(int i =start; i < end;i++){
			JMap row = new JMap();
			for(int j=0; j < cols;j++){
				String name = columnModel_.getName(j);
				Object val = model_.getValueAt(i, j);
				row.put(name, val.toString());
			}
			data.add(row);
		}
		
		JMap resp = new JMap().put("total", size).put("rows", data);
		
		OutputStream out = response.getOutputStream();
		out.write(resp.getJavascript().getBytes());
		out.flush();
		out.close();
		
		return null;
		
	}
	
	
	
	
	//resizeHandle
	
	
	

	
	
}
