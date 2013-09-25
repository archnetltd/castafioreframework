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
package org.castafiore.ui.layout;

public class DroppableSection {

	private String componentId;

	private String label;

	private Object layoutData;

	public DroppableSection(String componentId, String label, Object layoutData) {
		super();
		this.componentId = componentId;
		this.label = label;
		this.layoutData = layoutData;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getLayoutData() {
		return layoutData;
	}

	public void setLayoutData(Object layoutData) {
		this.layoutData = layoutData;
	}

	public String toString() {
		return label;
	}

}
