package org.castafiore.shoppingmall.user.ui.settings;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXCompanySetting extends EXXHTMLFragment implements EventDispatcher, FileFilter, SelectFileHandler{
	
	public EXCompanySetting(){
		super("EXCompanySetting", "templates/EXCompanySetting.xhtml");
		addChild(new EXInput("companyCode"))
		.addChild(new EXInput("companyName"))
		.addChild(new EXLabel("username", ""))
		.addChild(new EXPassword("password").setAttribute("validation-method", "empty").setAttribute("error-message", "Please a valid password"))
		.addChild(new EXInput("businessRegistrationNumber"))
		.addChild(new EXInput("vatRegistrationNumber"))
		.addChild(new EXRichTextArea("summary").setStyle("width", "700px").setStyle("height", "350px"))
		.addChild(new EXInput("businessAddressLine1"))
		.addChild(new EXInput("businessAddressLine2"))
		.addChild(new EXInput("businessAddressLine3"))
		
		
		.addChild(new EXInput("companyPhone"))
		.addChild(new EXInput("companyMobilePhone"))
		.addChild(new EXInput("natureOfBusiness"))
		
		
		.addChild(new EXInput("companyFax"))
		.addChild(new EXInput("companyEmail"))
		.addChild(new EXInput("website"))
		.addChild(new EXContainer("banner", "img").setStyle("border", "double").setAttribute("width", "700").setAttribute("src", "youdo/images/banners/banner1.jpg").addEvent(DISPATCHER, Event.CLICK))
		.addChild(new EXContainer("logo", "img").setStyle("width", "200px").setStyle("border", "double").setAttribute("src", "http://www.teline-tv.net/images/unknown-user.jpg").addEvent(DISPATCHER, Event.CLICK));
//		try{
//			addChild(new  EXSelect("businessCountry", new DefaultDataModel<Object>((List)FinanceUtil.getCountries())));
//			getDescendentOfType(EXSelect.class).setValue(FinanceUtil.getClientCountry());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
//		try{
//			addChild(new  EXSelect("businessCurrency", new DefaultDataModel<Object>((List)FinanceUtil.getCurrencies())));
//			//getDescendentOfType(EXSelect.class).setValue(FinanceUtil.getClientCountry());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
		
//		Map<String, String> llls = new HashMap<String, String>(); 
//		
//		DefaultDataModel<Object> model = new DefaultDataModel<Object>();
//		for(Locale l : Locale.getAvailableLocales()){
//			//
//			if(!llls.containsKey(l.getLanguage())){
//				llls.put(l.getLanguage(), l.getDisplayLanguage());
//				model.addItem(new SimpleKeyValuePair(l.getLanguage(),l.getDisplayLanguage()));
//			}
//		}
//		EXSelect select = new EXSelect("defaultLanguage", model);
//		addChild(select);
		
	}

	@Override
	public void executeAction(Container source) {
		if(source.getName().equalsIgnoreCase("banner")){
			EXFinder finder = new EXFinder("BannerFinder", this, this, "/root/users/" + MallUtil.getCurrentUser().getUser().getUsername() + "/Media");
			getAncestorOfType(PopupContainer.class).addPopup(finder);
		}else{
			EXFinder finder = new EXFinder("LogoFinder", this, this, "/root/users/" + MallUtil.getCurrentUser().getUser().getUsername() + "/Media");
			getAncestorOfType(PopupContainer.class).addPopup(finder);
		}
		
	}

	@Override
	public boolean accept(File pathname) {
		if(pathname.getClazz().equals(Directory.class.getName()) || pathname instanceof BinaryFile){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void onSelectFile(File file, EXFinder finder) {
		if(file instanceof BinaryFile){
			if(finder.getName().equals("BannerFinder")){
				getChild("banner").setAttribute("src", ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath()));
			}else{
				getChild("logo").setAttribute("src", ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath()));
			}
			
			finder.remove();
		}
		
	}

}
