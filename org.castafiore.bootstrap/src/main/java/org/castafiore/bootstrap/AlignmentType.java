package org.castafiore.bootstrap;

public enum AlignmentType {

	LEFT(0), RIGHT(1);

	private final Integer value;

	AlignmentType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static AlignmentType fromValue(Integer v) {
		if(v == 0){
			return LEFT;
		}else if(v == 1){
			return RIGHT;
		}
		throw new IllegalArgumentException(v + "");
	}

}
