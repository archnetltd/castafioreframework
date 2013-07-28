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
 package org.castafiore.demo;

import org.castafiore.ui.DescriptibleApplication;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXContainer;

public class HelloWorldApplication extends EXApplication implements DescriptibleApplication {

	public HelloWorldApplication() {
		
		super("helloworld");
		
		
		EXContainer container = new EXContainer("hello", "div");
		container.setText("<p>Hello world!</p>");
		addChild(container);
		
	}

	
	public String getDescription() {
		return "A very simple application displaying the popular 'Hello World!'";
	}

}
