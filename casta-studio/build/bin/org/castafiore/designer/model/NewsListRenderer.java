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

import java.text.SimpleDateFormat;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.types.Article;

public class NewsListRenderer extends ArticleListRenderer {

	@Override
	public Container getContainer(Article instance, int index) {
		EXContainer div = new EXContainer("", "div");
		div.addChild(ComponentUtil.getContainer("", "h3", new SimpleDateFormat("dd/MM/yyyy").format(instance.getDateCreated().getTime()), null));
		div.addChild(ComponentUtil.getContainer("", "p", instance.getSummary(), null));
		div.addChild(ComponentUtil.getContainer("", "div", " ", "hr-dots"));
		return div;
	}

	@Override
	public String getUniqueId() {
		return "arch:news";
	}
	
	

}
