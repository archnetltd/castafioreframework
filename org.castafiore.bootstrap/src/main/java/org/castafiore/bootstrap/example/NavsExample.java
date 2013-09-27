package org.castafiore.bootstrap.example;

import org.castafiore.bootstrap.demo.BSExample;
import org.castafiore.bootstrap.dropdown.BTDropDown;
import org.castafiore.bootstrap.layout.BTLayout;
import org.castafiore.bootstrap.nav.BTNav;
import org.castafiore.bootstrap.nav.BTNavContent;
import org.castafiore.bootstrap.nav.NavType;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tab.TabModel;
import org.castafiore.ui.ex.tab.TabPanel;
import org.castafiore.utils.IOUtil;

public class NavsExample extends EXContainer{

	public NavsExample(String name) {
		super(name, "div");
		createExample0();
		createExample1();
	}
	
	public void createExample1(){
		BTNavContent content = new BTNavContent("s", "div") {
			private void load(TabPanel panel, TabModel model){
				if(getChildren().size() >0)
					return;
				for(int i =0; i < model.size(); i++){
					Container c = model.getTabContentAt(panel, i);
					addChild(c.setDisplay(false));
				}
			}
			
			
			@Override
			public Container showTab(TabPanel panel, TabModel model, int index) {
				load(panel, model);
				
				for(int i =0; i < getChildren().size(); i++){
					getChildByIndex(i).setDisplay(i==index);
				}
				return getChildByIndex(index);
			}
			
			
		};
		
		BTLayout layout = new BTLayout("ll", "12,12");
		BTNav nav = new BTNav("nav", content);
		
		layout.addChild(nav, "0,0");
		layout.addChild(content,"0,1");
		
		nav.setModel(getSampleNav());
		nav.setDropDown(2, getDropdown());
		nav.setNavType(NavType.PILLS);
		BSExample example = new BSExample("s");
		String src = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("samples/NavsExample1.txt"));
		example.setExample(layout, src);
		addChild(example);
	}
	
	public void createExample0(){
		BTNavContent content = new BTNavContent("s", "div") {
			private void load(TabPanel panel, TabModel model){
				if(getChildren().size() >0)
					return;
				for(int i =0; i < model.size(); i++){
					Container c = model.getTabContentAt(panel, i);
					addChild(c.setDisplay(false));
				}
			}
			
			
			@Override
			public Container showTab(TabPanel panel, TabModel model, int index) {
				load(panel, model);
				
				for(int i =0; i < getChildren().size(); i++){
					getChildByIndex(i).setDisplay(i==index);
				}
				return getChildByIndex(index);
			}
			
			
		};
		
		BTLayout layout = new BTLayout("ll", "12,12");
		BTNav nav = new BTNav("nav", content);
		
		layout.addChild(nav, "0,0");
		layout.addChild(content,"0,1");
		
		nav.setModel(getSampleNav());
		nav.setDropDown(2, getDropdown());
		
		BSExample example = new BSExample("s");
		String src = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("samples/NavsExample.txt"));
		example.setExample(layout, src);
		addChild(example);
	}
	
	
	private BTDropDown getDropdown(){
		BTDropDown drop = new BTDropDown("dp");
		drop.addItem("ss", "@Tic tac toe");
		drop.addItem("vv", "Quartz");
		return drop;
	}
	
	public TabModel getSampleNav(){
		TabModel model = new TabModel() {
			
			@Override
			public int size() {
				return 3;
			}
			
			@Override
			public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
				if(index == 0){
					return "Home";
				}else if(index ==1){
					return "Profile";
				}else{
					return "Drop down";
				}
			}
			
			@Override
			public Container getTabContentAt(TabPanel pane, int index) {
				Container c = new EXContainer("", "p");
				if(index == 0){
					c.setText("Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua, retro synth master cleanse. Mustache cliche tempor, williamsburg carles vegan helvetica. Reprehenderit butcher retro keffiyeh dreamcatcher synth. Cosby sweater eu banh mi, qui irure terry richardson ex squid. Aliquip placeat salvia cillum iphone. Seitan aliquip quis cardigan american apparel, butcher voluptate nisi qui.");
				}else if(index == 1){
					c.setText("Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes anderson artisan four loko farm-to-table craft beer twee. Qui photo booth letterpress, commodo enim craft beer mlkshk aliquip jean shorts ullamco ad vinyl cillum PBR. Homo nostrud organic, assumenda labore aesthetic magna delectus mollit. Keytar helvetica VHS salvia yr, vero magna velit sapiente labore stumptown. Vegan fanny pack odio cillum wes anderson 8-bit, sustainable jean shorts beard ut DIY ethical culpa terry richardson biodiesel. Art party scenester stumptown, tumblr butcher vero sint qui sapiente accusamus tattooed echo park.");
				}else{
					c.setText("Etsy mixtape wayfarers, ethical wes anderson tofu before they sold out mcsweeney's organic lomo retro fanny pack lo-fi farm-to-table readymade. Messenger bag gentrify pitchfork tattooed craft beer, iphone skateboard locavore carles etsy salvia banksy hoodie helvetica. DIY synth PBR banksy irony. Leggings gentrify squid 8-bit cred pitchfork. Williamsburg banh mi whatever gluten-free, carles pitchfork biodiesel fixie etsy retro mlkshk vice blog. Scenester cred you probably haven't heard of them, vinyl craft beer blog stumptown. Pitchfork sustainable tofu synth chambray yr.");
				}
				return c;
			}
			
			@Override
			public int getSelectedTab() {
				
				return 0;
			}
		};
		
		return model;
	}

}
