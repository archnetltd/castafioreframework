package org.castafiore.wfs.iterator;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.service.QueryExecutor;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;

public class ScrollableFileIterator implements FileIterator<File>{
	
	private int currentPage = -1;
	
	private int pageSize = 10;
	
	private QueryParameters params;
	
	private int count ;
	
	
	
	private List<File> buffer = new ArrayList<File>(pageSize);
	
	private int cursor = -1;
	
	private String username;
	

	public ScrollableFileIterator(int pageSize, QueryParameters params, String username) {
		super();
		this.pageSize = pageSize;
		this.params = params;
		this.username = username;
		count = SpringUtil.getRepositoryService().countRows(params, username);
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public File get(int index) {
		int row = index%pageSize;
		int page = (index-row)/pageSize;
		if(currentPage == page){
			return buffer.get(row);
		}else{
			int start = page*pageSize;
			
			params.setFirstResult(start);
			params.setMaxResults(pageSize);
			
			List<File> files = SpringUtil.getBeanOfType(QueryExecutor.class).executeQuery(params,SpringUtil.getBeanOfType(Dao.class).getReadOnlySession());
			buffer = files;
			currentPage = page;
			return get(index);
		}
	}
	
	public File get() {
		return get(cursor);
	}

	@Override
	public boolean hasNext() {
		return cursor < (count-1);
	}

	@Override
	public void initialise(Integer directoryId, String username)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invalidate() {
		currentPage = -1;
		buffer.clear();
		cursor = SpringUtil.getRepositoryService().countRows(params, username);
		
	}

	@Override
	public File next() {
		cursor++;
		return get(cursor);
	}

	@Override
	public List<File> toList() {
		return new ScrollableList(this);
	}

	@Override
	public File first() {
		cursor = -1;
		return next();
		
	}

	@Override
	public File last() {
		cursor = count-2;
		return next();
	}

	@Override
	public File previous() {
		cursor = cursor -2;
		return next();
	}

	
}
