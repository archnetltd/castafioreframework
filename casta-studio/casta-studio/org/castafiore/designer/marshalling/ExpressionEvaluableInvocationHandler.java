package org.castafiore.designer.marshalling;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.castafiore.ui.Container;

public class ExpressionEvaluableInvocationHandler implements InvocationHandler {
	
	private Container source;
	
	

	public ExpressionEvaluableInvocationHandler(Container source) {
		super();
		this.source = source;
	}



	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if(method.getName().equals("setRendered")){
			if((Boolean)args[0]){
				ExpressionEvaluatorUtil.evaluate(source);
			}
		}
		
		return method.invoke(source, args);
	}

}
