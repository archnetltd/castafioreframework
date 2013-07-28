package org.castafiore.designer.newpage;

import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class NewPage {
	
	private String name;
	
	private String width;
	
	private String layout;
	
	private String dir;
	
	private String portalDir;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	
	
	
	public String getPortalDir() {
		return portalDir;
	}

	public void setPortalDir(String portalDir) {
		this.portalDir = portalDir;
	}

	public BinaryFile generate()throws Exception{
		Directory pages = SpringUtil.getRepositoryService().getDirectory(portalDir + "/pages",Util.getLoggedOrganization() );
		String height = "600";
		int rWidth = 900;
		if(StringUtil.isNotEmpty(width)){
			try{
			rWidth = Integer.parseInt(width) -6;
			}catch(Exception e){
				
			}
		}
		
		if(pages != null){
			BinaryFile page = pages.createFile(name, BinaryFile.class);
			String xml = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/designer/newpage/" + layout + ".xml"));
			xml = xml.replace("${x}", this.width + "px");
			xml = xml.replace("${x/2}", (rWidth/2) + "px");
			xml = xml.replace("${x/3}", (rWidth/3) + "px");
			xml = xml.replace("${y}", height + "px");
			xml = xml.replace("${y/2}", (Integer.parseInt(height)/2) + "px");
			xml = xml.replace("${y/3}", (Integer.parseInt(height)/3) + "px");
			xml = xml.replace("${name}", name);
			xml = xml.replace("${portalPath}", portalDir + "/");
			page.write(xml.getBytes());
			pages.save();
			return page;
		}
		return null;
	}

}
