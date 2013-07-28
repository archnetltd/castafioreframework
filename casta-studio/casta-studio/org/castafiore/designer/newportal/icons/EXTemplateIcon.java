package org.castafiore.designer.newportal.icons;



public class EXTemplateIcon extends EXPortalIcon {

	public EXTemplateIcon(String name) {
		super(name);
	}
	
	
	public void setThumbnail(String imgUrl){
		getChild("img").setStyle("background", "url('" + imgUrl + "')");
	}
	
	
	public void setInfo(String info){
		getChild("info").setText(info);
	}

}
