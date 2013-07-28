package org.castafiore.designer.dataenvironment;

import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.types.File;
import org.hibernate.EntityMode;

public class FileDataRow implements DataRow{
	
	private File file;
	
	private DataSet dataSet;

	public FileDataRow(File file, DataSet dataSet) {
		super();
		this.file = file;
		this.dataSet = dataSet;
	}

	@Override
	public DataSet getDataSet() {
		return dataSet;
	}

	@Override
	public Object getValue(String field) {
		return SpringUtil.getBeanOfType(Dao.class).getSession().getSessionFactory().getClassMetadata(file.getClass()).getPropertyValue(file, field, EntityMode.POJO);
	}
	
	

}
