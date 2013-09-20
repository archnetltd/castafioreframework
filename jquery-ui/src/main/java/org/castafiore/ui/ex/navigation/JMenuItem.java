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
 package org.castafiore.ui.ex.navigation;

import java.awt.MenuItem;

import org.castafiore.ui.EXContainer;

/**
 * Represents an item in a menu
 * @author arossaye
 *
 */
public class JMenuItem extends EXContainer implements org.castafiore.ui.navigation.MenuItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@link MenuItem}
	 * @param name The name of the menu item
	 * @param label The label to display on the menu item
	 */
	public JMenuItem(String name, String label) {
		super(name, "a");
		setAttribute("href", "#");
		setText(label);
	}

	public String getLabel() {
		return getText(false);
	}

}
