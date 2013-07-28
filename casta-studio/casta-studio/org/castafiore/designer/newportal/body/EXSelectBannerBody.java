package org.castafiore.designer.newportal.body;

import java.net.URL;
import java.util.Map;

import org.castafiore.designer.newportal.EXNewPortal;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;

public class EXSelectBannerBody extends AbstractWizardBody implements Event {

	private final static String[] LAYOUTS= {"banner1", "banner9", "banner2", "banner3", "banner4", "banner6"};
	public EXSelectBannerBody(String name) {
		super(name);
		addClass("EXSelectBannerBody");
		try{
			PicasawebService myService = new PicasawebService("exampleCo-exampleApp-1");
			myService.setUserCredentials("kureem@gmail.com", "18072008hummykur");
			
			URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/kureem?kind=album");

			UserFeed myUserFeed = myService.getFeed(feedUrl, UserFeed.class);

			for (AlbumEntry myAlbum : myUserFeed.getAlbumEntries()) {
				URL f =new URL( "https://picasaweb.google.com/data/feed/api/user/kureem/albumid/" + myAlbum.getGphotoId());

				AlbumFeed feed = myService.getFeed(f, AlbumFeed.class);

				for(PhotoEntry photo : feed.getPhotoEntries()) {
				    addChild(new EXContainer("", "img").setAttribute("src", ((MediaContent)photo.getContent()).getUri()).addEvent(this, CLICK));

				}
			}
			for(String s : LAYOUTS){
				
			}
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	@Override
	public AbstractWizardBody clickButton(Container button) {
		if(button.getName().equals("next")){
			String selected =getSelected();
			if(selected != null){
				getAncestorOfType(EXNewPortal.class).getNewPortal().setBanner(selected);
				return new EXSelectMenuBody("EXSelectBannerBody");
			}
		}else if(button.getName().equals("back")){
			return new EXNewPortalBody("EXNewPortalBody");
		}
		return null;
	}
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}
	
	public String getSelected(){
		for(Container c : getChildren()){
			if("true".equals(c.getAttribute("selected"))){
				return c.getAttribute("src");
			}
		}
		return null;
	}
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		for(Container c : container.getParent().getChildren()){
			c.setStyle("border", "none");
			c.setAttribute("selected", "false");
		}
		container.setStyle("border", "dotted 5px red");
		container.setAttribute("selected", "true");
		
		return true;
	}
	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
