package org.castafiore.designer.marshalling;

import org.castafiore.designer.EXPortalExecutor;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.interceptors.Interceptor;
import org.castafiore.utils.StringUtil;

public class EvaluatorInterceptor implements Interceptor{

	@Override
	public Interceptor next() {
		
		return null;
	}

	@Override
	public boolean onRender(Container container) {
		try{
			if(StringUtil.isNotEmpty(container.getAttribute("des-id"))){
				if(container.getRoot() instanceof EXPortalExecutor)
					ExpressionEvaluatorUtil.evaluate(container);
				
				
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		return true;
	}

}
