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
 //$Id: Version.java 15639 2008-12-02 14:49:20Z hardy.ferentschik $
package org.hibernate.search;

import java.util.Date;

import org.slf4j.Logger;

import org.hibernate.search.util.LoggerFactory;


/**
 * @author Emmanuel Bernard
 */
public class Version {
	public static final String VERSION = "3.1.0.GA";

	private static final Logger log = LoggerFactory.make();

	static {
		log.info( "Hibernate Search {}", VERSION );
	}

	public static void touch() {
	}
}
