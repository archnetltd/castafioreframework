package org.castafiore.designer.info;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.ComponentUtil;

public class EXDimensionInfo extends EXContainer{

	private final static String[] PREFIX = new String[]{"", "min-", "max-"};
	private final static String[] PREFIX_LABEL = new String[]{"", "Min ", "Max "};
	
	private final static String[] TYPES = new String[]{"width", "height"};
	
	
	public EXDimensionInfo(String name, Container container) {
		super(name, "div");
		addClass("dimension-content");
		for(int i = 0; i < PREFIX.length; i ++){
			Container row = new EXContainer("", "div").addClass("content-row");
			addChild(row);
			for(int j = 0 ; j < TYPES.length; j ++){
				Container col = new EXContainer("", "div").addClass("col" + (j +1));
				row.addChild(col);
				String label = PREFIX_LABEL[i] + TYPES[j];
				col.addChild(new EXContainer("", "div").setText(label));
				String stName = PREFIX[i] + TYPES[j];
				EXInput input = new EXScrollableInput(stName,container);
				col.addChild(input);
			}	
		}
	}
	
	public void setContainer(Container item){
		List<Container> inputs = new ArrayList<Container>();
		ComponentUtil.getDescendentsOfType(this, inputs, EXScrollableInput.class);
		for(Container c : inputs){
			EXScrollableInput s = (EXScrollableInput)c;
			s.setContainer(item);
		}
	}
}
