package org.racingtips.tips;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Directory;

@Entity
public class AbstractTips extends Directory{
private String horseNumber;
	
	private String raceNumber;
	
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
}
