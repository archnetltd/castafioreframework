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

import java.util.List;

/**
 * 
 * Has purpose to manipulate a word
 * @author Kureem Rossaye
 *
 */
public interface TextAnalyzer {
	
	/**
	 * analyze the word and return list of resulting String<br>
	 * return null of does not want to include word in search
	 * @param word
	 * @return
	 */
	public void analyseWord(String word, List<String> analyzed);

}
