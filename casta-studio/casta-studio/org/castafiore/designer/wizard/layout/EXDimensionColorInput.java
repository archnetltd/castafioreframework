/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.designer.wizard.layout;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.input.Decoder;
import org.castafiore.ui.input.Encoder;
import org.castafiore.utils.ComponentUtil;

public class EXDimensionColorInput extends EXContainer implements StatefullComponent {
	
	
	

	private EXDimensionColorInput(String name, String sdimension, String sunit, String scolor, String sdimensionLabel, String scolorLabel) {
		super(name, "div");
		setStyle("padding", "5px 5px");
		Container dimensionLabel = ComponentUtil.getContainer("dimensionLabel", "label", sdimensionLabel, null);
		dimensionLabel.setStyle("padding-right", "65px");
		addChild(dimensionLabel);
		
		EXInput dimension = new EXInput("dimension", sdimension);
		dimension.setAttribute("size", "4");
		addChild(dimension);
		
		Container unit = ComponentUtil.getContainer("unit", "span", sunit, null);
		addChild(unit);
		
		
		addChild(new EXContainer("", "br"));
		
		Container colorLabel = ComponentUtil.getContainer("colorLabel", "label",scolorLabel	, null);
		addChild(colorLabel);
		
		EXColorPicker color = new EXColorPicker("color", scolor);
		color.setAttribute("size", "8");
		addChild(color);
		
		
		setDisplay(false);
	}
	
	public EXDimensionColorInput(String name,DimensionColor ds,String unit, String dimensionLabel, String colorLabel){
		this(name,ds.dimension, unit,ds.color, dimensionLabel, colorLabel);
	}
	public EXDimensionColorInput(String name,DimensionColor ds,String unit, String dimensionLabel){
		this(name,ds.dimension, unit,ds.color, dimensionLabel, "Background color :");
	}
	public EXDimensionColorInput(String name,DimensionColor ds,String unit){
		this(name,ds.dimension, unit,ds.color, "Height :", "Background color :");
	}

	public Decoder getDecoder() {
		
		return null;
	}

	public Encoder getEncoder() {
		
		return null;
	}

	public String getRawValue() {
		
		return null;
	}

	public Object getValue() {
		
		DimensionColor ds = new DimensionColor(
				((StatefullComponent)getChild("dimension")).getValue().toString(),
				((StatefullComponent)getChild("color")).getValue().toString(),
				this.getChild("unit").getText()
			);
		
		  
		return ds;
	}

	public void setDecoder(Decoder decoder) {
	
		
	}

	public void setEncoder(Encoder encoder) {
		
		
	}

	public void setRawValue(String rawValue) {
		
		
	}

	public void setValue(Object value) {
		if(value instanceof DimensionColor){
			((StatefullComponent)getChild("dimension")).setValue( ((DimensionColor)value).dimension );
			((StatefullComponent)getChild("color")).setValue( ((DimensionColor)value).color );
		}
		
	}
	
	public static class DimensionColor{
		
		
		
		public DimensionColor(String dimension, String color, String unit) {
			super();
			this.dimension = dimension;
			this.color = color;
			this.unit = unit;
		}

		
		private String unit;
		private String dimension;
		
		private String color;

		public String getDimension() {
			return dimension;
		}

		public void setDimension(String dimension) {
			this.dimension = dimension;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
		
		public Dimension getDimensionObject(){
			return new Dimension(unit,Integer.parseInt(dimension));
		}
		
	}

}
