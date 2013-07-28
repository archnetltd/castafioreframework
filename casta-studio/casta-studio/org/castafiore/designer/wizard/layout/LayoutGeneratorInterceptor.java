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
 package org.castafiore.designer.wizard.layout;

import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.castafiore.designable.DesignableFactory;
import org.castafiore.designer.marshalling.ExpressionEvaluableInvocationHandler;
import org.castafiore.ui.Container;

public class LayoutGeneratorInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation method) throws Throwable {
		Object o = method.proceed();
		if(method.getMethod().getName().equals("getInstance") && method.getArguments().length == 0){
			DesignableFactory factory = (DesignableFactory)method.getThis();
			//Container c = addProxy((Container)o);
			Container c = (Container)o;
			c.setAttribute("des-id", factory.getUniqueId());
			c.setAttribute("__oid", c.getId());
			c.addClass("des");
			return c;
		}
		
		return o;
	}
	
	
	protected Container addProxy(Container c){
		 return  (Container)Proxy.newProxyInstance(LayoutGeneratorInterceptor.class.getClassLoader(), new Class[]{Container.class}, new ExpressionEvaluableInvocationHandler(c));
	}


	

}
