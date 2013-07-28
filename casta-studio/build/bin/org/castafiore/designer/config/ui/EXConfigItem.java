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
 package org.castafiore.designer.config.ui;

import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.events.CopyEvent;
import org.castafiore.designer.events.DeleteItemEvent;
import org.castafiore.designer.events.EditPageEvent;
import org.castafiore.designer.events.MoveDownEvent;
import org.castafiore.designer.events.MoveUpEvent;
import org.castafiore.designer.events.PasteEvent;
import org.castafiore.designer.events.PopulateEvent;
import org.castafiore.designer.events.ShowConfigPanel;
import org.castafiore.designer.events.ShowScriptEditor;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.contextmenu.ContextMenuAble;
import org.castafiore.ui.ex.contextmenu.ContextMenuModel;
import org.castafiore.ui.scripting.TemplateComponent;

//public class EXConfigItem extends EXContainer implements ContextMenuAble {
public class EXConfigItem extends EXContainer {

	public Container container = null;
	
	public EXConfigItem(Container c) {
		super(c.getAttribute("__oid")+ "_c-item", "div");
		setText(c.getName());
		this.container = c;
		addEvent(new ShowConfigPanel(), Event.DOUBLE_CLICK);
		
		
		
	}
	
	public Container getContainer(){
		return container;
	}

	public ContextMenuModel getContextMenuModel() {
		return new ContextMenuModelImpl();
	}
	
	public  class ContextMenuModelImpl implements ContextMenuModel{
		
		private   String[] PORTAL_ITEM = new String[]{"Scripts",  "Configure", "Paste"};
		private String [] PORTAL_ITEMS_ICON = new String[]{"script.png",  "application_form_edit.png", "paste_plain.png"};
		private Event[] PORTAL_EVENT = new Event[]{
				new ShowScriptEditor(),
				new ShowConfigPanel()	,
				new PasteEvent()
		};
		
		private   String[] PAGES_ITEM = new String[]{"Switch to page", "Close page", "Configure", "Copy", "Paste"};
		
		private String [] PAGE_ITEMS_ICON = new String[]{"application_side_expand.png", "cancel.png", "application_form_edit.png", "copy16.jpg", "paste_plain.png"};
		
		private Event[] PAGES_EVENT = new Event[]{
				new EditPageEvent(),
				new DeleteItemEvent(),
				new ShowConfigPanel(),	
				new CopyEvent(),
				new PasteEvent()
		};
		
		private String[] NORM_COMPONENTS = new String[]{"Move up", "Move down", "Delete item", "Configure", "Copy", "Paste"};
		
		private String[] NORM_COMPONENTS_ICONS = new String[]{"arrow_up.png", "arrow_down.png", "delete.png", "application_form_edit.png", "copy16.jpg", "paste_plain.png"};
		
		private Event[] NORM_COMPONENTS_EVENTS = new Event[]{
				new MoveUpEvent(),
				new MoveDownEvent(),
				new DeleteItemEvent(),
				new ShowConfigPanel(),	
				new CopyEvent(),
				new PasteEvent()	
		};
		
		
private String[] TEMPLATE_COMPONENTS = new String[]{"Move up", "Move down", "Delete item", "Configure", "Copy", "Paste", "Populate"};
		
		private String[] TEMPLATE_COMPONENTS_ICONS = new String[]{"arrow_up.png", "arrow_down.png", "delete.png", "application_form_edit.png", "copy16.jpg", "paste_plain.png", "table_multiple.png"};
		
		private Event[] TEMPLATE_COMPONENTS_EVENTS = new Event[]{
				new MoveUpEvent(),
				new MoveDownEvent(),
				new DeleteItemEvent(),
				new ShowConfigPanel(),	
				new CopyEvent(),
				new PasteEvent(),
				new PopulateEvent()
		};
		

		public Event getEventAt(int index) {
			
			if(isPage()){
				return PAGES_EVENT[index];
			}else if(isPortal()){
				return PORTAL_EVENT[index];
			}else if(isTemplate()){
				return TEMPLATE_COMPONENTS_EVENTS[index];
			}
			else{
				return NORM_COMPONENTS_EVENTS[index];
			}
		}

		public String getIconSource(int index) {
			if(isPage()){
				
				return "icons/" + PAGE_ITEMS_ICON[index];
			}else if(isPortal()){
				return "icons/" + PORTAL_ITEMS_ICON[index];
			}else if(isTemplate()){
				return "icons/" + TEMPLATE_COMPONENTS_ICONS[index];
			}else{
				return "icons/" + NORM_COMPONENTS_ICONS[index];
			}
			
		}

		public String getTitle(int index) {
			
			if(isPage()){
				return PAGES_ITEM[index];
			}else if(isPortal()){
				return PORTAL_ITEM[index];
			}else if(isTemplate()){
				return TEMPLATE_COMPONENTS[index];
			}else{
				return NORM_COMPONENTS[index];
			}
		}
		
		private boolean isPage(){
			return container.getParent() instanceof PageContainer;
		}

		public int size() {
			if(isPage()){
				return PAGES_ITEM.length;
			}else if(isPortal()){
				return PORTAL_ITEM.length;
			}else if(isTemplate()){
				return TEMPLATE_COMPONENTS.length;
			}else{
				return NORM_COMPONENTS.length;
			}
		}
		
		private boolean isPortal(){
			return container instanceof PortalContainer;
		}
		
		private boolean isTemplate(){
			return (container instanceof TemplateComponent);
		}
		
	}
}
