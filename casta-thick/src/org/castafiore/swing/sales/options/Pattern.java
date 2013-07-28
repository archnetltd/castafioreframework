package org.castafiore.swing.sales.options;

import org.castafiore.ui.js.Var;

public class Pattern {
	
	private Var image;
	
	private String repeat;
	
	private Context2D context;
	
	

	public Pattern(Context2D context, Var image, String repeat) {
		super();
		this.image = image;
		this.repeat = repeat;
		this.context = context;
	}

	public Var getImage() {
		return image;
	}

	public void setImage(Var image) {
		this.image = image;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

}
