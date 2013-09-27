package org.castafiore.bootstrap.inputgroup;

import org.castafiore.bootstrap.AlignmentType;
import org.castafiore.bootstrap.buttons.BTButton;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXRadioButton;

public class BTInputGroup extends EXContainer {
	


	public BTInputGroup(String name, EXInput input) {
		super(name, "div");
		addClass("input-group");
		addChild(new EXContainer("addon", "span").addClass("input-group-addon").setText("@"));
		addChild(input.addClass("form-control"));
	}
	
	
	
	
	public BTInputGroup setAddOn(String text){
		Container addon = getChild("addon");
		if(addon != null){
			getChild("addon").setText("").setStyleClass("input-group-addon").getChildren();
			getChild("addon").setText(text);
			return this;
		}else{
			Container dropdown = getChild("dpd");
			if(dropdown != null){
				dropdown.remove();
			}
			addChildAt(new EXContainer("addon", "span").addClass("input-group-addon").setText(text),0);
			return this;
		}
		
	}
	
	public BTInputGroup setAddOn(EXCheckBox cb){
		
		Container addon = getChild("addon");
		if(addon != null){
			getChild("addon").setText("").setStyleClass("input-group-addon").getChildren().clear();
			getChild("addon").addChild(cb);
			return this;
		}else{
			Container dropdown = getChild("dpd");
			if(dropdown != null){
				dropdown.remove();
			}
			addChildAt(new EXContainer("addon", "span").addClass("input-group-addon").addChild(cb),0);
			return this;
		}
	}
	
	public BTInputGroup setAddOn(EXRadioButton cb){
		Container addon = getChild("addon");
		if(addon != null){
			getChild("addon").setText("").setStyleClass("input-group-addon").getChildren().clear();
			getChild("addon").addChild(cb);
			return this;
		}else{
			Container dropdown = getChild("dpd");
			if(dropdown != null){
				dropdown.remove();
			}
			addChildAt(new EXContainer("addon", "span").addClass("input-group-addon").addChild(cb),0);
			return this;
		}
	}
	
	public BTInputGroup setAddOn(BTButton bt){
		
		Container addon = getChild("addon");
		if(addon != null){
			getChild("addon").setText("").setStyleClass("input-group-btn").getChildren().clear();
			getChild("addon").addChild(bt);
			return this;
		}else{
			Container dropdown = getChild("dpd");
			if(dropdown != null){
				dropdown.remove();
			}
			addChildAt(new EXContainer("addon", "span").addClass("input-group-btn").addChild(bt),0);
			return this;
		}
	}
	
	public BTInputGroup setPlaceHolder(String placeHolder){
		getDescendentOfType(EXInput.class).setAttribute("placeholder",placeHolder);
		return this;
	}

	public BTInputGroup setAddonAlignment(AlignmentType position){
		
		if(getChild("addon") != null){
			if(position == AlignmentType.LEFT){
				if(!getChildByIndex(0).getName().equals("addon")){
					Container addon = getChildByIndex(1);
					Container input = getChildByIndex(0);
					getChildren().clear();
					addChild(addon);
					addChild(input);
					setRendered(false);
				}
			}else{
				if(!getChildByIndex(1).getName().equals("addon")){
					Container addon = getChildByIndex(0);
					Container input = getChildByIndex(1);
					getChildren().clear();
					
					addChild(input);
					addChild(addon);
					setRendered(false);
				}
			}
		}
		
		return this;
	}
	
	
}
