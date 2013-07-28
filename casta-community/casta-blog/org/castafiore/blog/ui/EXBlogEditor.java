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

import org.castafiore.blog.BlogPost;
import org.castafiore.ecm.ui.fileexplorer.FileEditor;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;

public class EXBlogEditor extends EXContainer implements FileEditor {
	private String currentDir = null;
	
	private boolean isNew = true;
	
	private BlogPost doc ;

	public EXBlogEditor() {
		
		super("EXBlogEditor", "div");
		addField("Title :", new EXInput("title"));
		EXRichTextArea textArea = new EXRichTextArea("summary");
		textArea.setWidth(Dimension.parse("425px"));
		textArea.setHeight(Dimension.parse("220px"));
		addField("Summary :", textArea);
		
		EXRichTextArea text = new EXRichTextArea("text");
		text.setWidth(Dimension.parse("425px"));
		text.setHeight(Dimension.parse("220px"));
		addField("Text :", text);
		
		
		


		ComponentUtil.applyStyleOnAll(this, EXInput.class, "width", "350px");
		setHeight(Dimension.parse("400px"));
		setStyle("overflow-y", "auto").setStyle("overflow-x", "hidden");
	}

	public File save(String name){
		if(isNew){
			doc = futil.getDirectory(currentDir).createFile(name,BlogPost.class);
		}
		doc.setTitle(getField("title").getValue().toString());
		doc.setSummary(getField("summary").getValue().toString());
		doc.setDetail(getField("text").getValue().toString());

		doc.save();
	
		
		return doc;
	}
	
	public void addField(String label, StatefullComponent input){
		addChild(new EXContainer("", "label").setText(label).setStyle("display", "block"));
		addChild(input);
	}
	
	public StatefullComponent getField(String name){
		return (StatefullComponent)getDescendentByName(name);
	}

	public void initialiseEditor(File file,String directory, boolean isNew) {
		this.currentDir = directory;
		this.isNew = isNew;
		if(file instanceof BlogPost){
			doc = (BlogPost)file;
			getField("title").setValue(((BlogPost) file).getTitle());
			getField("summary").setValue(((BlogPost) file).getSummary());
			getField("text").setValue(((BlogPost) file).getDetail());
		}else{
			throw new UIException("error loading this file editor for file " + file.getName());
		}	
	}

}
