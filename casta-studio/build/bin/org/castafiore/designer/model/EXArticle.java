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

import org.castafiore.ui.Container;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.types.Article;

public class EXArticle extends EXXHTMLFragment {

	public EXArticle(String name) {
		super(name, "/architecture2/templates/article.xhtml");
		addChild(ComponentUtil.getContainer("title", "h2", "", null));
		addChild(ComponentUtil.getContainer("summary", "div", "", null));
		Container img = ComponentUtil.getContainer("imageUrl", "img", "", "left");
		img.setAttribute("width", "171");
		img.setAttribute("height", "137");
		
		addChild(img);
	}
	
	public void setInstance(Article article){
		getChild("title").setText(article.getTitle());
		getChild("summary").setText(article.getSummary());
		getChild("imageUrl").setAttribute("src", ResourceUtil.getDownloadURL("ecm", article.getAttachments().get(0).getAbsolutePath()));
		
	}

}
