package org.castafiore.google.youtube;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;

import sample.youtube.YouTubeReadonlyClient;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

public class EXYoutubePlayList extends EXContainer implements Event{

	public EXYoutubePlayList(String name) {
		super(name, "div");
		addClass("image_thumb");
		addChild(new EXContainer("search", "ul"));

		EXContainer li = new EXContainer("", "li");
		getChild("search").addChild(li);
		li.addChild(new EXContainer("h2", "h2").setText("Search :"));
		li.addChild(new EXInput("doSearch").addEvent(this, Event.BLUR));

		addChild(new EXContainer("ul", "ul"));
	}

	public void setFeed(VideoFeed feed) {

		getChild("ul").getChildren().clear();
		getChild("ul").setRendered(false);
		List<VideoEntry> entries = feed.getEntries();
		if (entries != null) {
			for (VideoEntry entry : entries) {
				EXYoutubeListItem item = new EXYoutubeListItem("");
				item.setVideoEntry(entry);
				getChild("ul").addChild(item);
			}
		}
	}

	private void searchFeed() throws IOException,
			ServiceException {
		String searchTerms = ((StatefullComponent)getDescendentByName("doSearch")).getValue().toString();
		YouTubeService service = new YouTubeService("gdataSample-YouTube-1");
		YouTubeQuery query = new YouTubeQuery(new URL(
				YouTubeReadonlyClient.VIDEOS_FEED));
		query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
		query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);
		query.setFullTextQuery(searchTerms);
		VideoFeed videoFeed = service.query(query, VideoFeed.class);
		setFeed(videoFeed);

	}

	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			searchFeed();
		}catch(Exception e){
			throw new UIException(e);
		}
		
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
