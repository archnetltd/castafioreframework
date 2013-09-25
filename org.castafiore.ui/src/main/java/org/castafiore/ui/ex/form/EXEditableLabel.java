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

package org.castafiore.ui.ex.form;

import java.util.Map;

import org.castafiore.ui.AbstractFormComponent;
import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class EXEditableLabel extends AbstractFormComponent implements Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static int MODE_EDIT = 0;

	public final static int MODE_READ = 1;

	public EXEditableLabel(String name, String value) {
		super(name, "div");

		EXContainer label = new EXContainer(name + "_label", "span");
		label.setText(value);

		addChild(label);

		label.addEvent(this, Event.DOUBLE_CLICK);

		EXInput input = new EXInput(name + "_input");
		input.setValue(value);
		input.setStyle("display", "none");
		input.addEvent(this, Event.DOUBLE_CLICK);
		addChild(input);

	}

	public void ClientAction(ClientProxy application) {

		ClientProxy label = application.getParent().getChildren().get(0);
		ClientProxy input = application.getParent().getChildren().get(1);

		if (application.getName().endsWith("_label")) {
			label.setStyle("display", "none");
			input.setStyle("display", "block");
			input.setAttribute("value", label.text().getJavascript());
		} else {
			label.setStyle("display", "block");
			input.setStyle("display", "none");
			label.setText(input.getAttribute("value").getJavascript());
			if (getAncestorOfType(EXEditableLabel.class).getEvents()
					.containsKey(Event.CHANGE)
					&& getAncestorOfType(EXEditableLabel.class).getEvents()
							.get(Event.CHANGE).size() > 0)
				application.makeServerRequest(this);
		}

	}

	public boolean ServerAction(Container component,
			Map<String, String> requestParameters) throws UIException {

		return component
				.getAncestorOfType(EXEditableLabel.class)
				.getEvents()
				.get(Event.CHANGE)
				.get(0)
				.ServerAction(
						component.getAncestorOfType(EXEditableLabel.class),
						requestParameters);

	}

	public void Success(ClientProxy component,
			Map<String, String> requestParameters) throws UIException {

	}

	public String getRawValue() {
		return ((EXInput) this.getChildByIndex(1)).getValue().toString();
	}

	public void setRawValue(String rawValue) {
		((EXInput) this.getChildByIndex(1)).setValue(rawValue);

	}

	@Override
	public String getValue() {
		return ((EXInput) this.getChildByIndex(1)).getValue().toString();
	}

	public void setValue(String value) {
		((EXInput) this.getChildByIndex(1)).setValue(value);
		this.getChildByIndex(0).setText(value);
		this.getChildByIndex(0).setRendered(false);
	}

	public void setMode(int mode) {
		if (mode == 0) {
			this.getChildByIndex(0).setStyle("display", "block");
			this.getChildByIndex(1).setStyle("display", "none");
		} else {
			this.getChildByIndex(1).setStyle("display", "none");
			this.getChildByIndex(0).setStyle("display", "block");
		}
	}

}
