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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Comment;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class BlogServiceImpl implements BlogService{
	
	private RepositoryService repositoryService;
	
	private String rootBlogDir = "/root/users/ "+Util.getRemoteUser()+ "/blog";

	public List<String> getDirectories()throws InvalidBlogDirectoryStructureException {
		
		Directory rootDir = getRepositoryService().getDirectory(getRootBlogDir(), Util.getRemoteUser());
		List<String> result = new ArrayList<String>();
		if(rootDir != null){
			FileIterator iter = rootDir.getFiles(Directory.class);
			while(iter.hasNext()){
				result.add(iter.next().getName());
			}
			return result;
		}
		else{
			throw new InvalidBlogDirectoryStructureException("The directory " + getRootBlogDir() + " does not exist");
		}
		
		//return null;
	}
	
	public List<String> getCategories(String directory){
		Directory rootDir = getRepositoryService().getDirectory(getRootBlogDir() + "/" + directory, Util.getRemoteUser());
		List<String> result = new ArrayList<String>();
		if(rootDir != null){
			FileIterator iter = rootDir.getFiles(Directory.class);
			while(iter.hasNext()){
				result.add(iter.next().getName());
			}
			return result;
		}
		else{
			throw new InvalidBlogDirectoryStructureException("The directory " + getRootBlogDir() + "/" +directory + " does not exist");
		}
	}

	

	public List<BlogPost> getRecentPosts(int amount) {
		//String hql = "from " + BlogPost.class.getName() + " order by dateCreated desc";
		QueryParameters params = new QueryParameters().setEntity(Comment.class).addOrder(Order.desc("dateCreated")).setMaxResults(amount);
		
		List list = repositoryService.executeQuery(params, Util.getRemoteUser());
		return  list;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public String getRootBlogDir() {
		return rootBlogDir;
	}

	public void setRootBlogDir(String rootBlogDir) {
		this.rootBlogDir = rootBlogDir;
	}
	
	public List<BlogPost> getPosts(String directory, String category){
		String dir = getRootBlogDir() + "/" + directory + "/" + category;
		
		Directory oDir = repositoryService.getDirectory(dir, Util.getRemoteUser());
		
		FileIterator<BlogPost> posts = oDir.getFiles(BlogPost.class);
		List<BlogPost> result = new ArrayList<BlogPost>();
		while(posts.hasNext()){
			result.add((BlogPost)posts.next());
		}
		Collections.reverse(result);
		return result;
	}
	
	public List<Comment> getRecentComments(int amount){
		QueryParameters params = new QueryParameters().setEntity(Comment.class).addOrder(Order.desc("dateCreated")).setMaxResults(amount);
		List list = repositoryService.executeQuery(params, Util.getRemoteUser());
		return list;
	}

	public List<BlogPost> getPosts(Calendar startDate, Calendar endDate){
		QueryParameters params = new QueryParameters().setEntity(BlogPost.class).addOrder(Order.desc("dateCreated")).addRestriction(Restrictions.between("dateCreated", startDate, endDate));
		//String hql = "from " + BlogPost.class.getName() + " where dateCreated between ?  and ? order by dateCreated desc";
		List list = repositoryService.executeQuery(params,Util.getRemoteUser());
		return list;
	}
	
	public void addPost(String directory, String category, BlogPost post){
//		String dir = getRootBlogDir() + "/" + directory + "/" + category;
//		//Directory oDir = repositoryService.getDirectory(dir, Util.getRemoteUser());
//		
//		//oDir.addChild(post);
//		post.setEditPermissions("member:users");
//		repositoryService.saveIn(dir,post, Util.getRemoteUser());		
	}
	
	public List<BlogPost> searchBlog(String input){
		//search in comments
		//search in blogpost
		QueryParameters params = new QueryParameters().setEntity(BlogPost.class).addRestriction(Restrictions.or(Restrictions.like("summary", "%" + input + "%"), Restrictions.or(Restrictions.like("summary", "%" + input + "%"), Restrictions.like("detail", "%" + input + "%"))));
		//String hql = "from " + BlogPost.class.getName() + " where title like ? or summary like ? or text like ?";
		
		//String[] params = new String[]{"%" + input + "%","%" + input + "%","%" + input + "%"};
		
		List result = repositoryService.executeQuery( params, Util.getRemoteUser());
		
		return result;
	}
	
	public void addComment(Comment comment, int postId){
//		BlogPost post = getPost(postId);
//		if(post != null){
//			post.addChild(comment);
//			post.incrementCount();
//			repositoryService.update(post, Util.getRemoteUser());
//		}
	}

	public BlogPost getPost(int id) {
//		File file = repositoryService.getFileById(id, Util.getRemoteUser());
//		if(file instanceof BlogPost){
//			return (BlogPost)file;
//		}else{
//			throw new RuntimeException("The specified id "  + id + " is not a file of BlogPost");
//		}
		return null;
	}
}
