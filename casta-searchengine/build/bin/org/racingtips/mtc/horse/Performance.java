package org.racingtips.mtc.horse;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Directory;

@Entity
public class Performance extends Directory {
	
	private String year;
	
	private String eff;
	
	private String winners;
	
	private String rank;
	
	private String entries;
	
	private String wins;
	
	private String second;
	
	private String third;
	
	private String fourth;
	
	private String unplaced;
	
	private String stakesMoney;
	
	private String cups;
	
	private String percentageFirst;
	
	private String sequence1;
	
	private String sequence2;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEff() {
		return eff;
	}

	public void setEff(String eff) {
		this.eff = eff;
	}

	public String getWinners() {
		return winners;
	}

	public void setWinners(String winners) {
		this.winners = winners;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getEntries() {
		return entries;
	}

	public void setEntries(String entries) {
		this.entries = entries;
	}

	public String getWins() {
		return wins;
	}

	public void setWins(String wins) {
		this.wins = wins;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public String getThird() {
		return third;
	}

	public void setThird(String third) {
		this.third = third;
	}

	public String getFourth() {
		return fourth;
	}

	public void setFourth(String fourth) {
		this.fourth = fourth;
	}

	public String getUnplaced() {
		return unplaced;
	}

	public void setUnplaced(String unplaced) {
		this.unplaced = unplaced;
	}

	public String getStakesMoney() {
		return stakesMoney;
	}

	public void setStakesMoney(String stakesMoney) {
		this.stakesMoney = stakesMoney;
	}

	public String getCups() {
		return cups;
	}

	public void setCups(String cups) {
		this.cups = cups;
	}

	public String getPercentageFirst() {
		return percentageFirst;
	}

	public void setPercentageFirst(String percentageFirst) {
		this.percentageFirst = percentageFirst;
	}

	public String getSequence1() {
		return sequence1;
	}

	public void setSequence1(String sequence1) {
		this.sequence1 = sequence1;
	}

	public String getSequence2() {
		return sequence2;
	}

	public void setSequence2(String sequence2) {
		this.sequence2 = sequence2;
	}
	
	
	

}
