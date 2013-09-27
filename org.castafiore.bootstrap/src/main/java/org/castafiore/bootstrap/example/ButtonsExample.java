package org.castafiore.bootstrap.example;

import org.castafiore.bootstrap.AlignmentType;
import org.castafiore.bootstrap.buttons.BTButton;
import org.castafiore.bootstrap.buttons.BTButtonGroup;
import org.castafiore.bootstrap.buttons.BTToolBar;
import org.castafiore.bootstrap.buttons.ButtonType;
import org.castafiore.bootstrap.demo.BSExample;
import org.castafiore.bootstrap.dropdown.BTDropDown;
import org.castafiore.bootstrap.inputgroup.BTInputGroup;
import org.castafiore.bootstrap.layout.BTLayout;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXRadioButton;

public class ButtonsExample extends EXContainer{

	public ButtonsExample() {
		super("buttons", "div");
		createExample0();
		createExample();
		createExample1();
		createExample2();
		createExample3();
	}
	
	
	
	
	public void createExample2(){
		BTInputGroup group1 = new BTInputGroup("group1", new EXInput("email"));
		group1.setAddOn("@");
		
		group1.setPlaceHolder("username");
		
		Container container = new EXContainer("ctn", "div");
		container.addChild(group1);
		
		BTInputGroup group2 = new BTInputGroup("group2", new EXInput("decimal"));
		group2.setAddOn(".00").setAddonAlignment(AlignmentType.RIGHT);
		container.addChild(group2);
		
		String source = "BTInputGroup group1 = new BTInputGroup(\"group1\", new EXInput(\"email\"));\n"
		+"group1.setAddOn(\"@\");\n"
		+"group1.setPlaceHolder(\"username\");\n"
		+"Container container = new EXContainer(\"ctn\", \"div\");\n"
		+"container.addChild(group1);\n"
		+"BTInputGroup group2 = new BTInputGroup(\"group2\", new EXInput(\"decimal\"));\n"
		+"group2.setAddOn(\".00\").setAddonAlignment(AlignmentType.RIGHT);\n"
		+"container.addChild(group2);";
		
		BSExample ex = new BSExample("ex");
		ex.setExample(container, source);
		
		addChild(ex);
	}
	
	
	public void createExample3(){
		
		BTLayout layout = new BTLayout("layout", "6:6");
		BTInputGroup group1 = new BTInputGroup("group1", new EXInput("email"));
		group1.setAddOn(new EXCheckBox("cb"));
		
		group1.setPlaceHolder("username");
		
	
		
		BTInputGroup group2 = new BTInputGroup("group2", new EXInput("decimal"));
		group2.setAddOn(new EXRadioButton("br")).setAddonAlignment(AlignmentType.RIGHT);
		
		layout.addChild(group1, "0,0");
		layout.addChild(group2, "1,0");
	
		
		String source = "BTLayout layout = new BTLayout(\"layout\", \"6:6\");\n"
		+"BTInputGroup group1 = new BTInputGroup(\"group1\", new EXInput(\"email\"));\n"
		+"group1.setAddOn(new EXCheckBox(\"cb\"));\n"
		+"group1.setPlaceHolder(\"username\");\n"
		+"BTInputGroup group2 = new BTInputGroup(\"group2\", new EXInput(\"decimal\"));\n"
		+"group2.setAddOn(new EXRadioButton(\"br\")).setAddonAlignment(AlignmentType.RIGHT);\n"
		+"layout.addChild(group1, \"0,0\");\n"
		+"layout.addChild(group2, \"1,0\");\n";
		
		BSExample ex = new BSExample("ex");
		
		
		ex.setExample(layout, source);
		
		addChild(ex);
	}
	
