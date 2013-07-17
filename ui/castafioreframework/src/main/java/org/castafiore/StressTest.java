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
 package org.castafiore;

import java.util.Map;
import java.util.Random;

import org.castafiore.ui.Container;
import org.castafiore.ui.DescriptibleApplication;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.button.EXButton;

public class StressTest extends EXApplication implements DescriptibleApplication {
	
	private final static String[] colors = new String[]{"aqua", "black", "blue", "fuchsia", "gray", "green", "lime", "maroon", "navy", "olive", "purple", "red", "silver", "teal", "white", "yellow"};

	public StressTest() {
		super("stress");
		
		int amount = 10;
		EXGrid grid = new EXGrid("grid", amount, amount);
		
		for(int i = 0; i < amount; i ++)
		{
			for(int j = 0; j < amount; j ++)
			{
				EXContainer div = new EXContainer("", "div");
				div.setStyle("width", "15px");
				div.setStyle("height", "15px");
				div.setStyle("background-color", "blue");
				grid.getCell(i, j).addChild(div);
			}
		}
		addChild(grid);
		//EXButton button = new EXButton("", "Add 10");
		//addChild(button);
		/*button.addEvent(new Event(){

			public void ClientAction(ClientProxy container) {
				//container.mask();
				container.makeServerRequest(this);
				
			}

			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				 EXGrid grid = (EXGrid)container.getParent().getDescendentByName("grid");
				 int cols = grid.getColumns();
				//container.getParent().getChildren().remove(grid);
				 //grid.remove();
				 
				 grid.addRow();
				 int rows = grid.getRows() - 1;
				 for(int j = 0; j < cols; j ++)
				{
					EXContainer div = new EXContainer("", "div");
					div.setStyle("width", "15px");
					div.setStyle("height", "15px");
					div.setStyle("background-color", "blue");
					grid.getCell(j, rows).addChild(div);
				}
				 
				 grid.addColumn();
				 
				 for(int i = 0;  i < grid.getRows(); i ++)
				 {
					 EXContainer div = new EXContainer("", "div");
					div.setStyle("width", "15px");
					div.setStyle("height", "15px");
					div.setStyle("background-color", "blue");
					grid.getCell(grid.getColumns() - 1, i).addChild(div);
				 }
				 ((EXButton)container).setValue(cols + 1 + " ");
				 return true;
				 
				 
				 
			}

			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub
				container.makeServerRequest(this);
			}
			
		}, Event.CLICK);*/
		
		//EXButton button2 = new EXButton("", "Simple");
		//addChild(button2);
		
		addEvent(new Event(){

			public void ClientAction(ClientProxy container) {
				container.setTimeout(container.clone().makeServerRequest(this), 400);
				//container.makeServerRequest(this);
			}

			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				EXGrid grid = (EXGrid)container.getDescendentByName("grid");
				int cols = grid.getColumns();
				
				if(cols < 50){

					grid.addRow();
					int rows = grid.getRows() - 1;
					for(int j = 0; j < cols; j ++)
					{
						EXContainer div = new EXContainer("", "div");
						div.setStyle("width", "15px");
						div.setStyle("height", "15px");
						div.setStyle("background-color", "blue");
						grid.getCell(j, rows).addChild(div);
					}
					 
					 grid.addColumn();
					 
					 for(int i = 0;  i < grid.getRows(); i ++)
					 {
						 EXContainer div = new EXContainer("", "div");
						div.setStyle("width", "15px");
						div.setStyle("height", "15px");
						div.setStyle("background-color", "blue");
						grid.getCell(grid.getColumns() - 1, i).addChild(div);
					 }
				}
				 //((EXButton)container).setValue(cols + 1 + " ");
				
				Random rand = new Random();
				
				
				
				
				for(int i = 0; i < grid.getColumns(); i ++)
				{
					for(int j = 0; j < grid.getColumns(); j ++)
					{
						
						int r = rand.nextInt(colors.length);
						
						
						grid.getCell(i, j).getChildByIndex(0).setStyle("background-color",colors[r] );
						//grid.getCell(i, j).getChildByIndex(0).setRendered(false);
					}
				}
				return true;
			}

			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				container.setTimeout(container.clone().makeServerRequest(this), 4000);
				//container.makeServerRequest(this);
			}
			
		}, Event.READY);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescription() {
		return "Application for performing stress tests";
	}

}
