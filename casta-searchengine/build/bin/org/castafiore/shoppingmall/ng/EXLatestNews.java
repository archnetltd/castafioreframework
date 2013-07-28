package org.castafiore.shoppingmall.ng;

import java.text.SimpleDateFormat;
import java.util.List;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.types.Article;

public class EXLatestNews extends EXContainer {

	public EXLatestNews(String name, List<Article> latestNews) {
		super(name, "div");
		setStyle("line-height", "20px");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		for(Article art : latestNews){
			addChild(new EXContainer("", "label").setText(format.format(art.getDateCreated().getTime())));
			addChild(new EXContainer("", "label").setStyle("display", "block").setStyle("color", "red").setText(art.getTitle()));
			addChild(new EXContainer("", "p").setText(art.getSummary()));
			addChild(new EXContainer("", "hr"));
		}
		// TODO Auto-generated constructor stub
	}

}
