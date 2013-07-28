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
 package org.castafiore.ecm.ui.config;

import java.util.Comparator;
import java.util.Map;

import org.springframework.util.PathMatcher;

public class SimplePathMatcher implements PathMatcher {

	public String combine(String pattern1, String pattern2) {
		return pattern1 + pattern2;
	}

	public String extractPathWithinPattern(String pattern, String path) {
		return path;
	}

	public Map<String, String> extractUriTemplateVariables(String pattern,
			String path) {
		return null;
	}

	public Comparator<String> getPatternComparator(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPattern(String path) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean match(String pattern, String path) {
		return pattern.equalsIgnoreCase(path);
	}

	public boolean matchStart(String pattern, String path) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
