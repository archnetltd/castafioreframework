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

package org.castafiore.ui.ex.form.list;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXRadioButton;
import org.castafiore.ui.ex.layout.Layout;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXRadio extends AbstractEXList<Object> implements StatefullComponent {
	
	private final static Event SETVALUE_EVENT = new Event(){

		public void ClientAction(ClientProxy application) {
			String attrRef = application.getAttribute("value").getJavascript();
			
			application.getParent().getParent().setAttribute("value", attrRef);
			
		}

		

		public boolean ServerAction(Container component,
				Map<String, String> requestParameters) throws UIException {
			
			return false;
		}



		public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private final static ListItemRenderer<Object> RADIO_CELLRENDERER = new ListItemRenderer<Object>(){

		public ListItem<Object> getCellAt(int index, Object value, DataModel<Object> model, List<Object> parent)
		{
			EXListItem<Object> div = new EXListItem<Object>("", "div");
			div.setStyle("float", "left");
			EXContainer label = new EXContainer("", "label");
			label.setText(value.toString());
			div.addChild(label);
			EXRadioButton radio = new EXRadioButton("radiobutton_" +parent.getName() );
			//radio.setText("<label>" + value.toString() + "</label>");
			radio.setRawValue(index + "");	
			radio.addEvent(SETVALUE_EVENT, Event.CLICK);
			
			div.addChild(radio);
			div.setData(value);
			
			return div;
		}
		
	};
	


	public EXRadio(String name, DataModel<Object> model) {
		super(name, "div", model);
		
		super.setRenderer(RADIO_CELLRENDERER);
	}

	
	public EXRadio(String name, DataModel<Object> model, Object value) {
		super(name, "div", model);
		
		super.setRenderer(RADIO_CELLRENDERER);
		setValue(value);
	}


	@Override
	public void selectItem(ListItem<Object> child, boolean selected)
	{
		if(selected)
			child.getChildByIndex(1).setAttribute("checked", "checked");
		else
			child.getChildByIndex(1).setAttribute("checked", (String)null);
		
	}



	public void setVertical(){
		super.setLayout(null);
		
	}
	
	public void setHorizontal(){
		super.setLayout(new Layout(){

			public void doStyling(Container child, Container container) {
				child.setStyle("float", "left");
				
			}
			
		});
	}


	@Override
	public void addItem(ListItem<Object> item) {
		addChild(item);
		
	}


	@Override
	public ListItem<Object> getItem(int index) {
		return (ListItem<Object>)getChildByIndex(index);
	}


	@Override
	public int getSize() {
		return getChildren().size();
	}
	


	


}
