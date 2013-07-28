package org.castafiore.designer.dataenvironment.invocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.Types;
import org.castafiore.designer.dataenvironment.DataRow;
import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.ui.UIException;
import org.castafiore.wfs.types.File;

public class MethodInvocationDataSet implements DataSet{

private Datasource datasource;
	
	private List<File> fileIterator;
	
	
	private int currentIndex = -1;
	
	private Map<String, Types> fields = new HashMap<String, Types>();
	
	

	public MethodInvocationDataSet(Datasource datasource,
			List<File> fileIterator) {
		super();
		this.datasource = datasource;
		this.fileIterator = fileIterator;
		
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
		return new MethodInvocationDataRow(this, fileIterator.get(currentIndex));
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
		
		try{
		String clazz = datasource.getAttribute("ClassType");
		Class c = Thread.currentThread().getContextClassLoader().loadClass(clazz);
		Method[] methods = c.getMethods();
		
		for(Method m : methods){
			if(m.getName().startsWith("get") && m.getParameterTypes().length == 0){
				fields.put(m.getName().substring(3), Types.STRING);
				
			}
		}
		return fields.keySet().toArray(new String[fields.keySet().size()]);
		}catch(Exception e){
			throw new UIException("unable to load fields " ,e);
		}
		
	}

	@Override
	public Types getType(String field) {
		return fields.get(field);
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
