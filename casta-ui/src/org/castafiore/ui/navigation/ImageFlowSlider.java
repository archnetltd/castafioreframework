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

import java.util.List;
import java.util.Map;

import org.castafiore.Constant;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.ResourceUtil;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class ImageFlowSlider extends EXContainer {

	/**
	 * <div id="scrollbar" style="margin-top: 18.3px; margin-left: 183px; width: 549px; visibility: visible;">
		<div id="slider" style="cursor: default; margin-left: 204.241px; left: -37.3621px;"/>
	</div>
	 * @param name
	 */
	public ImageFlowSlider(String name) {
		super(name, "div");
		
		
		setStyle("width", "700px");
		setStyle("visibility", "visibility");
		setStyle("border-bottom", "1px solid #B3B3B3");
		setStyle("position", "relative");
		setStyle("z-index", "10001");
		setStyle("text-align", "left");
		setStyle("position", "absolute");
		setStyle("top", "350px");
		setStyle("left", "50px");
		
		
		
		EXContainer slider = new EXContainer("slider", "div");
		slider.setStyle("cursor", "pointer");
		slider.setStyle("left", "350px");
		
		
		
		
		
		
		
		Event sliderEvent = new Event()
		{

			public void ClientAction(ClientProxy application) {
				JMap dragOptions = new JMap();
				dragOptions.put("axis", "x").put("containment",new Var(Constant.NO_CONFLICT + "(\"#"+getId() +"\")", ""));
				
				JMap parameters = new JMap().put("x-position", application.getStyle("left")).put("width", application.getParent().getStyle("width"));
				
				dragOptions.put("stop", application.clone().makeServerRequest(parameters,this));
				
				application.draggable(dragOptions);
				
			}

			public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
				List<ClientProxy> children = component.getAncestorOfType(EXImageFlow.class).getDescendentByName("dataContainer").getChildren();
				int mean = Integer.parseInt(requestParameters.get("mean"));
				for(int i = 0; i < children.size(); i ++)
				{ 
					JMap options = new JMap();
					ClientProxy element = children.get(i);
					int elementIndex = Integer.parseInt(requestParameters.get("element-" + element.getId()));
					
					if(EXImageFlow.getMode(elementIndex) <=  mean)
					{
						element.setStyle("display", "block");
						options.put("width", EXImageFlow.getWidth(elementIndex) + "px");
						
						options.put("height", EXImageFlow.getHeight(elementIndex) + "px");
						options.put("left", EXImageFlow.getLeft(elementIndex) + "px");
						options.put("top",EXImageFlow. getTop(elementIndex) + "px");
						element.animate(options, "50", null);
						 
						element.setStyle("opacity", EXImageFlow.getOpacity(elementIndex) + "");
						element.setStyle("z-index",EXImageFlow.getZIndex(elementIndex) + "");
					}
					else
					{
						element.setStyle("display", "none");
					}
					
					
				}
				
			}

			public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
				EXImageFlow imageFlow = (EXImageFlow)component.getAncestorOfType(EXImageFlow.class);
				
				int size = imageFlow.getModel().size();
				int mean = imageFlow.getMean();
				requestParameters.put("mean", mean + "");
				
				
				int xPosition =Dimension.parse(requestParameters.get("x-position")).getAmount();
				int width = Dimension.parse(requestParameters.get("width")).getAmount();
				
				
				double partSize = width/size;
				
				
				
				double relativePosition = xPosition/partSize;
				
				
				long lelementIndex = Math.round((mean*-1) + relativePosition );
				
				//int elementIndex = new Long(lelementIndex).intValue();
				int changeInElementIndex = new Long(lelementIndex).intValue();
				List<Container> items = imageFlow.getChild("dataContainer").getChildren();
				
				 
				for(int i = 0; i < items.size(); i ++)
				{  
					Container item = items.get(i);
					int currentElementIndex = Integer.parseInt(item.getAttribute("element-index"));
					if(changeInElementIndex < 0)
					{
						item.setAttribute("element-index", (currentElementIndex+EXImageFlow.getMode(changeInElementIndex)) + "");
					}
					else
					{
						item.setAttribute("element-index", (currentElementIndex-EXImageFlow.getMode(changeInElementIndex)) + "");
					}
					requestParameters.put("element-" + item.getId(), item.getAttribute("element-index"));
					
				}
				
				
				
				
				return true;
			}
			
		};
		
		
		slider.addEvent(sliderEvent, Event.READY);
		
		
		slider.setStyle("background-image", "url("+ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/navigation/imageflow/slider.png")+")");
		slider.setStyle("background-repeat", "no-repeat");
		slider.setStyle("height", "14px");
		slider.setStyle("margin-top", "-7px");
		slider.setStyle("position", "absolute");
		slider.setStyle("width", "14px");
		slider.setStyle("z-index", "10002");
		
		addChild(slider);
		
	}

}
