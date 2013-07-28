/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.wfs.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.iterator.FileIterator;
import org.hibernate.annotations.Type;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 29, 2008
 */
@Entity
public class Article extends Directory {
	
	//designation produit
	private String title;
	
	//La phrase produit ou slogan
	@Type(type="text")
	private String summary;
	
	//Autres info
	@Type(type="text")
	private String detail;
	
	//utilite
	@Type(type="text")
	private String tags;
	
	private double likeIt = 1;
	
	private double dislikeIt = 1;
	
	private boolean commentable = true;
	
	private boolean ratable = true;
	
	public Article(){
		super();
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public FileIterator<BinaryFile> getAttachments(){
		FileIterator<BinaryFile> iter = getFiles(BinaryFile.class);
		return iter;
	}
	
	
	public FileIterator<Shortcut> getShortcuts(){
		FileIterator<Shortcut> shortCuts = getFiles(Shortcut.class);
		return shortCuts;
	}
	
	
	public FileIterator<Link> getExternalLinks(){
		return getFiles(Link.class);
	}
	
	public Link createLink(String name){
		//addChild(link);
		return createFile(name, Link.class);
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	
	public Comment createComment(String name){
		//addChild(comment);
		return createFile(name, Comment.class);
	}
	
	public FileIterator<Comment> getComments(){
		return getFiles(Comment.class);
	}

	
	
	public double getLikeIt() {
		return likeIt;
	}

	public void setLikeIt(double likeIt) {
		this.likeIt = likeIt;
	}

	public double getDislikeIt() {
		return dislikeIt;
	}

	public void setDislikeIt(double dislikeIt) {
		this.dislikeIt = dislikeIt;
	}

	public void thumbUp(){
		likeIt= likeIt +1;
	}
	
	public void thumbDown(){
		dislikeIt=dislikeIt +1;
	}
	public boolean isCommentable() {
		return commentable;
	}

	public void setCommentable(boolean commentable) {
		this.commentable = commentable;
	}

	public boolean isRatable() {
		return ratable;
	}

	public void setRatable(boolean ratable) {
		this.ratable = ratable;
	}

	
	public String getLocalisedMessage(String property, Locale locale){
		Directory locals = (Directory)getFile("i18n");
		if(locals != null){
			Value val =  (Value)locals.getFile(property + "_" + locale.getLanguage());
			if(val != null){
				return val.getString();
			}
		}return null;
	}
	
	public List<Value> getLocalisedMessages(String property){
		List<Value> result = new ArrayList<Value>();
		Directory locals = (Directory)getFile("i18n");
		if(locals != null){
			List<Value> vals = getFiles(Value.class).toList();
			for(Value val : vals){
				if(val.getName().startsWith(property)){
					result.add(val);
				}
			}
		}
		return result;
	}
	
	
	public void setLocalisedMessage(String property, Locale locale, String value){
		Directory locals = (Directory)getFile("i18n");
		if(locals == null){
			locals = createFile("i18n", Directory.class);
			Value val = locals.createFile(property +"_"+ locale.getLanguage(), Value.class);
			val.setString(value);
		}else{
			Value val = (Value)locals.getFile(property+"_" + locale.getLanguage());
			if(val != null){
				val.setString(value);
			}else{
				val = locals.createFile(property+"_" + locale.getLanguage(), Value.class);
				val.setString(value);
			}
		}
	}
	
}
