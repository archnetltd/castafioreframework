package org.castafiore;

public class SimpleKeyValueType extends SimpleKeyValuePair implements KeyValueType{

	private Types type;
	
	
	
	
	
	public SimpleKeyValueType() {
		super();
		
	}
	public SimpleKeyValueType(String key, String value, Types type) {
		super(key, value);
		this.type = type;
	}
	@Override
	public Types getType() {
		return type;
	}
	public void setType(Types type) {
		this.type = type;
	}
	
	

}
