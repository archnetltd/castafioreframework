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

package org.castafiore.ui.ex.form;

import java.awt.Color;

import org.castafiore.ui.AbstractFormComponent;
import org.castafiore.ui.dynaform.Focusable;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.utils.ImageUtil;
import org.castafiore.utils.ResourceUtil;

/**
 *  
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class EXColorPicker extends AbstractFormComponent<Color> implements Focusable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXColorPicker(String name, Color value) {
		super(name,"input");
		setReadOnlyAttribute("type", "text");
		setValue(value);
	}

	public EXColorPicker(String name) {
		super(name,"input");
		setReadOnlyAttribute("type", "text");

	}

	@Override
	public void onReady(ClientProxy proxy) {

		super.onReady(proxy);

		proxy.getCSS(ResourceUtil.getDownloadURL("classpath",
				"org/castafiore/resources/colorpicker/colorpicker.css"));
		proxy.getScript(
				ResourceUtil.getJavascriptURL("ui.colorpicker.js"),
				proxy.clone().click(
						proxy.clone()
								.appendJSFragment("startColorPicker(this)")));
	}

	@Override
	public String serialize(Color value) {
		if(value == null){
			return "";
		}
		String hex = "#"+Integer.toHexString(value.getRGB()).substring(2);
		return hex;
	}

	@Override
	public Color deserialize(String s) {
		return ImageUtil.hex2Rgb(s);
	}
	
	@Override
	public int getTabIndex() {
		try{
		return Integer.parseInt(getAttribute("tabindex"));
		}catch(Exception e){
			return -1;
		}
	}

	@Override
	public void setAccessKey(char key) {
		
		setAttribute("accesskey", new String(new char[]{key}));
	}

	@Override
	public void setFocus(boolean focused) {
		setAttribute("hasfocus", focused + "");
	}

	@Override
	public void setTabIndex(int index) {
		setAttribute("tabindex", index + "");
		
	}

}
