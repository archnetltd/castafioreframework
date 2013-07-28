package org.racingtips.mtc.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.Meeting;
import org.racingtips.mtc.Race;
import org.racingtips.mtc.RaceResultCardItem;

public class EXForms extends EXXHTMLFragment implements EventDispatcher{

	public EXForms(String name, String horseId) {
		super(name,"templates/racingtips/EXForms.xhtml");
		Container save = new EXContainer("save","a").setText("<img style=\"padding: 4px 0\" src=\"icons-2/fugue/icons/tick.png\"></img>").addEvent(DISPATCHER, Event.CLICK);
		addChild(save);
		Container tbody = new EXContainer("formsItem", "tbody");
		addChild(tbody);
		setAttribute("horseId", horseId);
		setAttribute("page", "-1");
		
		addChild(new EXContainer("morePage", "button").setStyle("margin", "auto").addClass("form-submit").setText("Show more Forms").addEvent(DISPATCHER, Event.CLICK));
		addPage();
	}
	
	
	public void addPage(){
		int curPage = Integer.parseInt(getAttribute("page"));
		String horseId = getAttribute("horseId");
		Container tbody = getChild("formsItem");
		curPage = curPage + 1;
		List<RaceResultCardItem> forms = SpringUtil.getBeanOfType(MTCDTO.class).getForms(horseId, curPage);
		if(forms.size() > 0){
			
			if(forms.size() < 5){
				Container morePage = getChild("morePage");
				morePage.setDisplay(false);
			}
			setAttribute("page", curPage + "");
			for(RaceResultCardItem form : forms){
				EXContainer row1 = new EXContainer("r1", "tr");
				Race race = (Race)form.getParent().getParent();
				Meeting meeting = (Meeting)race.getParent();
				String name = StringUtils.split(meeting.getName(), "-")[1].trim();
				Date ddate = null;
				try{
					ddate = new SimpleDateFormat("EEEE, dd MMMM yyyy").parse(name);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
				String date = "-";
				if(ddate != null){
					date = new SimpleDateFormat("dd-MM-yyyy").format(ddate);
				}
				String jockey = form.getJockey();
				String distance = race.getLength();
				String weightJockey =form.getWeight();
				String weightHorse =  form.getHorseWeight();
				String draw = form.getDraw();
				
				String number = form.getHorseNumber();
				
				String rank = form.getRank();
				String margin = form.getMargin();
				RaceResultCardItem winner  = race.getWinner();
				if(winner != null){
					if(winner.getHorseId().equals(form.getHorseId())){
						winner = race.getSecond();
					}
				}
				String sWinner = "-";
				if(winner != null){
					sWinner = winner.getHorse();
				}
				String m600 = form.getM600();
				String m400 = form.getM400();
				String time = form.getTime();
				row1.addChild(new EXContainer("date", "td").setText(date));
				row1.addChild(new EXContainer("jockey", "td").setText(jockey));
				row1.addChild(new EXContainer("jockeyWeight", "td").setText(weightJockey));
				row1.addChild(new EXContainer("horseNumber", "td").setText(number));
				row1.addChild(new EXContainer("rank", "td").setText(rank));
				row1.addChild(new EXContainer("600m", "td").setText(m600));
				row1.addChild(new EXContainer("winner", "td").setText(sWinner));
				tbody.addChild(row1);
				
				String comment = form.getSummary();
				String video = form.getVideo();
				
				EXContainer row2 = new EXContainer("row2", "tr");
				Container showFormButton = new EXContainer("showFormComment", "a").setAttribute("form", form.getAbsolutePath()).setText("<img src=\"icons-2/fugue/icons/sticky-note.png\"></img>").setAttribute("title", "Show comment").addEvent(DISPATCHER, Event.CLICK);
				Container playButton = new EXContainer("play", "a").setText("<img src=\"icons-2/fugue/icons/control.png\"></img>").setAttribute("form", form.getAbsolutePath()).setAttribute("title", "View race").addEvent(DISPATCHER, Event.CLICK);
				Container buttons = new EXContainer("buttons", "td").setStyle("border-top", "1px dotted");
				if(StringUtils.isNotEmpty(comment)){
					buttons.addChild(showFormButton);
				}
				if(StringUtils.isNotEmpty(video)){
					buttons.addChild(playButton);
				}
				row2.addChild(buttons);
				row2.addChild(new EXContainer("distance", "td").setStyle("border-top", "1px dotted").setText(distance));
				row2.addChild(new EXContainer("horseWeight", "td").setStyle("border-top", "1px dotted").setText(weightHorse));
				row2.addChild(new EXContainer("draw", "td").setStyle("border-top", "1px dotted").setText(draw));
				row2.addChild(new EXContainer("margin", "td").setStyle("border-top", "1px dotted").setText(margin));
				row2.addChild(new EXContainer("400m", "td").setStyle("border-top", "1px dotted").setText(m400));
				row2.addChild(new EXContainer("time", "td").setStyle("border-top", "1px dotted").setText(time));
				tbody.addChild(row2);
				
				if(StringUtils.isNotEmpty(comment)){
					EXContainer row3 = new EXContainer(form.getAbsolutePath() + "_r_3", "tr");
					row3.addChild(new EXContainer("resultComment", "td").setStyle("background", "beige").setAttribute("colspan", "7").setText(comment));
					row3.setStyle("display", "none");
					
					tbody.addChild(row3);
				}
			}
		}else{
			Container morePage = getChild("morePage");
			morePage.setText("No more forms available").getEvents().clear();
		}
	}
	
	
	public void showComment(String form){
		getDescendentByName(form + "_r_3").setDisplay(true);
	}
	
	public void playVideo(String form){
		
	}
	
	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("morePage")){
			addPage();
		}else if(source.getName().equalsIgnoreCase("showFormComment")){
			showComment(source.getAttribute("form"));
		}else if(source.getName().equalsIgnoreCase("play")){
			playVideo(source.getAttribute("form"));
		}
		
		
	}

}
