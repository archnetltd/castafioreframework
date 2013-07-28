package org.castafiore.designer.script.ui;

import java.io.InputStream;
import java.util.List;

import org.castafiore.designer.EXCSSEditor;
import org.castafiore.designer.EXCodeMirror;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.script.ScriptService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.types.BinaryFile;

public class EXScriptEditor extends EXContainer{

	private String portalPath;
	
	private boolean javascript = false;
	
	public EXScriptEditor(String name, String portalPath, boolean javascript) {
		super(name, "div");
		try
		{
			DesignableUtil.generateFreescc(null);
			this.javascript = javascript;
			this.portalPath = portalPath;
			if(!javascript){
				List<BinaryFile> bfs = SpringUtil.getBeanOfType(ScriptService.class).getScripts(portalPath);
				addToolbar();
				addScriptsToolbar(bfs);
				
				addEditor(bfs);
			}else{
				List<BinaryFile> bfs = SpringUtil.getBeanOfType(ScriptService.class).getJavascripts(portalPath);
				addToolbar();
				addScriptsToolbar(bfs);
				
				addEditor(bfs);
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}
	
	private void addToolbar(){
		EXToolBar toolbar = new EXToolBar("tt");
		
		toolbar.removeClass("ui-corner-all");
		
		EXIconButton New = new EXIconButton("new", Icons.ICON_DOCUMENT);
		New.addEvent(new NewScriptEvent(), Event.CLICK);
		
		EXIconButton save = new EXIconButton("save", Icons.ICON_DISK);
		save.addEvent(new SaveScriptEvent(), Event.CLICK);
		
		EXButtonSet icons = new EXButtonSet("icons");
		icons.setTouching(true);
		icons.addItem(New).addItem(save);
		
		
		toolbar.addItem(icons);
		addChild(toolbar);
		
		//save.removeClass("ui-corner-all").removeClass("ui-corner-left").removeClass("ui-corner-right");
		//New.removeClass("ui-corner-all").removeClass("ui-corner-left").removeClass("ui-corner-right");
		//toolbar.setStyle("padding", "2px");
	}
	
	private void addScriptsToolbar(List<BinaryFile> bfs){
		EXToolBar toolbar = new EXToolBar("scripts");
		toolbar.removeClass("ui-corner-all");
		addChild(toolbar);
		
		EXButtonSet mm = new EXButtonSet("mm");
		mm.setTouching(true);
		toolbar.addItem(mm);
		for(BinaryFile bf : bfs){
			addScriptButton(bf.getName());
		}
		for(Container icon : mm.getChildren()){
			icon.removeClass("ui-corner-all").removeClass("ui-corner-left").removeClass("ui-corner-right");
		}
		toolbar.setStyle("padding", "2px");
		
	}
	
	public void addScriptButton(String name){
		
		EXButtonSet toolbar = (EXButtonSet)getDescendentByName("mm");
		EXIconButton icon = new EXIconButton(name,name);
		icon.addEvent(new OpenScriptEvent(), Event.CLICK);
		toolbar.addItem(icon);
		
		
		
	}
	
	
	public void saveCurrentScript(){
		String name = getAttribute("current");
		String script = getDescendentOfType(EXTextArea.class).getValue().toString().trim();
		if(javascript)
			SpringUtil.getBeanOfType(ScriptService.class).saveJavascript(name, script.getBytes(), portalPath);
		else
			SpringUtil.getBeanOfType(ScriptService.class).saveScript(name, script.getBytes(), portalPath);
		
	}
	
	public void openDefaultScript(){
		if(javascript){
			List<BinaryFile> modules = SpringUtil.getBeanOfType(ScriptService.class).getJavascripts(portalPath);
			if(modules.size() == 0){
				setAttribute("current", "Main.js");
				saveCurrentScript();
				openScript("Main.js");
			}else{
				openScript(modules.get(0).getName());
			}
		}else{
			List<BinaryFile> modules = SpringUtil.getBeanOfType(ScriptService.class).getScripts(portalPath);
			if(modules.size() == 0){
				setAttribute("current", "Main");
				saveCurrentScript();
				openScript("Main");
			}else{
				openScript(modules.get(0).getName());
			}
		}
		
		
	}
	
	public void openScript(String name){
		try{
			if(javascript){
				BinaryFile bf = SpringUtil.getBeanOfType(ScriptService.class).getJavascript(name, portalPath);
				if(bf != null){
					String script = IOUtil.getStreamContentAsString(bf.getInputStream());
					getDescendentOfType(EXTextArea.class).setValue(script);
					setAttribute("current", name);
					
					if(getAncestorOfType(EXPanel.class )!= null){
						getAncestorOfType(EXPanel.class).setTitle("Javascript - " + name);
					}
				}
			}else{
				BinaryFile bf = SpringUtil.getBeanOfType(ScriptService.class).getScript(name, portalPath);
				if(bf != null){
					String script = IOUtil.getStreamContentAsString(bf.getInputStream());
					getDescendentOfType(EXTextArea.class).setValue(script);
					setAttribute("current", name);
					
					if(getAncestorOfType(EXPanel.class )!= null){
						getAncestorOfType(EXPanel.class).setTitle("Groovy - " + name);
					}
				}
			}
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	

	
	private void addEditor(List<BinaryFile> bfs)throws Exception{
		EXTextArea area = new EXCodeMirror("script", "", "javascript");
		area.setWidth(Dimension.parse("576px")).setHeight(Dimension.parse("400px")).setStyle("padding", "0").setStyle("margin", "0");
		addChild(area);
		if(bfs.size() > 0){
			BinaryFile bf = bfs.get(0);
			InputStream in = bf.getInputStream();
			String script =IOUtil.getStreamContentAsString(in);
			area.setText(script);
			setAttribute("current", bf.getName());
			
			if(getAncestorOfType(EXPanel.class )!= null){
				getAncestorOfType(EXPanel.class).setTitle((javascript?"Javascript":"Groovy - ") + bf.getName());
			}
			getDescendentByName("mm").getChildByIndex(0).addClass("ui-state-active");
		}
	}

	public String getPortalPath() {
		return portalPath;
	}

	public void setPortalPath(String portalPath) {
		this.portalPath = portalPath;
	}
	
	

}
