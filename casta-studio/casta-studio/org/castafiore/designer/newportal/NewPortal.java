package org.castafiore.designer.newportal;

import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.ui.Application;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;


public class NewPortal {
	
	private String name;
	
	private String banner;
	
	private String menu;
	
	
	private String footerText;
	
	/**
	 * Template /Normal
	 */
	private String portalType = "Normal";
	
	//Blank, FullArc, ThreeRows, LeftPanel, RightPanel, TemplateUrl
	private String portalSelected ="Blank";

	private String portalDir;
	
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getFooterText() {
		return footerText;
	}

	public void setFooterText(String footerText) {
		this.footerText = footerText;
	}

	public String getPortalType() {
		return portalType;
	}

	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}

	public String getPortalSelected() {
		return portalSelected;
	}

	public void setPortalSelected(String portalSelected) {
		this.portalSelected = portalSelected;
	}

	public String getPortalDir() {
		return portalDir;
	}

	public void setPortalDir(String portalDir) {
		this.portalDir = portalDir;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BinaryFile generate(Application root)throws Exception{
		return DesignableUtil.generateSite(root, banner + ".jpg", menu,"org/castafiore/designer/newportal/" + portalSelected + ".xml", portalDir, name);
	}
	

}
