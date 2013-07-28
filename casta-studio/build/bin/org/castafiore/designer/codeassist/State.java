package org.castafiore.designer.codeassist;

import org.json.JSONObject;

public class State {
	
	private String tokenize;
	
	private Context context;
	
	private int indented;
	
	private boolean startLine;
	
	private String lastToken;
	
	public State(JSONObject obj)throws Exception{
		tokenize = obj.getString("tokenize");
		
		context = new Context( obj.getJSONObject("context"));
		indented = obj.getInt("indented");
		if(obj.has("startLine"))
			startLine = obj.getBoolean("startLine");
		lastToken = obj.getString("lastToken");
	}

	public String getTokenize() {
		return tokenize;
	}

	public void setTokenize(String tokenize) {
		this.tokenize = tokenize;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public int getIndented() {
		return indented;
	}

	public void setIndented(int indented) {
		this.indented = indented;
	}

	public boolean isStartLine() {
		return startLine;
	}

	public void setStartLine(boolean startLine) {
		this.startLine = startLine;
	}

	public String getLastToken() {
		return lastToken;
	}

	public void setLastToken(String lastToken) {
		this.lastToken = lastToken;
	}
	
	

}
