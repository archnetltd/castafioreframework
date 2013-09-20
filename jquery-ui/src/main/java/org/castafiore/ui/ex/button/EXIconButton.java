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
 package org.castafiore.ui.ex.button;

import org.castafiore.JQContants;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.button.Button;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.utils.StringUtil;

public class EXIconButton extends EXContainer implements Button , JQContants{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String text;
	
	private Icons iconLeft;
	
	private Icons iconRight;
	
	private String iconLeftUrl;
	
	private String iconRightUrl;

	@Override
	public void onReady(ClientProxy proxy) {
		super.onReady(proxy);
		proxy.addClass("ui-button").addClass("ui-widget").addClass("ui-state-default").addClass("ui-corner-" + getAttribute("corner"));
		proxy.mouseover(proxy.clone().addClass("ui-state-hover")).mouseout(proxy.clone().removeClass("ui-state-hover").removeClass("ui-state-active").mousedown(proxy.clone().addClass("ui-state-active")));
		if(StringUtil.isNotEmpty(text)){
			if(iconLeft != null && iconRight != null){
				proxy.addClass("ui-button-text-icons");
				String txt = "<span class='ui-button-icon-primary ui-icon' style='background-position:"+ICONS_BACK_POSITON[iconLeft.ordinal()]+"'></span><span class='ui-button-text'>"+text+"</span>";
				txt = txt + "<span class='ui-button-icon-secondary ui-icon' style='background-position:"+ICONS_BACK_POSITON[iconRight.ordinal()]+"'></span>";
				proxy.html(txt);
			}else if(iconLeft == null && iconRight != null){
				proxy.addClass("ui-button-text-icons");
				String txt = "<span class='ui-button-text'>"+text+"</span>";
				txt = txt + "<span class='ui-button-icon-secondary ui-icon' style='background-position:"+ICONS_BACK_POSITON[iconRight.ordinal()]+"'></span>";
				proxy.html(txt);
			}else if(iconLeft != null && iconRight == null){
				proxy.addClass("ui-button-text-icon-primary");
				String txt = "<span class='ui-button-icon-primary ui-icon' style='background-position:"+ICONS_BACK_POSITON[iconLeft.ordinal()]+"'></span><span class='ui-button-text'>"+text+"</span>";
				proxy.html(txt);
			}else if(iconLeft == null && iconRight==null){
				
				if(iconLeftUrl == null && iconRightUrl == null){
					proxy.addClass("ui-button-text-only");
					String txt = "<span class='ui-button-text'>"+text+"</span>";
					proxy.html(txt);
				}else if(iconLeftUrl != null && iconRightUrl != null){
					proxy.addClass("ui-button-text-icons");
					String txt = "<span class='ui-button-icon-primary ui-icon' style='background-image:url("+iconLeftUrl+")'></span><span class='ui-button-text'>"+text+"</span>";
					txt = txt + "<span class='ui-button-icon-secondary ui-icon' style='background-image:url("+iconRightUrl+")'></span>";
					proxy.html(txt);
				}else if(iconLeftUrl == null && iconRightUrl != null){
					proxy.addClass("ui-button-text-icons");
					String txt = "<span class='ui-button-text'>"+text+"</span>";
					txt = txt + "<span class='ui-button-icon-secondary ui-icon' style='background-image:url("+iconRightUrl+")'></span>";
					proxy.html(txt);
				}else if(iconLeftUrl != null && iconRightUrl == null){
					proxy.addClass("ui-button-text-icon-primary");
					String txt = "<span class='ui-button-icon-primary ui-icon' style='background-image:url("+iconLeftUrl+")'></span><span class='ui-button-text'>"+text+"</span>";
					proxy.html(txt);
				}
			}
			
		}else{
			if(iconLeft != null && iconRight != null){
				proxy.addClass("ui-button-icons-only");
				String txt = "<span class='ui-button-icon-primary ui-icon' style='background-position:"+ICONS_BACK_POSITON[iconLeft.ordinal()]+"'></span><span class='ui-button-text'>&nbsp;</span>";
				txt = txt + "<span class='ui-button-icon-secondary ui-icon' style='background-position:"+ICONS_BACK_POSITON[iconRight.ordinal()]+"'></span>";
				proxy.html(txt);
			}else if(iconLeft == null && iconRight != null){
				proxy.addClass("ui-button-icons-only");
				String txt = "<span class='ui-button-text'>&nbsp;</span>";
				txt = txt + "<span class='ui-button-icon-secondary ui-icon' style='background-position:"+ICONS_BACK_POSITON[iconRight.ordinal()]+"'></span>";
				proxy.html(txt).setStyle("width", "2.7em");
			}else if(iconLeft != null && iconRight == null){
				proxy.addClass("ui-button-icons-only");
				String txt = "<span class='ui-button-icon-primary ui-icon' style='background-position:"+ICONS_BACK_POSITON[iconLeft.ordinal()]+"'></span><span class='ui-button-text'>&nbsp;</span>";
				
				proxy.html(txt).setStyle("width", "2.7em");
			}else if(iconLeft == null && iconRight==null){
				
				if(iconLeftUrl == null && iconRightUrl == null){
					proxy.addClass("ui-button-icons-only");
					String txt = "<span class='ui-button-text'>&nbsp;</span>";
					proxy.html(txt);
				}else if(iconLeftUrl != null && iconRightUrl != null){
					proxy.addClass("ui-button-icons-only");
					String txt = "<span class='ui-button-icon-primary ui-icon' style='background-image:url("+iconLeftUrl+")'></span><span class='ui-button-text'>&nbsp;</span>";
					txt = txt + "<span class='ui-button-icon-secondary ui-icon' style='background-image:url("+iconRightUrl+")'></span>";
					proxy.html(txt);
				}else if(iconLeftUrl == null && iconRightUrl != null){
					proxy.addClass("ui-button-icons-only");
					String txt = "<span class='ui-button-text'>&nbsp;</span>";
					txt = txt + "<span class='ui-button-icon-secondary ui-icon' style='background-image:url("+iconRightUrl+")'></span>";
					proxy.html(txt).setStyle("width", "2.7em");
				}else if(iconLeftUrl != null && iconRightUrl == null){
					proxy.addClass("ui-button-icons-only");
					String txt = "<span class='ui-button-icon-primary ui-icon' style='background-image:url("+iconLeftUrl+")'></span><span class='ui-button-text'>&nbsp;</span>";
					proxy.html(txt).setStyle("width", "2.7em");
				}
			}
		}
	}

