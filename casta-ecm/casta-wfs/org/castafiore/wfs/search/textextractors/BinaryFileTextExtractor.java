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
 package org.castafiore.wfs.search.textextractors;

import java.io.InputStream;
import java.util.Map;

import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

public class BinaryFileTextExtractor extends FileTextExtractor {

	public void extractText(File file, Map<String, String> toIndex) {
		super.extractText(file, toIndex);
		try{
			if(file instanceof BinaryFile)
			{	

				if(((BinaryFile) file).getMimeType().startsWith("text")){
					BinaryFile bf = (BinaryFile)file;
					InputStream in = bf.getInputStream();
					addKeyValue("text", IOUtil.getStreamContentAsString(in), toIndex) ;
				}
				
			}
		}catch(Exception e){
			
		}
		
	}

}
