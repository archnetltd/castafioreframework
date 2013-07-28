package org.castafiore.designer.script;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
 
public class ScriptServiceImpl implements ScriptService {
	
	private GroovyShell shell;

	private RepositoryService repositoryService;
	
	
	private ContextManager contextManager;




	public ContextManager getContextManager() {
		return contextManager;
	}


	public void setContextManager(ContextManager contextManager) {
		this.contextManager = contextManager;
	}


	public void setShell(GroovyShell shell) {
		this.shell = shell;
	}


	@Override
	public List<BinaryFile> getScripts(String portalPath) {
		BinaryFile portal = (BinaryFile) repositoryService.getFile(portalPath,
				Util.getRemoteUser());
		Directory modules = (Directory) portal.getFile("modules");
		List<BinaryFile> result = new ArrayList<BinaryFile>();
		if (modules != null) {
			FileIterator iter = modules.getFiles(BinaryFile.class);
			while (iter.hasNext()) {
				BinaryFile module = (BinaryFile) iter.next();
				result.add(module);
			}
		}

		return result;
	}
	
	
	public GroovyShell getShell(){
		return shell;
	}

	@Override
	public void saveScript(String name, byte[] script, String portalPath) {

		try {
			BinaryFile portal = (BinaryFile) repositoryService.getFile(
					portalPath, Util.getRemoteUser());
			Directory modules = (Directory) portal.getFile("modules");

			if (modules == null) {
				modules = portal.createFile("modules", Directory.class);//new Directory();
				//modules.setName("modules");
				modules.makeOwner(Util.getRemoteUser());
				//portal.addChild(modules);
			}

			BinaryFile bf = (BinaryFile) modules.getFile(name);
			if (bf == null) {
				bf = modules.createFile(name, BinaryFile.class);//new BinaryFile();
				//bf.setName(name);
				bf.makeOwner(Util.getRemoteUser());
				//modules.addChild(bf);

			}
			bf.write(script);
			portal.save();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Override
	public BinaryFile getScript(String name, String portalPath) {
		try {
			BinaryFile portal = (BinaryFile) repositoryService.getFile(
					portalPath + "/modules/" + name, Util.getRemoteUser());
			return portal;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public BinaryFile getJavascript(String name, String portalPath) {
		try {
			BinaryFile portal = (BinaryFile) repositoryService.getFile(
					portalPath + "/javascript/" + name, Util.getRemoteUser());
			return portal;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 
	public void evaluateModules(PortalContainer pc){
		try{
			
			String portalPath = pc.getDefinitionPath();
			
				
				
				Binding b = contextManager.getCurrentContext(pc);
				List<BinaryFile> modules = getScripts(portalPath);
				shell.getContext().getVariables().clear();
				shell.getContext().getVariables().putAll(b.getVariables());
				for (BinaryFile module : modules) {
					InputStream in = module.getInputStream();
					
					shell.evaluate(new InputStreamReader(in));
				}
				
			
			
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}

	public void executeEvent(String template, Container component, Map<String,String> params) {
		try {
			
			
			Binding b = contextManager.getCurrentContext(component);
			shell.getContext().getVariables().clear();
			shell.getContext().getVariables().putAll(b.getVariables());
			shell.getContext().getVariables().putAll(params);
			shell.evaluate(template);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new UIException(e);
		}

		
	}


	@Override
	public List<BinaryFile> getJavascripts(String portalPath) {
		BinaryFile portal = (BinaryFile) repositoryService.getFile(portalPath,
				Util.getRemoteUser());
		Directory modules = (Directory) portal.getFile("javascripts");
		List<BinaryFile> result = new ArrayList<BinaryFile>();
		if (modules != null) {
			FileIterator iter = modules.getFiles(BinaryFile.class);
			while (iter.hasNext()) {
				BinaryFile module = (BinaryFile) iter.next();
				result.add(module);
			}
		}

		return result;
	}


	@Override
	public void saveJavascript(String name, byte[] script, String portalPath) {

		try {
			BinaryFile portal = (BinaryFile) repositoryService.getFile(
					portalPath, Util.getRemoteUser());
			Directory modules = (Directory) portal.getFile("javascripts");

			if (modules == null) {
				modules = portal.createFile("javascripts", Directory.class);//new Directory();
				//modules.setName("modules");
				modules.makeOwner(Util.getRemoteUser());
				//portal.addChild(modules);
			}

			BinaryFile bf = (BinaryFile) modules.getFile(name);
			if (bf == null) {
				bf = modules.createFile(name, BinaryFile.class);//new BinaryFile();
				//bf.setName(name);
				bf.makeOwner(Util.getRemoteUser());
				//modules.addChild(bf);

			}
			bf.write(script);
			portal.save();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
