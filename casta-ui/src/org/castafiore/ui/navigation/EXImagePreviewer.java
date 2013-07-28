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

package org.castafiore.ui.navigation;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.layout.Layout;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXImagePreviewer extends EXContainer {

	private NavigationFlowFlowModel model_;
	public EXImagePreviewer(String name, NavigationFlowFlowModel model) {
		
		
		
		super(name, "div");
		this.model_= model;
		setWidth(Dimension.parse("1000px"));
		setHeight(Dimension.parse("800px"));
		EXContainer viewContainer = new EXContainer("viewContainer", "div");
		viewContainer.setStyle("text-align", "center");
		viewContainer.setWidth(Dimension.parse("100%"));
		viewContainer.setHeight(Dimension.parse("75%"));
		viewContainer.setStyle("background-color", "black");
		addChild(viewContainer);
		
		
		EXContainer previewContainer = new EXContainer("previewContainer", "div");
		previewContainer.setWidth(Dimension.parse("100%"));
		previewContainer.setHeight(Dimension.parse("25%"));
		previewContainer.setStyle("background-color", "silver");
		previewContainer.setStyle("margin", "5px");
		previewContainer.setStyle("text-align", "center");
		previewContainer.setLayout(new Layout(){

			public void doStyling(Container child, Container container) {
				child.setStyle("display", "inline");
				child.setStyle("padding", "5px");
				
			}
			
		});
		previewContainer.setStyle("overflow-x", "scroll");
		previewContainer.setStyle("overflow-y", "scroll");
		for(int i = 0; i < model_.size(); i ++)
		{
			EXContainer div = new EXContainer("div", "div");
			div.addChild(model_.getPreviewComponentAt(i, this));
			div.setAttribute("index", i + "");
			div.addEvent(new Event(){

				public void ClientAction(ClientProxy application) {
					application.makeServerRequest( this);
					
				}

				public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
					component.getAncestorOfType(EXImagePreviewer.class).getDescendentByName("viewContainer").fadeIn(100);
					
				}

				public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
					int index = Integer.parseInt(component.getAttribute("index"));
					
					EXImagePreviewer previer = (EXImagePreviewer)component.getAncestorOfType(EXImagePreviewer.class);
					
					EXContainer viewContainer = (EXContainer)previer.getChild("viewContainer");
					
					Container view = previer.model_.getComponentAt(index, previer);
					
					viewContainer.getChildren().clear();
					viewContainer.addChild(view);
					viewContainer.setDisplay(false);
					viewContainer.setRendered(false);
					return true;
					
				}
				
			}, Event.CLICK);
			
			previewContainer.addChild(div);
		}
		
		addChild(previewContainer);
		
		
		
	}

}
