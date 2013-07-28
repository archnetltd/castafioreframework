package org.castafiore.designer.macro;

import java.util.LinkedList;
import java.util.List;

import org.castafiore.KeyValueType;
import org.castafiore.SimpleKeyValueType;
import org.castafiore.designer.events.GroovyEvent;
import org.castafiore.ui.events.Event;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Value;




public class EventMacoFile extends Article implements EventMacro {

	private String category;

	@Override
	public String getHelp() {
		
		return getSummary();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUniqueId() {
		return getAbsolutePath();
	}

	

	@Override
	public List<KeyValueType> getParameters() {
		List<Value> vals = getFiles(Value.class).toList();
		List<KeyValueType> types = new LinkedList<KeyValueType>();
		for(Value val : vals){
			SimpleKeyValueType t = new SimpleKeyValueType(val.getAbsolutePath(), val.getName(), val.getType());
			types.add(t);
		}
		return types;
	}

	
	@Override
	public Event getEvent() {
		GroovyEvent event = new GroovyEvent(getDetail(), Event.CLICK);
		return event;
	}

	

}
