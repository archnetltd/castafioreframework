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

import org.castafiore.utils.ExceptionUtil;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public abstract class AbstractFormComponent extends EXContainer implements
		FormComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Encoder encoder_ = DefaultEncoder.INSTANCE;

	private Decoder decoder_ = DefaultDecoder.INSTANCE;

	public AbstractFormComponent(String name, String tagName) {
		super(name, tagName);

	}

	public Decoder getDecoder() {
		return this.decoder_;
	}

	public Encoder getEncoder() {
		return this.encoder_;
	}

	public void setDecoder(Decoder decoder) {
		this.decoder_ = decoder;

	}

	public void setEncoder(Encoder encoder) {
		this.encoder_ = encoder;

	}

	public void setValue(Object value) {
		try {
			setRawValue((getDecoder().decode(value, this)));
		} catch (Exception e) {
			throw ExceptionUtil.getRuntimeException(e.getMessage());
		}
	}

	public Object getValue() {
		try {
			return getEncoder().encode(getRawValue(), this);
		} catch (Exception e) {

			throw ExceptionUtil.getRuntimeException("error in encoding value");
		}
	}

}
