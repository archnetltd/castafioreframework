/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.ui.ex.form;

import java.util.Date;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JDate;
import org.castafiore.ui.js.JMap;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class JDatePicker extends EXInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMap options = new JMap();

	

	public JDatePicker(String name, String value) {
		super(name, value);
	}

	public JDatePicker(String name) {
		super(name);
	}

	public JDatePicker setAltField(String altField) {
		options.put("altField", altField);
		return this;
	}

	public JDatePicker setAltFormat(String altFormat) {
		options.put("altFormat", altFormat);
		return this;
	}

	public JDatePicker setAppendText(String appendText) {
		options.put("appendText", appendText);
		return this;
	}

	public JDatePicker setAutoSize(Boolean autoSize) {
		options.put("autoSize", autoSize);
		return this;
	}

	public JDatePicker setButtonImage(String buttonImage) {
		options.put("buttonImage", buttonImage);
		return this;
	}

	public JDatePicker setButtonImageOnly(Boolean buttonImageOnly) {
		options.put("buttonImageOnly", buttonImageOnly);
		return this;
	}

	public JDatePicker setButtonText(String buttonText) {
		options.put("buttonText", buttonText);
		return this;
	}

	public JDatePicker setChangeMonth(Boolean changeMonth) {
		options.put("changeMonth", changeMonth);
		return this;
	}

	public JDatePicker setChangeYear(Boolean changeYear) {
		options.put("changeYear", changeYear);
		return this;
	}

	public JDatePicker setCloseText(String closeText) {
		options.put("closeText", closeText);
		return this;
	}

	public JDatePicker setConstrainInput(String constrainInput) {
		options.put("constrainInput", constrainInput);
		return this;
	}

	public JDatePicker setCurrentText(String currentText) {
		options.put("currentText", currentText);
		return this;
	}

	public JDatePicker setDateFormat(String dateFormat) {
		options.put("dateFormat", dateFormat);
		return this;
	}

	public JDatePicker setDayNames(String[] dayNames) {
		options.put("dayNames", dayNames);
		return this;
	}

	public JDatePicker setDayNamesMin(String[] dayNamesMin) {
		options.put("dayNamesMin", dayNamesMin);
		return this;
	}

	public JDatePicker setDayNamesShort(String[] dayNamesShort) {
		options.put("dayNamesShort", dayNamesShort);
		return this;
	}

	public JDatePicker setDuration(Integer duration) {
		options.put("duration", duration);
		return this;
	}

	public JDatePicker setFirstDay(Integer firstDay) {
		options.put("firstDay", firstDay);
		return this;
	}

	public JDatePicker setGotoCurrent(Boolean gotoCurrent) {
		options.put("gotoCurrent", gotoCurrent);
		return this;
	}

	public JDatePicker setHideIfNoPrevNext(Boolean hideIfNoPrevNext) {
		options.put("hideIfNoPrevNext", hideIfNoPrevNext);
		return this;
	}

	public JDatePicker setIsRTL(Boolean isRTL) {
		options.put("isRTL", isRTL);
		return this;
	}

	public JDatePicker setMaxDate(Date maxDate) {
		options.put("maxDate", new JDate(maxDate));
		return this;
	}

	public JDatePicker setMinDate(Date minDate) {
		options.put("minDate", new JDate(minDate));
		return this;
	}

	public JDatePicker setMonthNames(String[] monthNames) {
		options.put("monthNames", monthNames);
		return this;
	}

	public JDatePicker setMonthNamesShort(String[] monthNamesShort) {
		options.put("monthNamesShort", monthNamesShort);
		return this;
	}

	public JDatePicker setNavigationAsDateFormat(Boolean navigationAsDateFormat) {
		options.put("navigationAsDateFormat", navigationAsDateFormat);
		return this;
	}

	public JDatePicker setNextText(String nextText) {
		options.put("nextText", nextText);
		return this;
	}

	public JDatePicker setNumberOfMonths(Integer[] numberOfMonths) {
		options.put("numberOfMonths", numberOfMonths);
		return this;
	}

	public JDatePicker setOnChangeMonthYear(Event onChangeMonthYear) {
		options.put("onChangeMonthYear", onChangeMonthYear, this);
		return this;
	}

	public JDatePicker setOnClose(Event onClose) {
		options.put("onClose", onClose, this);
		return this;
	}

	public JDatePicker setOnSelect(Event onSelect) {
		options.put("onSelect", onSelect, this);
		return this;
	}

	public JDatePicker setPrevText(String prevText) {
		options.put("prevText", prevText);
		return this;
	}

	public JDatePicker setSelectOtherMonths(Boolean selectOtherMonths) {
		options.put("selectOtherMonths", selectOtherMonths);
		return this;
	}

	public JDatePicker setShortYearCutoff(Integer shortYearCutoff) {
		options.put("shortYearCutoff", shortYearCutoff);
		return this;
	}

	public JDatePicker setShowAnim(String showAnim) {
		options.put("showAnim", showAnim);
		return this;
	}

	public JDatePicker setShowButtonPanel(Boolean showButtonPanel) {
		options.put("showButtonPanel", showButtonPanel);
		return this;
	}

	public JDatePicker setShowCurrentAtPos(Integer showCurrentAtPos) {
		options.put("showCurrentAtPos", showCurrentAtPos);
		return this;
	}

	public JDatePicker setShowMonthAfterYear(Boolean showMonthAfterYear) {
		options.put("showMonthAfterYear", showMonthAfterYear);
		return this;
	}

	public JDatePicker setShowOn(String showOn) {
		options.put("showOn", showOn);
		return this;
	}

	public JDatePicker setShowOptions(JMap showOptions) {
		options.put("showOptions", showOptions);
		return this;
	}

	public JDatePicker setShowOtherMonths(Boolean showOtherMonths) {
		options.put("showOtherMonths", showOtherMonths);
		return this;
	}

	public JDatePicker setShowWeek(Boolean showWeek) {
		options.put("showWeek", showWeek);
		return this;
	}

	public JDatePicker setStepMonths(Integer stepMonths) {
		options.put("stepMonths", stepMonths);
		return this;
	}

	public JDatePicker setWeekHeader(String weekHeader) {
		options.put("weekHeader", weekHeader);
		return this;
	}

	public JDatePicker setYearRange(Integer from, Integer to) {
		options.put("yearRange", from + ":" + to);
		return this;
	}

	public JDatePicker setYearSuffix(String yearSuffix) {
		options.put("yearSuffix", yearSuffix);
		return this;
	}

	

	public void onReady(ClientProxy proxy) {
		proxy.addMethod("datepicker",options);
	}

}
