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
 //$Id: DateBridge.java 14713 2008-05-29 15:18:15Z sannegrinovero $
package org.hibernate.search.bridge.builtin;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.lucene.document.DateTools;
import org.hibernate.AssertionFailure;
import org.hibernate.HibernateException;
import org.hibernate.search.bridge.ParameterizedBridge;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.bridge.TwoWayStringBridge;
import org.hibernate.util.StringHelper;

/**
 * Bridge a java.util.Date to a String, truncated to the resolution
 * Date are stored GMT based
 * <p/>
 * ie
 * Resolution.YEAR: yyyy
 * Resolution.MONTH: yyyyMM
 * Resolution.DAY: yyyyMMdd
 * Resolution.HOUR: yyyyMMddHH
 * Resolution.MINUTE: yyyyMMddHHmm
 * Resolution.SECOND: yyyyMMddHHmmss
 * Resolution.MILLISECOND: yyyyMMddHHmmssSSS
 *
 * @author Emmanuel Bernard
 */
//TODO split into StringBridge and TwoWayStringBridge?
public class DateBridge implements TwoWayStringBridge, ParameterizedBridge {

	public static final TwoWayStringBridge DATE_YEAR = new DateBridge( Resolution.YEAR );
	public static final TwoWayStringBridge DATE_MONTH = new DateBridge( Resolution.MONTH );
	public static final TwoWayStringBridge DATE_DAY = new DateBridge( Resolution.DAY );
	public static final TwoWayStringBridge DATE_HOUR = new DateBridge( Resolution.HOUR );
	public static final TwoWayStringBridge DATE_MINUTE = new DateBridge( Resolution.MINUTE );
	public static final TwoWayStringBridge DATE_SECOND = new DateBridge( Resolution.SECOND );
	public static final TwoWayStringBridge DATE_MILLISECOND = new DateBridge( Resolution.MILLISECOND );

	private DateTools.Resolution resolution;

	public DateBridge() {
	}

	public DateBridge(Resolution resolution) {
		setResolution( resolution );
	}

	public Object stringToObject(String stringValue) {
		if ( StringHelper.isEmpty( stringValue ) ) return null;
		try {
			return DateTools.stringToDate( stringValue );
		}
		catch (ParseException e) {
			throw new HibernateException( "Unable to parse into date: " + stringValue, e );
		}
	}

	public String objectToString(Object object) {
		return object != null ?
				DateTools.dateToString( (Date) object, resolution ) :
				null;
	}

	public void setParameterValues(Map parameters) {
		Object resolution = parameters.get( "resolution" );
		Resolution hibResolution;
		if ( resolution instanceof String ) {
			hibResolution = Resolution.valueOf( ( (String) resolution ).toUpperCase( Locale.ENGLISH ) );
		}
		else {
			hibResolution = (Resolution) resolution;
		}
		setResolution( hibResolution );
	}

	private void setResolution(Resolution hibResolution) {
		switch (hibResolution) {
			case YEAR:
				this.resolution = DateTools.Resolution.YEAR;
				break;
			case MONTH:
				this.resolution = DateTools.Resolution.MONTH;
				break;
			case DAY:
				this.resolution = DateTools.Resolution.DAY;
				break;
			case HOUR:
				this.resolution = DateTools.Resolution.HOUR;
				break;
			case MINUTE:
				this.resolution = DateTools.Resolution.MINUTE;
				break;
			case SECOND:
				this.resolution = DateTools.Resolution.SECOND;
				break;
			case MILLISECOND:
				this.resolution = DateTools.Resolution.MILLISECOND;
				break;
			default:
				throw new AssertionFailure( "Unknown Resolution: " + hibResolution );

		}
	}
}
