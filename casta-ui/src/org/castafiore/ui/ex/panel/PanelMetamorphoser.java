package org.castafiore.ui.ex.panel;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.interceptors.Interceptor;
import org.castafiore.utils.ComponentUtil;

public class PanelMetamorphoser implements Interceptor {

	@Override
	public Interceptor next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onRender(Container container) {
		if(!"WARP".equalsIgnoreCase(container.getAttribute("skin"))){
			if(container instanceof EXPanel){
				
				ComponentUtil.metamorphosePanel((EXPanel)container);
			}
			if(container instanceof EXDynaformPanel){
				ComponentUtil.metamorphoseDynaform(container);
			}
		}
		return true;
	}

}
