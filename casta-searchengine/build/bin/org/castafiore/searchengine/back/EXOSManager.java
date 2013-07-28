package org.castafiore.searchengine.back;

import java.util.Map;

import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXOSManager extends EXXHTMLFragment implements Event, FileFilter, SelectFileHandler{

	public EXOSManager(String name, User user) {
		super(name, "templates/back/EXOSManager.xhtml");
		Merchant merchant = MallUtil.getCurrentMerchant();
		setAttribute("username", user.getUsername());
		addChild(new EXContainer("name", "div")
			.setText(merchant.getCompanyName())
		);
		String url = merchant.getLogoUrl();
		if(url == null || url.trim().length()<= 0){
			url = "http://www.teline-tv.net/images/unknown-user.jpg";
		}
		addChild(new EXContainer("companyLogo", "img").addEvent(this, CLICK).setAttribute("width", "83").setAttribute("height", "83").setAttribute("src", url));
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.setAttribute("src", "http://search.nj.com/media/images/loading.gif");
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String username = getAttribute("username");
		String url ="/root/users/" + username + "/Media";
//		String url = getChild("companyLogo").getAttribute("src");
//		if(url == null || url.trim().length() <= 0 || url.equalsIgnoreCase("http://www.teline-tv.net/images/unknown-user.jpg")){
//			
//		}else{
//			url = url.replace("castafiore/resource?spec=ecm:", "");
//		}
		EXFinder finder = new EXFinder("filder", this, this, url );
		
		getAncestorOfType(PopupContainer.class).addPopup(finder);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean accept(File file) {
		if(file.getClazz().equalsIgnoreCase(Directory.class.getName()) || file instanceof BinaryFile){
			return true;
		}
		return false;
	}


	@Override
	public void onSelectFile(File file, EXFinder finder) {
		//Container img = getDescendentById( getChild("slider").getAttribute("tid"));
		//img.setAttribute("src", ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath())).setAttribute("path",file.getAbsolutePath());
		
		String url = ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath());
		getChild("companyLogo").setAttribute("src", url);
		Merchant merchant = MallUtil.getCurrentMerchant();
		merchant.setLogoUrl(url);
		merchant.save();
		//SpringUtil.getRepositoryService().update(merchant, SpringUtil.getRepositoryService().getSuperUser());
		
	}

}
