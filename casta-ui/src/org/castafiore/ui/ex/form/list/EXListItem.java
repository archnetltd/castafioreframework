package org.castafiore.ui.ex.form.list;



import org.castafiore.ui.ex.EXContainer;

public class EXListItem<T> extends EXContainer implements ListItem<T>{

	private T data = null;
	
	private boolean selected = false;
	
	public EXListItem(String name, String tag) {
		super(name, tag);
		
	}


	@Override
	public T getData() {
		
		return data;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setData(T data) throws UnSupportedTypeException {
		
		this.data = data;
	}

	@Override
	public void setSelected(boolean selected) {
		
		this.selected = selected;
		if(selected)
		setStyle("background", "silver");
		else 
			setStyle("background", "transparent");
	}

}
