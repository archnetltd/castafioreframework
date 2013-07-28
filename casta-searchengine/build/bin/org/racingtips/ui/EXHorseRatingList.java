package org.racingtips.ui;

import java.util.List;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.horse.Horse;
import org.racingtips.ui.cards.EXHorseCard;

public class EXHorseRatingList extends EXContainer implements Event{

	public EXHorseRatingList() {
		super("EXHorseRatingList", "div");
		
		List<Horse> horses = SpringUtil.getBeanOfType(MTCDTO.class).getHorseRatingList();
		//<p> <span class="hn">The Cardinal</span> <span class="rt">76</span></p>
		EXGrid grid = new EXGrid("grid", 2, 1);
		grid.getCell(0, 0).setStyle("border", "solid 1px").setStyle("font-size", "12px").setStyle("background", "silver");
		grid.getCell(1, 0).setStyle("border", "solid 1px").setStyle("font-size", "12px").setStyle("background", "silver");
		
		addChild(grid);
		int col = 0;
		for(Horse horse : horses){
			if(col == 2){
				col = 0;
			}
			Container  c = new EXContainer("", "p");
			c.setText("<span >"+horse.getHorse()+"</span> <span style=\"float:right\">"+horse.getRating()+"</span>").setAttribute("horseId", horse.getHorseId());
			c.setStyle("cursor", "pointer");
			c.addEvent(this, CLICK);
			add(c, col);
			col++;
		}
		
	}
	
	
	public void add(Container c, int col){
		getDescendentOfType(EXGrid.class).getCell(col, 0).addChild(c);
	}
	
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String horseId = container.getAttribute("horseId");
		EXHorseCard card = new EXHorseCard(horseId);
		container.getAncestorOfType(EXPageContainer.class).showPage(card, false);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
