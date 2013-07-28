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
 package org.castafiore.catalogue.ui;

import java.math.BigDecimal;

import org.castafiore.catalogue.Product;
import org.castafiore.ecm.ui.fileexplorer.FileEditor;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;

public class EXProductEditor extends EXContainer implements FileEditor {
	
	private Product product;
	
	private String currentDir;
	
	private boolean isNew = true;

	public EXProductEditor() {
		super("EXProductEditor", "div");
		addField("Code :", new EXInput("code"));
		addField("Title :", new EXInput("title"));
		addField("Image :", new EXInput("imgUrl"));
		addField("Price :", new EXInput("price"));
		addField("Sold by :", new EXInput("soldBy"));
		addField("From :", new EXInput("made"));
		addField("Warranty :", new EXInput("warranty"));
		EXRichTextArea textArea = new EXRichTextArea("description");
		
		addField("Description :", textArea);
		
		
		ComponentUtil.applyStyleOnAll(this, EXInput.class, "width", "350px");
		
		
	}
	public void addField(String label, StatefullComponent input){
		addChild(new EXContainer("", "label").setText(label).setStyle("display", "block"));
		addChild(input);
	}
	public StatefullComponent getField(String name){
		return (StatefullComponent)getDescendentByName(name);
	}
	public void initialiseEditor(File file, String directory, boolean isNew) {
		this.product = (Product)file;
		this.currentDir = directory;
		this.isNew = isNew;
		if(!isNew){
			getField("code").setValue(product.getCode());
			getField("title").setValue(product.getTitle());
			
			getField("price").setValue(product.getTotalPrice());
			getField("soldBy").setValue(product.getProvidedBy());
			getField("made").setValue(product.getMade());
			getField("warranty").setValue(product.getWarranty());
			getField("description").setValue(product.getSummary());
		}
			
		
	}

	public File save(String name) {
		if(isNew){
			product = futil.getDirectory(currentDir).createFile(name,Product.class);
		}
		product.setCode(getField("code").getValue().toString());
		product.setTitle(getField("title").getValue().toString());
		
		product.setTotalPrice(new BigDecimal(getField("price").getValue().toString()));
		product.setProvidedBy(getField("soldBy").getValue().toString());
		product.setMade(getField("made").getValue().toString());
		product.setWarranty(Integer.parseInt(getField("warranty").getValue().toString()));
		product.setSummary(getField("description").getValue().toString());
		product.save();
		
		
		return product;
		
	}

}
