package org.castafiore.shoppingmall.util.list;

import org.castafiore.ui.Container;
import org.castafiore.wfs.types.File;

public interface ListItem<T> extends Container{
	
	
	public T getItem();
	
	public void setItem(T file);

}
