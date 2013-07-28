package org.castafiore.wfs.cloud;

public interface EditableCloudResource  extends CloudResource{
	
	/**
	 * Returns the link that points to the web software to edit this resource
	 * @return
	 */
	public String getEditLink();

}
