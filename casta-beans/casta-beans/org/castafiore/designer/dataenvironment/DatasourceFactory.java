package org.castafiore.designer.dataenvironment;

import org.castafiore.beans.IdentifyAble;


/**
 * 
 * @author Kureem Rossaye
 *
 */
public interface DatasourceFactory extends IdentifyAble{
	
	/**
	 * should return a new instance each time it is called.
	 * 
	 * @return
	 */
	public Datasource getInstance();
	
	
	/**
	 * this method should
	 * @param c
	 * @param attributeName
	 * @param attributeValue
	 */
	public void applyAttribute(Datasource d, String attributeName, String attributeValue);
	
	
	/**
	 * returns minimum required attributes
	 * @return
	 */
	public String[] getRequiredAttributes();
	
	
	/**
	 * returns configuration form of this type of datasource
	 * @return
	 */
	public DatasourceConfigForm getAdvancedConfig();
	
	
	
	/**
	 * refreshes the datasource
	 * @param d
	 */
	public void refresh(Datasource d);
	
	/**
	 * Returns an icon to identify this datasource factory
	 * @return
	 */
	public String getIcon();
	
	
	/**
	 * returns the default viewer for this type of datasource
	 * @return
	 */
	public DatasourceViewer getViewer();
	
	

}
