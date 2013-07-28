package org.castafiore.designer.macro.ui;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.castafiore.KeyValueType;
import org.castafiore.designer.macro.EventMacro;
import org.castafiore.designer.macro.MacroService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.AutoCompleteSource;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXMacroConfigurator extends EXContainer{

	public EXMacroConfigurator(String name, EventMacro macro) {
		super(name, "div");
		
		setStyle("float", "left");
		
		addChild(new EXAutoComplete("event", macro.getName()).setSource(new AutoCompleteSource() {
			
			@Override
			public JArray getSource(String param) {
				Collection<EventMacro> macros = SpringUtil.getBeanOfType(MacroService.class).getMacros();
				JArray array = new JArray();
				Iterator<EventMacro> i =macros.iterator();
				while(i.hasNext()){
					EventMacro m = i.next();
					array.add(m.getName());
				}
				
				return array;
			}
		}).setStyle("float", "left"));
		
		
		List<KeyValueType> params = macro.getParameters();
		
		
		
	}

}
