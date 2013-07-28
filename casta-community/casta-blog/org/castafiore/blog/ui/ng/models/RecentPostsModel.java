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
import org.castafiore.blog.ui.ng.EXBlogList;
import org.castafiore.blog.ui.ng.EXBlogWindow;
import org.castafiore.blog.ui.ng.EXPostList;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.wfs.Util;

public class RecentPostsModel extends AbstractBlogModel {

	private List<BlogPost> recentPosts;

	public RecentPostsModel(List<BlogPost> recentPosts) {
		super();
		this.recentPosts = recentPosts;
	}

	public Event getEventAt(int index) {
		return new ShowRecentPostEvent(recentPosts.get(index));
	}

	public String getTextAt(int index) {
		return recentPosts.get(index).getTitle();
	}

	public int size() {
		return recentPosts.size();
	}

	public class ShowRecentPostEvent implements Event {

		private BlogPost post;

		public ShowRecentPostEvent(BlogPost post) {
			super();
			this.post = post;
		}

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);

		}

		public boolean ServerAction(Container container, Map<String, String> request)
				throws UIException {
			List<BlogPost> posts = new ArrayList<BlogPost>(1);
			
			//posts.add((BlogPost)SpringUtil.getRepositoryService().getFileById(post.getId(), Util.getRemoteUser()));
			
			EXBlogWindow window = container.getAncestorOfType(EXBlogWindow.class);
			
			
			Container body =	 window.getBody();
			if(body instanceof EXBlogList){
				((EXBlogList)body).setPostList(posts);
				((EXBlogList)body).setTitle(post.getTitle());
				((EXBlogList)body).unAllowAddPost();
			}else{
				EXBlogList list = new EXBlogList("list");
				list.setPostList(posts);
				list.setTitle(post.getTitle());
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
