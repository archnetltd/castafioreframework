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
 package org.castafiore.designer.designable.datarepeater;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.designer.designable.table.JSONTableModel;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;

public class EXDataRepeater extends EXContainer implements DataContainer{

	private JSONTableModel model;
	
	private int rowsPerPage = 5;
	
	private String injectionScript;
	
	private int curPage = 0;
	
	public EXDataRepeater() {
		super("EXDataRepeater", "ul");
	}
	
	public EXDataRepeater(String name, String template) {
		super(name, "ul");
		setTemplateLocation(template);
	}
	
	public String getTemplateLocation(){
		return getAttribute("templatelocation");
	}
	
	
	
	
	public String getInjectionScript() {
		return injectionScript;
	}

	public void setInjectionScript(String injectionScript) {
		this.injectionScript = injectionScript;
	}

	public void setTemplateLocation(String templateLocation){
		setAttribute("templatelocation", templateLocation);
		refresh();
	}
	
	//how does this works.
	//The user configures a component
	//he select a datasource
	//he selects the datafield for each item
	//#d(title).substring(10)
	//#f(image).getValue();
	//#d(index)
	//#d(size)
	//#d(sum(price))
	
	
	public void refresh(){
		this.getChildren().clear();
		this.setRendered(false);
		int size = 3;
		if(model != null){
			size = model.getRowCount();
			rowsPerPage = model.getRowsPerPage();
		}
		
		if(size <= rowsPerPage || rowsPerPage == -1){
			rowsPerPage = size;
		}
		
		if(StringUtil.isNotEmpty(getTemplateLocation())){
		BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(getTemplateLocation(), Util.getRemoteUser());
		String script = injectionScript;
		Binding b = new Binding();
		
		GroovyShell shell = new GroovyShell(b);
		
		if(injectionScript != null){
		if(injectionScript.startsWith("/") || injectionScript.startsWith("http") || injectionScript.endsWith(".groovy")){
			try{
				script = ResourceUtil.readUrl(injectionScript);
			}catch(Exception e){
				script = injectionScript;
			}
			
		}
		}
		try{
			String src = IOUtil.getStreamContentAsString(bf.getInputStream());
			Exception ee = null;
			for(int i = 0; i < rowsPerPage;i++){
				EXRepeaterItem c = new EXRepeaterItem("");
				c.setComponentPath(bf.getAbsolutePath(), src);
				
				Container li = new EXContainer("", "li");
				addChild(li);
				li.addChild(c);
				
				if(StringUtil.isNotEmpty(script) && model != null){
					Object data = model.getValueAt(0, i, curPage);
					shell.setProperty("me", c);
					shell.setProperty("data", data);
					try{
					shell.evaluate(script);
					}catch(Exception e){
						ee = e;
					}
				}
				
			}
			
			if(ee != null){
				throw new UIException(ee);
			}
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		
	}
	
public void goToPage(int page){
	curPage = page;
	refresh();
}

	public String getRowsPerPage() {
		return getAttribute("rowsPerPage");
	}


	public void setRowsPerPage(String rowsPerPage) {
		setAttribute("rowsPerPage", rowsPerPage);
	}


	public Object getFileAt(int index){
		if(model != null){
			return model.getItemAt(index);
		}return "null";
	}


	public TableModel getModel() {
		return this.model;
	}


	public void setModel(TableModel model) {
		this.model = (JSONTableModel)model;
		
	}
	
	
	public int getPages()
	{
		int rows = model.getRowCount();

		int rPerPage = model.getRowsPerPage();

		int remainder = rows % rPerPage;

		int multiple = Math.round(rows / rPerPage);

		int pages = multiple;
 
		if (remainder != 0)
		{
			pages = pages + 1;
		}

		return pages;

	}


	public List getData(){
		if(model instanceof JSONTableModel){
			return ((JSONTableModel)model).getData();
		}else{
			return new ArrayList();
		}
	}
	
	
	public void setData(List data){
		this.model.setDate(data);
		refresh();
	}

	@Override
	public void changePage(int page) {
		goToPage(page);
		
	}

	@Override
	public int getCurrentPage() {
		return curPage;
	}
	
	

}
