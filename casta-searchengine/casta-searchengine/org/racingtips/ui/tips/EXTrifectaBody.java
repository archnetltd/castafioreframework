package org.racingtips.ui.tips;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.racingtips.mtc.RaceCardItem;
import org.racingtips.tips.BankerTwo;
import org.racingtips.tips.SelectionFour;
import org.racingtips.tips.SelectionOne;
import org.racingtips.tips.SelectionThree;
import org.racingtips.tips.SelectionTwo;
import org.racingtips.tips.Trifecta;

public class EXTrifectaBody extends EXContainer{

	public EXTrifectaBody(String name) {
		super(name, "tbody");
		
	}
	public void addItem(Trifecta trif){
		String horseNumber = trif.getRaceNumber();
		Container row = getChild(horseNumber);
		if(row == null){
			row = new EXContainer(horseNumber, "tr");
			row.addClass("cardItem");
			addChild(row);
			row.addChild(new EXContainer("race", "td").setText(horseNumber));
			row.addChild(new EXContainer("banker1", "td"));
			row.addChild(new EXContainer("banker2", "td"));
			row.addChild(new EXContainer("sel1", "td"));
			row.addChild(new EXContainer("sel2", "td"));
			row.addChild(new EXContainer("sel3", "td"));
			row.addChild(new EXContainer("sel4", "td"));
		}
		RaceCardItem item = ((RaceCardItem)trif.getParent());
		String horse =  item.getHorse();
	
		String child = "banker1";
		if(trif instanceof BankerTwo){
			child = "banker2";
		}else if(trif instanceof SelectionOne){
			child = "sel1";
		}else if(trif instanceof SelectionTwo){
			child = "sel2";
		}else if(trif instanceof SelectionThree){
			child = "sel3";
		}else if(trif instanceof SelectionFour){
			child = "sel4";
		}
		row.getChild(child).setText(horse);
	}

}
