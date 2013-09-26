package org.castafiore.ui.ex.form;

import org.castafiore.ui.EXContainer;
import org.castafiore.ui.button.Button;

public class EXButton extends EXContainer implements Button{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXButton(String name, String label) {
		super(name, "button");
		setText(label);
	}
	

}
