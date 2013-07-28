package org.castafiore.designer.dataenvironment.invocation;

import org.castafiore.designer.dataenvironment.DataRow;
import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.ui.UIException;

public class MethodInvocationDataRow implements DataRow{
	
	private DataSet dataSet;
	
	private Object data;
	
	
	

	public MethodInvocationDataRow(DataSet dataSet, Object data) {
		super();
		this.dataSet = dataSet;
		this.data = data;
	}

	@Override
	public DataSet getDataSet() {
		return dataSet;
	}

	@Override
	public Object getValue(String field) {
		try{
		if(data != null){
			Class c = data.getClass();
			return c.getMethod("get" + field).invoke(data);
		}else{
			return null;
		}
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	

}
