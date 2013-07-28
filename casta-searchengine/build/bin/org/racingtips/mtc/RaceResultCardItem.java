package org.racingtips.mtc;

import javax.persistence.Entity;

@Entity
public class RaceResultCardItem extends RaceCardItem implements Comparable<RaceResultCardItem>{
	
	private String rank;
	
	private String time;
	
	private String prize;
	
	private String margin = "-";
	
	private String m600 = "-";
	
	private String m400 = "-";
	
	private String video;
	

	public String getM600() {
		return m600;
	}

	public void setM600(String m600) {
		this.m600 = m600;
	}

	public String getM400() {
		return m400;
	}

	public void setM400(String m400) {
		this.m400 = m400;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	@Override
	public int compareTo(RaceResultCardItem o) {
		return rank.compareTo(o.rank);
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
	
	

}
