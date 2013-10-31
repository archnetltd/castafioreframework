/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.ui.ex.list;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.ui.CastafioreController;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.FormComponent;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ResourceUtil;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
public class EXSelect<T> extends EXContainer implements FormComponent<T>, CastafioreController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataModel<T> model;
	

	public EXSelect(String name) {
		super(name, "select");
	}

	@Override
	public T getValue() {
		String selected = getAttribute("value");
		try{
			return model.getValue(Integer.parseInt(selected));
		}catch(Exception e){
			setAttribute("value", "0");
			return model.getValue(0);
		}
	}
	
	public EXSelect<T> setModel(DataModel<T> model){
		this.model = model;
		setRendered(false);
		return this;
	}
	
	public DataModel<T> getModel(){
		return model;
	}

	@Override
	public void setValue(T value) {
		if(value == null)
		{
			setSelectedIndex(0);
		}else{
			for(int i = 0; i < model.getSize();i++){
				if(value.equals(model.getValue(i))){
					setAttribute("value", i + "");
				}
			}
		}
		
	}
	
	public EXSelect<T> setSelectedIndex(int i){
		setAttribute("value", i +"");
		setRendered(false);
		return this;
		
	}
	
	public int getSelectedIndex(){
		try{
			return Integer.parseInt(getAttribute("value"));
		}catch(Exception e){
			setAttribute("value", "0");
			return 0;
		}
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(model != null){
			
		
		int size = model.getSize();
		JArray array = new JArray();
		int selected = getSelectedIndex();
		for(int i =0; i < size; i++){
			T v = model.getValue(i);
			JMap m = new JMap().put("value", v.toString());
			m.put("index", i);
			m.put("selected",selected==i );
			array.add(m);
		}
		response.getWriter().write(array.getJavascript());
		
		}
		return null;
	}
	
	public void onReady(ClientProxy proxy){
		proxy.appendJSFragment("combo($('"+proxy.getIdRef()+"'), '"+ResourceUtil.getMethodUrl(this)+"');");
	}

	
}
