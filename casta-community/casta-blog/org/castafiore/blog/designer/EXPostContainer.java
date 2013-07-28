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
 package org.castafiore.blog.designer;


import java.util.List;

import org.castafiore.blog.BlogPost;
import org.castafiore.designer.designable.datarepeater.EXDataContainer;
import org.castafiore.groovy.EXGroovyContainer;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Comment;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;

public class EXPostContainer extends EXDataContainer implements PostContainer {

	public EXPostContainer(String name) {
		super(name);
		EXContainer title = new EXContainer("title", "a");
		title.setAttribute("href", "#");
		addChild(title);
		addChild(ComponentUtil.getContainer("date", "span"));
		addChild(ComponentUtil.getContainer("summary", "div"));
		addChild(ComponentUtil.getContainer("text", "p"));
		getChild("text").setDisplay(false);
		
		Container comments = new EXContainer("comments", "div");
		comments.setDisplay(false);
		addChild(comments);
		setAttribute("commentstemplate", ResourceUtil.getDownloadURL("ecm", "/root/portals/opposed/templates/comments.xhtml"));
		//comments.setTemplateLocation(ResourceUtil.getDownloadURL("ecm", "/root/portals/opposed/templates/comment.xhtml"));
		Container vfs = ComponentUtil.getContainer("viewFullStory", "a", "View Full Story", "links");
		vfs.setAttribute("method", "viewFullStory");
		vfs.setAttribute("ancestor", getClass().getName());
		vfs.setAttribute("href", "#");
		vfs.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		addChild(vfs);
		Container vc = ComponentUtil.getContainer("viewComments", "a", "View comments", "comments");
		vc.setAttribute("method", "viewComments");
		vc.setAttribute("ancestor", getClass().getName());
		vc.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		vc.setAttribute("href", "#");
		addChild(vc);
		
		
	}
	
	public void viewComments(){
		BlogPost post = getBlogPost();
		if(post != null){
			Container comments = getChild("comments");
			comments.getChildren().clear();
			comments.setRendered(false);
			
			RepositoryService service = SpringUtil.getRepositoryService();
			QueryParameters params = new QueryParameters().setEntity(Comment.class).addRestriction(Restrictions.eq("parent.absolutePath", post.getAbsolutePath()));
			List<File> lst = service.executeQuery(params, Util.getRemoteUser());
			for(File o : lst){
				Comment comment = (Comment)o;
				EXGroovyContainer gv = new EXGroovyContainer("", getAttribute("commentstemplate"));
				EXContainer title = new EXContainer("title", "a");
				title.setAttribute("href", "#");
				title.setText(comment.getTitle());
				title.addClass("comment-title");
				gv.addChild(title);
				gv.addChild(ComponentUtil.getContainer("date", "span", comment.getDateCreated().getTime().toString(), "comment-date"));
				gv.addChild(ComponentUtil.getContainer("summary", "div", comment.getSummary(), "comment-summary"));
				gv.addChild(ComponentUtil.getContainer("owner", "a", comment.getOwner(), "comment-owner"));
				
				
				comments.addChild(gv);
			}
			comments.setRendered(false);
			comments.setDisplay(true);
		}
	}
	
	public void viewFullStory(){
		getChild("text").setDisplay(true);
	}

	
	public BlogPost getBlogPost() {
		try{
			return (BlogPost)super.getData().get(0);
		}catch(Exception e){
			return null;
		}
	}

	public void setBlogPost(BlogPost post) {
		super.getData().clear();
		super.getData().add(post);
		refresh();
		
	}

	@Override
	public void refresh() {
		super.refresh();
		BlogPost post = getBlogPost();
		if(post != null){
			getChild("title").setText(post.getTitle());
			getChild("date").setText(post.getDateCreated().getTime().toString());
			getChild("summary").setText(post.getSummary());
			getChild("text").setText(post.getDetail());
		}
	}

	


	

}
