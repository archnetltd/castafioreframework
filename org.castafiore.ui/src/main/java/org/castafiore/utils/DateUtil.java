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
package org.castafiore.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtil {

	public static String getMonthName(int month, Locale locale) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		return new SimpleDateFormat("MMMM").format(cal.getTime());
	}

	public static boolean isLeapYear(int year) {
		return (year % 4 == 0);
	}

	public static int getDaysInMonth(int month, int year) {
		if (month == 0) {
			return 31;
		} else if (month == 1) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		} else if (month == 2) {
			return 31;
		} else if (month == 3) {
			return 30;
		} else if (month == 4) {
			return 31;
		} else if (month == 5) {
			return 30;
		} else if (month == 6) {
			return 31;
		} else if (month == 7) {
			return 31;
		} else if (month == 8) {
			return 30;
		} else if (month == 9) {
			return 31;
		} else if (month == 10) {
			return 30;
		} else if (month == 11) {
			return 31;
		}
		return -1;
	}

	public static int getDayOfWeek(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		return (cal.get(Calendar.DAY_OF_WEEK));

	}

	public static boolean isWeekEnd(int year, int month, int day) {
		int dayOfWeek = getDayOfWeek(year, month, day);

		if (Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek) {
			return true;
		} else {
			return false;
		}

	}

	public static Calendar[] getStartAndEndOfDay(Calendar calendar) {
		Calendar startDate = Calendar.getInstance();
		startDate
				.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DATE), 0, 0);
		Calendar endDate = Calendar.getInstance();
		endDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), 23, 59);

		return new Calendar[] { startDate, endDate };
	}

	public static Calendar[] getStartAndEndOfMonth(int month, int year) {
		Calendar startDate = Calendar.getInstance();
		startDate.set(year, month, 1, 0, 0);
		Calendar endDate = Calendar.getInstance();
		int days = getDaysInMonth(month, year);
		endDate.set(year, month, days, 23, 59);

		return new Calendar[] { startDate, endDate };
	}
}
