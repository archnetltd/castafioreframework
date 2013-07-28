package org.castafiore.ui.svg.animation;


public class SVGAnimateMotion extends SVGAnimate {
	
	public SVGAnimateMotion(String name,  String attributeType) {
		super(name, "animateMotion", attributeType);
	
	}

	public String getKeyPoints() {
		return getAttribute("keyPoints");
	}

	public void setKeyPoints(String keyPoints) {
		setAttribute("keyPoints", keyPoints);
	}

	public String getPath() {
		return getAttribute("path");
	}

	public void setPath(String path) {
		setAttribute("path", path);
	}

	public String getRotate() {
		return getAttribute("rotate");
	}

	public void setRotate(String rotate) {
		setAttribute("rotate", rotate);
	}

	public String getOrigin() {
		return getAttribute("origin");
	}

	public void setOrigin(String origin) {
		setAttribute("origin", origin);
	}
	
	

}
