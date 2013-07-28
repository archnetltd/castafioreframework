package org.castafiore.splashy;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;

public class EXJustSlider extends EXXHTMLFragment{

	public EXJustSlider(String name) {
		super(name, "templates/splashy/EXJustSlider.xhtml");
	}
	
	public void setProducts(String val){
		setAttribute("products", val);
		this.getChildren().clear();
		setRendered(false);
		String[] paths = StringUtil.split(val, ";");
		QueryParameters params = new QueryParameters().setEntity(Product.class);
		for(String path : paths){
			params.addSearchDir(path);
		}
		params.addSearchDir("/oo");
		List<Product> products = (List)SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		Container items = new EXContainer("products", "ul").addClass("pro_items");
		addChild(items);
		boolean detail = true;
		if(StringUtil.isNotEmpty(getAttribute("showDetail"))){
			detail = Boolean.parseBoolean(getAttribute("showDetail"));
		}
		for(Product p : products){
			items.addChild(new EXJustSliderItem("", p, detail));
		}
	}
	
	
	public void onReady(ClientProxy p){
		super.onReady(p);
		
		Object[] defs = new Object[]{
				0,
				false,
				".pro_prev",
				".pro_next",
				".pro_play",
				".pro_items>li",
				1000,
				"simpleFade",
				".pro_banner",
				".pro_numStatus",
				".pro_paused",
				true,
				".pro_pagination",
				false,
				7000,
				true,
				"fade",
				false
		};
		
		String[] vars = new String[]{
				"show",
				"pauseOnHover",
				"prevBu",
				"nextBu",
				"playBu",
				"items",
				"duration",
				"preset",
				"bannerCl",
				"numStatusCl",
				"pauseCl",
				"pagination",
				"paginationCl",
				"pagNums",
				"slideshow",
				"numStatus",
				"banners",
				"waitBannerAnimation"
		};
		
		JMap options = new JMap();
		
	
		for(int i =0; i < defs.length; i++){
			Object def = defs[i];
			String var = vars[i];
			if(StringUtil.isNotEmpty(getAttribute(var))){
				if(def instanceof Boolean){
					try{
						def = Boolean.parseBoolean(getAttribute(var));
					}catch(Exception e){
						
					}
				}else if( def instanceof Number){
					def = Integer.parseInt(getAttribute(var));
				}else
					def = getAttribute(var);
			}
			
			if(def instanceof String)
				options.put(new Var(var), def.toString());
			else if(def instanceof Boolean){
				options.put(new Var(var), (Boolean)def);
			}else
				options.put(new Var(var), (Number)def);
		}
		

		options.put(new Var("progressBar"),"<div class=pro_progbar></div>");
		
		ClientProxy clone = new ClientProxy(p.getIdRef()+ " #pro_slider").addMethod("_TMS", options);
		
		p.getScript("http://livedemo00.template-help.com/wt_40020/js/tms-0.4.1.js", clone);
	}
	
	public class EXJustSliderItem extends EXContainer{

		public EXJustSliderItem(String name, Product p, boolean showdetail) {
			super(name, "li");
			Container img = new EXContainer("img", "img").setAttribute("src", p.getImageUrl("")).setStyle("width", "900px").setStyle("height", "500px");
			addChild(img);
			
			if(showdetail){
			Container pro = new EXContainer("", "div").addClass("pro_banner");
			addChild(pro);
			Container title = new EXContainer("title", "span").setText(p.getTitle());
			pro.addChild(title);
			Container summary = new EXContainer("summary", "p").setText(p.getSummary());
			pro.addChild(summary);
			Container addtocart = new EXContainer("addToCart", "a").addClass("pro_btn").setAttribute("href", "#cart").setText("Add To Cart");
			pro.addChild(addtocart);
			}
		}
		
	}

}
