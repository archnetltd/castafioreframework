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
 package org.castafiore.blog.ui.ng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXBlogWidget extends EXPanel {

	private int autoRefreshTimeOut = -1;

	private List<RefreshHandler> refreshHandlers = new ArrayList<RefreshHandler>(
			1);

	public EXBlogWidget(String name, String title) {
		super(name,title);
		// addStyleSheet(ResourceUtil.getDownloadURL("classpath",
		// "org/castafiore/blog/ui/ng/resources/EXBlogWidget.css"));
		//addClass("dbx-box");
		setWidth(Dimension.parse("190px"));
		setStyle("margin-top", "3px");
		setStyle("margin-bottom", "3px");
		//setStyle("margin-bottom", "7px");
		setDraggable(false);
		setShowCloseButton(false);

//		EXContainer handle = new EXContainer("handle", "h3");
//		handle.addClass("dbx-handle");
//
//		addChild(handle);
//
//		EXContainer uiTitle = new EXContainer("title", "span");
//		uiTitle.setText(title);
//		uiTitle.setStyle("line-height", "11px");
//		handle.addChild(uiTitle);
//
//		EXContainer closeButton = new EXContainer("closeButton", "a");
//		closeButton.setStyleClass("dbx-toggle dbx-toggle-open");
//		closeButton.setAttribute("href", "#");
//		closeButton.setStyle("cursor", "pointer");
//		handle.addChild(closeButton);
//		closeButton.addEvent(CLOSE_EVENT, Event.CLICK);
//
//		EXContainer content = new EXContainer("content", "div");
//		content.addClass("dbx-content");
//		addChild(content);
	}

	public void setAutoRefreshTimeOut(int timeout){
		this.autoRefreshTimeOut = timeout;
		setRendered(false);
		removeEvent(RefreshEvent.class, Event.READY);
		addEvent(new RefreshEvent(), Event.READY);
	}
	
//	public void setTitle(String title) {
//		getDescendentByName("title").setText(title);
//	}
//
//	public String getTitle() {
//		return getDescendentByName("title").getText();
//	}
//
//	public void setBody(Container body) {
//		getChild("content").getChildren().clear();
//		getChild("content").setRendered(false);
//		getChild("content").addChild(body);
//	}
//
//	public Container getBody() {
//		if (getChild("content").getChildren().size() > 0) {
//			return getChild("content").getChildren().get(0);
//		} else {
//			return null;
//		}
//	}

	public void addRefreshHandler(RefreshHandler handler) {
		refreshHandlers.add(handler);
	}

	public void clearRefreshHandlers() {
		refreshHandlers.clear();
	}

	public void fireRefreshHandlers() {
		for (RefreshHandler handler : refreshHandlers) {
			handler.doRefresh(this);
		}
		//setRendered(false);
	}

	public RefreshHandler removeRegreshHandler(int index) {
		return refreshHandlers.remove(index);
	}

	public static interface RefreshHandler {
		public void doRefresh(EXBlogWidget This);
	}

	public class RefreshEvent implements Event {

		public void ClientAction(ClientProxy container) {
			if(autoRefreshTimeOut > 0){
				container.setTimeout(container.clone().makeServerRequest(this),autoRefreshTimeOut);
			}

		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXBlogWidget.class)
					.fireRefreshHandlers();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			container.setTimeout(container.clone().makeServerRequest(this),autoRefreshTimeOut);

		}

	}

//	public static Event CLOSE_EVENT = new Event() {
//		public void ClientAction(ClientProxy container) {
//			container.makeServerRequest(this, "Do you really want to close this widget?");
//
//		}
//
//		public void Success(ClientProxy component,
//				Map<String, String> requestParameters) throws UIException {
//			component.mergeCommand(new ClientProxy("#"
//					+ requestParameters.get("dId")).fadeOut(100,
//					new ClientProxy("#" + requestParameters.get("dId"))
//							.remove()));
//		}
//
//		public boolean ServerAction(Container component,
//				Map<String, String> requestParameters) throws UIException {
//			EXBlogWidget dialog = component
//					.getAncestorOfType(EXBlogWidget.class);
//			requestParameters.put("dId", dialog.getId());
//			dialog.remove();
//
//			return true;
//		}
//	};

}
