package org.castafiore.easyui.grid;

public interface DataGridColumnModel {
	
	public String getTitle(int index);
	
	public int size();
	
	public int getWidth(int index);
	
	public int getRowSpan(int index);
	
	public String getAlign(int index);
	
	public String getHAlign(int index);
	
	public boolean isSortable(int index);
	
	public String getOrder(int index);
	
	public boolean isResizable(int index);
	
	public boolean isHidden(int index);
	
	public boolean isCheckBox(int index);
	
	
	public String getName(int index);
	
	public boolean isIdentityField(int index);
	
	

}
