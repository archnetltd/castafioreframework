package org.racingtips.mtc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.types.Article;

import edu.emory.mathcs.backport.java.util.Collections;

@Entity
public class Race extends Article {
	
	private String raceId;
	
	private String url;
	
	private String raceNumber;
	
	private String length;
	
	private String rating;
	
	private String time;
	

	public String getLength() {
		return length;
	}

	public String getRating() {
		return rating;
	}

	public String getTime() {
		return time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRaceNumber() {
		return raceNumber;
	}

	public void setRaceNumber(String raceNumber) {
		this.raceNumber = raceNumber;
	}
	
	public RaceResult getResult()throws Exception{
		List<RaceResult> results = getFiles(RaceResult.class).toList();
		if(results.size() <= 0){
			return loadResult();
		}else{
			return results.get(0);
		}
	}

	
	protected void setLength(String length) {
		this.length = length;
	}

	protected void setRating(String rating) {
		this.rating = rating;
	}

	protected void setTime(String time) {
		this.time = time;
	}
	
	
	public RaceResultCardItem getWinner(){
		List<RaceResultCardItem> results = getFirstChild(RaceResult.class).getFiles(RaceResultCardItem.class).toList();
		for(RaceResultCardItem result : results){
			if(result.getRank().equalsIgnoreCase("1")){
				return result;
			}
		}
		
		return null;
	}
	
	
	public RaceResultCardItem getSecond(){
		List<RaceResultCardItem> results = getFirstChild(RaceResult.class).getFiles(RaceResultCardItem.class).toList();
		for(RaceResultCardItem result : results){
			if(result.getRank().equalsIgnoreCase("2")){
				return result;
			}
		}
		
		return null;
	}

	public void resynch()throws Exception{
		
		List<Element> tables = new Source(ResourceUtil.readUrl(url)).getAllElements("table");
		int index = 0;
		for(Element table : tables){

			List<Element> theads = table.getAllElements("thead");
			if(theads.size() > 0){
				Element thead = theads.get(0);
				List<Element> trs = thead.getAllElements("tr");
				if(trs.size() > 0){}
				List<Element> tbodys = table.getAllElements("tbody");
				if(tbodys.size() > 0){
					Element tbody = tbodys.get(0);
					List<Element> rows = tbody.getAllElements("tr");
					int horseNumber = 1;
					for(Element row : rows){
						
						RaceCardItem item = (RaceCardItem)getFile(horseNumber + ""); //createFile(horseNumber + "", RaceCardItem.class);
						List<Element> cells = row.getAllElements("td");
						Element e = cells.get(1);
						String all = e.getTextExtractor().toString();
						if(e.getAllElements("a").size()> 0){
							Element a = e.getAllElements("a").get(0);
							String horseId = StringUtils.splitByWholeSeparator(a.getAttributeValue("href"), "horse_id=")[1];
							item.setHorseId(horseId);
							String horse = a.getTextExtractor().toString();
							item.setHorse(horse);
							String weight = all.replace(horse, "").replace("(", "").replace(")", "").trim();
							item.setHorseWeight(weight);
						}else{
							String[] parts = StringUtils.split(all, "(");
							item.setHorse(parts[0]);
							if(parts.length > 1){
								item.setHorseWeight(parts[1].replace(")", "").trim());
							}
							item.setHorseId("nill");
						}
						horseNumber++;
					}
				}
			}
			index++;
		}
		save();
	}
	
	private RaceResult loadResult()throws Exception{
		
		String sUrl = url.replace("mtc_racecards", "mtc_results");
		List<Element> tables = new Source(ResourceUtil.readUrl(sUrl)).getAllElements("table");
		int index = 0;
		RaceResult result = createFile("result", RaceResult.class);
		for(Element table : tables){

			List<Element> theads = table.getAllElements("thead");
			if(theads.size() > 0){
				Element thead = theads.get(0);
				List<Element> trs = thead.getAllElements("tr");
				if(trs.size() > 0){
					Element tr = trs.get(0);
					List<Element> tds = tr.getAllElements("th");
					int i = 0;
					for(Element td : tds){
						System.out.print(td.getTextExtractor().toString() + "\t\t");
						if(index == 0){
							
							if(i == 0){
								result.setTitle((td.getTextExtractor().toString()));
								
							}else if( i == 1){
								result.setLength(td.getTextExtractor().toString());
							}else if(i ==2){
								result.setRating(td.getTextExtractor().toString());
							}else if(i==3){
								result.setTime( td.getTextExtractor().toString());
							}
							i++;
						}
					}
					System.out.println("");
					System.out.println("------------------------------------------------------------------------------------------------------------------");
					
				}
				
				List<Element> tbodys = table.getAllElements("tbody");
				if(tbodys.size() > 0){
					Element tbody = tbodys.get(0);
					List<Element> rows = tbody.getAllElements("tr");
					int horseNumber = 1;
					if(rows.size() > 0){
						
						for(Element row : rows){
							
							RaceResultCardItem item = result.createFile(horseNumber + "", RaceResultCardItem.class); //new RaceCardItem(this);
							List<Element> cells = row.getAllElements("td");
							
							int ii=0;
							for(Element e : cells){
								System.out.print(e.getTextExtractor().toString() + "\t\t");
								
								if(ii==0){
									item.setRank(e.getTextExtractor().toString());
								}else if(ii==1){
									
									item.setHorseNumber(e.getTextExtractor().toString());
								}else if(ii==2){
									item.setHorse(e.getTextExtractor().toString());
									
									if(e.getAllElements("a").size() > 0){
										
										String href = e.getAllElements("a").get(0).getAttributeValue("href");
										item.setHorseId(StringUtils.splitByWholeSeparator(href, "horse_id=")[1]);
									}
									
								}else if(ii==3){
									item.setStable(e.getTextExtractor().toString());
								}else if(ii==4){
									item.setJockey(e.getTextExtractor().toString());
								}else if(ii==5){
									item.setTime(e.getTextExtractor().toString());
								}else if(ii==6){
									item.setPrize(e.getTextExtractor().toString());
								}
								
								
								
								ii++;
							}
							List<RaceCardItem> origs = getRaceCard();
							for(RaceCardItem orig : origs){
								if(orig.getHorseNumber().trim().equalsIgnoreCase(item.getHorseNumber().trim())){
									item.setAge(orig.getAge());
									item.setDraw(orig.getDraw());
									item.setGear(orig.getGear());
									item.setPerf(orig.getPerf());
									item.setWeight(orig.getWeight());
									item.setHorse(orig.getHorse());
									item.setHorseNumber(orig.getHorseNumber());
									item.setHorseWeight(orig.getHorseWeight());
									item.setJockey(orig.getJockey());
									item.setOpeningOdd(orig.getOpeningOdd());
									item.setPerf(orig.getPerf());
									item.setRecentOdd(orig.getRecentOdd());
									item.setSilk(orig.getSilk());
									item.setStable(orig.getStable());
								}
							}
							
							
							System.out.println("");
							horseNumber++;
						}
						save();
						return result;
					}
					//r.setRaceCard(cards);
				}
			}
			index++;
		}
		
		return null;
	}
	
	private List<RaceCardItem> loadRaceCard()throws Exception{
		List<RaceCardItem> cards = new ArrayList<RaceCardItem>();
		List<Element> tables = new Source(ResourceUtil.readUrl(url)).getAllElements("table");
		int index = 0;
		for(Element table : tables){

			List<Element> theads = table.getAllElements("thead");
			if(theads.size() > 0){
				Element thead = theads.get(0);
				List<Element> trs = thead.getAllElements("tr");
				if(trs.size() > 0){
					Element tr = trs.get(0);
					List<Element> tds = tr.getAllElements("th");
					int i = 0;
					for(Element td : tds){
						System.out.print(td.getTextExtractor().toString() + "\t\t");
						if(index == 1){
							
							if(i == 0){
								setTitle((td.getTextExtractor().toString()));
								
							}else if( i == 1){
								length = (td.getTextExtractor().toString());
							}else if(i ==2){
								rating =(td.getTextExtractor().toString());
							}else if(i==3){
								time =(td.getTextExtractor().toString());
							}
							i++;
						}
					}
					System.out.println("");
					System.out.println("------------------------------------------------------------------------------------------------------------------");
					
				}
				
				List<Element> tbodys = table.getAllElements("tbody");
				if(tbodys.size() > 0){
					Element tbody = tbodys.get(0);
					List<Element> rows = tbody.getAllElements("tr");
					int horseNumber = 1;
					for(Element row : rows){
						RaceCardItem item = createFile(horseNumber + "", RaceCardItem.class); //new RaceCardItem(this);
						List<Element> cells = row.getAllElements("td");
						int ii=0;
						for(Element e : cells){
							System.out.print(e.getTextExtractor().toString() + "\t\t");
							
							if(ii==0){
								item.setHorseNumber(e.getTextExtractor().toString());
							}else if(ii==1){
								String all = e.getTextExtractor().toString();
								if(e.getAllElements("a").size()> 0){
									Element a = e.getAllElements("a").get(0);
									String horseId = StringUtils.splitByWholeSeparator(a.getAttributeValue("href"), "horse_id=")[1];
									item.setHorseId(horseId);
									String horse = a.getTextExtractor().toString();
									item.setHorse(horse);
									String weight = all.replace(horse, "").replace("(", "").replace(")", "").trim();
									item.setHorseWeight(weight);
								}else{
									String[] parts = StringUtils.split(all, "(");
									item.setHorse(parts[0]);
									if(parts.length > 1){
										item.setHorseWeight(parts[1].replace(")", "").trim());
									}
									item.setHorseId("nill");
								}
							}else if(ii==2){
								item.setStable(e.getTextExtractor().toString());
							}else if(ii==3){
								item.setPerf(e.getTextExtractor().toString());
							}else if(ii==4){
								item.setAge(e.getTextExtractor().toString());
							}else if(ii==5){
								String gear = e.getTextExtractor().toString();
								if(gear != null && !gear.equals("-"))
									item.setGear(e.getTextExtractor().toString());
								else
									item.setGear("");
							}else if(ii==6){
								item.setWeight(e.getTextExtractor().toString());
							}else if(ii==7){
								item.setJockey(e.getTextExtractor().toString());
							}else if(ii==8){
								item.setDraw(e.getTextExtractor().toString());
							}
							
							ii++;
						}
						cards.add(item);
						
						
						System.out.println("");
						horseNumber++;
					}
					//r.setRaceCard(cards);
				}
			}
			index++;
		}
		save();
		Collections.sort(cards, new RaceCardItem());
		return cards;
		
	}
	public List<RaceCardItem> getRaceCard()throws Exception {
		List<RaceCardItem> result = getFiles(RaceCardItem.class).toList();
		Collections.sort(result, new RaceCardItem());
		if(result.size() <= 0){
			return loadRaceCard();
		}else{
			return result;
		}
	}

	
	
	
	public static void main(String[] arg)throws Exception{
		Race r = new Race();
		r.setUrl("http://www.mauritiusturfclub.com/index.php/en/component/mtc_results/?view=all&race_id=1842&year=2011-263");
		new Race().loadResult();
	}
	

}
