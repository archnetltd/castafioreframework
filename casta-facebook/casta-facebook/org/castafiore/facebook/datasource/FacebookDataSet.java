package org.castafiore.facebook.datasource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.Types;
import org.castafiore.designer.dataenvironment.DataRow;
import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.ui.UIException;
import org.castafiore.utils.StringUtil;

import com.restfb.Facebook;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.User;

public class FacebookDataSet implements DataSet{
	private int currentIndex =-1;
	
	private Datasource datasource;
	
	private List<NamedFacebookType> data;
	
	

	public FacebookDataSet(Datasource datasource, List<NamedFacebookType> data) {
		super();
		this.datasource = datasource;
		this.data = data;
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
		if(currentIndex < 0){
			throw new UIException("should move cursor next position -1. Call the next() method");
		}
		if(currentIndex >= data.size()){
			throw new UIException("Already reached end of list. Cannot retrieve any more data");
		}
		
		return new FacebookDataRow(this,data.get(currentIndex));
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
		try {
			String clazz = datasource.getAttribute("Type");
			List<String> arr = new ArrayList<String>();
			Class c;
			if(StringUtil.isNotEmpty(clazz)){
				 c= Thread.currentThread().getContextClassLoader().loadClass(clazz);
			}else{
				c = User.class; 
			}
			Field[] fields = c.getDeclaredFields();
			for(Field f : fields){
				if(f.getAnnotation(Facebook.class) != null){
					Facebook fb = f.getAnnotation(Facebook.class);
					if(StringUtil.isNotEmpty(fb.value())){
						arr.add(fb.value());
					}else
						arr.add(f.getName());
				}
			}
			return arr.toArray(new String[arr.size()]);
		}catch (ClassNotFoundException e) {
			throw new UIException(e);
		}
	}

	@Override
	public Types getType(String field) {
		return Types.STRING;
	}

	@Override
	public DataRow last() {
		currentIndex = data.size() -1;
		return get();
	}

	@Override
	public DataRow previous() {
		currentIndex--;
		return get();
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean hasNext() {
		return currentIndex < (data.size()-1);
	}

	@Override
	public DataRow next() {
		currentIndex ++;
		return get();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove is not supported in " +getClass().getName());
		
	}

}
