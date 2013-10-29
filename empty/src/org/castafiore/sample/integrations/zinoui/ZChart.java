package org.castafiore.sample.integrations.zinoui;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;

public class ZChart extends EXContainer{
	
	private JMap options = new JMap();
	public ZChart(String name) {
		super(name, "div");
		setWidth(530).setHeight(320);
	}
	
	
	public void setType(String type) {
		options.put("type", type);
	}
	public void setLegend(boolean legend) {
		options.put("legend", legend);
	}
	public void setTooltip(boolean tooltip) {
		options.put("tooltip", tooltip);
	}
	public void setSeries(Object[][] series) {
		JArray ser = new JArray();
		for(Object s[] : series){
			JArray j = new JArray();
			for(Object ss : s){
				if(ss instanceof Number)
					j.add((Number)ss);
				else
					j.add(ss.toString());
			}
			ser.add(j);
		}
		options.put("series", ser);
	}
	public ZChart setWidth(Integer width){
		options.put("width", width);
		super.setStyle("width", width + "px");
		return this;
	}
	
	public ZChart setHeight(Integer height){
		options.put("height", height);
		super.setStyle("height", height + "px");
		return this;
	}
	
	public Container setHeight(Dimension h){
		return setHeight(h.getAmount());
		
	}
	
	public Container setWidth(Dimension w){
		return setWidth(w.getAmount());
	}
	
	public Container setStyle(String key, String value){
		if(key.equalsIgnoreCase("width")){
			this.setWidth(Dimension.parse(value));
		}else if(key.equalsIgnoreCase("height")){
			this.setHeight(Dimension.parse(value));
		}else{
			super.setStyle(key, value);
		}
		return this;
	}
	
	public void setFontFamily(String fontFamily) {
		options.put("fontFamily", fontFamily);
	}
	public void setFontSize(String fontSize) {
		options.put("fontSize", fontSize);
	}
	public void setRadius(Integer radius) {
		options.put("radius", radius);
	}
	public void setInnerRadius(Integer innerRadius) {
		options.put("innerRadius", innerRadius);
	}
	public void setLineRowColor(String lineRowColor) {
		options.put("lineRowColor", lineRowColor);
	}
	public void setTextColor(String textColor) {
		options.put("textColor", textColor);
	}
	
	
	public void onReady(ClientProxy proxy){
		proxy.getCSS("http://zinoui.com/1.3/themes/silver/zino.core.css").getCSS("http://zinoui.com/1.3/themes/silver/zino.chart.css");
		proxy.getScript("http://zinoui.com/1.3/compiled/zino.svg.min.js",proxy.clone().getScript("http://zinoui.com/1.3/compiled/zino.chart.min.js", proxy.clone().addMethod("zinoChart",options)));
	}
}
