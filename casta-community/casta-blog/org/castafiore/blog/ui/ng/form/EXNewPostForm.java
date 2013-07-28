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
 package org.castafiore.blog.ui.ng.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.castafiore.blog.BlogPost;
import org.castafiore.blog.BlogService;
import org.castafiore.blog.ui.BlogApplication;
import org.castafiore.blog.ui.ng.EXBlogList;
import org.castafiore.blog.ui.ng.EXBlogWindow;
import org.castafiore.blog.ui.ng.EXPostList;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.validator.EmptyStringValidator;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;

public class EXNewPostForm extends EXXHTMLFragment {

	public EXNewPostForm(String name) {
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/blog/ui/ng/resources/EXNewPostForm.xhtml"));
		EXContainer category = new EXContainer("category", "span");
		addChild(category);
		
		EXContainer date = new EXContainer("date", "span");
		addChild(date);
		date.setText(new SimpleDateFormat("dd/MMM/yyyy").format(new Date()));
		
		EXContainer author = new EXContainer("author", "span");
		addChild(author);
		author.setText(Util.getRemoteUser());
		
		EXInput title = new EXInput("title");
		title.setWidth(Dimension.parse("375px"));
		addChild(title);
		
		EXRichTextArea content = new EXRichTextArea("content");
		content.setStyle("float", "left");
		content.setColumns(45);
		content.setRows(14);
		addChild(content);
		
		
		EXButton submit = new EXButton("submit", "Submit");
		submit.addEvent(new SavePostEvent(), Event.CLICK);
		submit.addClass("button");
		addChild(submit);
		submit.setStyle("float", "left");
		submit.setStyle("margin-left", "5px");
		
		EXButton preview = new EXButton("preview", "Preview");
		preview.addClass("button");
		addChild(preview);
		preview.setStyle("float", "left");
		preview.setStyle("margin-left", "5px");
		
		EXButton cancel = new EXButton("cancel", "Cancel");
		cancel.addEvent(new CancelPostEvent(), Event.CLICK);
		cancel.addClass("button");
		addChild(cancel);
		cancel.setStyle("float", "left");
		cancel.setStyle("margin-left", "5px");
		
	}
	
	public void setCategory(String category){
		getChild("category").setText(category);
		
		getChild("date").setText(new SimpleDateFormat("dd MMM yyyy").format(new Date()));
		
		((StatefullComponent)getChild("title")).setValue("");
		((EXRichTextArea)getChild("content")).setValue("");
		
		
	}
	
	public BlogPost savePost(String directory){
//		BlogPost post = new BlogPost();
//		post.setName(new Date().getTime() + "");
//		String title = ((StatefullComponent)getChild("title")).getValue().toString();
//		String summary = ((StatefullComponent)getChild("content")).getValue().toString();
//		String category = getChild("category").getText();
//		
//		if(
//				EmptyStringValidator.INSTANCE.validate((StatefullComponent)getChild("title")) && 
//				EmptyStringValidator.INSTANCE.validate((StatefullComponent)getChild("content"))
//			){
//			
//			post.setTitle(title);
//			post.setSummary(summary);
//			
//			BlogService service =  ((BlogApplication)getRoot()).getBlogService();
//			service.addPost(directory, category, post);
//			return post;
//		}
//		 
		return null;
		
		
	}
	
	public static class SavePostEvent implements Event{

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this, "Do you really want to save this post?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			EXNewPostForm form = container.getAncestorOfType(EXNewPostForm.class);
			BlogApplication app = (BlogApplication)form.getRoot();
			String directory = app.getCurrentDir();
			BlogPost newPost = form.savePost(directory);
			if(newPost != null){
				container.getAncestorOfType(EXBlogList.class).addPostForDisplay(newPost);
				container.getAncestorOfType(EXBlogList.class).getDescendentOfType(EXPostList.class).setDisplay(true);
				container.getAncestorOfType(EXNewPostForm.class).setDisplay(false);
			}
			
			return true;
			
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static class CancelPostEvent implements Event{

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this, "Do you really want to cancel?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXBlogList.class).getDescendentOfType(EXPostList.class).setDisplay(true);
			container.getAncestorOfType(EXNewPostForm.class).setDisplay(false);
			
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			
			
		}
		
	}

}
