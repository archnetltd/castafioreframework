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

import org.castafiore.ui.input.Decoder;
import org.castafiore.ui.input.Encoder;

/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */
public interface StatefullComponent extends Container {
	
	/**
	 * returns the raw value of the component
	 * the raw value has not been passed through the decoder
	 * @return
	 */
	public String getRawValue();
	
	/**
	 * sets the raw value of the component.
	 * The raw value has not been passed through the encoder
	 * @param rawValue
	 */
	public void setRawValue(String rawValue);
	
	/**
	 * returns the value after the raw value has been passed through the encoder
	 * @return
	 */
	public Object getValue();
	
	
	/**
	 * sets the raw value by passing the specified value through the decoder
	 * @param value
	 */
	public void setValue(Object value);
	
	/**
	 * returns the encoder
	 * @return
	 */
	public Encoder getEncoder();
	
	/**
	 * returns the decoder
	 * @return
	 */
	public Decoder getDecoder();
	
	
	/**
	 * sets the encoder
	 * @param encoder
	 */
	public void setEncoder(Encoder encoder);
	
	/**
	 * sets the decoder
	 * @param decoder
	 */
	public void setDecoder(Decoder decoder);
	


}
