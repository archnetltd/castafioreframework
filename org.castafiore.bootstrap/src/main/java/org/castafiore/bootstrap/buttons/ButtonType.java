package org.castafiore.bootstrap.buttons;


public enum ButtonType {

	
	
	DEFAULT("default"),PRIMARY("primary"), DANGER("danger"), WARNING("warning"), SUCCESS("success"),
	INFO("info"), INVERSE("link");

	private final String value;

	ButtonType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	
}
