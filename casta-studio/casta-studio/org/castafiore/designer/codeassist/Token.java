package org.castafiore.designer.codeassist;

import org.json.JSONObject;


public class Token {
	
	private int start;
	
	private int end;
	
	private String string;
	
	private String className;
	
	private State state;

	public Token(JSONObject obj)throws Exception{
		start = obj.getInt("start");
		end = obj.getInt("end");
		string = obj.getString("string");
		state = new State(obj.getJSONObject("state"));
		
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	
	
	

}
