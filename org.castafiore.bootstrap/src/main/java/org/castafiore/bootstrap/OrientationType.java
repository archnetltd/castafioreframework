package org.castafiore.bootstrap;


public enum OrientationType {

	VERTICAL(0), HORIZONTAL(1);

	private final Integer value;

	OrientationType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static OrientationType fromValue(Integer v) {
		if(v == 0){
			return VERTICAL;
		}else if(v == 1){
			return HORIZONTAL;
		}
		throw new IllegalArgumentException(v + "");
	}

}
