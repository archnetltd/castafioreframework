/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.designer.designable.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.json.JSONObject;

public class JSONTableModel implements TableModel {

	private JSONObject model;
	
	private JSONObject datasource;
	private List data = new ArrayList<Object>();
	protected final transient Log logger = LogFactory.getLog(getClass());
	
	public JSONTableModel(JSONObject model, JSONObject datasource) {
		super();
		this.model = model;
		this.datasource = datasource;
		loadData();
	}
	
	public JSONTableModel(org.castafiore.ui.Container c) {
		super();
		try{
			this.datasource = new JSONObject(c.getAttribute("datasource"));
			try{
				this.model = new JSONObject(c.getAttribute("datamodel"));
			}catch(Exception e){
				logger.error(e);
			}
			loadData();
		}catch(Exception e){
			//e.printStackTrace();
			logger.error(e);
		}
	}
	
	public String getColumnWidth(int index){
		try{
			return model.getJSONArray("widths").getString(index);
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	private void loadData(){
		try{
			String type = datasource.getString("type");
			String value = datasource.getString("value");
			String entity = datasource.getString("entitytype");
			
			String[] names = JSONObject.getNames(datasource);
			
			String[] paths = StringUtil.split(value, ";");
			Class cls = Thread.currentThread().getContextClassLoader().loadClass(entity);
			QueryParameters params = new QueryParameters().setEntity(cls);
			
			for(String s : names){
				if(!s.equalsIgnoreCase("type") && !s.equalsIgnoreCase("value") && !s.equalsIgnoreCase("entitytype") && !s.equalsIgnoreCase("pageSize")){
					params.addRestriction(Restrictions.eq(s, datasource.get(s)));
				}
			}
			
			
			//String begin = "from " + entity + " WHERE 0!=0 OR";
			String crit = " absolutePath='";
			
			if(type.equalsIgnoreCase("folders")){
				crit = " parent.absolutePath='";
			}
			for(String path : paths){
				
				
				//begin = begin + crit + path + "' OR";
				if(type.equalsIgnoreCase("folders")){
					params.addSearchDir(path);
				}else{
					params.addRestriction(Restrictions.eq("absolutePath", path));
				}
			}
		
			//begin = begin + " 1!=1";
//			if(type.equalsIgnoreCase("path")){
//				
//					
//				if(model != null)
//					entity = model.getString("entity");
//				
//				value = "from " + entity + " where parent.absolutePath = '" + value + "'";
//			}
			//System.out.println(begin);
			data = BaseSpringUtil.getBeanOfType(RepositoryService.class).executeQuery(params, Util.getRemoteUser());
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("unable to load data", e);
		}
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		try{
			return model.getJSONArray("labels").length();
		}catch(Exception e){
			logger.error(e);
			return 1;
		}
	}

	public String getColumnNameAt(int index) {
		try{
			return model.getJSONArray("labels").getString(index);
		}catch(Exception e){
			//e.printStackTrace();
			logger.error(e);
			return "Field 1";
		}
	}

	public int getRowCount() {
		return data.size();
	}

	public int getRowsPerPage() {
		try{
			return model.getInt("pageSize");
		}catch(Exception e){
			//e.printStackTrace();
			logger.error(e);
			return 1000;
		}
	}

	public Object getValueAt(int col, int row, int page) {
		try{
			String property = null;
			String entity = null;
			ClassMetadata metadata = null;
			int index = (getRowsPerPage() * page) + row;
			if(model != null){
				property = this.model.getJSONArray("properties").getString(col);
			
				entity =model.getString("entity");
				SessionFactory factory = BaseSpringUtil.getBeanOfType(SessionFactory.class);
				metadata = factory.getClassMetadata(entity);
				Object value = metadata.getPropertyValue(data.get(index), property, EntityMode.POJO);
				return value;
			}else{
				return data.get(index);
			}
			
			
			
			 
			
			
			
			
			
		}catch(Exception e){
			//e.printStackTrace();
			logger.error(e);
			return "??";
		}
		
	}
	
	public Object getItemAt(int index){
		return data.get(index);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		try{
			return model.getJSONArray("editables").getBoolean(columnIndex);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void setDate(List d){
		this.data = d;
	}
	public List getData(){
		return data;
	}

}
