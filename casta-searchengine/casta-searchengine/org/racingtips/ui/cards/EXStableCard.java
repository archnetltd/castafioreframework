package org.racingtips.ui.cards;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.racingtips.mtc.horse.Horse;
import org.racingtips.mtc.horse.Performance;
import org.racingtips.mtc.horse.Stable;
import org.racingtips.ui.EXPageContainer;

public class EXStableCard extends EXXHTMLFragment implements Event{

	public EXStableCard(Stable stable) {
		super("EXStableCard", "templates/racingtips/EXStableCard.xhtml");
		Container info = new EXContainer("info", "th");
		info.setText("Stable Name : "+stable.getStableName()+" </br>Stable manager: "+stable.getStableManager()+"</br>Trainer: " + stable.getTrainer());
		addChild(info);
		List<Performance> performances = stable.getPerformance();
		Container uiPerfs = new EXContainer("performances", "tbody");
		StringBuilder b = new StringBuilder();
		for(Performance perf : performances){
			b.append("<tr class=cardItem>");
			//Year 	Eff 	Winners 	Rank 	Entries 	Wins 	2nd 	3rd 	4th 	Unplaced 	Stakes Money (Rs) 	Cups 	%1sts 	Seq1 	Seq2
			b.append("<td>"+perf.getYear()+"</td>");
			b.append("<td>"+perf.getEff()+"</td>");
			b.append("<td>"+perf.getWinners()+"</td>");
			b.append("<td>"+perf.getRank()+"</td>");
			b.append("<td>"+perf.getEntries()+"</td>");
			b.append("<td>"+perf.getWins()+"</td>");
			b.append("<td>"+perf.getSecond()+"</td>");
			b.append("<td>"+perf.getThird()+"</td>");
			b.append("<td>"+perf.getFourth()+"</td>");
			b.append("<td>"+perf.getUnplaced()+"</td>");
			b.append("<td>"+perf.getStakesMoney()+"</td>");
			b.append("<td>"+perf.getCups()+"</td>");
			b.append("<td>"+perf.getPercentageFirst()+"</td>");
			b.append("<td>"+perf.getSequence1()+"</td>");
			b.append("<td>"+perf.getSequence2()+"</td>");
			b.append("</tr>");
		}
		uiPerfs.setText(b.toString());
		addChild(uiPerfs);
		
		List<Horse> horses = stable.getHorses();
		Container uiHorses = new EXContainer("horses", "tbody");
		
		int index = 1;
		for(Horse perf : horses){
			Container tr = new EXContainer("tr", "tr").addClass("cardItem");
			StringBuilder bh = new StringBuilder();
			//# 	Horse 	Pedigree 	Age 	Rating
			bh.append("<td>"+index +"</td>");
			bh.append("<td>"+perf.getHorse()+"</td>");
			bh.append("<td>"+perf.getPedigree()+"</td>");
			bh.append("<td>"+perf.getAge()+"</td>");
			bh.append("<td>"+perf.getRating()+"</td>");
			
			
			
			index++;
			tr.setText(bh.toString());
			tr.setAttribute("horseId", perf.getHorseId());
			tr.addEvent(this, CLICK);
			tr.setStyle("cursor", "pointer");
			uiHorses.addChild(tr);
		}
		
		addChild(uiHorses);
		
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
