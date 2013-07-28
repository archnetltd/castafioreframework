package org.castafiore.designer.codeassist;

import org.json.JSONObject;

public class Data {
	
	private String code;
	
	private Token contextToken;
	
	private Token token;
	
	private Cursor cursor;
	
	public Data(JSONObject obj)throws Exception{
		code = obj.getString("code");
		contextToken = new Token(obj.getJSONArray("context").getJSONObject(0));
		token = new Token(obj.getJSONObject("token"));
		cursor = new Cursor(obj.getJSONObject("cursor"));
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public Token getContextToken() {
		return contextToken;
	}

	public void setContextToken(Token contextToken) {
		this.contextToken = contextToken;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

}
