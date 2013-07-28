package org.castafiore.ui.svg.ables;

public interface ConditionalProcessingAttributeAble extends CoreAttributeAble{
//requiredExtensions, requiredFeatures, systemLanguage.
	
	
	public String getRequiredExtensions();
	
	public String getRequiredFeatures();
	
	public String getSystemLanguage();
	
	public void setRequiredExtensions(String requiredExtensions);
	
	public void setRequiredFeatures(String requiredFeatures);
	
	public void setSystemLanguage(String systemLanguage);
	
	
}
