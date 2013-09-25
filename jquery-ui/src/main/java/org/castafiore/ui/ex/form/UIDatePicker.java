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
public class UIDatePicker extends EXInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMap options = new JMap();

	

	public UIDatePicker(String name, String value) {
		super(name, value);
	}

	public UIDatePicker(String name) {
		super(name);
	}

	public UIDatePicker setAltField(String altField) {
		options.put("altField", altField);
		return this;
	}

	public UIDatePicker setAltFormat(String altFormat) {
		options.put("altFormat", altFormat);
		return this;
	}

	public UIDatePicker setAppendText(String appendText) {
		options.put("appendText", appendText);
		return this;
	}

	public UIDatePicker setAutoSize(Boolean autoSize) {
		options.put("autoSize", autoSize);
		return this;
	}

	public UIDatePicker setButtonImage(String buttonImage) {
		options.put("buttonImage", buttonImage);
		return this;
	}

	public UIDatePicker setButtonImageOnly(Boolean buttonImageOnly) {
		options.put("buttonImageOnly", buttonImageOnly);
		return this;
	}

	public UIDatePicker setButtonText(String buttonText) {
		options.put("buttonText", buttonText);
		return this;
	}

	public UIDatePicker setChangeMonth(Boolean changeMonth) {
		options.put("changeMonth", changeMonth);
		return this;
	}

	public UIDatePicker setChangeYear(Boolean changeYear) {
		options.put("changeYear", changeYear);
		return this;
	}

	public UIDatePicker setCloseText(String closeText) {
		options.put("closeText", closeText);
		return this;
	}

	public UIDatePicker setConstrainInput(String constrainInput) {
		options.put("constrainInput", constrainInput);
		return this;
	}

	public UIDatePicker setCurrentText(String currentText) {
		options.put("currentText", currentText);
		return this;
	}

	public UIDatePicker setDateFormat(String dateFormat) {
		options.put("dateFormat", dateFormat);
		return this;
	}

	public UIDatePicker setDayNames(String[] dayNames) {
		options.put("dayNames", dayNames);
		return this;
	}

	public UIDatePicker setDayNamesMin(String[] dayNamesMin) {
		options.put("dayNamesMin", dayNamesMin);
		return this;
	}

	public UIDatePicker setDayNamesShort(String[] dayNamesShort) {
		options.put("dayNamesShort", dayNamesShort);
		return this;
	}

	public UIDatePicker setDuration(Integer duration) {
		options.put("duration", duration);
		return this;
	}

	public UIDatePicker setFirstDay(Integer firstDay) {
		options.put("firstDay", firstDay);
		return this;
	}

	public UIDatePicker setGotoCurrent(Boolean gotoCurrent) {
		options.put("gotoCurrent", gotoCurrent);
		return this;
	}

	public UIDatePicker setHideIfNoPrevNext(Boolean hideIfNoPrevNext) {
		options.put("hideIfNoPrevNext", hideIfNoPrevNext);
		return this;
	}

	public UIDatePicker setIsRTL(Boolean isRTL) {
		options.put("isRTL", isRTL);
		return this;
	}

	public UIDatePicker setMaxDate(Date maxDate) {
		options.put("maxDate", new JDate(maxDate));
		return this;
	}

	public UIDatePicker setMinDate(Date minDate) {
		options.put("minDate", new JDate(minDate));
		return this;
	}

	public UIDatePicker setMonthNames(String[] monthNames) {
		options.put("monthNames", monthNames);
		return this;
	}

	public UIDatePicker setMonthNamesShort(String[] monthNamesShort) {
		options.put("monthNamesShort", monthNamesShort);
		return this;
	}

	public UIDatePicker setNavigationAsDateFormat(Boolean navigationAsDateFormat) {
		options.put("navigationAsDateFormat", navigationAsDateFormat);
		return this;
	}

	public UIDatePicker setNextText(String nextText) {
		options.put("nextText", nextText);
		return this;
	}

	public UIDatePicker setNumberOfMonths(Integer[] numberOfMonths) {
		options.put("numberOfMonths", numberOfMonths);
		return this;
	}

	public UIDatePicker setOnChangeMonthYear(Event onChangeMonthYear) {
		options.put("onChangeMonthYear", onChangeMonthYear, this);
		return this;
	}

	public UIDatePicker setOnClose(Event onClose) {
		options.put("onClose", onClose, this);
		return this;
	}

	public UIDatePicker setOnSelect(Event onSelect) {
		options.put("onSelect", onSelect, this);
		return this;
	}

	public UIDatePicker setPrevText(String prevText) {
		options.put("prevText", prevText);
		return this;
	}

	public UIDatePicker setSelectOtherMonths(Boolean selectOtherMonths) {
		options.put("selectOtherMonths", selectOtherMonths);
		return this;
	}

	public UIDatePicker setShortYearCutoff(Integer shortYearCutoff) {
		options.put("shortYearCutoff", shortYearCutoff);
		return this;
	}

	public UIDatePicker setShowAnim(String showAnim) {
		options.put("showAnim", showAnim);
		return this;
	}

	public UIDatePicker setShowButtonPanel(Boolean showButtonPanel) {
		options.put("showButtonPanel", showButtonPanel);
		return this;
	}

	public UIDatePicker setShowCurrentAtPos(Integer showCurrentAtPos) {
		options.put("showCurrentAtPos", showCurrentAtPos);
		return this;
	}

	public UIDatePicker setShowMonthAfterYear(Boolean showMonthAfterYear) {
		options.put("showMonthAfterYear", showMonthAfterYear);
		return this;
	}

	public UIDatePicker setShowOn(String showOn) {
		options.put("showOn", showOn);
		return this;
	}

	public UIDatePicker setShowOptions(JMap showOptions) {
		options.put("showOptions", showOptions);
		return this;
	}

	public UIDatePicker setShowOtherMonths(Boolean showOtherMonths) {
		options.put("showOtherMonths", showOtherMonths);
		return this;
	}

	public UIDatePicker setShowWeek(Boolean showWeek) {
		options.put("showWeek", showWeek);
		return this;
	}

	public UIDatePicker setStepMonths(Integer stepMonths) {
		options.put("stepMonths", stepMonths);
		return this;
	}

	public UIDatePicker setWeekHeader(String weekHeader) {
		options.put("weekHeader", weekHeader);
		return this;
	}

	public UIDatePicker setYearRange(Integer from, Integer to) {
		options.put("yearRange", from + ":" + to);
		return this;
	}

	public UIDatePicker setYearSuffix(String yearSuffix) {
		options.put("yearSuffix", yearSuffix);
		return this;
	}

	

	public void onReady(ClientProxy proxy) {
		proxy.addMethod("datepicker",options);
	}

}
