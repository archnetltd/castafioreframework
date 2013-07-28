package org.castafiore.facebook.datasource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.castafiore.designer.dataenvironment.DataRow;
import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.ui.UIException;

import com.restfb.Facebook;
import com.restfb.types.NamedFacebookType;

public class FacebookDataRow implements DataRow {
	
	private DataSet dataSet;
	
	private NamedFacebookType data;
	
	

	public FacebookDataRow(DataSet dataSet, NamedFacebookType data) {
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
		
		if(data == null){
			return null;
		}
		try {
			Field f = data.getClass().getDeclaredField(field);
			
			return getDataFromField(f,data);
			
		} catch (IllegalArgumentException e) {
			throw new UIException("Error invoking " + field + " in class " + data.getClass(), e);
		} catch (SecurityException e) {
			throw new UIException("There is no field " + field, e);
		} catch (NoSuchFieldException e) {
			
			Field[] fields = data.getClass().getDeclaredFields();
			for(Field f : fields){
				Facebook fb = f.getAnnotation(Facebook.class);
				if(fb != null && fb.value().equals(field)){
					//this is the field
					try {
						return getDataFromField(f,data);
					} catch (IllegalArgumentException e1) {
						throw new UIException("Error invoking " + f.getName() + " in class " + data.getClass(), e);
					}
				}
			}
		}
		
		return null;
		
	}
	
	private Object getDataFromField(Field f, Object src) {
		try{
		String name = f.getName();
		String getter = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
		return src.getClass().getMethod(getter).invoke(src);
		}catch(Exception e){
			throw new UIException(e);
		}
	}

}
