package org.racingtips.mtc;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;
import org.castafiore.utils.ResourceUtil;

@Entity
public class RaceResult extends Race implements Comparator<RaceResultCardItem>{
	
	
	public List<RaceResultCardItem> getResultCard()throws Exception{
		List<RaceResultCardItem> results = getFiles(RaceResultCardItem.class).toList();
		if(results.size() == 0){
			resyncres();
			 results = getFiles(RaceResultCardItem.class).toList();
		}
		Collections.sort(results, this);
		return results;
	}
	
	
	private void resyncres()throws Exception{
		String url = getUrl();
		
		if(url == null){
			url = ((Race)getParent()).getUrl();
		}
		String sUrl = url.replace("mtc_racecards", "mtc_results");
		List<Element> tables = new Source(ResourceUtil.readUrl(sUrl)).getAllElements("table");
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
						if(index == 0){
							
							if(i == 0){
								setTitle((td.getTextExtractor().toString()));
								
							}else if( i == 1){
								setLength(td.getTextExtractor().toString());
							}else if(i ==2){
								setRating(td.getTextExtractor().toString());
							}else if(i==3){
								setTime( td.getTextExtractor().toString());
							}
							i++;
						}
					}
					System.out.println("");
					System.out.println("------------------------------------------------------------------------------------------------------------------");
					
				}
				
				List<Element> tbodys = table.getAllElements("tbody");
				if(tbodys.size() > 0){
					Race race = (Race)getParent();
					Element tbody = tbodys.get(0);
					List<Element> rows = tbody.getAllElements("tr");
					if(rows.size() > 0){
						
						for(Element row : rows){
							
							List<Element> cells = row.getAllElements("td");
							String horseNumber = cells.get(1).getTextExtractor().toString();
							RaceResultCardItem item = createFile(horseNumber + "", RaceResultCardItem.class);
							
							RaceCardItem rc = (RaceCardItem)race.getFile(horseNumber);
							
							item.setRank(cells.get(0).getTextExtractor().toString());
							item.setTime(cells.get(5).getTextExtractor().toString());
							item.setPrize(cells.get(6).getTextExtractor().toString());
							
							item.setGear(rc.getGear());
							item.setAge(rc.getAge());
							item.setDraw(rc.getDraw());
							item.setHorseId(rc.getHorseId());
							item.setHorse(rc.getHorse());
							item.setHorseId(rc.getHorseId());
							item.setHorseNumber(rc.getHorseNumber());
							item.setHorseWeight(rc.getHorseWeight());
							item.setJockey(rc.getJockey());
							item.setOpeningOdd(rc.getOpeningOdd());
							item.setPerf(rc.getPerf());
							
							item.setRecentOdd(rc.getRecentOdd());
							item.setSilk(rc.getSilk());
							item.setStable(rc.getStable());
							System.out.println("");
						}
						save();
					}
				}
			}
			index++;
		}
	}

	@Override
	public int compare(RaceResultCardItem o1, RaceResultCardItem o2) {
		int r1 = Integer.parseInt(o1.getRank());
		int r2 = Integer.parseInt(o2.getRank());
		if(r1 > r2){
			return 1;
		}else if(r2 > r1){
			return -1;
		}else{
			return 0;
		}
		
	}

}
