package org.racingtips.mtc.horse;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Directory;

@Entity
public class Stable extends Directory{
	
	private String stableId;
	
	private String stableManager;
	
	private String trainer;
	
	
	private String stableName;
	
	public List<Performance> getPerformance(){
		Directory performances = (Directory)getFile("performances");
		if(performances != null ){
			return performances.getFiles(Performance.class).toList();
		}
		return new ArrayList<Performance>();
	}
	
	public Performance createPerformance(String year){
		Directory performances = (Directory)getFile("performances");
		if(performances == null ){
			performances = createFile("performances", Directory.class);
		}
		
		return performances.createFile(year, Performance.class);
	}
	
	
	public List<Horse> getHorses(){
		Directory horses = (Directory)getFile("horses");
		if(horses != null ){
			return horses.getFiles(Horse.class).toList();
		}
		return new ArrayList<Horse>();
	}


	public Horse createHorse(String horseId){
		Directory horses = (Directory)getFile("horses");
		if(horses ==null){
			horses = createFile("horses", Directory.class);
		}
		
		return horses.createFile(horseId, Horse.class);
	}


	public String getStableId() {
		return stableId;
	}


	public void setStableId(String stableId) {
		this.stableId = stableId;
	}


	public String getStableManager() {
		return stableManager;
	}


	public void setStableManager(String stableManager) {
		this.stableManager = stableManager;
	}


	public String getTrainer() {
		return trainer;
	}


	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	public String getStableName() {
		return stableName;
	}

	public void setStableName(String stableName) {
		this.stableName = stableName;
	}
	
	
	

}
