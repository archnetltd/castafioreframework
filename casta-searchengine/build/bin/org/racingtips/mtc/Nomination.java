package org.racingtips.mtc;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Directory;

@Entity
public class Nomination extends Directory implements Comparable<Nomination>{
	
	private String raceNumber;
	
	private String horseNumber;
	
	private String horseId;
	
	private String horse;
	
	private String stable;
	
	private String weight;
	
	private String draw;

	public String getHorseId() {
		return horseId;
	}

	public void setHorseId(String horseId) {
		this.horseId = horseId;
	}

	public String getHorse() {
		return horse;
	}

	public void setHorse(String horse) {
		this.horse = horse;
	}

	public String getStable() {
		return stable;
	}

	public void setStable(String stable) {
		this.stable = stable;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public String getHorseNumber() {
		return horseNumber;
	}

	public void setHorseNumber(String horseNumber) {
		this.horseNumber = horseNumber;
	}

	public String getRaceNumber() {
		return raceNumber;
	}

	public void setRaceNumber(String raceNumber) {
		this.raceNumber = raceNumber;
	}

	@Override
	public int compareTo(Nomination o) {
		try
		{
			Integer.parseInt(horseNumber);
		}catch(Exception e){
			return 1;
		}
		try
		{
			Integer.parseInt(o.horseNumber);
		}catch(Exception e){
			return -1;
		}
		return new Integer( horseNumber).compareTo(new Integer(o.horseNumber));
	}
	
	
	

}
