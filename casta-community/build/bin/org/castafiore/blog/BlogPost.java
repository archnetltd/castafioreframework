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
 package org.castafiore.blog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;

import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Comment;

/**
 * 
 * This class represents a blog post.
 * It is a directory
 * The owner is used as the creator
 * the dateCreated is used as date created
 * the title is used as title
 * the comment is used as comment
 * 
 * This blog entry placed be placed inside a directory, whose name is actually the category of the blog
 * 
 * This directory may contain binary files which will be treated as attachements to the post
 * 
 * Furthermore, this Directory, can contain {@link BlogComment} which are comments to the blog
 * 
 * This blog post 
 * 
 * @author Kureem Rossaye
 *
 */
@Entity
public class BlogPost extends Comment{

	private int commentsCount = 0;
	
	public BlogPost(){
		super();
	}
	
	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}
	
	
	public void incrementCount(){
		commentsCount++;
	}
	

}
