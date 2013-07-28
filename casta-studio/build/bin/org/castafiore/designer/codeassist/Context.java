package org.castafiore.designer.codeassist;

import org.json.JSONObject;

public class Context {

	private int indented;

	private int column;

	private String type;

	private boolean align;

	private Context prev;

	public Context(JSONObject obj) throws Exception {
		indented = obj.getInt("indented");
		column = obj.getInt("column");
		type = obj.getString("type");
		align = obj.getBoolean("align");
		if(obj.has("prev"))
			prev = new Context(obj.getJSONObject("prev"));

	}

	public int getIndented() {
		return indented;
	}

	public void setIndented(int indented) {
		this.indented = indented;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAlign() {
		return align;
	}

	public void setAlign(boolean align) {
		this.align = align;
	}

	public Context getPrev() {
		return prev;
	}

	public void setPrev(Context prev) {
		this.prev = prev;
	}

}
