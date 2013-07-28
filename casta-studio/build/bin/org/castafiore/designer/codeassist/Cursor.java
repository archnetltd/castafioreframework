package org.castafiore.designer.codeassist;

import org.json.JSONObject;

public class Cursor {
	
	private int line;
	
	private int character;

	public Cursor(JSONObject obj)throws Exception {
		super();
		this.line = obj.getInt("line");
		this.character = obj.getInt("ch");
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getCharacter() {
		return character;
	}

	public void setCharacter(int character) {
		this.character = character;
	}
	
	

}
