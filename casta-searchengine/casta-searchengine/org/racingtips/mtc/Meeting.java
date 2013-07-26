package org.racingtips.mtc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.types.Directory;

@Entity
public class Meeting extends Directory implements Comparable<Meeting>{
	
	public final static int MEETING_COMPLETED = -1;
	
	public final static int MEETING_CURRENT = 145;
	
	public final static int MEETING_WAITING = 149;
	
	
	private String meetingId;
	
	private String label;
	
	private String year="2011";
	
	

	public String getYear() {
		if(StringUtils.isEmpty(year)){
			year = "2011";
		}
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public static int getMeetingCompleted() {
		return MEETING_COMPLETED;
	}

	public static int getMeetingCurrent() {
		return MEETING_CURRENT;
	}

	public static int getMeetingWaiting() {
		return MEETING_WAITING;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String id) {
		this.meetingId = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
	private List<Race> loadRaces()throws Exception{
		
		
		String url = "http://www.mauritiusturfclub.com/index.php/racing/index.php?year="+meetingId+"&option=com_mtc_racecards&view=all&Itemid=&lang=en";
		Source source = new Source(ResourceUtil.readUrl(url));
		
		List<Race> urls = new ArrayList<Race>();
		Race r = createFile("1", Race.class);
		r.setRaceNumber("1");
		r.setUrl(url);
		urls.add(r);
		List<Element> divs = source.getAllElements("div");
		int index = 2;
		for(Element div : divs){
			if("racelist".equalsIgnoreCase(div.getAttributeValue("id"))){
				List<Element> links = div.getAllElements("a");
				for(Element link : links){
					if( link.getAttributeValue("href") != null){
						Race race = createFile(index + "", Race.class);//new Race(this);
						race.setRaceNumber(index + "");
						String href = link.getAttributeValue("href");
						String itemid = href.replace("/index.php/en/component/mtc_racecards/?view=all&race_id=", "");
						String ttUrl = "http://www.mauritiusturfclub.com/index.php/en/component/mtc_racecards/?view=all&race_id="+itemid+"&year=" + meetingId;
						//String tmpUrl = "http://www.mauritiusturfclub.com/index.php/racing/index.php?year="+id+"&option=com_mtc_racecards&view=all&race_id="+itemid+"&lang=en";
						System.out.println(ttUrl);
						race.setUrl(ttUrl);
						urls.add(race);
						index++;
					}
				}
				break;
			}
			
			
		}
			
		save();
		return urls;
	}
	public List<Race> getRaces()throws Exception {
		
		List<Race> currentRaces = getFiles(Race.class).toList();
		if(currentRaces.size() <= 0){
			return loadRaces();
		}else{
			return currentRaces;
		}
		
		
	}
	
	public Race getRace(String index)throws Exception{
		Race r = (Race)getFile(index);
		if(r == null){
			loadRaces();
			return getRace(index);
		}else{
			return r;
		}
		
	}
	
	public String toString(){
		return label;
	}

	@Override
	public int compareTo(Meeting o) {
		return meetingId.compareTo(o.meetingId);
	}
	
	
}
