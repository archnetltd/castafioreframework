package org.castafiore.ui.ex.dialog;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.js.JMap;

public class UIDialog extends EXContainer{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMap options = new JMap();
	public UIDialog(String name, String title) {
		super(name, "div");
		setTitle(title);
	}
	
	public UIDialog setBody(Container body){
		getChildren().clear();
		setRendered(false);
		addChild(body);
		return this;
	}

	public UIDialog setAutoOpen(Boolean autoOpen) {
		options.put("autoOpen", autoOpen);
		return this;
	}
	
	
	public UIDialog setCloseOnEscape(Boolean closeOnEscape) {
		options.put("closeOnEscape", closeOnEscape);
		return this;
	}
	public UIDialog setCloseText(String closeText) {
		options.put("closeText", closeText);
		return this;
	}
	public UIDialog setDialogClass(String dialogClass) {
		options.put("dialogClass", dialogClass);
		return this;
	}
	public UIDialog setDraggable(Boolean draggable) {
		options.put("draggable", draggable);
		return this;
	}
	public UIDialog setHeight(Integer height) {
		options.put("height", height);
		return this;
	}
	public UIDialog setHide(Integer hide) {
		options.put("hide", hide);
		return this;
	}
	public UIDialog setMaxHeight(Integer maxHeight) {
		options.put("maxHeight", maxHeight);
		return this;
	}
	public UIDialog setMaxWidth(Integer maxWidth) {
		options.put("maxWidth", maxWidth);
		return this;
	}
	public UIDialog setMinHeight(Integer minHeight) {
		options.put("minHeight", minHeight);
		return this;
	}
	public UIDialog setMinWidth(Integer minWidth) {
		options.put("minWidth", minWidth);
		return this;
	}
	public UIDialog setModal(Boolean modal) {
		options.put("modal", modal);
		return this;
	}
	public UIDialog setPosition(String position) {
		options.put("position", position);
		return this;
	}
	public UIDialog setResizable(Boolean resizable) {
		options.put("resizable", resizable);
		return this;
	}
	public UIDialog setShow(Integer show) {
		options.put("show", show);
		return this;
	}
	public UIDialog setTitle(String title) {
		options.put("title", title);
		return this;
	}
	public UIDialog setWidth(Integer width) {
		options.put("width", width);
		return this;
	}
	
	

}
