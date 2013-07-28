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
 package org.castafiore.persistence;

import javax.servlet.http.HttpServletRequest;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.engine.context.CastafioreFilter;


public class HibernateSessionFilter implements CastafioreFilter{
	
	public  void doStart(HttpServletRequest request)throws Exception{

		
	}
	
	
	public void doEnd(HttpServletRequest request)throws Exception{
		Dao dao = SpringUtil.getBeanOfType(Dao.class);

		dao.closeSession();
	}
	
	public void onException(HttpServletRequest request, Exception e){
//		e.printStackTrace();
		Dao dao = SpringUtil.getBeanOfType(Dao.class);
		dao.rollBack();
	}
	

}
