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

package org.castafiore.ui.ex.layout.template;

import groovy.lang.Writable;
import groovy.text.Template;

import java.io.CharArrayWriter;
import java.util.Map;

import org.castafiore.ui.ex.Compiler;
import org.castafiore.utils.GroovyUtil;
import org.castafiore.utils.JavascriptUtil;
/**
 * An {@link EXTemplateComponent} that uses groovy script as template.<br>
 * Very convenient class that harness the whole power of groovy into your web application
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXGroovyContainer extends EXTemplateComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6285574701992427207L;

	public EXGroovyContainer(String name, String templateLocation) {
		super(name, templateLocation);
	}


	public EXGroovyContainer(String name) {
		super(name);
	}


	public final static Compiler GROOVY_COMPILER = new Compiler(){

		public String compile(String template, Map<String, Object> context)
				throws Exception {
			Template gTemplate = GroovyUtil.getGroovyTemplate(template);
			
			Writable writable = gTemplate.make( context);
			
			 CharArrayWriter writer =  new CharArrayWriter(); 
			 writable.writeTo(writer);
			 return writer.toString();
		}
	};
	
	public Compiler getCompiler() {
		return GROOVY_COMPILER;
	}
}
