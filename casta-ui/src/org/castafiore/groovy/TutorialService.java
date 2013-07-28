package org.castafiore.groovy;

public class TutorialService {
	
	private String connectionName;
	
	private String testVariable;
	
	private TutorialService2 service2;

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public String getTestVariable() {
		return testVariable;
	}

	public void setTestVariable(String testVariable) {
		this.testVariable = testVariable;
	}
	
	
	
	public TutorialService2 getService2() {
		return service2;
	}

	public void setService2(TutorialService2 service2) {
		this.service2 = service2;
	}

	public void testMethod(){
		System.out.println("ConnectionName :" + connectionName);
		System.out.println("testVariable :" + testVariable);
		
		service2.executeMe();
	}
	

}
