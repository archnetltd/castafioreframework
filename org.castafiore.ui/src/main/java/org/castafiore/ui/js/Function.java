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
package org.castafiore.ui.js;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.utils.JavascriptUtil;

public class Function implements JSObject {

	Object[] params;
	ClientProxy body;

	public Function(ClientProxy body, Object... parameters) {
		this.body = body;
		this.params = parameters;
	}

	public Object[] getParams() {
		return params;
	}

	public Function setParams(Object[] params) {
		this.params = params;
		return this;
	}

	public ClientProxy getBody() {
		return body;
	}

	public Function setBody(ClientProxy body) {
		this.body = body;
		return this;
	}

	public String getJavascript() {
		// function(params){
		// ClientProxy.getCompleteJQuery();
		// }

		String result = "function(" + JavascriptUtil.generateJS(params) + "){"
				+ body.getCompleteJQuery() + "}";
		return result;
	}

}
