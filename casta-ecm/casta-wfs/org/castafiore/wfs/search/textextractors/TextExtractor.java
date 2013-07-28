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

import java.io.Serializable;
import java.util.Map;

import org.castafiore.wfs.types.File;

/**
 * Text Extractor interface.
 * 
 * 
 * @author Kureem Rossaye
 *
 */
public interface TextExtractor extends Serializable{
	
	
	/**
	 * Takes a file to extract the necessary text.<br> 
	 * The method should fill the {@link Map} with key value<br>
	 * e.g <br>
	 * name : name1
	 * 
	 * 
	 * @param file
	 * @param toIndexMap
	 */
	public void extractText(File file, Map<String, String> toIndexMap);

}
