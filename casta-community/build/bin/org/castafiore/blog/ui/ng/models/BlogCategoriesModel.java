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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.blog.BlogPost;
import org.castafiore.blog.BlogService;
import org.castafiore.blog.ui.BlogApplication;
import org.castafiore.blog.ui.ng.EXBlogList;
import org.castafiore.blog.ui.ng.EXBlogWindow;
import org.castafiore.blog.ui.ng.ListModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class BlogCategoriesModel extends AbstractBlogModel implements ListModel {
	private String directory;
	
	private List<String> categories = new ArrayList<String>();
	
	public BlogCategoriesModel(String directory, BlogService service){
		this.directory = directory;
		categories =service.getCategories(directory);
	}

	public Event getEventAt(int index) {
		return new ShowPostByCategoryEvent(directory,categories.get(index));
	}

	public String getTextAt(int index) {
		return categories.get(index);
	}

	public int size() {
		return categories.size();
	}
	
	
	public  class ShowPostByCategoryEvent implements Event{

		private String directory;
		
		private String category;
		
		
		
		
		
		

		public ShowPostByCategoryEvent(String directory, String category) {
			super();
			this.directory = directory;
			this.category = category;
		}

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container, Map<String, String> request)
				throws UIException {
			
			
			
			BlogService service =  ((BlogApplication)container.getRoot()).getBlogService();
			List<BlogPost> posts = service.getPosts(directory, category);
			//container.getAncestorOfType(BlogApplication.class).getDescendentOfType(BlogPostContainer.class).setEntries(posts, null, null);
			//container.getAncestorOfType(BlogApplication.class).getDescendentOfType(BlogPostContainer.class).setPageTitle("Archives of " + container.getText());
			
			EXBlogWindow window = container.getAncestorOfType(EXBlogWindow.class);
			
			try{
				Container body =	 window.getBody();
				if(body instanceof EXBlogList){
					((EXBlogList)body).setPostList(posts);
					((EXBlogList)body).setTitle(category);
					((EXBlogList)body).allowAddPost(category);
				}else{
					EXBlogList list = new EXBlogList("list");
					list.setPostList(posts);
					list.allowAddPost(category);
					list.setTitle(category);
					window.setBody(list);
				}
			}
			catch(Exception e){
				throw new UIException(e);
			}
				return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}

	}
	
	

}
