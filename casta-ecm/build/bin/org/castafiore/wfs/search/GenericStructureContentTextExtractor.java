package org.castafiore.wfs.search;

import java.util.Map;

import org.castafiore.wfs.search.textextractors.FileTextExtractor;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;

public class GenericStructureContentTextExtractor extends FileTextExtractor{
	

	@Override
	public void extractText(File file, Map<String, String> toIndexMap) {
		if(file instanceof Article){
			Article doc = (Article)file;
			addKeyValue("title", doc.getTitle(), toIndexMap);
			addKeyValue("summary", doc.getSummary(), toIndexMap);
			addKeyValue("detail", doc.getDetail(), toIndexMap);
		}
		
	}

}
