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
import org.castafiore.utils.ExceptionUtil;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public abstract class AbstractFormComponent<T> extends EXContainer implements FormComponent<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private InputVerifier inputVerifier;

	public AbstractFormComponent(String name, String tagName) {
		super(name, tagName);

	}

	public abstract String serialize(T value);
	
	public abstract T deserialize(String s);
	
	public void setValue(T value) {
		try {
			setAttribute("value", serialize(value));
		} catch (Exception e) {
			throw ExceptionUtil.getRuntimeException(e.getMessage());
		}
	}

	public T getValue() {
		try {
			return deserialize(getAttribute("value"));
		} catch (Exception e) {

			throw ExceptionUtil.getRuntimeException("error in encoding value");
		}
	}

	public InputVerifier getInputVerifier() {
		return inputVerifier;
	}

	public FormComponent<T> setInputVerifier(InputVerifier inputVerifier) {
		this.inputVerifier = inputVerifier;
		return this;
	}
	
	

}
