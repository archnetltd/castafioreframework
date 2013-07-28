package org.castafiore.swing.sales.options;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.castafiore.ui.js.Var;

public class Context2D {
	
	
	private List<String> commands = new LinkedList<String>();
	public Context2D fillStyle(Color color, Gradient gradient, Pattern pattern){
		
		return setAttribute("fillStyle", color);
	}
	
	
	
	
	private Context2D setAttribute(String name, Color color){
		commands.add(""+name+"=\""+colorToHex(color)+" \"");
		return this;
	}
	
	
	
	private String colorToHex(Color c){
		return Integer.toHexString(c.getRGB());
	}
	
	public Pattern createPattern(Var image, String repeat){
		return new Pattern(this, image, repeat);
	}

}
