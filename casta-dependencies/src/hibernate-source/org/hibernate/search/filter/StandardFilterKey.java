/*
 * Copyright (C) 2007-2010 Castafiore
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
 // $Id: StandardFilterKey.java 14713 2008-05-29 15:18:15Z sannegrinovero $
package org.hibernate.search.filter;

import java.util.List;
import java.util.ArrayList;

/**
 * Implements a filter key usign all injected parameters to compute
 * equals and hashCode
 * the order the parameters are added is significant
 *
 * @author Emmanuel Bernard
 */
public class StandardFilterKey extends FilterKey {
	private final List parameters = new ArrayList();
	private boolean implSet;


	public void setImpl(Class impl) {
		super.setImpl( impl );
		//add impl once and only once
		if (implSet) {
			parameters.set( 0, impl );
		}
		else {
			implSet = true;
			parameters.add( 0, impl );
		}
	}

	public void addParameter(Object value) {
		parameters.add( value );
	}
	public int hashCode() {
		int hash = 23;
		for (Object param : parameters) {
			hash = 31*hash + (param != null ? param.hashCode() : 0);
		}
		return hash;
	}

	public boolean equals(Object obj) {
		if ( ! ( obj instanceof StandardFilterKey ) ) return false;
		StandardFilterKey that = (StandardFilterKey) obj;
		int size = parameters.size();
		if ( size != that.parameters.size() ) return false;
		for (int index = 0 ; index < size; index++) {
			Object paramThis = parameters.get( index );
			Object paramThat = that.parameters.get( index );
			if (paramThis == null && paramThat != null) return false;
			if (paramThis != null && ! paramThis.equals( paramThat ) ) return false;
		}
		return true;
	}
}
