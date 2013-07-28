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

import org.castafiore.blog.BlogPost;
import org.castafiore.ui.ex.EXContainer;

public class EXPostList extends EXContainer {

	public EXPostList(String name) {
		super(name, "div");
	}
	
	
	public void setPostList(List<BlogPost> posts){
		this.getChildren().clear();
		setRendered(false);
		if(posts != null){
			for(BlogPost post : posts){
				EXPost uiPost = new EXPost("");
				uiPost.setPost(post);
				addChild(uiPost);
			}
		}
	}
	
	
	public void addPostForDisplay(BlogPost post){
		EXPost exPost = new EXPost("");
		exPost.setPost(post);
		addChildAt(exPost, 0);
	}
	
	

}
