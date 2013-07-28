package org.castafiore.designer.script;

import groovy.lang.Binding;

import java.io.Serializable;

import org.castafiore.ui.Container;

public interface ContextManager extends Serializable{
	
	public Binding getCurrentContext(Container component);
	
	

}
