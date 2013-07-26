/**
 * 
 */
package org.castafiore.shoppingmall.employee;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;

import org.castafiore.wfs.types.Article;

/**
 * @author acer
 *
 */
@Entity
public class TimesheetEntry extends Article{

	private Calendar date;
	
	private BigDecimal numberHours;
	
	private Calendar startTime;
	
	private Calendar endTime;
	
	private Calendar previsionalStartTime;
	
	private Calendar previsionalEndTime;
	
	private String project;

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public BigDecimal getNumberHours() {
		return numberHours;
	}

	public void setNumberHours(BigDecimal numberHours) {
		this.numberHours = numberHours;
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

	public Calendar getPrevisionalStartTime() {
		return previsionalStartTime;
	}

	public void setPrevisionalStartTime(Calendar previsionalStartTime) {
		this.previsionalStartTime = previsionalStartTime;
	}

	public Calendar getPrevisionalEndTime() {
		return previsionalEndTime;
	}

	public void setPrevisionalEndTime(Calendar previsionalEndTime) {
		this.previsionalEndTime = previsionalEndTime;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	
	
	
	
}
