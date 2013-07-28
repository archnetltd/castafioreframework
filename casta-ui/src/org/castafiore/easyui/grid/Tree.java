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
import org.springframework.web.servlet.ModelAndView;

public class Tree extends EXContainer implements CastafioreController {

	private TreeNode node_;

	private JMap options = new JMap();

	public Tree(String name) {
		super(name, "ul");

	}

	public Tree setRootNode(TreeNode node) {
		this.node_ = node;
		return this;
	}

	public Tree setAnimate(boolean animate) {
		options.put("animate", animate);
		return this;
	}

	public Tree setCascadeCheck(boolean cascade) {
		options.put("cascadeCheck", cascade);
		return this;
	}

	public Tree setOnlyLeafCheck(boolean onlyLeafCheck) {
		options.put("onlyLeafCheck", onlyLeafCheck);
		return this;
	}

	public Tree showCheckbox(boolean show) {
		options.put("checkbox", show);
		return this;

	}

	public Tree showLines(boolean show) {
		options.put("lines", show);
		return this;
	}

	public Tree enableDragNDrop(boolean enable) {
		options.put("dnd", enable);
		return this;
	}

	private JMap buildNode(TreeNode node) {
		JMap data = new JMap();
		data.put("id", node.getId());
		data.put("text", node.getText());
		if (node.isChecked()) {
			data.put("checked", true);
		}

		if (!node.isLeaf()) {
			if (node.isOpen()) {
				data.put("state", "open");
				JArray children = new JArray();
				for (TreeNode c : node.getChildren()) {

					JMap cn = buildNode(c);
					children.add(cn);
					// buildNode(c, data);
				}

				data.put("children", children);
			} else {
				data.put("state", "closed");
			}
		}

		return data;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		JArray resp;
		if (id == null) {
			JMap data;
			data = buildNode(node_);
			resp = new JArray().add(data);
		} else {
			TreeNode node = getNodeById(id, node_);
			resp = new JArray();
			for (TreeNode tn : node.getChildren()) {
				JMap data = buildNode(tn);
				resp.add(data);
			}

		}

		OutputStream out = response.getOutputStream();
		out.write(resp.getJavascript().getBytes());
		out.flush();
		out.close();
		return null;
	}

	private TreeNode getNodeById(String id, TreeNode root) {
		if (root.getId().equals(id)) {
			return root;
		}
		for (TreeNode model : root.getChildren()) {
			TreeNode n = getNodeById(id, model);
			if (n != null) {
				return n;
			}
		}
		return null;
	}

	public void onReady(ClientProxy proxy) {
		options.put("url", ResourceUtil.getMethodUrl(this));
		proxy.getCSS("http://www.jeasyui.com/easyui/themes/default/easyui.css");
		proxy.getScript("http://www.jeasyui.com/easyui/jquery.easyui.min.js",
				proxy.clone().addMethod("tree", options));
	}

}