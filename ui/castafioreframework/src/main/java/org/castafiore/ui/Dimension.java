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
 * Class that encapsulates all the dimension and units used on a web browser
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class Dimension {
	
	private String unit = "px";
	
	private int amount;
	
	private String ov;

	/**
	 * Constructs a {@link Dimension} for the specified unit and amount.<br>
	 * <b>Valid units are
	 * <ol>
	 * 	<li>px</li>
	 * <li>%</li>
	 * <li>cm</li>
	 * <li>pt</li>
	 * <li>mm</li>
	 * <li>ex</li>
	 * <li>em</li>
	 * <li>in</li>
	 * <li>pc</li>
	 * </ol>
	 * @param unit The unit
	 * @param amount The amount in integer
	 */
	public Dimension(String unit, int amount) {
		super();
		this.unit = unit;
		this.amount = amount;
	}
	
	/**
	 * Creates a {@link Dimension} based on a string representation that can be parsed
	 * @param userDefined The userdefined string
	 */
	public Dimension(String userDefined) {
		super();
		this.ov = userDefined;
	}
	
	/**
	 * Creates a {@link Dimension} by parsing a string representation of a dimension
	 * @param dimension The string representation
	 * @return The dimension created
	 */
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
	
	/**
	 * Creates a {@link Dimension} based simply on an integer value.<br>
	 * The default unit is pixel (px)
	 * @param amount The scalar value of the dimension
	 */
	public Dimension(int amount) {
		super();
		this.amount = amount;
	}
	

	/**
	 * 
	 * @return The unit of this dimension
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the unit of this dimension
	 * @param unit The unit of this dimension
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Returns the amount of this dimension
	 * @return The amount of this dimension
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount of this dimension
	 * @param amount The amount of this dimension
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}


	/**
	 * Converts the {@link Dimension} into a string representation to be rendered on the browser
	 */
	@Override
	public String toString() {
		if(StringUtil.isNotEmpty(ov)){
			return ov;
		}
		return ""  + this.amount + unit; 
	}
	
	
	

}