	public EXIconButton(String name, String text, Icons leftIcon, Icons rightIcon) {
		super(name, "button");
		setButtonText(text).setIconLeft(leftIcon).setIconRight(rightIcon);
		setAttribute("corner", "all");
		
	}
	
	public EXIconButton(String name, String text, Icons leftIcon) {
		super(name, "button");
		setButtonText(text).setIconLeft(leftIcon);
		setAttribute("corner", "all");
	}
	
	public EXIconButton(String name, String text) {
		super(name, "button");
		setButtonText(text);
		setAttribute("corner", "all");
	}
	

	public EXIconButton(String name,  Icons leftIcon) {
		super(name, "button");
		setIconLeft(leftIcon);
		setAttribute("corner", "all");
	}
	
	public String getButtonText() {
		return text;
	}

	public EXIconButton setButtonText(String text) {
		this.text = text;
		return this;
	}

	public Icons getIconLeft() {
		return iconLeft;
	}

	public EXIconButton setIconLeft(Icons iconLeft) {
		this.iconLeft = iconLeft;
		this.iconLeftUrl = null;
		return this;
	}

	public Icons getIconRight() {
		return iconRight;
	}

	public EXIconButton setIconRight(Icons iconRight) {
		this.iconRight = iconRight;
		this.iconRightUrl = null;
		return this;
	}

	public String getIconLeftUrl() {
		return iconLeftUrl;
	}

	public EXIconButton setIconLeftUrl(String iconLeftUrl) {
		this.iconLeftUrl = iconLeftUrl;
		this.iconLeft = null;
		return this;
	}

	public String getIconRightUrl() {
		return iconRightUrl;
	}

	public EXIconButton setIconRightUrl(String iconRightUrl) {
		this.iconRightUrl = iconRightUrl;
		this.iconRight = null;
		return this;
	}
	
	
}
