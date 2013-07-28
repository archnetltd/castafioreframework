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
 package org.castafiore.blog.ui;

import java.util.List;

import org.castafiore.blog.BlogPost;
import org.castafiore.blog.BlogService;
import org.castafiore.blog.ui.ng.EXBlogList;
import org.castafiore.blog.ui.ng.EXBlogWidget;
import org.castafiore.blog.ui.ng.EXBlogWindow;
import org.castafiore.blog.ui.ng.EXListableBlogWidget;
import org.castafiore.blog.ui.ng.events.StaticEvents;
import org.castafiore.blog.ui.ng.form.EXFormBlogWidget;
import org.castafiore.blog.ui.ng.form.RegisterFormModel;
import org.castafiore.blog.ui.ng.form.SignInFormModel;
import org.castafiore.blog.ui.ng.models.BlogArchiveModel;
import org.castafiore.blog.ui.ng.models.BlogCategoriesModel;
import org.castafiore.blog.ui.ng.models.RecentCommentsModel;
import org.castafiore.blog.ui.ng.models.RecentPostsModel;
import org.castafiore.blog.ui.ng.navigation.BlogNavigationModel;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXApplication;

public class BlogApplication extends EXApplication {
	
	private BlogService blogService;
	
	private SecurityService securityService;
	
	private String currentDir = null;
	
	private String currentCategory = null; 

	public BlogApplication() {
		super("bdmublog");
		addClass("blog");
		//addEvent(StaticEvents.AJUST_BROWSER_EVENT, Event.READY);
	}
	
	public void init(){
		
		
		
		EXBlogWindow window = new EXBlogWindow("blog");
		addChild(window);
		
		signOut();
		
		EXListableBlogWidget archives = new EXListableBlogWidget("archives", "Archive");
		archives.setModel(new BlogArchiveModel());
		window.addInLeftColumn(archives);
		
		
		
		EXListableBlogWidget categories = new EXListableBlogWidget("categories", "Categories");
		categories.setModel(new BlogCategoriesModel("Professional Blog", blogService));
		categories.setAutoRefreshTimeOut(120000);
		categories.addRefreshHandler(new EXBlogWidget.RefreshHandler(){

			public void doRefresh(EXBlogWidget This) {
				((EXListableBlogWidget)This).setModel(new BlogCategoriesModel("Professional Blog", blogService));
				
			}
			
		});
		
		window.addInRightColumn(categories);
		List<BlogPost> recPosts = blogService.getRecentPosts(12);
		EXListableBlogWidget recentPosts = new EXListableBlogWidget("recentPosts", "Recent posts");
		recentPosts.setAutoRefreshTimeOut(120000);
		recentPosts.addRefreshHandler(new EXBlogWidget.RefreshHandler(){
			public void doRefresh(EXBlogWidget This) {
				((EXListableBlogWidget)This).setModel(new RecentPostsModel(blogService.getRecentPosts(12)));		
			}
		});
		
		recentPosts.setModel(new RecentPostsModel(recPosts));
		window.addInRightColumn(recentPosts);
		
		EXListableBlogWidget recentComments = new EXListableBlogWidget("recentComments", "Recent comments");
		recentComments.setModel(new RecentCommentsModel(blogService.getRecentComments(30)));
		recentComments.setAutoRefreshTimeOut(120000);
		recentComments.addRefreshHandler(new EXBlogWidget.RefreshHandler(){
			public void doRefresh(EXBlogWidget This) {
				((EXListableBlogWidget)This).setModel(new RecentCommentsModel(blogService.getRecentComments(30)));
			}
		});
		
		window.addInRightColumn(recentComments);
		window.setNavigation(new BlogNavigationModel());
		
		EXBlogList list = new EXBlogList("");
		list.setTitle("Recent posts");
		list.unAllowAddPost();
		list.setPostList(recPosts);
		window.setBody(list);	
	}
		
	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public BlogService getBlogService() {
		return blogService;
	}
	
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}

	public String getCurrentCategory() {
		return currentCategory;
	}

	public void setCurrentCategory(String currentCategory) {
		this.currentCategory = currentCategory;
	}

	public String getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(String currentDir) {
		this.currentDir = currentDir;
	}
	
	public void signIn(String username, String password)throws Exception{
		
		if(getSecurityService().login(username, password)){
			EXBlogWindow window = getDescendentOfType(EXBlogWindow.class);
			window.setNavigation(new BlogNavigationModel());
			window.getDescendentByName("loginForm").remove();
			window.getDescendentByName("RegisterForm").remove();
			setRendered(false);
		}
	}
	
	public void signOut(){
		try{
			SpringUtil.getSecurityService().login("anonymous blogger", "anonymous");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		EXBlogWindow window =getDescendentOfType(EXBlogWindow.class);
		
		EXFormBlogWidget loginForm = new EXFormBlogWidget("loginForm", "Sign In");
		loginForm.setFormModel(new SignInFormModel());
		window.addInLeftColumn(loginForm);
		
		EXFormBlogWidget register = new EXFormBlogWidget("RegisterForm", "Register");
		register.setFormModel(new RegisterFormModel());
		window.addInLeftColumn(register);
		window.setNavigation(new BlogNavigationModel());
		
	}
}
