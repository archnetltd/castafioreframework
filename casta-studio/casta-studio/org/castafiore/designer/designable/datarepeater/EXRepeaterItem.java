package org.castafiore.designer.designable.datarepeater;

import java.io.ByteArrayInputStream;

import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;

public class EXRepeaterItem extends EXContainer{

	public EXRepeaterItem(String name) {
		super(name, "div");
	}
	
	public String getComponentPath(){
		return getAttribute("componentPath");
	}
	
	
	public void setComponentPath(String path){
		BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		try{
		Container  c= DesignableUtil.buildContainer(bf.getInputStream(), false);
		this.getChildren().clear();
		setRendered(false);
		addChild(c);
		
		}catch(Exception e){
			throw new UIException(e);
		}
		setAttribute("componentPath", path);
	}
	
	
	public void setComponentPath(String path, String src){
		try{
			Container  c= DesignableUtil.buildContainer(new ByteArrayInputStream(src.getBytes()), false);
			this.getChildren().clear();
			setRendered(false);
			addChild(c);
			
			}catch(Exception e){
				throw new UIException(e);
			}
			setAttribute("componentPath", path);
	}	

}
