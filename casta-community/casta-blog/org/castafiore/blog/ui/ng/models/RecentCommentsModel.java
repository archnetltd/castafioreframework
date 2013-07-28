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
import org.castafiore.blog.ui.ng.EXBlogList;
import org.castafiore.blog.ui.ng.EXBlogWindow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Comment;

public class RecentCommentsModel extends AbstractBlogModel {

	private List<Comment> comments = new ArrayList<Comment>();

	public RecentCommentsModel(List<Comment> comments) {
		super();
		for (Comment comment : comments) {
			if (!(comment instanceof BlogPost)) {
				this.comments.add(comment);
			}
		}
	}

	public Event getEventAt(int index) {
		return new ShowPostEvent(comments.get(index));
	}

	public String getTextAt(int index) {
		return comments.get(index).getTitle();
	}

	public int size() {
		return comments.size();
	}

	public class ShowPostEvent implements Event {

		private Comment comment;

		

		public ShowPostEvent(Comment comment) {
			super();
			this.comment = comment;
		}

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);

		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			
			List<BlogPost> posts = new ArrayList<BlogPost>(1);
			BlogPost post = (BlogPost)comment.getParent();
			//posts.add((BlogPost)SpringUtil.getRepositoryService().getFileById(post.getId(), Util.getRemoteUser()));
			//posts.add(post);
			
			EXBlogWindow window = container.getAncestorOfType(EXBlogWindow.class);
			
			
			Container body =	 window.getBody();
			if(body instanceof EXBlogList){
				((EXBlogList)body).setPostList(posts);
				((EXBlogList)body).setTitle(post.getTitle());
				((EXBlogList)body).unAllowAddPost();
			}else{
				EXBlogList list = new EXBlogList("list");
				list.setPostList(posts);
				list.unAllowAddPost();
				list.setTitle(post.getTitle());
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
