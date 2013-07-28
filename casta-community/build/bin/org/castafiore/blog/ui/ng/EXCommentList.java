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

import java.util.Map;

import org.castafiore.blog.BlogPost;
import org.castafiore.blog.BlogService;
import org.castafiore.blog.ui.BlogApplication;
import org.castafiore.blog.ui.ng.form.EXNewCommentForm;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.types.Comment;

public class EXCommentList extends EXContainer {

	private int postId;
	
	public EXCommentList(String name) {
		super(name, "div");
		addClass("commentList");
		
		EXContainer commentFormContainer = new EXContainer("commentFormContainer", "div");
		addChild(commentFormContainer);
		
		EXContainer title = new EXContainer("title", "h3");
		addChild(title);
		title.addClass("commentTitle");
		
		EXContainer commentList = new EXContainer("commentList", "ol");
		commentList.setStyle("list-style", "decimal");
		commentList.addClass("commentlist");
		addChild(commentList);
		
	}
	
	public int getPostId() {
		return postId;
	}

	
	
	public void setPostId(int postId) {
		this.postId = postId;
	}

	
	
	
	public void setPost(BlogPost post){
//		if(post != null){
//			this.postId = post.getId();
//			List<BlogComment> iter = post.getComments();
//			
//			
//			String title = iter.size() + " Responses for " + post.getTitle();
//			getChild("title").setText(title);
//			
//			Container commentList = getChild("commentList");
//			commentList.getChildren().clear();
//			commentList.setRendered(false);
//			int counter = 0;
//			for(BlogComment comment : iter){
//				
//				EXBlogComment uiComment = new EXBlogComment("", comment.getId());
//				uiComment.setComment(comment.getSummary());
//				uiComment.setDate(comment.getDateCreated());
//				uiComment.setUser(comment.getOwner());
//				
//				if((counter % 2) == 0 ){
//					uiComment.setStyle("background-color", "beige");
//				}
//				//commentList.addChild(li);
//				
//				commentList.addChild(uiComment);
//				counter++;
//			}
//		}
	}
	
	public void showAddCommentForm(){
		EXNewCommentForm form = getDescendentOfType(EXNewCommentForm.class);
		if(form == null){
			form = new EXNewCommentForm("");
			getChild("commentFormContainer").addChild(form);
		}
		form.clear();
		form.setDisplay(true);
		
	}
	
	public void hideCommentForm(){
		EXNewCommentForm form = getDescendentOfType(EXNewCommentForm.class);
		if(form != null)
		{
			form.setDisplay(false);
			form.clear();
		}
	}
	
	
	public void saveComment(){
//		EXNewCommentForm form = getDescendentOfType(EXNewCommentForm.class);
//		if(form != null)
//		{
//			Comment comment = form.getComment();
//			if(comment != null){
//				BlogService blogService =  ((BlogApplication)getRoot()).getBlogService();
//				blogService.addComment(comment, postId);
//				form.clear();
//				form.setDisplay(false);
//				Container commentList = getChild("commentList");
//				EXBlogComment uiComment = new EXBlogComment("", comment.getId());
//				uiComment.setComment(comment.getSummary());
//				uiComment.setDate(comment.getDateCreated());
//				uiComment.setUser(comment.getOwner());
//				
//				if((commentList.getChildren().size() % 2) == 0 ){
//					uiComment.setStyle("background-color", "beige");
//				}
//				//commentList.addChild(li);
//				
//				commentList.addChildAt(uiComment, 0);
//				commentList.setRendered(false);
//			}
//			
//		}
	}
	
	public final static Event DiscardPostEvent = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this, "Do you really want to discard this comment?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXCommentList.class).hideCommentForm();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			
			
		}
		
	};
	
	
	public final static Event SavePostEvent = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this, "Do you really want to save this comment?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXCommentList.class).saveComment();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			
		}
		
	};
	
	public final static Event showCommentFormEvent = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXPost.class).getDescendentOfType(EXCommentList.class).showAddCommentForm();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	

}
