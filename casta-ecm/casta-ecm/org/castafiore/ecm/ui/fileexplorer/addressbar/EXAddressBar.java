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

package org.castafiore.ecm.ui.fileexplorer.addressbar;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.toolbar.ToolBarItem;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXAddressBar extends EXInput implements ToolBarItem {
	
	private Stack<String> history = new Stack<String>();
	
	private boolean back = false;

	public EXAddressBar(String name) {
		super(name);
//		setWidth(Dimension.parse("300px"));
//		setStyle("border-style", "none");
//		setHeight(Dimension.parse("15px")).setStyle("margin", "1px 0 0 1px");
		addClass("filexploreraddressbar");
		setStyle("margin", "0");
	}
	
	public StatefullComponent getInput(){
		return this;
	}
	
	public void setAddress(String address)
	{
		
		String curadd = getInput().getValue().toString();
		String top = "";
		if(history.size() > 0)
		top = history.peek();
		if(StringUtil.isNotEmpty(curadd) && !address.equals(top)){
			if(!back)
			history.push(curadd);
		}
		
		Object o = getInput().getValue();
		getInput().setValue(address);
		back = false;
	}
	
	
	
	public void back(){
		if(history.size() <=0){
			return;
		}
		String pop = history.pop();
		Directory dir = BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(pop,  Util.getRemoteUser());
		back = true;
		getAncestorOfType(Explorer.class).OnFileSelected(dir);
//		if(!stack.isEmpty()){
//			getAncestorOfType(Explorer.class).OnFileSelected(BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(stack.peek(), Util.getRemoteUser()));
//			stack.pop();
//		}
//		String address = getAddress();
//		String as[] = StringUtil.split(address, "/");
//		
//		String newAddress = "";
//		if(as != null && as.length > 1){
//			history.add(address);
//			for(int i = 0; i < as.length - 1; i ++){
//				newAddress = newAddress + "/" + as[i];
//				
//				
//			}
//			Directory dir = BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(newAddress,  Util.getRemoteUser());
//			getAncestorOfType(Explorer.class).OnFileSelected(dir);
//		}
	}
	
	public String getAddress() {
		return getInput().getValue().toString();
	}

}
