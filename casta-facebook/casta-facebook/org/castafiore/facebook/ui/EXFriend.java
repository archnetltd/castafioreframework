package org.castafiore.facebook.ui;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.EXCheckBox;

public class EXFriend extends EXContainer{

	public EXFriend(String name, Map<String,String> friend) {
		super(name,"div");
		
		EXGrid grid = new EXGrid("", 3, 1);
		
		addChild(grid);
		setFriend(friend);
		
	}
	
	public void setFriend(Map<String,String> friend){
		EXGrid grid = getDescendentOfType(EXGrid.class);
		grid.getCell(0, 0).setStyle("width", "124px").setText("<img src=" + friend.get("pic") + "></img>").setStyle("padding", "12px");
		
		grid.getCell(1, 0).setStyle("width", "250px").setText(friend.get("first_name") + " " + friend.get("last_name")).setStyle("vertical-align", "middle").setStyle("padding", "12px");
		
		
		Container c = grid.getCell(2, 0);
		if(c.getChildren().size() > 0){
			c.getChildren().clear();
			
		}else
			c.setStyle("padding", "12px").setStyle("width", "60px").setStyle("text-align", "center").setStyle("vertical-align", "middle");
		c.addChild(new EXCheckBox("df"));
		
		setRendered(false);
	}

}
