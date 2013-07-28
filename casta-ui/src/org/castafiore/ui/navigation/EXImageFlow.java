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

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.PanelModel;
import org.castafiore.ui.js.JMap;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXImageFlow extends EXContainer {
	
	private int mean = 4;

	private NavigationFlowFlowModel model_;
	
	public EXImageFlow(String name, NavigationFlowFlowModel model) {
		super(name, "div");
		setWidth(Dimension.parse("500px"));
		setHeight(Dimension.parse("500px"));
		this.model_ = model;
		
		EXContainer dataContainer = new EXContainer("dataContainer", "div");
		addChild(dataContainer);
		
		EXPanel viewContainer= new EXPanel("viewContainer");
		addChild(viewContainer);
		viewContainer.setDisplay(false);
		
		addChild(new ImageFlowSlider("imageFlowSider"));
		
		
		
		
		
		for(int i = 0; i < model_.size(); i ++)
		{
			int elementIndex = (mean*-1) + i;
			EXContainer container = new EXContainer("", "div");
			
			container.setAttribute("element-index", elementIndex + "");
			container.setStyle("width", getWidth(elementIndex) + "px");
			container.setStyle("position", "absolute");
			container.setStyle("height", getHeight(elementIndex) + "px");
			container.setStyle("left", getLeft(elementIndex) + "px");
			container.setStyle("top", getTop(elementIndex) + "px");
			container.setStyle("opacity", getOpacity(elementIndex) + "");
			container.setStyle("z-index",getZIndex(elementIndex) + "");
			container.setStyle("overflow", "hidden");
			container.addChild(model_.getPreviewComponentAt(i, this));
			
			container.addEvent(getOnClickEvent(i), Event.CLICK);
			
			dataContainer.addChild(container);
		}
	}
	
	public static int getZIndex(int elementIndex)
	{
		return 20 - getMode(elementIndex);
	}
	
	public static double getOpacity(int elementIndex)
	{
		
		 double mod = Math.sqrt(elementIndex*elementIndex);
		double f = mod/10;
		
		double opacity = 1 -f;
		return opacity;
	}
	
	public static int getMode(int integer)
	{
		return new Double( Math.sqrt( integer*integer)).intValue();
	}
	
	public static int getTop(int elementIndex)
	{
		return 50 + getMode(elementIndex)*25;
	}
	
	public static int getWidth(int elementIndex)
	{
		return new Double( getHeight(elementIndex)*0.8).intValue();
	}
	public static int getHeight(int elementIndex)
	{
		return  250 -  getMode(elementIndex)*50;
	}
	
	public static int getLeft(int elementIndex)
	{
		if(elementIndex == 0)
		{
			return 285;
		}
		else if(elementIndex < 0)
		{
			return 120 + elementIndex*30;
		}
		else if(elementIndex > 0)
		{
			return 470 + elementIndex*65;
		}
		else
		{
			return -1;
		}
	}
	
	public Event getOnClickEvent(final int index)
	{
		Event event = new Event()
		{
			
			public void ClientAction(ClientProxy application) {
				application.makeServerRequest(new JMap().put("index", index + ""),this);
			}

			
			public void Success(ClientProxy component,
					Map<String, String> requestParameters) throws UIException {
				List<ClientProxy> children = component.getParent().getChildren();
				int mean = Integer.parseInt(requestParameters.get("mean"));
				if(!"true".equals(requestParameters.get("open")))
				{ 
				
					for(int i = 0; i < children.size(); i ++)
					{ 
						JMap options = new JMap();
						ClientProxy element = children.get(i);
						int elementIndex = Integer.parseInt(requestParameters.get("element-" + element.getId()));
						if(getMode(elementIndex) <=  mean)
						{
							element.setStyle("display", "block");
							options.put("width", getWidth(elementIndex) + "px");
							
							options.put("height", getHeight(elementIndex) + "px");
							options.put("left", getLeft(elementIndex) + "px");
							options.put("top", getTop(elementIndex) + "px");
							element.animate(options, "50", null);
							
							element.setStyle("opacity", getOpacity(elementIndex) + "");
							element.setStyle("z-index",getZIndex(elementIndex) + "");
						}
						else
						{
							element.setStyle("display", "none");
						}
						
						
					}
				}
				else
				{
					ClientProxy proxy = component.getParent().getParent().getDescendentByName("viewContainer");
					JMap options = new JMap();
					proxy.setStyle("z-index", "500");
					proxy.setStyle("background-color", "#DFE8F6");
					proxy.setStyle("display", "block");
					proxy.setStyle("position", "absolute");
					
					options.put("width", "500px");
					options.put("height", "400px");
					options.put("top", "50px");
					options.put("left", "150px");
					
					proxy.animate(options, "100", null);
					
				}
				
			}

			
			public boolean ServerAction(Container component,
					Map<String, String> requestParameters) throws UIException {
				EXImageFlow imageFlow = (EXImageFlow)component.getAncestorOfType(EXImageFlow.class);
				int index = Integer.parseInt(requestParameters.get("index"));
				
				int elementIndex = Integer.parseInt(component.getAttribute("element-index"));
				
				List<Container> items = imageFlow.getChild("dataContainer").getChildren();
				if(elementIndex != 0)
				{
					for(int i = 0; i < items.size(); i ++)
					{ 
						Container item = items.get(i);
						int currentElementIndex = Integer.parseInt(item.getAttribute("element-index"));
						if(elementIndex < 0)
						{
							item.setAttribute("element-index", (currentElementIndex+getMode(elementIndex)) + "");
						}
						else
						{
							item.setAttribute("element-index", (currentElementIndex-getMode(elementIndex)) + "");
						}
						requestParameters.put("element-" + item.getId(), item.getAttribute("element-index"));
						
					}
				}
				else
				{
					requestParameters.put("open", "true");
					EXPanel viewContainer = (EXPanel)imageFlow.getChild("viewContainer");
					if(viewContainer == null)
					{
						//imageFlow.addChild(new EXPanel())
						viewContainer= new EXPanel("viewContainer");
						imageFlow.addChild(viewContainer);
						//viewContainer.setDisplay(false);
					}
					viewContainer.setWidth(Dimension.parse("300px"));
					viewContainer.setHeight(Dimension.parse("300px"));
					
					
					
					Container comp = imageFlow.model_.getComponentAt(index, imageFlow);
					comp.setStyle("top", "0px");
					comp.setStyle("left", "0px");
					viewContainer.setBody(comp);
					viewContainer.setTitle("Open file");
					viewContainer.setShowCloseButton(true);
					imageFlow.getChild("viewContainer").setDisplay(true);

					
					
				}
				
				requestParameters.put("mean", imageFlow.mean + "");
				return true;
				
			}
			
		};
		
		return event;
	}

	public int getMean() {
		return mean;
	}

	public void setMean(int mean) {
		this.mean = mean;
	}

	public NavigationFlowFlowModel getModel() {
		return model_;
	}

	public void setModel(NavigationFlowFlowModel model_) {
		this.model_ = model_;
	}
	
	
	

}
