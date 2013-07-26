package org.racingtips.mtc.horse;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Directory;

@Entity
public class Horse extends Directory {
	
	private String stableId;
	
	private String horseId;
	
	private String horse;
	
	private String age;
	
	private String gear;
	
	private String weight;
	
	private String pedigree;
	
	private String trainer;
	
	private String stable;
	
	private String runs;
	
	private String first;
	
	private String seconds;
	
	private String thirds;
	
	private String rating;
	
	

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getHorseId() {
		return horseId;
	}

	public void setHorseId(String horseId) {
		this.horseId = horseId;
	}

	public String getPedigree() {
		return pedigree;
	}

	public void setPedigree(String pedigree) {
		this.pedigree = pedigree;
	}

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	public String getStable() {
		return stable;
	}

	public void setStable(String stable) {
		this.stable = stable;
	}

	public String getRuns() {
		return runs;
	}

	public void setRuns(String runs) {
		this.runs = runs;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getThirds() {
		return thirds;
	}

	public void setThirds(String thirds) {
		this.thirds = thirds;
	}

	public String getHorse() {
		return horse;
	}

	public void setHorse(String horse) {
		this.horse = horse;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGear() {
		return gear;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getStableId() {
		return stableId;
	}

	public void setStableId(String stableId) {
		this.stableId = stableId;
	}
	
	
	
	

}
