package org.castafiore.bootstrap.nav;


public enum NavType {
	TABS(0), PILLS(1), STACKED(3), BREADCRUMB(4);

	private final Integer value;

	NavType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static NavType fromValue(Integer v) {
		if(v == 0){
			return TABS;
		}else if(v == 1){
			return PILLS;
		}else if(v == 2){
			return STACKED;
		}
		throw new IllegalArgumentException(v + "");
	}

}
