package org.castafiore.sample.integrations.zinoui;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tab.TabModel;
import org.castafiore.ui.ex.tab.TabPanel;

public class ZinoApp extends EXApplication {

	public ZinoApp() {
		super("zino");
	}

	@Override
	public void initApp() {
		doAccordion();
		doCalendar();
	}
	
	private ZChart getChart(){
		ZChart chart = new ZChart("chart");
		chart.setType("line");
		Object[][] series = new Object[][]{
				{"Year", "NY Knicks", "Miami Heat", "LA Clippers"},
				{2007, 1, 3, 4},
				{2008, 3, 5, 1},
				{2009, 5, 4, 3},
				{2010, 2, 5, 6},
				{2011, 4, 6, 5},
				{2012, 6, 5, 3}
				};
		chart.setSeries(series);
		chart.setStyle("width", "530px").setStyle("height", "320px");
		return chart;
	}
	
	private void doCalendar(){
		ZCalendar cal = new ZCalendar("cal");
		addChild(cal);
	}

	private void doAccordion() {
		final String[] labels = new String[] { "First One", "Second One",
				"Third One" };

		final String[] contents = new String[] {
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non elit et nisi ornare luctus eget porta felis. Aenean semper, ipsum sit amet mollis commodo, nisl purus laoreet purus, pretium porta mauris quam ac velit. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean non nunc ac mauris euismod ultricies. Morbi risus dolor, molestie at ornare sed, convallis in libero. Nulla facilisi. Mauris non eros sem. Morbi id tellus at enim bibendum ultricies. Nulla eget nulla ut neque bibendum accumsan. Pellentesque convallis, ipsum at posuere laoreet, ante nunc eleifend enim, nec euismod quam mauris quis ipsum. Praesent sagittis dui vel sapien fermentum quis lobortis nisi facilisis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nunc eget purus lacus. Integer in sapien ac massa pharetra aliquet. Proin dictum pellentesque justo eget eleifend.",
				"Fusce sit amet sapien vitae justo pulvinar posuere quis in libero. Cras ut arcu vitae magna luctus pretium. Sed quis dolor in sem egestas interdum. Nunc pharetra auctor metus, eu sollicitudin nisl tincidunt in. Proin odio turpis, iaculis nec euismod nec, pharetra non augue. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc nulla ipsum, scelerisque non tempus non, dictum vel enim. Aliquam lobortis dolor sit amet massa ullamcorper quis egestas sapien egestas.",
				"..." };

		TabModel model = new TabModel() {

			@Override
			public int size() {
				return 3;
			}

			@Override
			public String getTabLabelAt(TabPanel pane, int index,
					boolean selected) {

				return labels[index];
			}

			@Override
			public Container getTabContentAt(TabPanel pane, int index) {
				if(index == 2){
					
					return new EXContainer("", "div").addChild(new ZCalendar("call"));
				}
				if(index == 1){
					return new EXContainer("", "div").addChild(getChart());
				}
				return new EXContainer("", "div").setText(contents[index]);
			}

			@Override
			public int getSelectedTab() {
				return 0;
			}
		};

		ZAccordion accordion = new ZAccordion("acc");
		accordion.setModel(model);
		addChild(accordion);
	}

}
