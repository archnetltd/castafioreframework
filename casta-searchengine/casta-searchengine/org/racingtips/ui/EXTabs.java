package org.racingtips.ui;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXTabs extends EXXHTMLFragment implements Event{

	public EXTabs(String name, String sourceDir) {
		super(name, "templates/racingtips/EXTab.xhtml");
		try{
		
			createTabs(sourceDir);
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}
	
	
	
	private void createTabs(String source)throws Exception{
		Directory dir = SpringUtil.getRepositoryService().getDirectory(source, Util.getRemoteUser());
		List<Directory> tabs = dir.getFiles(Directory.class).toList();
		Container uiTabs = new EXContainer("tabs", "ul").addClass("tabs-cont");
		addChild(uiTabs);
		Directory toOpen = null;
		int index = 0;
		for(Directory tab : tabs){
			
			Container li = new EXContainer("", "li").setText("<a href=\"#tab1\">"+tab.getName()+"</a>").setAttribute("path", tab.getAbsolutePath()).addEvent(this, CLICK);
			uiTabs.addChild(li);
			if(index == 0){
				toOpen = tab;
				li.addClass("active");
			}
			index++;
		}
		
		Container tabContent = new EXContainer("tabContent", "div").addClass("tab_content");
		addChild(tabContent);
		openTab(toOpen);
		
	}
	
	public void openTab(Container source)throws Exception{
		String dir = source.getAttribute("path");
		for(Container c : source.getParent().getChildren()){
			c.setStyleClass(" ");
		}
		Directory d = SpringUtil.getRepositoryService().getDirectory(dir, Util.getRemoteUser());
		source.addClass("active");
		openTab(d);
	}

	public void openTab(Directory dir)throws Exception{
		List<BinaryFile> files = dir.getFiles(BinaryFile.class).toList();
		
		Container tabContent = getChild("tabContent");
		tabContent.getChildren().clear();
		tabContent.setRendered(false);
		for(BinaryFile bf : files){
			
			String content = IOUtil.getStreamContentAsString(bf.getInputStream());
			Properties prop = new Properties();
			prop.load(new ByteArrayInputStream(content.getBytes()));
			if(prop.containsKey("date") && prop.containsKey("title")){
				String date = prop.getProperty("date");
				tabContent.addChild(new EXContainer("", "div").addClass("date").setText(date));
				String title = prop.getProperty("title");
				tabContent.addChild(new EXContainer("", "div").addClass("tab-event").setText(title));
			}else{
				
				tabContent.addChild(new EXContainer("", "div").addClass("tab-event").setText(content));
			}
			
			
			
			
			
			
			
			
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}



	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			openTab(container);
			return true;
		}catch(Exception e ){
			throw new UIException(e);
		}
	}



	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
