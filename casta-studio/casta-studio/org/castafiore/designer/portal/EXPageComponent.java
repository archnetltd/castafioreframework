package org.castafiore.designer.portal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;

public class EXPageComponent extends EXContainer {
	
	private String pagePath;

	public EXPageComponent() {
		super("Page Component", "div");
		setPagePath(pagePath);
	}
	
	
	
	public void setPagePath(String pagePath){
		try{
			this.pagePath = pagePath;
			this.getChildren().clear();
			this.setRendered(false);
			if(!StringUtil.isNotEmpty(pagePath)){
				addChild(new EXContainer("", "h3").setText("Please configure this component"));
			}else{
				//BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(pagePath, Util.getRemoteUser());
				String template = ResourceUtil.getTemplate(pagePath, getRoot());
				InputStream in = new ByteArrayInputStream(template.getBytes());
				Container c = DesignableUtil.buildContainer(in, false);
				c.setStyle("width", "100%");
				c.setStyle("heigh", "100%");
				addChild(c);
			}
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public String getPagePath(){
		return pagePath;
	}
	
	
	
	

}
