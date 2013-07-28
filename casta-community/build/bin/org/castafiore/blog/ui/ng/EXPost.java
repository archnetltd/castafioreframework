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

import java.text.SimpleDateFormat;
import java.util.Map;

import org.castafiore.blog.BlogPost;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;

public class EXPost extends EXXHTMLFragment {
	
	

	public EXPost(String name) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/blog/ui/ng/resources/EXPost.xhtml"));
		addClass("ui-widget");
		EXContainer date = new EXContainer("date", "span");
		addChild(date);
		//date.addClass("date");
		//EXContainer yearMonth = new EXContainer("yearMonth", "span");
		//date.addChild(yearMonth);
		
		//EXContainer dd = new EXContainer("dd", "div");
		//date.addChild(dd);
		
		
		EXContainer title = new EXContainer("title", "a");
		addChild(title);
		
		//EXContainer tags = new EXContainer("tags", "span");
		//addChild(tags);
		//tags.addClass("category");
		
		EXContainer comments = new EXContainer("comments", "a");
		comments.setAttribute("href", "#");
		comments.setAttribute("show", "true");
		addChild(comments);
		comments.addEvent(new showCommentEvent(), Event.CLICK);
		
		EXContainer addComment = new EXContainer("addComment", "a");
		addComment.setAttribute("href", "#");
		addComment.setText("Add comment");
		addComment.addEvent(EXCommentList.showCommentFormEvent, Event.CLICK);
		addChild(addComment);
		addComment.setDisplay(false);
		//comments.addClass("comments");
		
		EXContainer entry = new EXContainer("entry", "p");
		addChild(entry);
		//entry.addClass("entry");
		
	 	EXCommentList commentList = new EXCommentList("commentList");
	 	commentList.setDisplay(false);
		addChild(commentList);
		
		
	}
	
	public void setPost(BlogPost post){
		
//		if(post != null){
//			setAttribute("post-id", post.getId() + "");
//			getChild("title").setText(post.getTitle());
//			
//			int posts = post.getCommentsCount();
//			
//			getChild("comments").setText(posts + " comments >>");
//			getChild("comments").setAttribute("show", "true");
//		
//			//getChild("date").getChild("yearMonth").setText(new SimpleDateFormat("yyyy MM").format(post.getDateCreated().getTime()));
//			
//			//getChild("date").getChild("dd").setText(new SimpleDateFormat("dd").format(post.getDateCreated().getTime()));
//			getChild("date").setText(new SimpleDateFormat("MMM dd yyyy").format(post.getDateCreated().getTime()));
//			
//			/*Container tags = getChild("tags");
//			for(int i = 0; i < 5; i++){
//				EXContainer tag = new EXContainer("", "a");
//				tag.setText("tag-" + i);
//				tags.addChild(tag);
//			}*/
//			
//			getChild("entry").setText(post.getSummary());
//		}
	}
	
	public void showComments(){
//		BlogPost post =  (BlogPost)BaseSpringUtil.getBeanOfType(RepositoryService.class).getFileById(Integer.parseInt(getAttribute("post-id")), Util.getRemoteUser());
//		getDescendentOfType(EXCommentList.class).setPost(post);
//		getDescendentOfType(EXCommentList.class).setDisplay(true);
//		getDescendentByName("addComment").setDisplay(true);
	}
	
	public void hideComment(){
		getDescendentOfType(EXCommentList.class).setDisplay(false);
		getDescendentByName("addComment").setDisplay(false);
		
	}
	
	public static class showCommentEvent implements Event{

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			String show = container.getAttribute("show");
			if(show.equalsIgnoreCase("true")){
				container.getAncestorOfType(EXPost.class).showComments();
				container.setAttribute("show", "false");
				container.setText(container.getText().replace(">>", "<<"));
			}else{
				container.getAncestorOfType(EXPost.class).hideComment();
				container.setAttribute("show", "true");
				container.setText(container.getText().replace("<<", ">>"));
			}
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	public static class hdieCommentEvent implements Event{

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXPost.class).hideComment();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
		}
		
	}
}
