package org.castafiore.ui;

/**
 * Specialization of Application to add a description to it<br>
 * This is convenient to create an application that displays all applications configured in an application registry
 * @author arossaye
 *
 */
public interface DescriptibleApplication extends Application{
	

	/**
	 * 
	 * @return A description of the current application
	 */
	public String getDescription();
	

}
