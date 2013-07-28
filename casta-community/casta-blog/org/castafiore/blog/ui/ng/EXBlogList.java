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

import java.util.List;
import java.util.Map;

import org.castafiore.blog.BlogPost;
import org.castafiore.blog.ui.BlogApplication;
import org.castafiore.blog.ui.ng.form.EXNewPostForm;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;

public class EXBlogList extends EXContainer {

	public EXBlogList(String name) {
		super(name, "div");
		
		EXContainer header = new EXContainer("header", "div");
		addChild(header);
		
		EXContainer title = new EXContainer("title", "h2");
		header.addChild(title);
		title.setStyle("padding-left", "30px");
		title.setStyle("font-size", "150%");
		title.setStyle("padding-top", "17px");
		title.setStyle("padding-bottom", "5px");
		title.setStyle("font-weight", "bold");
		
		EXPostList list = new EXPostList("postList");
		addChild(list);

	}
	
	public void allowAddPost(String category)throws Exception{
		BlogApplication root = getRoot();
		if(root.getSecurityService().isUserAllowed("moderator:blogUsers", Util.getRemoteUser())){
		
			if(getDescendentByName("AddPost") == null){
				EXContainer addPost = new EXContainer("AddPost", "div");
				addPost.addEvent(new ShowAddPostEvent(), Event.CLICK);
				addPost.addClass("addPost");
				addPost.setText("+ Make a post");
				getChild("header").addChild(addPost);
			}
			getDescendentByName("AddPost").setDisplay(true);
			getDescendentByName("AddPost").setAttribute("category", category);
			
			if(getDescendentOfType(EXNewPostForm.class) == null){
				EXNewPostForm postForm = new EXNewPostForm("postForm");
				addChild(postForm);
				postForm.setDisplay(false);
			}
		}else{
			unAllowAddPost();
		}
	}
	
	public void unAllowAddPost(){
		Container container = getDescendentByName("AddPost");
		if(container != null){
			container.setDisplay(false);
		}
	}
	
	public void setPostList(List<BlogPost> posts){
		getDescendentOfType(EXPostList.class).setPostList(posts);
	}
	
	public void addPostForDisplay(BlogPost post){
		EXPostList postList = getDescendentOfType(EXPostList.class);
		postList.setRendered(false);
		postList.addPostForDisplay(post);
	}
	
	public void setTitle(String title){
		getDescendentByName("title").setText(title);
		
	}
	
	public static class ShowAddPostEvent implements Event{

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			String category = container.getAttribute("category");
			EXNewPostForm form = container.getAncestorOfType(EXBlogList.class).getDescendentOfType(EXNewPostForm.class);
			form.setCategory(category);
			form.setDisplay(true);
			container.getAncestorOfType(EXBlogList.class).getDescendentOfType(EXPostList.class).setDisplay(false);
			return true;
			//form.set
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	

}
