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
 package org.castafiore.ui.engine.context;

import org.castafiore.ui.Application;
import org.springframework.core.NamedThreadLocal;

public abstract class CastafioreApplicationContextHolder {
	
	private static final ThreadLocal castafioreApplicationContextHolderThread = new NamedThreadLocal("CastafioreApplicationContextHolder");

	public static void setApplicationContext(Application application)
	{
		castafioreApplicationContextHolderThread.set(application);
	}
	
	public static void resetApplicationContext() {
		castafioreApplicationContextHolderThread.set(null);
	}
	
	public static Application getCurrentApplication()
	{
		return (Application)castafioreApplicationContextHolderThread.get();
	}

}
