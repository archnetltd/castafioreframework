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
 package org.castafiore.blog.ui.ng.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.castafiore.blog.BlogPost;
import org.castafiore.blog.BlogService;
import org.castafiore.blog.ui.BlogApplication;
import org.castafiore.blog.ui.ng.EXBlogList;
import org.castafiore.blog.ui.ng.EXBlogWindow;
import org.castafiore.blog.ui.ng.EXPostList;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.utils.DateUtil;

public class BlogArchiveModel extends AbstractBlogModel {
	
	private final static SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");

	public Event getEventAt(int index) {
		return new ShowBlogMonthEvent(index);
	}

	public String getTextAt(int index) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, index*-1);
		String text = format.format(cal.getTime()) ;
		return text;
	}

	public int size() {
		return 15;
	}
	
	public  class ShowBlogMonthEvent implements Event{

		private int index = 0;
		
		
		
		public ShowBlogMonthEvent(int index) {
			super();
			this.index = index;
		}

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this, "Do you really want to ea?");
			
		}

		public boolean ServerAction(Container container, Map<String, String> request)
				throws UIException {
			//int month = Integer.parseInt(container.getAttribute("month"));
			//int year = Integer.parseInt(container.getAttribute("year"));
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, index*-1);
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			
			Calendar[] dates = DateUtil.getStartAndEndOfMonth(month, year);
			BlogService service = ((BlogApplication)container.getRoot()).getBlogService();
			List<BlogPost> posts = service.getPosts(dates[0], dates[1]);
			//container.getAncestorOfType(BlogApplication.class).getDescendentOfType(BlogPostContainer.class).setEntries(posts, null, null);
			//container.getAncestorOfType(BlogApplication.class).getDescendentOfType(BlogPostContainer.class).setPageTitle("Archives of " + container.getText());
			
			EXBlogWindow window = container.getAncestorOfType(EXBlogWindow.class);
			
			
			Container body =	 window.getBody();
			if(body instanceof EXBlogList){
				((EXBlogList)body).setPostList(posts);
				((EXBlogList)body).setTitle("Posts of " + new SimpleDateFormat("MMMM yyyy").format(cal.getTime()));
				((EXBlogList)body).unAllowAddPost();
			}else{
				EXBlogList list = new EXBlogList("list");
				list.setPostList(posts);
				list.setTitle("Posts of " + new SimpleDateFormat("MMMM yyyy").format(cal.getTime()));
				list.unAllowAddPost();
				window.setBody(list);
			}
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}

	}

}
