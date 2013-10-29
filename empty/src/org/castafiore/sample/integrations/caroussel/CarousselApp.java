package org.castafiore.sample.integrations.caroussel;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXContainer;

public class CarousselApp extends EXApplication{

	public CarousselApp() {
		super("caroussel");
		initApp();
	}

	@Override
	public void initApp() {
		Container style = new EXContainer("", "style");
		style.setText(" body {background: none repeat scroll 0 0 #F3F3F3; margin-bottom: 20px;margin-top: 20px;} .wrapper{background: none repeat scroll 0 0 #FFFFFF;border-top: 3px solid silver; margin: 0 auto; width: 930px;}");
		addChild(style);
		
		addClass("wrapper");
		
		final List<Integer> count = new ArrayList<Integer>(1);
		count.add(1);
		CarousselContentProvider provider = new CarousselContentProvider() {
			
			@Override
			public void remove() {
				
			}
			
			@Override
			public Container next() {
				Container c = new EXContainer("", "a").setAttribute("href", "#");
				c.addChild(new EXContainer("", "img").setAttribute("src", "http://1.s3.envato.com/files/43917021/images/slide-"+count.get(0)+".jpg"));
				Integer cc = count.get(0);
				cc = cc+1;
				count.clear();
				count.add(cc);
				return c;
			}
			
			@Override
			public boolean hasNext() {
				return count.get(0) < 7;
			}
		};
		
		EXCaroussel caroussel = new EXCaroussel("caroussel");
		caroussel.setContentProvider(provider);
		addChild(caroussel);
		//<link rel="Stylesheet" type="text/css" href="carousel.css" />
		//addChild(new EXContainer("", "link").setAttribute("type", "text/css").setAttribute("rel", "Stylesheet").setAttribute("href", "http://1.s3.envato.com/files/43917021/carousel.css"));
	}

}
