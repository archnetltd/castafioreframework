package org.castafiore.ui.ex.panel;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.Var;

public class EXOverlayPopupPlaceHolder extends EXContainer{

	public EXOverlayPopupPlaceHolder(String name) {
		super(name, "div");
		setStyle("display", "none").setStyle("width","100%").setStyle("height", "100%")
		.setStyle("opacity", "0.75").setStyle("background-color", "black").setStyle("position", "fixed").setStyle("top", "0").setStyle("left", "0").setStyle("z-index", "3000");
		// TODO Auto-generated constructor stub
	}
	
	public void onReady(ClientProxy p){
		super.onReady(p);
		p.appendTo(p.getRoot());
		if(p.getChildren().size() > 0){
			p.getChildren().get(0).appendTo(p.getRoot());
			p.setStyle("display", "block");
		}else{
			p.setStyle("display", "none");
		}
	}
	
	
	public Container addChild(Container c){
		setRendered(false);
		super.addChild(c);
		Dimension dwidth = c.getWidth();
		int width = 300/2;
		if(dwidth != null){
			width = dwidth.getAmount()/2;
		}
		
		String script = "(document.body.clientWidth/2)-" + width;
		String vert = "50 + $(window).scrollTop()";
		c.setStyle("position", "absolute").setStyle("top", new Var(vert)).setStyle("left", new Var(script));
		return this;
	}

}
