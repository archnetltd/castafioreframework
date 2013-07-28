package org.castafiore.sliders.nivoslider;

import java.util.Map;

import org.castafiore.ui.Container;

public interface EventListener {
	
	public void execute(Container source, Map<String, String> parameters);
	
	public String getType();

}
