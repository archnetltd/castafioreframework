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

package org.castafiore.ui;

import org.castafiore.utils.StringUtil;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class Dimension {
	
	private String unit = "px";
	
	private int amount;
	
	private String ov;

	public Dimension(String unit, int amount) {
		super();
		this.unit = unit;
		this.amount = amount;
	}
	public Dimension(String userDefined) {
		super();
		this.ov = userDefined;
	}
	
	public static Dimension parse(String dimension)
	{
		if(StringUtil.isNotEmpty(dimension))
		{
			String dim = dimension.toLowerCase().trim();
			int uLength = 2;
			if(dim.endsWith("px"))
			{
				
			}
			else if(dim.endsWith("%"))
			{
				uLength = 1;
			}
			else if(dim.endsWith("pt"))
			{
				
			}
			else if(dim.endsWith("cm"))
			{
				
			}
			else if(dim.endsWith("mm"))
			{
				
			}
			else if(dim.endsWith("ex"))
			{
				
			}
			else if(dim.endsWith("em"))
			{
				
			}
			else if(dim.endsWith("in")){
				
			}
			else if(dim.endsWith("pc"))
			{
				
			}else{
				return new Dimension(dimension);
			}
			
			String unit = dim.substring(dim.length()-uLength, dim.length());
			
			String amount = dim.substring(0,dim.length() - uLength);
			int iAmount = Integer.parseInt(amount.trim());
			
			return new Dimension(unit, iAmount);
		}
		return null;
	}
	public Dimension(int amount) {
		super();
		this.amount = amount;
	}
	

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		if(StringUtil.isNotEmpty(ov)){
			return ov;
		}
		return ""  + this.amount + unit; 
	}
	
	
	

}
