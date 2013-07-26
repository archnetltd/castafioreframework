package org.racingtips.mtc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Directory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.racingtips.mtc.horse.Form;
import org.racingtips.mtc.horse.Horse;
import org.racingtips.mtc.horse.Performance;
import org.racingtips.mtc.horse.Stable;
import org.racingtips.mtc.horse.TrackWork;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

import edu.emory.mathcs.backport.java.util.Collections;

public class MTCDTO {
	
	private List<Meeting> meetings = new ArrayList<Meeting>();
	
	private String ratingUrl = "http://www.mauritiusturfclub.com/index.php/en/racing/horse-rating-list";
	
	private String stablesDir = "/root/users/racingtips/stables";
	
	private VideoFeed feed = null;
	
	private Source horseRatingList;
	
	private String currentYear = "2011";
	
	private String _getCurrrentYearDir(){
		if(currentYear.equals("2011")){
			return "/root/users/racingtips";
		}else{
			return "/root/seasons/" + currentYear;
		}
	}
	
	
	public String getCurrentYear() {
		return currentYear;
	}


	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}


	public void importSeason(String year)throws Exception{
		//create dirs/
		currentYear = year;
		Directory seasons = SpringUtil.getRepositoryService().getDirectory(_getCurrrentYearDir(), Util.getRemoteUser());
		//Directory season = seasons.createFile(year, Directory.class);
		
		
		this.currentYear = year;
		
		List<Meeting> meetings = getMeetings();
		
		seasons.save();
		
		for(Meeting meeting : meetings){
			List<Race> races = meeting.getRaces();
			for(Race race : races){
				race.getRaceCard();
				race.getResult().getResultCard();
			}
		}
		seasons.save();
		
		//import meetings
		
		//import races
		
		//import results
	}
	
	public synchronized String getHorseRating(String horseId)throws Exception{
		if(horseRatingList == null){	
			String content = ResourceUtil.readUrl(ratingUrl);
			horseRatingList = new Source(content);
		}
		if(horseId == null || horseId.equals("nill")){
			return "-";
		}
		
		List<Element> ps = horseRatingList.getAllElements("p");
		
		for(Element p : ps){
			if(p.getAllElements("a").size() > 0){
				if(p.getAllElements("a").get(0).getAttributeValue("href").contains("horse_id=" + horseId)){
					return p.getAllElements("span").get(1).getTextExtractor().toString();
				}
			}
		}
		return null;
	}
	
	public List<Horse> getHorseRatingList(){
		QueryParameters params = new QueryParameters().setEntity(Horse.class).addOrder(Order.asc("horse"));
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		return result;
	}
	
	
	public void importStable(String id)throws Exception{
		Stable stable = SpringUtil.getRepositoryService().getDirectory(stablesDir, Util.getRemoteUser()).createFile(id, Stable.class);
		String url = "http://www.mauritiusturfclub.com/index.php/en/stables/" + id;
		String content = ResourceUtil.readUrl(url);
		Source source = new Source(content);
		
		List<Element> ps = source.getAllElements("p");
		String manager =null;

		for(Element p : ps){
			if(p.getTextExtractor().toString().contains("Stable manager")){
				manager = p.getTextExtractor().toString().replace("Stable manager", "").replace(":", "").trim();
			}
		}
		
		System.out.println(manager);
		stable.setStableManager(manager);
		stable.setStableId(id);
		stable.setStableName(source.getAllElements("h2").get(0).getTextExtractor().toString());
		
		
		List<Element> tables = source.getAllElements("table");
		List<Element> performances = tables.get(0).getAllElements("tbody").get(0).getAllElements("tr");
		for(Element perf : performances){
			List<Element> cells = perf.getAllElements("td");
			int index = 0;
			Performance p = null;
			for(Element cell : cells){
				if(index == 0){
					p = stable.createPerformance(cell.getTextExtractor().toString());
					p.setYear(cell.getTextExtractor().toString());
				}else if(index ==1 ){
					p.setEff(cell.getTextExtractor().toString());
				}else if(index ==2 ){
					p.setWinners(cell.getTextExtractor().toString());
				}else if(index ==3 ){
					p.setRank(cell.getTextExtractor().toString());
				}else if(index ==4 ){
					p.setEntries(cell.getTextExtractor().toString());
				}else if(index ==5 ){
					p.setWins(cell.getTextExtractor().toString());
				}else if(index ==6 ){
					p.setSecond(cell.getTextExtractor().toString());
				}else if(index ==7 ){
					p.setThird(cell.getTextExtractor().toString());
				}else if(index ==8 ){
					p.setFourth(cell.getTextExtractor().toString());
				}else if(index ==9 ){
					p.setUnplaced(cell.getTextExtractor().toString());
				}else if(index ==10 ){
					p.setStakesMoney(cell.getTextExtractor().toString());
				}else if(index ==11 ){
					p.setCups(cell.getTextExtractor().toString());
				}else if(index ==12 ){
					p.setPercentageFirst(cell.getTextExtractor().toString());
				}else if(index ==13 ){
					p.setSequence1(cell.getTextExtractor().toString());
				}else if(index ==14 ){
					p.setSequence2(cell.getTextExtractor().toString());
				}
				System.out.print(cell.getTextExtractor().toString()+ "\t\t");
				index ++;
			}
			System.out.println("");
		}
		
		
		List<Element> horses = tables.get(1).getAllElements("tbody").get(0).getAllElements("tr");
		for(Element horse : horses){
			List<Element> cells = horse.getAllElements("td");
			int index = 0;
			Horse h = null;
			for(Element cell : cells){
				System.out.print(cell.getTextExtractor().toString()+ "\t\t");
				
				if(index == 1){
					Element a = cell.getAllElements("a").get(0);
					String href = a.getAttributeValue("href");
					String horseId = StringUtils.splitByWholeSeparator(href, "horse_id=")[1];
					System.out.print(horseId+ "\t\t");
					h= stable.createHorse(horseId);
					h.setHorseId(horseId);
					h.setHorse(a.getTextExtractor().toString());
					h.setStable(manager);
				}else if(index == 2){
					h.setPedigree(cell.getTextExtractor().toString());
				}else if(index == 3){
					h.setAge(cell.getTextExtractor().toString());
				}else if(index == 4){
					h.setRating(cell.getTextExtractor().toString());
				}
				index ++;
			}
			System.out.println("");
		}
		stable.save();
	}
	
	public List<Stable> getStables(){
		//return SpringUtil.getRepositoryService().getDirectory(stableDir, Util.getRemoteUser()).getChildren(Stable.class).toList();
		return SpringUtil.getRepositoryService().getDirectory(stablesDir, Util.getRemoteUser()).getFiles(Stable.class).toList();
	}
	

	public  VideoFeed searchFeed() throws IOException,
	ServiceException {
		if(feed == null){
			YouTubeService service = new YouTubeService("gdataSample-YouTube-1");
			YouTubeQuery query = new YouTubeQuery(new URL("http://gdata.youtube.com/feeds/api/videos"));
			query.setOrderBy(YouTubeQuery.OrderBy.PUBLISHED);
			query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);
			query.setAuthor("3racingtips");
			feed= service.query(query, VideoFeed.class);
		}
		return feed;
	
	}
	
	public void kill(){
		meetings.clear();
	}
	
	
	public List<RaceResultCardItem> getForms(String horseId, int page){
		int pageSize = 5;
		int start = pageSize*page;
		
		QueryParameters params = new QueryParameters().setEntity(RaceResultCardItem.class).setFirstResult(start).setMaxResults(pageSize).addRestriction(Restrictions.eq("horseId", horseId));
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		return result;
	}
	
	
	public List<TrackWork> getTrackWork(String horseId){
		QueryParameters params = new QueryParameters().setEntity(TrackWork.class).addRestriction(Restrictions.eq("horseId", horseId)).addOrder(Order.asc("workNumber"));
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		return result;
	}
	
	
	public Horse getHorse(String horseId){
		QueryParameters params = new QueryParameters().setEntity(Horse.class).addRestriction(Restrictions.eq("horseId", horseId));
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		if(result.size() > 0){
			return (Horse)result.get(0);
		}else{
			return null;
		}
	}
	public void importForms(InputStream in, String horseId, String horseName)throws Exception{
		Source source = new Source(in);
		Horse dir = getHorse(horseId);
		//Directory dir = SpringUtil.getRepositoryService().getDirectory(formsDir, Util.getRemoteUser());
		List<Element> works = source.getAllElements("tbody").get(1).getAllElements("tr");
		QueryParameters params = new QueryParameters().setEntity(Form.class).addRestriction(Restrictions.eq("horseId", horseId)).addOrder(Order.asc("formNumber"));
		
		List existing = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		int size = existing.size();
		if(works.size() > size){
			size = works.size();
		}
		
		for(int i = 0; i < size; i ++){
			Form work = null;
			if(i >= existing.size()){
				work = dir.createFile(horseId + "-" + i, Form.class);
			}else{
				work = (Form)existing.get(i);
			}
			List<Element> cells = works.get(i).getAllElements("td");
			
			work.setHorseId(horseId);
			work.setHorse(horseName);
			work.setFormNumber((i + 1) + "");
			work.setDate(cells.get(1).getTextExtractor().toString());
			work.setPosition(cells.get(2).getTextExtractor().toString());
			work.setHorseWeight(cells.get(3).getTextExtractor().toString());
			work.setWeight(cells.get(4).getTextExtractor().toString());
			work.setDistance(cells.get(5).getTextExtractor().toString());
			work.setDraw(cells.get(6).getTextExtractor().toString());
			work.setCls(cells.get(7).getTextExtractor().toString());
			work.setJockey(cells.get(8).getTextExtractor().toString());
			work.setWinner(cells.get(9).getTextExtractor().toString());
			work.setMargin(cells.get(10).getTextExtractor().toString());
			work.setTime(cells.get(11).getTextExtractor().toString());
			work.setOpeningOdds(cells.get(12).getTextExtractor().toString());
			work.setClosingOdds(cells.get(13).getTextExtractor().toString());
			work.save();
		}
	}
	
	
	public Meeting getCurrentMeeting(){
		QueryParameters param = new QueryParameters().setEntity(Meeting.class).addRestriction(Restrictions.eq("status", Meeting.MEETING_CURRENT));
		List result = SpringUtil.getRepositoryService().executeQuery(param, Util.getRemoteUser());
		if(result.size()> 0){
			return (Meeting)result.get(0);
		}return null;
	}
	
	public void importTrackWork(InputStream in, String horseId, String horseName)throws Exception{
		Source source = new Source(in);
		Directory dir = getHorse(horseId);
		List<Element> works = source.getAllElements("tbody").get(0).getAllElements("tr");
		QueryParameters params = new QueryParameters().setEntity(TrackWork.class).addRestriction(Restrictions.eq("horseId", horseId)).addOrder(Order.asc("workNumber"));
		
		List existing = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		int size = existing.size();
		if(works.size() > size){
			size = works.size();
		}
		
		for(int i = 0; i < size; i ++){
			TrackWork work = null;
			if(i >= existing.size()){
				work = dir.createFile("tw-" + horseId + "-" + i, TrackWork.class);
			}else{
				work = (TrackWork)existing.get(i);
			}
			List<Element> cells = works.get(i).getAllElements("td");
			
			work.setWorkNumber((i + 1) + "");
			work.setDate(cells.get(1).getTextExtractor().toString());
			work.setJockey(cells.get(2).getTextExtractor().toString());
			work.setHorseId(horseId);
			work.setHorse(horseName);
			work.setM1000(cells.get(10).getTextExtractor().toString());
			work.setM800(cells.get(11).getTextExtractor().toString());
			work.setM600(cells.get(12).getTextExtractor().toString());
			work.setM400(cells.get(13).getTextExtractor().toString());
			work.setM200(cells.get(14).getTextExtractor().toString());
			work.save();
		}
	}
	
	public void importNominations(String meeting, String meetingId)throws Exception{
		Directory dir = SpringUtil.getRepositoryService().getDirectory(_getCurrrentYearDir() + "/nominations", Util.getRemoteUser());
		Directory meetingdir = dir.createFile(meeting, Directory.class);
		
		
		String url = "http://www.mauritiusturfclub.com/index.php?year="+meetingId+"&option=com_mtc_nds&view=all&Itemid=&lang=en";
		Source source = new Source(ResourceUtil.readUrl(url));
		
		List<NominationRace> urls = new ArrayList<NominationRace>();
		NominationRace r = meetingdir.createFile("1", NominationRace.class);
		r.setRaceNumber("1");
		r.setNominationUrl(url);
		urls.add(r);
		List<Element> divs = source.getAllElements("div");
		int index = 2;
		for(Element div : divs){
			if("racelist".equalsIgnoreCase(div.getAttributeValue("id"))){
				List<Element> links = div.getAllElements("a");
				for(Element link : links){
					if( link.getAttributeValue("href") != null){
						NominationRace race = meetingdir.createFile(index + "", NominationRace.class);//new Race(this);
						race.setRaceNumber(index + "");
						String href = link.getAttributeValue("href");
						String itemid = href.replace("/index.php/en/component/mtc_nds/?view=all&race_id=", "");
						String ttUrl = "http://www.mauritiusturfclub.com/index.php/en/component/mtc_nds/?view=all&race_id="+itemid+"&year=" + meetingId;
						System.out.println(ttUrl);
						race.setNominationUrl(ttUrl);
						urls.add(race);
						index++;
					}
				}
				break;
			}	
		}
		dir.save();
	}
	
	
	public void resynchNomination(String meeting){
		String dir = _getCurrrentYearDir() + "/nominations" + "/" + meeting;
		Directory meetingDir = SpringUtil.getRepositoryService().getDirectory(dir, Util.getRemoteUser());
		List<NominationRace> races = meetingDir.getFiles(NominationRace.class).toList();
		for(NominationRace r : races){
			r.resynchNomination();
		}
	}
	
	public NominationRace getNominationRace(String meeting, String raceNumber){
		String dir = _getCurrrentYearDir() + "/nominations" + "/" + meeting + "/" + raceNumber;
		return (NominationRace)SpringUtil.getRepositoryService().getFile(dir, Util.getRemoteUser());
		
	}
	
	public List<Meeting> getSelectedMeetings()throws Exception{
		List<Meeting> allMeetings = getMeetings();
		
		List<Meeting> result = new ArrayList<Meeting>();
		for(Meeting m : allMeetings){
			if(m.getStatus() != Meeting.MEETING_WAITING){
				result.add(m);
			}
		}
		Collections.sort(result);
		return result;
	}
	
	public List<Meeting> getMeetings()throws Exception {
		
		if(meetings.size() <= 0){
			loadMeetings();
		}
		return meetings;
	}
	public void  loadMeetings()throws Exception{
		Directory meetingdir = SpringUtil.getRepositoryService().getDirectory(_getCurrrentYearDir() + "/meetings", Util.getRemoteUser());
		
		Directory tipsdir = SpringUtil.getRepositoryService().getDirectory("/root/users/racingtips/tips", Util.getRemoteUser());
		Directory lmtipsdir = SpringUtil.getRepositoryService().getDirectory("/root/users/racingtips/lastminutetips", Util.getRemoteUser());
		meetings = meetingdir.getFiles(Meeting.class).toList();
		if(meetings.size() > 0){
			return;
		}
		String murl = "http://www.mauritiusturfclub.com/index.php/en/racing/racecards";
		if(!currentYear.equals("2011")){
			murl = "http://www.mauritiusturfclub.com/index.php/racing/index.php?year="+currentYear+"&option=com_mtc_racecards&view=all&Itemid=&lang=en";
			
			
		}
		String content = ResourceUtil.readUrl(murl);
		
		Source source = new Source(content);
		List<Element> selects = source.getAllElements("select");
		
		if(selects.size() > 0 ){
			List<Element> options = selects.get(0).getAllElements("option");
			
			for(Element option : options){
				String label = option.getTextExtractor().toString();
				if(!label.startsWith("--") && !label.toLowerCase().startsWith("season")){
					Meeting meeting = meetingdir.createFile(label,Meeting.class);
					tipsdir.createFile(label, Directory.class);
					lmtipsdir.createFile(label, Directory.class);
					meeting.setMeetingId(option.getAttributeValue("value"));
					meeting.setLabel(option.getTextExtractor().toString());
					meetings.add(meeting);
				}
				
			}
			
			meetingdir.save();
			tipsdir.save();
			lmtipsdir.save();
		}
	}
	
	
	
	public static void main(String[] args)throws Exception {
		MTCDTO d = new MTCDTO();
		List<Meeting> meetings = d.getMeetings();
		
		for(Meeting m : meetings){
			
			List<Race>  races = m.getRaces();
			for(Race r : races){
				long start = System.currentTimeMillis();
				List<RaceCardItem> card = r.getRaceCard();
				System.out.println(card);
				System.out.println("took : " + (System.currentTimeMillis() - start));
			}
			
			break;
		}
		
		
	}
}
