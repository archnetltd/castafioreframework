package org.castafiore.ui.js;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JDate implements JSObject {
 
	private Date date;

	private final static SimpleDateFormat JS_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy,hh:mm:ss");

	public JDate(Date date) {
		super();
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String getJavascript() {
		return "new Date('" + JS_FORMAT.format(date) + "')";
	}

}
