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

import org.castafiore.ui.dynaform.InputVerifier;

/**
 * Interface that marks a Form component. All component that wants its value to
 * be automatically retrieved or set to the browser should implement this
 * interface. The engine automatically manages the value attribute components
 * which implements this interface. A valid {@link Encoder} and {@link Decoder}
 * are required to serialize and deserialize the value attribute to and from the
 * browser. The component does not necessary need to an HTML form component. It
 * can be any component actually.
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
public interface FormComponent<T> extends Container {

	
	/**
	 * @return the value after the raw value has been passed through the encoder
	 */
	public T getValue();

	/**
	 * sets the raw value by passing the specified value through the decoder
	 * 
	 * @param value
	 *            The value
	 */
	public void setValue(T value);
	
	
	/**
	 * Sets the input verifier to be used to verify the validity of this FormComponent
	 * @param verifier The {@link InputVerifier} upon which validation is delegated
	 * @return This FormComponent itself
	 */
	public FormComponent<T> setInputVerifier(InputVerifier verifier);
	
	
	/**
	 * Returns the input verifier associated to this component
	 * @return
	 */
	public InputVerifier getInputVerifier();

}
