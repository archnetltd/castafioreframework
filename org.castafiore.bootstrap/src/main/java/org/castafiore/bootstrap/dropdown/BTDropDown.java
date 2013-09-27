package org.castafiore.bootstrap.dropdown;

import org.castafiore.bootstrap.AlignmentType;
import org.castafiore.bootstrap.buttons.BTButton;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.toolbar.ToolBarItem;

public class BTDropDown extends EXContainer implements ToolBarItem {

	private Container dropDownMenu;

	public BTDropDown(String name) {
		super(name, "div");
		addClass("dropdown");

		dropDownMenu = new EXContainer("dp", "ul").addClass("dropdown-menu");
		addChild(dropDownMenu);
	}

	public BTButton setRootButton(String label, boolean split) {
		addClass("btn-group");
		Container caret = new EXContainer("caret", "span").addClass("caret");
		BTButton btn = new BTButton("root", label);
		btn.addClass("btn-default");
		if (!split) {
			btn.addClass("dropdown-toggle");
			addChildAt(btn, 0);
			btn.addChild(caret);
			btn.setAttribute("data-toggle", "dropdown");
		} else {
			addChildAt(btn, 0);
			BTButton btSplit = new BTButton("split", "");
			btSplit.addClass("btn-default").addClass("dropdown-toggle")
					.setAttribute("data-toggle", "dropdown")
					.setText("<span class=\"caret\"></span>");
			addChildAt(btSplit, 1);

		}
		setRendered(false);
		return btn;
	}

	public BTDropDown setAlignment(AlignmentType alignment) {

		if (alignment == AlignmentType.LEFT) {
			dropDownMenu.removeClass("pull-right");
		} else if (alignment == AlignmentType.RIGHT) {
			dropDownMenu.addClass("pull-right");
		}
		
		return this;

	}

	public BTDropDownItem addItem(String name, String label) {
		BTDropDownItem item = new BTDropDownItem(name);
		item.setLabel(label);
		dropDownMenu.addChild(new EXContainer("", "li").addChild(item));
		return item;
	}

	public void addDivider() {
		dropDownMenu.addChild((new EXContainer("", "li").addClass("divider")));
	}

	public class BTDropDownItem extends EXContainer {

		private BTDropDownItem(String name) {
			super(name, "a");
			setAttribute("href", "#").setAttribute("tabindex", "-1");

		}

		public BTDropDownItem setHref(String url) {
			setAttribute("href", url);
			return this;
		}

		public BTDropDownItem setLabel(String label) {
			setText(label);
			return this;
		}

		public BTDropDownItem setDisabled(boolean disabled) {
			if (disabled) {
				getParent().addClass("disabled");
			} else {
				getParent().removeClass("disabled");
			}
			return this;

		}

		public BTDropDownItem setAsHeader(boolean header) {
			if (header) {
				getParent().addClass("dropdown-header");
			} else {
				getParent().removeClass("dropdown-header");
			}
			return this;
		}

	}

}
