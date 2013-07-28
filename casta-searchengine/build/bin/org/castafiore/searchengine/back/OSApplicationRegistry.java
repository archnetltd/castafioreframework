package org.castafiore.searchengine.back;

import java.io.Serializable;

import org.castafiore.ui.ex.panel.Panel;

public interface OSApplicationRegistry extends Serializable{
	
	public Panel getWindow(String appName);

}
