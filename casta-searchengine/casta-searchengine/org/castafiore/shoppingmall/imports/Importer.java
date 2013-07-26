package org.castafiore.shoppingmall.imports;

import java.util.Map;

public class Importer<T> {
	
	private ImporterTemplate<T> importerTemplate;

	public ImporterTemplate<T> getImporterTemplate() {
		return importerTemplate;
	}

	public void setImporterTemplate(ImporterTemplate<T> importerTemplate) {
		this.importerTemplate = importerTemplate;
	}
	
	public void doImport(Map<String, String> additional){
		int counter = 0;
		int batchcounter = importerTemplate.getBatchSize();
		while(importerTemplate.hasNextSource()){
			try{
			T source = (T)importerTemplate.getNextSource();
			Map<String,String> data = importerTemplate.transform(source);
			data.putAll(additional);
			importerTemplate.doImportInstance(data);
			batchcounter = batchcounter -1;
			if(batchcounter == 0){ 
				importerTemplate.flush();
				batchcounter=importerTemplate.getBatchSize();
				//batchcounter = importerTemplate.getBatchSize();
			}
			System.err.println("we have executed record :" + counter);
			counter = counter+1;
			}catch(Exception e){
				e.printStackTrace();
				System.err.println("error in line : " + counter);
			}
		}
		
		importerTemplate.flush();
	}

}
