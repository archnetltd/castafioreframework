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
package org.castafiore.designer.designable;

import org.castafiore.beans.info.IBeanInfo;
import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXImage;
import org.castafiore.utils.ResourceUtil;

public class EXImageDesignableFactory extends AbstractDesignableFactory
		implements IBeanInfo {

	public EXImageDesignableFactory() {
		super("EXImageDesignable");
		setText("Image");
	}

	@Override
	public String getCategory() {
		return "Primitive Layout";
	}

	@Override
	public Container getInstance() {

		EXImage image = new EXImage("EXImage");
		image.setAttribute("src", ResourceUtil.getDownloadURL("classpath",
				"org/castafiore/designer/resources/images/Castafiore.gif"));

		return image;
	}

	public String getUniqueId() {
		return "core:image";
	}

	@ConfigValues(configs={
			@ConfigValue(attribute="src",values="search:pages"),
			@ConfigValue(attribute="taggable",values={"true", "false"}, default_="false"),
			@ConfigValue(attribute="canDeleteTag",values={"true", "false"},default_="true"),
			@ConfigValue(attribute="canTag",values={"true", "false"},default_="true"),
			@ConfigValue(attribute="autoComplete",values={"true", "false"},default_="false"),
			@ConfigValue(attribute="autoShowTag",values={"true", "false"},default_="false"),
			@ConfigValue(attribute="clickToTag",values={"true", "false"},default_="true"),
			@ConfigValue(attribute="tagDraggable",values={"true", "false"},default_="true"),
			@ConfigValue(attribute="tagResizable",values={"true", "false"},default_="true"),
			@ConfigValue(attribute="showLabels",values={"true", "false"},default_="true"),
			@ConfigValue(attribute="showTagOn",values={ "hover", "always"}, default_="hover"),
			@ConfigValue(attribute="minWidth",values={}, default_=""),
			@ConfigValue(attribute="minHeight",values={}, default_=""),
			@ConfigValue(attribute="maxWidth",values={}, default_=""),
			@ConfigValue(attribute="maxHeight",values={}, default_=""),
			@ConfigValue(attribute="defaultWidth",values={}, default_="100"),
			@ConfigValue(attribute="defaultHeight",values={}, default_="100")
			
			
			
			})
	public String[] getRequiredAttributes() {
		return new String[] { "src",
				"taggable",
				"canDeleteTag",
				"canTag",
				"autoComplete",
				"autoShowTag",
				"clickToTag",
				"tagDraggable",
				"tagResizable",
				"showLabels",
				
				"minWidth",
				"minHeight",
				"maxWidth",
				"maxHeight",
				"defaultWidth",
				"defaultHeight",
				"showTagOn"
				 };
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		if (attributeName.equals("src")) {
			c.setAttribute("src", attributeValue);
		}else{
			c.setRendered(false);
		}
	}

	public String getInfoAttribute(String key) {
		return "Creates an image ";
	}

	public String[] getKeys() {
		return new String[] { "description" };
	}

	public String getSupportedUniqueId() {
		return getUniqueId();
	}

}
