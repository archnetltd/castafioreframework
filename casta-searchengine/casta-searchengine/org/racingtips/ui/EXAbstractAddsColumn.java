package org.racingtips.ui;

import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.RefreshSentive;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

public abstract class EXAbstractAddsColumn extends EXContainer {

	public EXAbstractAddsColumn(String name) {
		super(name, "ul");
		QueryParameters params = new QueryParameters().setEntity(BinaryFile.class).addSearchDir(getDir());
		List<File> files = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		this.getChildren().clear();
		
		for(File f : files){
			EXXHTMLFragment fragment = new EXXHTMLFragment("", ResourceUtil.getDownloadURL("ecm", f.getAbsolutePath()));
			EXContainer li = new EXContainer("", "li");
			addChild(li);
			li.addChild(fragment);
		}
	}
	
	
	public abstract String getDir();




}
