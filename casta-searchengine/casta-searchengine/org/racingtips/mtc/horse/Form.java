package org.racingtips.mtc.horse;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Directory;

@Entity
public class Form extends Directory{
	
	private String formNumber;
	
	private String horseId;
	private String horse;

	private String date;
	private String position;	
	private String horseWeight; 	
	private String weight; 	
	private String distance; 	
	private String draw; 	
	private String cls; 	
	private String jockey; 	
	private String winner; 	
	private String margin; 	
	private String time; 	
	private String openingOdds; 	
	private String closingOdds;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getHorseWeight() {
		return horseWeight;
	}
	public void setHorseWeight(String horseWeight) {
		this.horseWeight = horseWeight;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getJockey() {
		return jockey;
	}
	public void setJockey(String jockey) {
		this.jockey = jockey;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public String getMargin() {
		return margin;
	}
	public void setMargin(String margin) {
		this.margin = margin;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOpeningOdds() {
		return openingOdds;
	}
	public void setOpeningOdds(String openingOdds) {
		this.openingOdds = openingOdds;
	}
	public String getClosingOdds() {
		return closingOdds;
	}
	public void setClosingOdds(String closingOdds) {
		this.closingOdds = closingOdds;
	}
	public String getHorseId() {
		return horseId;
	}
	public void setHorseId(String horseId) {
		this.horseId = horseId;
	}
	
	public String getFormNumber() {
		return formNumber;
	}
	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}
	public String getHorse() {
		return horse;
	}
	public void setHorse(String horse) {
		this.horse = horse;
	}
	
	
	

}
