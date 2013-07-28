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

import java.util.Calendar;
import java.util.List;

import org.castafiore.wfs.types.Comment;

/**
 * 
 *This is the interface facade for the blog service
 * @author Kureem Rossaye
 *
 */
public interface BlogService {
	
	
	
	/**
	 * returns all directories in this blog
	 * @return
	 */
	public List<String> getDirectories();
	
	
	/**
	 * returns all categories for the specified directory;
	 * @param directory
	 * @return
	 */
	public List<String> getCategories(String directory);
	
	
	/**
	 * return the last numbers of post
	 * @param maximum number of posts to return
	 * @return
	 */
	public List<BlogPost> getRecentPosts(int amount);
	
	
	/**
	 * return all posts for the specified directory and specified category
	 * @param directory
	 * @param category
	 * @return
	 */
	public List<BlogPost> getPosts(String directory, String category);
	
	/**
	 * return  a maximum number of most  recent comments
	 * @param amount
	 * @return
	 */
	public List<Comment> getRecentComments(int amount);
	
	
	/**
	 * adds a post into the specified directory and specified category
	 * @param directory
	 * @param category
	 * @param post
	 */
	public void addPost(String directory, String category, BlogPost post);
	
	/**
	 * return all posts between the specified days
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<BlogPost> getPosts(Calendar startDate, Calendar endDate);
	
	/**
	 * full text search for a specified atring input
	 * @param input
	 * @return
	 */
	public List<BlogPost> searchBlog(String input);
	
	
	/**
	 * returns a unique post
	 * @param id
	 * @return
	 */
	public BlogPost getPost(int id);
	
	/**
	 * adds a comment into the specified blog
	 * @param comment
	 * @param postId
	 */
	public void addComment(Comment comment, int postId);
	
	

}
