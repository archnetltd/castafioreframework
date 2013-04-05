package org.castafiore.easyui.grid;

public interface PropertyGridModel {
	public int size();
	public String getName(int index);
	public String getValue(int index);
	public String getGroup(int index);
	public String getEditor(int index);

}
