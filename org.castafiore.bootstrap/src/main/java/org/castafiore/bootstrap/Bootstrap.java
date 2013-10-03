package org.castafiore.bootstrap;

import org.castafiore.bootstrap.example.TableExample;
import org.castafiore.bootstrap.layout.BTLayout;
import org.castafiore.ui.EXApplication;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.utils.ResourceUtil;

public class Bootstrap extends EXApplication{

	
	private BTLayout root = new BTLayout("root", "12,3:9,12");
	
	public Bootstrap() {
		super("bootstrap");
		
	}
	
	
	
	public void onReady(ClientProxy p){
		p.getCSS("http://getbootstrap.com/assets/css/docs.css");
		p.getScript(ResourceUtil.getDownloadURL("classpath", "lib/bootstrap/bootstrap.min.js"), p.clone());
		p.getCSS(ResourceUtil.getDownloadURL("classpath", "lib/bootstrap/bootstrap.min.css"));
		
	}



	@Override
	public void initApp() {
		addChild(root); 
		root.addChild(new TableExample(), "1,1");
	}

}
