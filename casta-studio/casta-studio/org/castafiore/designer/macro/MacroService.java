package org.castafiore.designer.macro;

import java.io.Serializable;
import java.util.Collection;

public interface MacroService  extends Serializable{
	
	
	public Collection<EventMacro> getMacros();
	
	public EventMacro getMacro(String eventId);

}
