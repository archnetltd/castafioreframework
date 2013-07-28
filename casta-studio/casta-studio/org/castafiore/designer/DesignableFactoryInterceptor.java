package org.castafiore.designer;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.ui.interceptors.Interceptor;

public class DesignableFactoryInterceptor implements Interceptor{
	
	private final static Map<String, String>  ICON_MAP = new HashMap<String, String>();
	
	static{
		ICON_MAP.put("Paragraph", "url('icons/pilcrow.png')");
		ICON_MAP.put("H1", "url('icons/text_heading_1.png')");
		ICON_MAP.put("H2", "url('icons/text_heading_1.png')");
		ICON_MAP.put("H3", "url('icons/text_heading_1.png')");
		ICON_MAP.put("H4", "url('icons/text_heading_1.png')");
		ICON_MAP.put("H5", "url('icons/pilcrow.png')");
		ICON_MAP.put("H6", "url('icons/pilcrow.png')");
		ICON_MAP.put("default", "icons/text_align_justify.png");
		
		
	}

	@Override
	public Interceptor next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onRender(Container container) {
//		if(container instanceof DesignableFactory){
//			String text = container.getText(false);
//			container.setStyle("border", "none").setStyle("margin", "0").setStyle("padding", "0").setText("");
//			container.removeClass("ui-state-hover");
//			container.setStyle("width", "16px").setStyle("height", "16px").setStyle("background-repeat", "no-repeat");
//			container.setStyle("min-width", "0");
//			container.setAttribute("title", text);
//			if(ICON_MAP.containsKey(text)){
//				container.setStyle("background-image", "url('"+ICON_MAP.get(text)+"')");
//			}else{
//				container.setStyle("background-image", "url('"+ICON_MAP.get("default")+"')");
//			}
//		}
		return true;
	}

}
