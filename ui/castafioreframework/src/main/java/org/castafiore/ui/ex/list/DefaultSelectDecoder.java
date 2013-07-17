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

import org.castafiore.ui.Decoder;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.utils.ExceptionUtil;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class DefaultSelectDecoder implements Decoder{

	public String decode(Object value, StatefullComponent component)
			throws UIException {
		if(component instanceof EXSelect)
		{
			EXSelect select = (EXSelect)component;
			
			DataModel model = select.getModel();
			
			
			for(int i = 0; i < model.getSize(); i ++)
			{
				if(model.getValue(i).equals(value))
				{
					return "" + i;
				}
			}
			
		}
		else
		{
			throw ExceptionUtil.getRuntimeException("this decoder works only on EXSelect components");
		}
		return null;
	}
	
	

}
