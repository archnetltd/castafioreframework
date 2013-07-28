package org.castafiore.designer.script;

import groovy.text.Template;

import java.util.List;
import java.util.Map;

import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.ui.Container;
import org.castafiore.wfs.types.BinaryFile;

public interface ScriptService {
	
	public void saveScript(String name, byte[] script, String portalPath);
	
	public List<BinaryFile> getScripts(String portalPath);
	
	public BinaryFile getScript(String name, String portalPath);

	public void evaluateModules(PortalContainer pc);
	
	public void executeEvent(String template, Container component, Map<String,String> params);
	
	
	public List<BinaryFile> getJavascripts(String portalPath);
	
	public void saveJavascript(String name, byte[] script, String portalPath);
	
	public BinaryFile getJavascript(String name, String portalPath);

}
