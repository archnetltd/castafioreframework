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

import java.util.Locale;

import org.castafiore.ui.ex.i18n.MutableMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

public class ResourceBundleMutableMessageSource extends ResourceBundleMessageSource implements MutableMessageSource {

	private MessageStore messageStore;
	
	public void store(String key, String message, Locale locale) {
		MessageStore ms = getMessageStore();
		
		ms.store(key, message, locale);
		
		ms.clearCache(key);
		
	}
	
	public String getMessage(String key,Locale locale){
		
		MessageStore ms = getMessageStore();
		boolean hasMessage = ms.hasCode(key, locale);
		if(!hasMessage){
			return super.getMessage(key, null, locale);
		}else{
			return ms.getMessage(key, locale);
		}
	}

	public MessageStore getMessageStore() {
		return messageStore;
	}

	public void setMessageStore(MessageStore messageStore) {
		this.messageStore = messageStore;
	}
	
	

}
