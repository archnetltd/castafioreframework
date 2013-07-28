package org.castafiore.groovy;

public class TutorialService2 {
	
	private String variable1;
	
	private String varialble2;

	public String getVariable1() {
		return variable1;
	}

	public void setVariable1(String variable1) {
		this.variable1 = variable1;
	}

	public String getVarialble2() {
		return varialble2;
	}

	public void setVarialble2(String varialble2) {
		this.varialble2 = varialble2;
	}
	
	
	public void executeMe(){
		System.out.println("v1:" + variable1);
		System.out.println("v2:" + variable1);
	}

}
