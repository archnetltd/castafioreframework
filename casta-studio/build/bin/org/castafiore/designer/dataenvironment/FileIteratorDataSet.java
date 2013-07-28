package org.castafiore.designer.dataenvironment;

import java.util.List;

import org.castafiore.Types;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.types.File;

public class FileIteratorDataSet implements DataSet{
	
	private Datasource datasource;
	
	private List<File> fileIterator;
	
	private Class entity;
	
	private int currentIndex = -1;
	
	

	public FileIteratorDataSet(Datasource datasource,
			List<File> fileIterator, Class entity) {
		super();
		this.datasource = datasource;
		this.fileIterator = fileIterator;
		this.entity = entity;
	}

	@Override
	public int currentIndex() {
		return currentIndex;
	}

	@Override
	public DataRow first() {
		currentIndex = 0;
		return get();
	}

	@Override
	public DataRow get() {
		return new FileDataRow(fileIterator.get(currentIndex), this);
	}

	@Override
	public DataRow get(int index) {
		currentIndex = index;
		return get();
	}

	@Override
	public Datasource getDatasource() {
		return datasource;
	}

	@Override
	public String[] getFields() {
		return SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().getSessionFactory().getClassMetadata(entity).getPropertyNames();
	}

	@Override
	public Types getType(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataRow last() {
		currentIndex = fileIterator.size()-1;
		return get();
	}

	@Override
	public DataRow previous() {
		currentIndex--;
		return get();
	}

	@Override
	public int size() {
		return fileIterator.size();
	}

	@Override
	public boolean hasNext() {
		return currentIndex < (size()-1);
	}

	@Override
	public DataRow next() {
		currentIndex ++;
		return get();
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	

}
