package org.castafiore.agenda;

import java.util.Calendar;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Article;

@Entity
public class AgendaEvent extends Article {
	
	private Calendar startTime;
	
	private Calendar endTime;
	
	private String repeats;
	
	private String alert;
	
	private String secondAlert;
	
	private String location;
	
	private String color;
	
	

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getRepeats() {
		return repeats;
	}

	public void setRepeats(String repeats) {
		this.repeats = repeats;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getSecondAlert() {
		return secondAlert;
	}

	public void setSecondAlert(String secondAlert) {
		this.secondAlert = secondAlert;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	

}
