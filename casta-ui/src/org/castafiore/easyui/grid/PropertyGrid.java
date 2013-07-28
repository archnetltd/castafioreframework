package org.castafiore.easyui.grid;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.mvc.CastafioreController;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.springframework.web.servlet.ModelAndView;

public class PropertyGrid extends EXContainer implements CastafioreController {

	private PropertyGridModel model_;
	private JMap options = new JMap();

	public PropertyGrid(String name) {
		super(name, "table");

	}

	public PropertyGrid setModel(PropertyGridModel model) {
		this.model_ = model;
		return this;
	}

	public PropertyGrid showGroup(boolean show) {
		options.put("showGroup", show);
		return this;
	}

	public PropertyGrid setFitColumns(boolean fit) {
		options.put("fitColumns", fit);
		return this;
	}

	public PropertyGrid setResizeHandle(String handle) {
		options.put("resizeHandle", handle);
		return this;
	}

	public PropertyGrid setAutoRowHeight(boolean auto) {
		options.put("autoRowHeight", auto);
		return this;
	}

	public PropertyGrid setStriped(boolean strip) {
		options.put("striped", strip);
		return this;
	}

	public PropertyGrid setNoWrap(boolean nowrap) {
		options.put("nowrap", nowrap);
		return this;
	}

	public PropertyGrid setLoadMsg(String msg) {
		options.put("loadMsg", msg);
		return this;
	}

	public PropertyGrid showPagination(boolean show) {
		options.put("pagination", show);
		return this;
	}

	public PropertyGrid showRowNumbers(boolean show) {
		options.put("rownumbers", show);
		return this;
	}

	public PropertyGrid setSingleSelect(boolean show) {
		options.put("singleSelect", show);
		return this;
	}

	public PropertyGrid setCheckOnSelect(boolean show) {
		options.put("checkOnSelect", show);
		return this;
	}

	public PropertyGrid setSelectOnCheck(boolean show) {
		options.put("selectOnCheck", show);
		return this;
	}

	public PropertyGrid setPagePosition(String position) {
		options.put("pagePosition", position);
		return this;
	}

	public PropertyGrid setPageNumber(int page) {
		options.put("pageNumber", page);
		return this;
	}

	public PropertyGrid setPageSize(int size) {
		options.put("pageSize", size);
		return this;
	}

	public PropertyGrid setPageList(int... page) {

		JArray opts = new JArray();
		for (int p : page) {
			opts.add(p);
		}
		options.put("pageNumber", opts);
		return this;
	}

	public PropertyGrid showHeader(boolean show) {
		options.put("showHeader", show);
		return this;
	}

	public PropertyGrid showFooter(boolean show) {
		options.put("showFooter", show);
		return this;
	}

	public PropertyGrid setScrollbarSize(int size) {
		options.put("scrollbarSize", size);
		return this;
	}

	public void onReady(ClientProxy proxy) {
		options.put("url", ResourceUtil.getMethodUrl(this));
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
		proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js",
				proxy.clone().addMethod("propertygrid", options));
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int size = model_.size();
		JArray rows = new JArray();
		for (int i = 0; i < size; i++) {
			JMap row = new JMap();
			row.put("name", model_.getName(i));
			row.put("value", model_.getValue(i));
			row.put("group", model_.getGroup(i));
			String editor = model_.getEditor(i);
			if (StringUtil.isNotEmpty(editor)) {
				row.put("editor", editor);
			}
			rows.add(row);
		}

		JMap opts = new JMap();
		opts.put("size", size);
		opts.put("rows", rows);
		OutputStream out = response.getOutputStream();
		out.write(opts.getJavascript().getBytes());
		out.flush();
		out.close();
		return null;
	}

}