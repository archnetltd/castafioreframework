package org.castafiore.agenda;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXWeekView extends EXContainer{
	
	private final static String[] days = new String[]{"time","dim.", "lun.", "mar.", "mer.", "jeu.", "ven.", "sam."};

	public EXWeekView(String name, Calendar calendar) {
		super(name, "div");
		
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DATE, (day-1)*-1);
		
		Container body = new EXContainer("agendabody", "div").setAttribute("style", "overflow-x: hidden; overflow-y: scroll; height: 400px; float: left; width: 718px;");
		addChild(body);
		Container timerow = new EXContainer("timerow", "div").addClass("timerow").setStyle("width", "702px").setStyle("border-bottom", "none");
		body.addChild(timerow);
		
		SimpleDateFormat format = new SimpleDateFormat("dd");
		for(int i = 1; i <=8;i++){
			
			Container time = new EXContainer("time", "div").addClass("time").setStyle("width", "95px");
			if(i == 1){
				time.setStyle("width", "25px");
			}
			
			time.addChild(new EXContainer("txt", "div").addClass("timetxt").setText(days[i-1] + " " + format.format(calendar.getTime())));
			timerow.addChild(time);
			calendar.add(Calendar.DATE, 1);
		}
		
		
		for(int i = 1; i <=24; i++){
			Container row = new EXContainer("timerow", "div").addClass("timerow").setStyle("height", "120px").setStyle("width", "702px").setStyle("border-bottom", "none");
			for(int j = 1; j<=8; j++){
				
				Container time = new EXContainer("time", "div").addClass("time").setStyle("height", "120px").setStyle("width", "95px").setStyle("background", "none");
				
				
				time.addChild(new EXContainer("col" + (j-2) + "-" + (i-1), "div").setStyle("height", "120px").addClass("timetxt").setStyle("padding", "0px"));
				row.addChild(time);
				
				if(j == 1){
					time.setStyle("width", "25px");
					String s = i + "";
					if(s.length() == 1){
						s = "0" + s;
					}
					s = s + " h";
					
					
					time.getDescendentByName("col" + (j-2) + "-" + (i-1)).setText(s).setStyle("padding-left", "1px");
					
				}
			}
			
			getDescendentByName("agendabody").addChild(row);
		}
		
		setDate(calendar);
		
		
	}
	
	
	public void setDate(Calendar date){
		List<AgendaEvent> events = SpringUtil.getBeanOfType(AgendaService.class).getEvents(date, Calendar.WEEK_OF_MONTH);
		String[] alph = new String[]{"red", "blue", "green", "yellow", "beige", "marron", "steelblue", "RoyalBlue", "SaddleBrown", "Salmon", "SandyBrown", "SeaGreen", "SeaShell", "Sienna", "Silver", "SkyBlue"};
		Random rand = new Random();
		 
		for(AgendaEvent evt : events){
			
			
			
			
			Calendar start = evt.getStartTime();
			Calendar end = evt.getEndTime();
			
			
			//the column to start widht
			int startDay = start.get(Calendar.DAY_OF_WEEK);
			int endDay = end.get(Calendar.DAY_OF_WEEK);
			
			int col = startDay+1;
			
			int width = ((endDay - startDay) +1)*95;
			
			
			int startHour = start.get(Calendar.HOUR);
			if(start.get(Calendar.AM_PM) == Calendar.PM){
				startHour = startHour + 12;
			}
			//int paddingTop = (startHour)*35;
			
			
			int endHour = end.get(Calendar.HOUR);
			if(end.get(Calendar.AM_PM) == Calendar.PM){
				endHour = endHour + 12;
			}
			
			
			int height = 25;
			
			String color =  alph[rand.nextInt(16)] ;
			Container li = new EXContainer("d", "li").setText(evt.getTitle()).setStyle("background", color);
			
			
		
			//getDescendentByName("col" + col + "-" + (startHour)).addChild(div);
			Container parent = getDescendentByName("col" + col + "-" + (startHour));
			Container ul = null;
			
			if(parent.getChildren().size() >0){
				ul = parent.getChild("ul");
			}else{
				ul = new EXContainer("ul", "ul").setStyle("list-style", "none").setStyle("padding", "0px").setStyle("margin", "0px");
				parent.addChild(ul);
			}
			
			ul.addChild(li.setStyle("padding", "4px 2px").setStyle("margin", "4px 2px").setStyle("color", "black"));
			
			
			
			
		}
		
		
	}
	
	
	public void addHeader(){
		
	}

}
