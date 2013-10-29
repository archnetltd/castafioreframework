package org.castafiore.sample.integrations.caroussel;

import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ResourceUtil;

public class EXCaroussel extends EXContainer{

	private final Container slides = new EXContainer("slides", "div").addClass("slides");
	
	private CarousselContentProvider contentProvider;
	
	public EXCaroussel(String name) {
		super(name, "div");
		addClass("carousel");
		addChild(slides);
	}

	public CarousselContentProvider getContentProvider() {
		return contentProvider;
	}

	public void setContentProvider(CarousselContentProvider contentProvider) {
		this.contentProvider = contentProvider;
		slides.getChildren().clear();
		slides.setRendered(false);
		
		while(contentProvider.hasNext()){
			Container c = contentProvider.next();
			Container w = new EXContainer("div", "div").addChild(c);
			slides.addChild(w);
		}
	}

	
	
	
	public void onReady(ClientProxy proxy){
		
		proxy.getScript("http://1.s3.envato.com/files/43917021/scripts/jquery.mousewheel.min.js",
				proxy.clone().getScript("http://1.s3.envato.com/files/43917021/scripts/jquery.carousel-1.1.min.js",
						proxy.clone().getCSS(ResourceUtil.getDownloadURL("classpath", "org/castafiore/sample/integrations/caroussel/caroussel.css"))
						.addMethod("carousel", new JMap()
						.put("carouselWidth", 930)
						.put("carouselHeight", 330)
						.put("directionNav", true)
						.put("shadow", true)
						.put("buttonNav", "bullets")
								)));
	}
	

}
