package org.castafiore.bootstrap.buttons;

public enum ButtonSize {
	MINI(0), SMALL(1), LARGE(2);

	private final Integer value;

	ButtonSize(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static ButtonSize fromValue(Integer v) {
		if(v == 0){
			return MINI;
		}else if(v == 1){
			return SMALL;
		}else if(v == 1){
			return LARGE;
		}
		throw new IllegalArgumentException(v + "");
	}
}
