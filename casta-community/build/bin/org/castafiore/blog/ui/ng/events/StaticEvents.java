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
 package org.castafiore.blog.ui.ng.events;

import java.util.List;
import java.util.Map;

import org.castafiore.blog.BlogPost;
import org.castafiore.blog.ui.BlogApplication;
import org.castafiore.blog.ui.ng.EXBlogList;
import org.castafiore.blog.ui.ng.EXBlogWindow;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public interface StaticEvents {
	
	
	/**
	 * event to go to home page
	 */
	public final static Event HOME_PAGE_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container, Map<String, String> request)
				throws UIException {
			BlogApplication app =  (BlogApplication)container.getRoot();
			
			List<BlogPost> recPosts = app.getBlogService().getRecentPosts(12);
			
			EXBlogList list = new EXBlogList("");
			list.setTitle("Recent posts");
			list.unAllowAddPost();
			list.setPostList(recPosts);
			container.getAncestorOfType(EXBlogWindow.class).setBody(list);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	
	/**
	 * event to sign out
	 */
	public final static Event SIGN_OUT_EVENT = new Event(){
		
		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this, "Do you really want to sign out?");
			
		}

		public boolean ServerAction(Container container, Map<String, String> request)
				throws UIException {
			BlogApplication app =  (BlogApplication)container.getRoot();
			
			app.signOut();
			return true;
			
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	/**
	 * event that makes browser scollable
	 */
	public final static Event AJUST_BROWSER_EVENT = new Event(){
		public void ClientAction(ClientProxy container) {
			container.mergeCommand(new ClientProxy("html, body").setStyle("overflow", "auto"));
			container.mergeCommand(new ClientProxy("body")
				.setStyle("color", "#333333")
				.setStyle("font-family", "Verdana, Arial, Helvetica, sans-serif")
				.setStyle("font-size", "70%")
				.setStyle("font-size-adjust", "none")
				.setStyle("font-style", "normal")
				.setStyle("font-variant", "normal")
				.setStyle("font-weight", "normal")
				.setStyle("line-height", "170%")
				.setStyle("background-color", "steelBlue")
			);
			
			
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			return false;
		}

		public void Success(ClientProxy container,
				Map<String, String> request) throws UIException {
			
			
		}
	};

}
