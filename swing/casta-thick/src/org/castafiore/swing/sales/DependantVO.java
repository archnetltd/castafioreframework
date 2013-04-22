package org.castafiore.swing.sales;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class DependantVO extends ValueObjectImpl{
	
	private String id;
	
	private String gender;
	
	private String nic;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
