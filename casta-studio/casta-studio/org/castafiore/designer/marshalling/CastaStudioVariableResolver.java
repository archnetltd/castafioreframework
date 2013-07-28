package org.castafiore.designer.marshalling;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.jeval.VariableResolver;
import net.sourceforge.jeval.function.FunctionException;

import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.utils.StringUtil;

public class CastaStudioVariableResolver implements VariableResolver{
	
	private Container source;
	
	private static ThreadLocal<Map<String,DataSet>> dataSets = new ThreadLocal<Map<String,DataSet>>();
	
	public CastaStudioVariableResolver(Container source) {
		super();
		this.source = source;
	}
	
	
	public static DataSet getDataSet(String datasource, PortalContainer pc){
		if(dataSets.get() == null){
			dataSets.set(new HashMap<String, DataSet>());
		}
		if(dataSets.get().containsKey(datasource)){
			return dataSets.get().get(datasource);
		}
		
		for(Datasource ds : pc.getDatasources()){
			if(ds.getName().equals(datasource)){
				ds.open();
				DataSet d = ds.getDataSet();
				dataSets.get().put(datasource, d);
				return d;
				
			}
		}
		
		throw new UIException("unable to load dataset from datasource " + datasource);
	}

	private  String getString(Container tmp, String[] oo, String orig){
		if(oo.length ==1){
			if(tmp instanceof StatefullComponent){
				return ((StatefullComponent)tmp).getValue().toString();
			}else{
				return tmp.getText(false);
			}
		}else if(oo.length == 2){
			if(oo[1].equals("name")){
				return tmp.getName();
			}else if(oo[1].equals("text")){
				return tmp.getText(false);
			}else if(oo[1].equals("id")){
				return tmp.getId();
			}else if(oo[1].equals("value") && tmp instanceof StatefullComponent){
				return ((StatefullComponent)tmp).getValue().toString();
			}else{
				return tmp.getAttribute(oo[2]);
			}
		}else if(oo.length == 3){
			String type = oo[1];
			if(type.equals("style")){
				return tmp.getStyle(oo[2]);
			}else if(type.equals("attribute")){
				return tmp.getAttribute(oo[2]);
			}else{
				throw new UIException("unable to evaluate varialble " + orig + " was expecting type or attribute");
			}
		}else{
			throw new UIException("unable to evaluate value " + orig);
		}
	}

	@Override
	public String resolveVariable(String v) throws FunctionException {
		if(v.startsWith("ui:")){
			
			String s = v.replace("ui:", "");
			Container tmp = source;
			if(s.contains("|")){
				String[] parts = StringUtil.split(s, "|");
				String anc = parts[0];
				tmp = source.getAncestorByName(anc);
				if(tmp == null){
					throw new UIException("Cannot resolve expression : " + v + " :Error finding component " + s + ": component " + parts[0] + " does not exist in chain");
				}
				for(int i =1; i < parts.length-1;i++){
					tmp = tmp.getDescendentByName(parts[i]);
					if(tmp == null){
						throw new UIException("Cannot resolve expression : " + v + " :Error finding component " + s + ": component " + parts[i] + " does not exist in chain");
					}
				}
				
				String fi = parts[parts.length-1];
				String[] oo = StringUtil.split(fi, ".");
				tmp = tmp.getDescendentByName(oo[0]);
				
				return "'" + getString(tmp, oo, v) + "'";
				
			}else{
				tmp = source.getAncestorOfType(PortalContainer.class);
				String fi =s;
				String[] oo = StringUtil.split(fi, ".");
				tmp = tmp.getDescendentByName(oo[0]);
				
				return getString(tmp, oo, v);
				
			}
			
			
			
			//ui:portal>form>username.style.ad
			//variable is an interface 
			//ds:datasource[10].
		}else if(v.startsWith("ds:")){
			String dsName = v.replace("ds:", "");
			PortalContainer pf = source.getAncestorOfType(PortalContainer.class);
			String[] parts = StringUtil.split(dsName, ".");
			if(parts == null || parts.length != 2){
				throw new UIException("cannot resolve expression " + v + " expecting a format of ds:name.field ");
			}
			
			String name = parts[0];
			String field =parts[1];
			//name contains
			
			String datasourcename = StringUtil.split(name, "[")[0];
			int index = Integer.parseInt(StringUtil.split(name, "[")[1].replace("]", ""));
			
			DataSet d = getDataSet(datasourcename, pf);
			return "'" + d.get(index).getValue(field).toString() + "'";
			//throw new UIException("cannot resolve expression " + v + " Datasource " + name + " could not be found");
		}else if(v.startsWith("env:")){
			return System.getenv(v.replace("env:", ""));
		}else{
			return null;
		}
	}

}