	public void createExample0(){
		 BTDropDown bt = new BTDropDown("drop");
		 bt.addItem("1", "Action");
		 bt.addItem("2", "Another action");
		 bt.addItem("3", "Something else here");
		 bt.addDivider();
		 bt.addItem("4", "Seperate link");
		 
		 
		 
		BSExample example = new BSExample("Btdropdown");
		example.setExample(bt, 
				"org.castafiore.bootstrap.dropdown.BTDropDown;\n\n" +
				"BTDropDown bt = new BTDropDown(\"drop\");\n" +
				"bt.addItem(\"1\", \"Action\");\n" +
				"bt.addItem(\"2\", \"Another action\");\n" +
				"bt.addItem(\"3\", \"Something else here\");\n" +
				"bt.addDivider();\n" +
				"bt.addItem(\"4\", \"Seperate link\");\n");
		 
		addChild(example);
	}
	
	public void createExample(){
		BTButtonGroup group = new BTButtonGroup("ssfs");
		BTButton left = new BTButton("left", "Left");
		left.setButtonType(ButtonType.DEFAULT);
		BTButton middle = new BTButton("middle", "Middle");
		BTButton right = new BTButton("right", "Right");
		right.setButtonType(ButtonType.WARNING);
		group.addButton(left).addButton(middle).addButton(right);
		
		BSExample example1 = new BSExample("example1");
		
		example1.setExample(group, "import org.castafiore.bootstrap.buttons.BTButtonGroup;\n" +
				"import org.castafiore.bootstrap.buttons.BTButton;\n" +
				"import org.castafiore.bootstrap.buttons.ButtonType;\n\n"+
				"BTButtonGroup group = new BTButtonGroup(\"ssfs\");\n\n" +
				"BTButton left = new BTButton(\"left\",\"Left\");\n" +
				"left.setButtonType(ButtonType.DEFAULT);\n\n" +
				"BTButton middle = new BTButton(\"middle\", \"Middle\");\n\n" +
				"BTButton right = new BTButton(\"right\", \"Right\");\n" +
				"right.setButtonType(ButtonType.WARNING);\n\n" +
				"group.addButton(left).addButton(middle).addButton(right);");
		
		addChild(example1);
	}
	
	
	public void createExample1(){
		BTToolBar toolbar = new BTToolBar("toolbar");
		
		BTButtonGroup group = new BTButtonGroup("ssfs");
		BTButton left = new BTButton("left", "Left");
		left.setButtonType(ButtonType.DEFAULT);
		BTButton middle = new BTButton("middle", "Middle");
		BTButton right = new BTButton("right", "Right");
		right.setButtonType(ButtonType.WARNING);
		group.addButton(left).addButton(middle).addButton(right);
		
		toolbar.addItem(group);
		
		
		BTDropDown bt = new BTDropDown("drop");
		bt.addItem("1", "Action");
		bt.addItem("2", "Another action");
		bt.addItem("3", "Something else here");
		bt.addDivider();
		bt.addItem("4", "Seperate link");
		
		bt.setRootButton("Action", false);
		
		toolbar.addItem(bt);
		
		BSExample e = new BSExample("sdf");
		e.setExample(toolbar, "BTToolBar toolbar = new BTToolBar(\"toolbar\");\n" +
				"BTButton left = new BTButton(\"left\", \"Left\");" +
				"left.setButtonType(ButtonType.DEFAULT);" +
				"BTButton middle = new BTButton(\"middle\", \"Middle\");" +
				"BTButton right = new BTButton(\"right\", \"Right\");" +
				"right.setButtonType(ButtonType.WARNING);\n" +
				"group.addButton(left).addButton(middle).addButton(right);\n" +
				"toolbar.addItem(group);\n" +
				"BTDropDown bt = new BTDropDown(\"drop\");\n" +
				"bt.addItem(\"1\", \"Action\");\n" +
				"bt.addItem(\"2\", \"Another action\");\n" +
				"bt.addItem(\"3\", \"Something else here\");\n" +
				"bt.addDivider();\n" +
				"bt.addItem(\"4\", \"Seperate link\");\n" +
				"bt.setRootButton(\"Action\", false);\n" +
				"toolbar.addItem(bt);\n") ;
		
		addChild(e);
		
		
	}

}
