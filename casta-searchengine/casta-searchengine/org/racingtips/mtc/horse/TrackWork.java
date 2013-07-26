package org.racingtips.mtc.horse;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Directory;

@Entity
public class TrackWork extends Directory{
//Horse 	Jockey 	1000p 	800p 	600p 	400p 	200p 	2400p 	2200p 	1000m 	800m 	600m 	400m 	200m
	private String workNumber;
	
	private String horseId;
	
	private String horse;
	
	private String jockey;
	
	private String m200;
	
	private String m400;
	
	private String m600;
	
	private String m800;
	
	private String m1000;
	
	private String date;

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

	public String getJockey() {
		return jockey;
	}

	public void setJockey(String jockey) {
		this.jockey = jockey;
	}

	public String getM200() {
		return m200;
	}

	public void setM200(String m200) {
		this.m200 = m200;
	}

	public String getM400() {
		return m400;
	}

	public void setM400(String m400) {
		this.m400 = m400;
	}

	public String getM600() {
		return m600;
	}

	public void setM600(String m600) {
		this.m600 = m600;
	}

	public String getM800() {
		return m800;
	}

	public void setM800(String m800) {
		this.m800 = m800;
	}

	public String getM1000() {
		return m1000;
	}

	public void setM1000(String m1000) {
		this.m1000 = m1000;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
	
	
}
