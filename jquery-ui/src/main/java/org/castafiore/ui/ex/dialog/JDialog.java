package org.castafiore.ui.ex.dialog;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;

public class JDialog extends EXContainer{
	
	
	private JMap options = new JMap();
	public JDialog(String name, String title) {
		super(name, "div");
		setTitle(title);
	}
	
	public JDialog setBody(Container body){
		getChildren().clear();
		setRendered(false);
		addChild(body);
		return this;
	}

	public JDialog setAutoOpen(Boolean autoOpen) {
		options.put("autoOpen", autoOpen);
		return this;
	}
	
	
	public JDialog setCloseOnEscape(Boolean closeOnEscape) {
		options.put("closeOnEscape", closeOnEscape);
		return this;
	}
	public JDialog setCloseText(String closeText) {
		options.put("closeText", closeText);
		return this;
	}
	public JDialog setDialogClass(String dialogClass) {
		options.put("dialogClass", dialogClass);
		return this;
	}
	public JDialog setDraggable(Boolean draggable) {
		options.put("draggable", draggable);
		return this;
	}
	public JDialog setHeight(Integer height) {
		options.put("height", height);
		return this;
	}
	public JDialog setHide(Integer hide) {
		options.put("hide", hide);
		return this;
	}
	public JDialog setMaxHeight(Integer maxHeight) {
		options.put("maxHeight", maxHeight);
		return this;
	}
	public JDialog setMaxWidth(Integer maxWidth) {
		options.put("maxWidth", maxWidth);
		return this;
	}
	public JDialog setMinHeight(Integer minHeight) {
		options.put("minHeight", minHeight);
		return this;
	}
	public JDialog setMinWidth(Integer minWidth) {
		options.put("minWidth", minWidth);
		return this;
	}
	public JDialog setModal(Boolean modal) {
		options.put("modal", modal);
		return this;
	}
	public JDialog setPosition(String position) {
		options.put("position", position);
		return this;
	}
	public JDialog setResizable(Boolean resizable) {
		options.put("resizable", resizable);
		return this;
	}
	public JDialog setShow(Integer show) {
		options.put("show", show);
		return this;
	}
	public JDialog setTitle(String title) {
		options.put("title", title);
		return this;
	}
	public JDialog setWidth(Integer width) {
		options.put("width", width);
		return this;
	}
	
	

}
