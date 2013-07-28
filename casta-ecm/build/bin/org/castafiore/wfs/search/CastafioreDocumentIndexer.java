/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.wfs.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.search.textextractors.TextExtractor;
import org.castafiore.wfs.types.File;

public class CastafioreDocumentIndexer implements DocumentIndexer {

	public static final Log logger = LogFactory.getLog(CastafioreDocumentIndexer.class);
	
	private TextExtractorFactory textExtractorFactory = null;

	public void indexFile(File file) {
		TextExtractor extractor = textExtractorFactory.getTextExtractor(file);
		if(extractor != null){
			try{
				Map<String, String> toIndex = new HashMap<String, String>();
				extractor.extractText(file, toIndex);
				file.setContainsText(getText(toIndex));
			}catch(Exception e){
			}	
		}
	}

	protected String getText(Map<String, String> toIndex){
		 Iterator<String> iterKeys = toIndex.keySet().iterator();
		 StringBuilder b = new StringBuilder();
		 while(iterKeys.hasNext()){
			 String key  = iterKeys.next();
			 String value = toIndex.get(key);
			 if(b.length() > 0){
				 b.append("[{}]");
			 }
			 b.append(value);
		 }
		 return b.toString();
	}
	
	public TextExtractorFactory getTextExtractorFactory() {
		return textExtractorFactory;
	}

	public void setTextExtractorFactory(TextExtractorFactory textExtractorFactory) {
		this.textExtractorFactory = textExtractorFactory;
	}
	
	
	public void ping(){
		SpringUtil.getRepositoryService().getFile("/root", null);
	}
}
