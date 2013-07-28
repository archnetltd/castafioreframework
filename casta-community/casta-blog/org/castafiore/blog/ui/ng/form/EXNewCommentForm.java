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

import java.util.Date;

import org.castafiore.blog.ui.ng.EXCommentList;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.validator.EmptyStringValidator;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.wfs.types.Comment;

public class EXNewCommentForm extends EXContainer{

	public EXNewCommentForm(String name) {
		super(name, "div");
		EXTextArea content = new EXTextArea("content");
		content.setStyle("margin", "1px");
		content.setAttribute("cols", "34");
		content.setAttribute("rows", "9");
		addChild(content);
		EXButton button = new EXButton("postComment", "Post comment");
		button.addEvent(EXCommentList.SavePostEvent, Event.CLICK);
		addChild(button);
		button.addClass("postComment");
		
		EXButton discard = new EXButton("discard", "Discard");
		addChild(discard);
		discard.setStyle("float", "left");
		discard.addEvent(EXCommentList.DiscardPostEvent, Event.CLICK);
		
		addClass("commentForm");
		
		
		
		
	}
	
	public void clear(){
		((EXTextArea)getChild("content")).setValue("");
	}
	
	public Comment  getComment(){
//		Comment comment = new Comment();
//		comment.setName(new Date().getTime() + "");
//		
//		if(EmptyStringValidator.INSTANCE.validate(getChild("content"))){
//		
//			String title; 
//			String value = ((EXTextArea)getChild("content")).getValue().toString();
//			if(value.length() > 30){
//				title = value.substring(25)+ "...";
//			}else{
//				title = value;
//			}
//			
//			comment.setTitle(title);
//			comment.setSummary(value);
//			return comment;
//			
//		}
//		
		return null;
		
		
	}
	
	

}
