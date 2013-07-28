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
 package org.hibernate.search.util;

import org.hibernate.search.annotations.FilterCacheModeType;
import org.hibernate.annotations.common.AssertionFailure;

/**
 * @author Emmanuel Bernard
 */
public class FilterCacheModeTypeHelper {
	private FilterCacheModeTypeHelper() {}

	public static boolean cacheInstance(FilterCacheModeType type) {
		switch ( type ) {
			case NONE:
				return false;
			case INSTANCE_AND_DOCIDSETRESULTS:
				return true;
			case INSTANCE_ONLY:
				return true;
			default:
				throw new AssertionFailure("Unknwn FilterCacheModeType:" + type);
		}
	}

	public static boolean cacheResults(FilterCacheModeType type) {
		switch ( type ) {
			case NONE:
				return false;
			case INSTANCE_AND_DOCIDSETRESULTS:
				return true;
			case INSTANCE_ONLY:
				return false;
			default:
				throw new AssertionFailure("Unknwn FilterCacheModeType:" + type);
		}
	}
}
