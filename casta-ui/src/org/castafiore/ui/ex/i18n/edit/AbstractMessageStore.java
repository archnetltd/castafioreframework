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
 package org.castafiore.ui.ex.i18n.edit;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class AbstractMessageStore implements MessageStore{

	Map<String, String> cache = Collections.synchronizedMap(new WeakHashMap<String, String>());
	
	Map<String, Boolean> presenceMap = Collections.synchronizedMap(new WeakHashMap<String, Boolean>());
	
	public synchronized void clearCache(String code) {
		cache.remove(code);
		presenceMap.remove(code);
		
	}

	public synchronized void clearCache() {
		cache.clear();
		presenceMap.clear();
		
	}
	
	public abstract String doGetMessageFromStorageDevice(String key, Locale locale);
	
	public abstract void doStoreMessageIntoStorageDevice(String key, String message, Locale locale);

	public String getMessage(String key, Locale locale) {
		String buildCode = buildCode(key, locale);
		if(cache.containsKey(buildCode)){
			return cache.get(buildCode);
		}else{
			return doGetMessageFromStorageDevice(key, locale);
		}
	}

	public boolean hasCode(String code, Locale locale) {
		String buildCode = buildCode(code, locale);
		if(presenceMap.containsKey(buildCode)){
			return false;
		}
		
		String message = getMessage(code, locale);
		if(message == null){
			presenceMap.put(buildCode, true);
			return false;
		}else{
			cache.put(buildCode, message);
			return true;
		}
		
		
	}
	
	protected String buildCode(String code, Locale locale){
		return code + "_" + locale.toString();
	}

	public void store(String key, String message, Locale locale) {
		doStoreMessageIntoStorageDevice(key, message, locale);
		
	}

}
