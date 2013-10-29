package org.castafiore.sample.integrations.zinoui;

import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tab.TabModel;
import org.castafiore.ui.ex.tab.TabPanel;
import org.castafiore.ui.ex.tab.TabRenderer;

public class ZAccordion extends EXContainer implements TabPanel, TabRenderer{

	private TabModel model;
	
	public ZAccordion(String name) {
		super(name, "div");
	}
	
	private void repaint(){
		this.getChildren().clear();
		setRendered(false);
		if(model != null){
			int size  = model.size();
			for(int i = 0; i < size;i++){
				String label = model.getTabLabelAt(this, i, false);
				Container content = getTabRenderer().getComponentAt(this, model, i);
				Container title = new EXContainer("", "h3").setText(label);
				addChild(title);
				addChild(content);
			}
		}
	}

	public void setModel(TabModel model){
		this.model = model;
		repaint();
	}
	
	@Override
	public TabModel getModel() {
		return model;
	}

	@Override
	public TabRenderer getTabRenderer() {
		return this;
	}

	@Override
	public Container getComponentAt(TabPanel pane, TabModel model, int index) {
		return model.getTabContentAt(pane, index);
	}

	@Override
	public void onSelect(TabPanel pane, TabModel model, int index, Container tab) {
		
	}

	@Override
	public void onDeselect(TabPanel pane, TabModel model, int index,
			Container tab) {
		
	}

	
	public void onReady(ClientProxy proxy){
		proxy.getCSS("http://zinoui.com/1.3/themes/silver/zino.core.css").getCSS("http://zinoui.com/1.3/themes/silver/zino.accordion.css");
		proxy.getScript("http://zinoui.com/1.3/compiled/zino.accordion.min.js", proxy.clone().addMethod("zinoAccordion"));
	}
	

}
