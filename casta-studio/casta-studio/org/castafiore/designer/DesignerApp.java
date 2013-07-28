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
 package org.castafiore.designer;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.castafiore.designer.marshalling.DesignableDTO;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.form.button.EXButton;

public class DesignerApp extends EXApplication{

	public DesignerApp() { 
		super("designer");

		
		
		
		addChild(new EXDesigner());

		
	}
	
	public void reset(){
//		Container tor = getChild("toRemove");
//		if(tor != null){
//			tor.remove();
//		}
//		this.setRendered(false);
//		try{
//			//DesignableDTO dto = DesignableDTO.buildContainer();
//			
//			Container c = DesignableDTO.buildContainer(new FileInputStream(new File("/home/kureem/java/eXoProjects/casta-studio/WEB-INF/architecture.xml")));
//			c.addClass("architecture");
//			c.setName("toRemove");
//			
//			System.out.println(c);
//			addChild(c);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}

}
