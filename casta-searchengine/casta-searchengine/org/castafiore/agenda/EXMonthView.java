package org.castafiore.agenda;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;

public class EXMonthView extends EXContainer{

	public EXMonthView(String name, Calendar date) {
		super(name, "div");
		
		addChild(new EXContainer("title", "h1").setText(new SimpleDateFormat("MMMM yyyy").format(date.getTime())));
		EXGrid grid = new EXGrid("grid", 7,5);
		
		date.set(Calendar.DATE, 1);
		date.add(Calendar.DATE, (date.get( Calendar.DAY_OF_WEEK)-1)*-1 );
		addChild(grid);
		SimpleDateFormat format = new SimpleDateFormat("EEE dd");
		int counter = 0;
		for(int j =0; j < 5;j++){
			for(int i =0; i < 7;i++){
				date.add(Calendar.DATE, 1);
				Container cell = grid.getCell(i, j);
				cell.addChild(new EXContainer("label", "label").setStyle("float", "right").setText(format.format(date.getTime())));
				cell.setStyle("width", "100px").setStyle("height", "100px");
				cell.setStyle("vertical-align", "top");
				if(i <6){
					cell.setStyle("border-right", "1px solid gray");
				}
				cell.setStyle("border-top", "1px solid gray");
				if(j == 4){
					cell.setStyle("border-bottom", "1px solid gray");
				}
				counter = counter+1;
			}
		}
		
	}
	
	

}
