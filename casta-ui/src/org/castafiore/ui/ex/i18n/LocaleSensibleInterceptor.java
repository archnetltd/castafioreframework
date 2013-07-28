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
 package org.castafiore.ui.ex.i18n;

import java.util.Locale;

import org.castafiore.ui.Container;
import org.castafiore.ui.interceptors.Interceptor;
import org.springframework.context.NoSuchMessageException;

public class LocaleSensibleInterceptor implements Interceptor {
	
	private MutableMessageSource mutableMessageSource;
	
	private LocaleResolver localeResolver;

	public Interceptor next() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean onRender(Container container) {
		if(container instanceof LocalSensible){
			String text = container.getText();
			if(text.startsWith("$")){
				String key = text.substring(1);
				Locale locale= localeResolver.getCurrentLocal(container.getRoot());
				try{
					String message = mutableMessageSource.getMessage(key, null, locale);
					container.setText(message);
				}catch(NoSuchMessageException nse){
					
				}
			}
		}
		return true;
	}

}
