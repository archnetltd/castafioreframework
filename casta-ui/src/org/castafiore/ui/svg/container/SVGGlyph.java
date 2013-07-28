package org.castafiore.ui.svg.container;

import java.awt.Point;

import org.castafiore.ui.svg.graphic.SVGPath;

public class SVGGlyph extends SVGPath {

	
	/*
	 * 


	 */
	public SVGGlyph(String name) {
		super(name, "glyph");
		
	}
	
	public void setAdv(Point p){
		setAttribute("horiz-adv-x", p.x + "");
		setAttribute("vert-adv-y", p.y + "");
	}
	
	public Point getAdv(){
		return new Point(Integer.parseInt(getAttribute("horiz-adv-x")), Integer.parseInt(getAttribute("vert-adv-y")));
	}
	
	public void setLang(String s){
		setAttribute("lang", s);
	}
	
	public String getLang(){
		return getAttribute("lang");
	}

	public void setOrientation(String s){
		
		setAttribute("orientation", s);
	}
	
	public String getOrientation(){
		return getAttribute("orientation");
	}
	
	public void setUnicode(String s){
		setAttribute("unicode", s);
	}
	
	public String getUnicode(){
		return getAttribute("unicode");
	}
	
	public void setArabicForm(String s){
		setAttribute("arabic-form", s);
	}
	
	public String getArabicForm(){
		return getAttribute("arabic-form");
	}
	
	public void setGlyphName(String s){
		setAttribute("glyph-name", s);
	}
	
	public String getGlyphName(){
		return getAttribute("glyph-name");
	}
	
	public void setVerticalOrigin(Point p){
		setAttribute("vert-origin-x", p.x + "");
		setAttribute("vert-origin-y", p.y + "");
	}
	
	public Point getVerticalOrigin(){
		return new Point(Integer.parseInt(getAttribute("vert-origin-x")), Integer.parseInt(getAttribute("vert-origin-y")));
	}
	
	
	
}
