package org.castafiore.site.wizard;

import java.net.URL;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;

public class EXSelectBanner extends EXXHTMLFragment implements Event{

	public EXSelectBanner(String name) {
		super(name, "webos/gs/Banners.xhtml");
		addChild(new EXContainer("banners", "div").setStyle("width", "575px"));
		
		try{
			PicasawebService myService = new PicasawebService("exampleCo-exampleApp-1");
			myService.setUserCredentials("kureem@gmail.com", "18072008hummykur");
			
			URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/kureem?kind=album");

			UserFeed myUserFeed = myService.getFeed(feedUrl, UserFeed.class);

			for (AlbumEntry myAlbum : myUserFeed.getAlbumEntries()) {
				URL f =new URL( "https://picasaweb.google.com/data/feed/api/user/kureem/albumid/" + myAlbum.getGphotoId());

				AlbumFeed feed = myService.getFeed(f, AlbumFeed.class);

				for(PhotoEntry photo : feed.getPhotoEntries()) {
				   getChild("banners").addChild(new EXContainer("img", "img").setAttribute("src", ((MediaContent)photo.getContent()).getUri()).addEvent(this, CLICK));

				}
			}
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	
	public String getUrl(){
		return getChild("banners").getAttribute("selVal");
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
			getChild("banners").setAttribute("selVal", container.getAttribute("src"));
			for(Container c : getChild("banners").getChildren()){
				c.setStyle("border", "solid 1px silver");
			}
			
			container.setStyle("border", "double 3px red");
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
