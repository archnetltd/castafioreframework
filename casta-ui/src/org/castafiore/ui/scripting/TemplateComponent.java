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

package org.castafiore.ui.scripting;

import java.util.Map;

import org.castafiore.ui.LayoutContainer;
import org.castafiore.ui.scripting.compiler.Compiler;

/**
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */
public interface TemplateComponent extends LayoutContainer {

	public void setTemplate(String xhtml);

	public String getTemplate();

	public Compiler getCompiler();

	public void setContext(Map<String, Object> context);

	public Map<String, Object> getContext();

}
