package org.castafiore.designer.info;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.ComponentUtil;

public class EXPaddingMarginInfo extends EXContainer{

	private final static String[][] GRID = new String[][]{
		{"top", "bottom"}, 
		{"left", "right"}
	};
	
	
	public EXPaddingMarginInfo(String name, Container container, String type) {
		super(name, "div");
		addClass("dimension-content");
		for(int i = 0; i < GRID.length; i ++){
			String[] row = GRID[i];
			Container uirow = new EXContainer("", "div").addClass("content-row");
			addChild(uirow);
			for(int j = 0; j < row.length; j ++){
				Container col = new EXContainer("", "div").addClass("col" + (j +1));
				uirow.addChild(col);
				String label = type + " " + GRID[i][j];
				col.addChild(new EXContainer("", "div").setText(label));
				String stName = type + "-" + GRID[i][j];
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
