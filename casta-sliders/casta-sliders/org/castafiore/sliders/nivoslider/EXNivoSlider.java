package org.castafiore.sliders.nivoslider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;



public class EXNivoSlider extends EXContainer implements Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DataModel<String> model;
	
	private Map<String, List<EventListener>> listeners = new HashMap<String, List<EventListener>>(); 

	
	private String effect = "random";
	
	
	private int slices = 15;
	
	
	private int animSpeed = 500; // Slide transition speed
	
	
	private int pauseTime = 3000;
	
	
	private int startSlide = 0; // Set starting Slide (0 index)
	
	
	private boolean directionNav = true; // Next & Prev
	
	
	private boolean directionNavHide = true; // Only show on hover
	
	
	private boolean controlNav = true; // 1,2,3...
	
	
	private boolean controlNavThumbs = false; // Use thumbnails for Control Nav
	
	
	private boolean controlNavThumbsFromRel = false; // Use image rel for thumbs
	
	
	private String controlNavThumbsSearch = ".jpg"; // Replace this with...
	
	
	private String controlNavThumbsReplace = "_thumb.jpg"; // ...this in thumb
			
	// Image src
	
	private boolean keyboardNav = true; // Use left & right arrows
	
	
	private boolean pauseOnHover = true; // Stop animation while hovering
	
	
	private boolean manualAdvance = false; // Force manual transitions
	
	
	private double captionOpacity = 0.8; // Universal caption opacity
	
	
	private String beforeChange = null;
	
	
	private String afterChange = null;
	
	
	private String slideshowEnd = null; // Triggers after all slides have been

	
	private String lastSlide = null; // Triggers when last slide is shown
	
	
	private String afterLoad = null; // Triggers when slider has loaded

	
	public EXNivoSlider(String name) {
		super(name, "div");
		addClass("slider");
		addScript(ResourceUtil.getDownloadURL("classpath", "org/castafiore/sliders/nivoslider/EXNivoSlider.js"));
		addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/sliders/nivoslider/EXNivoSlider.css"));
		String[] samples = new String[]{"walle", "toystory", "nemo", "dev7logo"};
		DefaultDataModel<String> model = new DefaultDataModel<String>();
		for(String s : samples)
			model.addItem(ResourceUtil.getDownloadURL("classpath", "org/castafiore/sliders/nivoslider/images/" + s + ".jpg"));
		
		setModel(model);
		
		addEvent(this, READY);
	}
	
	
	
	public void ClientAction(ClientProxy proxy) {
		JMap options = new JMap();
		options.put("effect", effect)
		.put("slices", slices)
		.put("animSpeed", animSpeed)
		.put("pauseTime", pauseTime)
		.put("startSlide", startSlide)
		.put("directionNav", directionNav)
		.put("directionNavHide", directionNavHide)
		.put("controlNav", controlNav)
		.put("controlNavThumbs", controlNavThumbs)
		.put("controlNavThumbsFromRel", controlNavThumbsFromRel)
		.put("controlNavThumbsSearch", controlNavThumbsSearch)
		.put("controlNavThumbsReplace", controlNavThumbsReplace)
		.put("keyboardNav", keyboardNav)
		.put("pauseOnHover", pauseOnHover)
		.put("manualAdvance", manualAdvance)
		.put("captionOpacity", captionOpacity);
		
		if(StringUtil.isNotEmpty(beforeChange)){
			options.put("beforeChange", proxy.clone().makeServerRequest(new JMap().put("trigger","beforeChange" ),this));
		}
		
		if(StringUtil.isNotEmpty(afterChange)){
			options.put("afterChange", proxy.clone().makeServerRequest(new JMap().put("trigger","afterChange" ),this));
		}
		if(StringUtil.isNotEmpty(slideshowEnd)){
			options.put("slideshowEnd", proxy.clone().makeServerRequest(new JMap().put("trigger","slideshowEnd" ),this));
		}
		if(StringUtil.isNotEmpty(lastSlide)){
			options.put("lastSlide", proxy.clone().makeServerRequest(new JMap().put("trigger","lastSlide" ),this));
		}
		
		if(StringUtil.isNotEmpty(afterLoad)){
			options.put("afterLoad", proxy.clone().makeServerRequest(new JMap().put("trigger","afterLoad" ),this));
		}
		
		proxy.addMethod("nivoSlider", options);
		
	}

	
	public void addBeforeChangeListener(EventListener listener){
		addListener(listener, "beforeChange");
	}
	
	public void addAfterChangeListener(EventListener listener){
		addListener(listener, "afterChange");
	}
	public void addSlideshowEndListener(EventListener listener){
		addListener(listener, "slideshowEnd");
	}
	public void addLastSlideListener(EventListener listener){
		addListener(listener, "lastSlide");
	}
	public void addAfterLoadListener(EventListener listener){
		addListener(listener, "afterLoad");
	}
	
	private void addListener(EventListener listener, String type){
		if(listeners.containsKey(type)){
			listeners.get(type).add(listener);
		}else{
			List<EventListener> ll = new java.util.LinkedList<EventListener>();
			ll.add(listener);
			listeners.put(type, ll);
		}
	}
	
	
	private void fireListeners(String type, Container source, Map<String, String> params){
		
		List<EventListener> ll = listeners.get(type);
		if(ll != null){
			for(EventListener l : ll){
				l.execute(source, params);
			}
		}
		
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(request.containsKey("trigger")){
			fireListeners(request.get("trigger"), container, request);
		}
		return true;
	}


	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}
	
	
	
	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public int getSlices() {
		return slices;
	}

	public void setSlices(int slices) {
		this.slices = slices;
	}

	public int getAnimSpeed() {
		return animSpeed;
	}

	public void setAnimSpeed(int animSpeed) {
		this.animSpeed = animSpeed;
	}

	public int getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(int pauseTime) {
		this.pauseTime = pauseTime;
	}

	public int getStartSlide() {
		return startSlide;
	}

	public void setStartSlide(int startSlide) {
		this.startSlide = startSlide;
	}

	public boolean isDirectionNav() {
		return directionNav;
	}

	public void setDirectionNav(boolean directionNav) {
		this.directionNav = directionNav;
	}

	public boolean isDirectionNavHide() {
		return directionNavHide;
	}

	public void setDirectionNavHide(boolean directionNavHide) {
		this.directionNavHide = directionNavHide;
	}

	public boolean isControlNav() {
		return controlNav;
	}

	public void setControlNav(boolean controlNav) {
		this.controlNav = controlNav;
	}

	public boolean isControlNavThumbs() {
		return controlNavThumbs;
	}

	public void setControlNavThumbs(boolean controlNavThumbs) {
		this.controlNavThumbs = controlNavThumbs;
	}

	public boolean isControlNavThumbsFromRel() {
		return controlNavThumbsFromRel;
	}

	public void setControlNavThumbsFromRel(boolean controlNavThumbsFromRel) {
		this.controlNavThumbsFromRel = controlNavThumbsFromRel;
	}

	public String getControlNavThumbsSearch() {
		return controlNavThumbsSearch;
	}

	public void setControlNavThumbsSearch(String controlNavThumbsSearch) {
		this.controlNavThumbsSearch = controlNavThumbsSearch;
	}

	public String getControlNavThumbsReplace() {
		return controlNavThumbsReplace;
	}

	public void setControlNavThumbsReplace(String controlNavThumbsReplace) {
		this.controlNavThumbsReplace = controlNavThumbsReplace;
	}

	public boolean isKeyboardNav() {
		return keyboardNav;
	}

	public void setKeyboardNav(boolean keyboardNav) {
		this.keyboardNav = keyboardNav;
	}

	public boolean isPauseOnHover() {
		return pauseOnHover;
	}

	public void setPauseOnHover(boolean pauseOnHover) {
		this.pauseOnHover = pauseOnHover;
	}

	public boolean isManualAdvance() {
		return manualAdvance;
	}

	public void setManualAdvance(boolean manualAdvance) {
		this.manualAdvance = manualAdvance;
	}

	public double getCaptionOpacity() {
		return captionOpacity;
	}

	public void setCaptionOpacity(double captionOpacity) {
		this.captionOpacity = captionOpacity;
	}

	public String getBeforeChange() {
		return beforeChange;
	}

	public void setBeforeChange(String beforeChange) {
		this.beforeChange = beforeChange;
	}

	public String getAfterChange() {
		return afterChange;
	}

	public void setAfterChange(String afterChange) {
		this.afterChange = afterChange;
	}

	public String getSlideshowEnd() {
		return slideshowEnd;
	}

	public void setSlideshowEnd(String slideshowEnd) {
		this.slideshowEnd = slideshowEnd;
	}

	public String getLastSlide() {
		return lastSlide;
	}

	public void setLastSlide(String lastSlide) {
		this.lastSlide = lastSlide;
	}

	public String getAfterLoad() {
		return afterLoad;
	}

	public void setAfterLoad(String afterLoad) {
		this.afterLoad = afterLoad;
	}



	public DataModel<String> getModel() {
		return model;
	}

	public void setModel(DataModel<String> model) {
		this.model = model;
		init();
	}

	public void init() {
		getChildren().clear();
		setRendered(false);
		if (model != null) {
			int size = model.getSize();
			for (int i = 0; i < size; i++) {
				addChild(new EXContainer("", "img").setAttribute("src", model
						.getValue(i).toString()));
			}
		}
	}


	
	
	

}
