package org.castafiore.ui.tabbedpane;

import java.io.Serializable;

import org.castafiore.ui.Container;

public interface TabContentDecorator extends Serializable{
	
	public void decorateContent(Container contentContainer);

}
