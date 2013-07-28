package org.castafiore.forum.ui;

import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.types.Article;

public class ForumTableModel implements TableModel{
	
	private Article forum;

	
	
	public ForumTableModel(Article forum) {
		super();
		this.forum = forum;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnNameAt(int index) {
		if(index == 0){
			return "";
		}else if(index == 1){
			return "Forum";
		}else if(index == 2){
			return "Topics";
		}else if(index == 3){
			return "Lastest Post";
		}return "";
	}

	@Override
	public int getRowCount() {
		return forum.getFiles(Article.class).toList().size();
	}

	@Override
	public int getRowsPerPage() {
		return getRowCount();
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		return forum.getFiles(Article.class).get(row);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	

}
