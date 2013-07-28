package org.castafiore.swing.options;

import org.openswing.swing.client.PropertyGridControl;
import org.openswing.swing.properties.client.PropertyGridController;

public class OptionPropertiesController extends PropertyGridController{

	
	public static String[] propertyNames = new String[]{"thermalPrinterName", "normalPrinterName", "server.endpoint"} ;
	
	
	public OptionPropertiesController() {
		super();
	}

	@Override
	public boolean insertRecords(PropertyGridControl grid,
			int[] changedRowNumbers) {
		return super.insertRecords(grid, changedRowNumbers);
	}
	
	

	@Override
	public void loadData(PropertyGridControl grid) {
		for(int i =0; i < propertyNames.length;i++){
		
			grid.setPropertyValue(i, PropertiesUtil.properties.get(propertyNames[i]));
		}
	}

	@Override
	public boolean updateRecords(PropertyGridControl grid,
			int[] changedRowNumbers){
		
		try{
		if(changedRowNumbers != null && changedRowNumbers.length > 0){
			for(int index :changedRowNumbers){
				String key = propertyNames[index];
				Object propertyValue = grid.getPropertyValue(key);
				if(propertyValue != null){
					PropertiesUtil.saveProperties(key, propertyValue.toString());
				}
			}
			
			PropertiesUtil.saveAll();
		}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		//return super.updateRecords(grid, changedRowNumbers);
		return true;
	}

}
