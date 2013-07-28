package org.castafiore.splashy;

import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.designer.designable.ConfigValue;
import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.ui.Container;

public class EXJustSliderDesignableFactory extends AbstractDesignableFactory{

	public EXJustSliderDesignableFactory() {
		super("Just Slider");
	}

	@Override
	public String getCategory() {
		return "Ecommerce";
	}

	@Override
	public Container getInstance() {
		return new EXJustSlider("Slider");
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		if(attributeName.equalsIgnoreCase("products")){
			((EXJustSlider)c).setProducts(attributeValue);
		}
		
	}

	
	@ConfigValues(configs={
			@ConfigValue(attribute="pauseOnHover",values={"true", "false"}, default_="false"),
			@ConfigValue(attribute="pagination",values={"true", "false"},default_="true"),
			@ConfigValue(attribute="pagNums",values={"true", "false"},default_="false"),
			@ConfigValue(attribute="numStatus",values={"true", "false"},default_="true"),
			@ConfigValue(attribute="waitBannerAnimation",values={"true", "false"},default_="false"),
			@ConfigValue(attribute="showDetail",values={"true", "false"},default_="true"),
			
			@ConfigValue(attribute="show",values={}, default_="0"),
			@ConfigValue(attribute="prevBu",values={}, default_=".pro_prev"),
			@ConfigValue(attribute="nextBu",values={}, default_=".pro_next"),
			@ConfigValue(attribute="playBu",values={}, default_=".pro_play"),
			@ConfigValue(attribute="items",values={}, default_=".pro_items>li"),
			@ConfigValue(attribute="duration",values={}, default_="1000"),
			@ConfigValue(attribute="preset",values={"centralExpand","zoomer","fadeThree","simpleFade","gSlider","vSlider","slideFromLeft","slideFromTop","diagonalFade","diagonalExpand","fadeFromCenter","lines","verticalLines","horizontalLines","random"}, default_="simpleFade"),
			@ConfigValue(attribute="bannerCl",values={}, default_=".pro_banner"),
			@ConfigValue(attribute="numStatusCl",values={}, default_=".pro_numStatus"),
			@ConfigValue(attribute="pauseCl",values={}, default_=".pro_paused"),
			@ConfigValue(attribute="paginationCl",values={}, default_=".pro_pagination"),
			@ConfigValue(attribute="slideshow",values={}, default_="7000"),
			@ConfigValue(attribute="banners",values={}, default_="fade")
			
			})
	@Override
	public String[] getRequiredAttributes() {
		return new String[]{
				"slideshow",
				"duration",
				"show",
				"pauseOnHover",
				"numStatus",
				"waitBannerAnimation",
				"showDetail",
				"pagNums",
				"pagination",
				
				"prevBu",
				"nextBu",
				"playBu",
				"items",
				"bannerCl",
				"numStatusCl",
				"pauseCl",
				"paginationCl",
				"preset",
				"banners"
				

		};
	}

	@Override
	public String getUniqueId() {
		return "ecommerce:slider";
	}

	@Override
	public Map<String, ConfigForm> getAdvancedConfigs() {
		Map<String, ConfigForm> forms = new ListOrderedMap();
		forms.put("Products", new EXJustSliderConfig("config"));
		return forms;
	}
}
