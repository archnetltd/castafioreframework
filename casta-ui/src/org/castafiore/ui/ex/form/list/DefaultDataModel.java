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

package org.castafiore.ui.ex.form.list;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */
public class DefaultDataModel<T> implements DataModel<T> {
	
	private List<T> data_ = new ArrayList<T>();
	
	public DefaultDataModel()
	{
		
	}
	public DefaultDataModel(List<T> data)
	{
		this.data_ = data;
	}

	public int getSize() {
		return data_.size();
	}

	public T getValue(int index) {
		return data_.get(index);
	}
	
	public DefaultDataModel<T> addItem(T o){
		data_.add(o);
		return this;
	}
	
	public DefaultDataModel<T> addItem(T... oo){
		if(oo != null){
			for(T o :oo)
			data_.add(o);
		}
		return this;
	}
	
	public List<T> getData(){
		return data_;
	}

}
