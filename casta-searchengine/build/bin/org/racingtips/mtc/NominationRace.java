package org.racingtips.mtc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;
import org.castafiore.utils.ResourceUtil;

import edu.emory.mathcs.backport.java.util.Collections;

@Entity
public class NominationRace extends Race{
	
	private String nominationUrl;

	public String getNominationUrl() {
		return nominationUrl;
	}

	public void setNominationUrl(String nominationUrl) {
		this.nominationUrl = nominationUrl;
	}
	
	public void resynchNomination(){
		try{
			Source source  = new Source(ResourceUtil.readUrl(nominationUrl));
			List<Element> headers = source.getAllElements("table").get(0).getAllElements("th");
			setTitle(headers.get(0).getTextExtractor().toString());
			setLength(headers.get(1).getTextExtractor().toString());
			setRating(headers.get(2).getTextExtractor().toString());
			setTime(headers.get(3).getTextExtractor().toString());
			List<Element> trs = source.getAllElements("tbody").get(0).getAllElements("tr");
			for(Element tr : trs){
				List<Element> cells = tr.getAllElements("td");
				String nominationName =cells.get(0).getTextExtractor().toString();
				
				Nomination n = (Nomination)getFile(nominationName);
				n.setHorseNumber(cells.get(0).getTextExtractor().toString());
				n.setHorse(cells.get(1).getTextExtractor().toString());
				n.setStable(cells.get(2).getTextExtractor().toString());
				n.setWeight(cells.get(3).getTextExtractor().toString());
				n.setDraw(cells.get(4).getTextExtractor().toString());
				
				String href = null;
				
				if(cells.get(1).getAllElements("a").size() > 0){
					href = cells.get(1).getAllElements("a").get(0).getAttributeValue("href");
					
				}
				if(href != null){
					String[] as = StringUtils.splitByWholeSeparator(href, "horse_id=");
					if(as.length >= 2){
						n.setHorseId(as[1]);
					}	
				}
			}
			save();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private List<Nomination> loadNominations(){
		
		List<Nomination> cards = new ArrayList<Nomination>();
		try{
			
			Source source  = new Source(ResourceUtil.readUrl(nominationUrl));
			
			List<Element> headers = source.getAllElements("table").get(0).getAllElements("th");
			setTitle(headers.get(0).getTextExtractor().toString());
			setLength(headers.get(1).getTextExtractor().toString());
			setRating(headers.get(2).getTextExtractor().toString());
			setTime(headers.get(3).getTextExtractor().toString());
			
			List<Element> trs = source.getAllElements("tbody").get(0).getAllElements("tr");
			for(Element tr : trs){
				List<Element> cells = tr.getAllElements("td");
				Nomination n = createFile(cells.get(0).getTextExtractor().toString(), Nomination.class);
				n.setHorseNumber(cells.get(0).getTextExtractor().toString());
				n.setHorse(cells.get(1).getTextExtractor().toString());
				n.setStable(cells.get(2).getTextExtractor().toString());
				n.setWeight(cells.get(3).getTextExtractor().toString());
				n.setDraw(cells.get(4).getTextExtractor().toString());
				
				String href = null;
				
				if(cells.get(1).getAllElements("a").size() > 0){
					href = cells.get(1).getAllElements("a").get(0).getAttributeValue("href");
					
				}
				
				if(href != null){
					String[] as = StringUtils.splitByWholeSeparator(href, "horse_id=");
					if(as.length >= 2){
						n.setHorseId(as[1]);
					}
					
				}
				
				
				cards.add(n);
			}
			save();
		}catch(Exception e){
			e.printStackTrace();
		}
		Collections.sort(cards);
		return cards;
	}
	
	public List<Nomination> getNominations(){
		List<Nomination> result = getFiles(Nomination.class).toList();
		Collections.sort(result);
		if(result.size() <= 0){
			return loadNominations();
		}else{
			return result;
		}
	}

}
