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
 package org.castafiore.designer.model;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.designer.renderer.ListRenderer;
import org.castafiore.ui.Container;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.ui.DataProvider;

public class ArticleListRenderer implements ListRenderer<Article>{

	private int expectedSize;
	
	private DataProvider<Article> dataProvider;
	
	public String getCategory() {
		return "ECM";
	}

	

	public Class<Article> getExpectedType() {
		return Article.class;
	}

	public String getUniqueId() {
		return "arch:article";
	}



	public void setDataProvider(DataProvider<Article> dataProvider) {
		this.dataProvider = dataProvider;
	}



	public Container getContainer(Article instance, int index) {
		EXArticle art = new EXArticle("");
		art.setInstance(instance);
		return art;
	}



	public DataProvider<Article> getDataProvider() {
		return dataProvider;
	}



	public int getExpectedSize() {
		if(isConfigured()){
			return getDataProvider().getSize();
		}else{
			return expectedSize;
		}
	}



	public void setExpectedSize(int expectedSize) {
		this.expectedSize = expectedSize;
	}



	public List<Article> getRealList() {
		return getDataProvider().getList();
		
	}



	public boolean isConfigured() {
		if(getDataProvider() == null){
			return false;
		}else{
			return true;
		}
	}



	public List<Article> getDummyList() {
		List<Article> result = new ArrayList<Article>();
		int size = getExpectedSize();
		
		for(int i = 0; i < size; i ++){
			//result.add(Article.getDummyInstance());
		}
		return result;
	}

	

}
