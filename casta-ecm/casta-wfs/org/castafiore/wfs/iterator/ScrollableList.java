package org.castafiore.wfs.iterator;

import java.util.AbstractList;
import java.util.List;

import org.castafiore.wfs.types.File;

public class ScrollableList extends AbstractList<File> implements List<File>{
	
	private ScrollableFileIterator scroll;
	
	

	public ScrollableList(ScrollableFileIterator scroll) {
		super();
		this.scroll = scroll;
	}

	@Override
	public File get(int index) {
		return scroll.get(index);
	}

	@Override
	public int size() {
		return scroll.count();
	}
	
	
	

}
