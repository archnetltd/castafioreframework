package org.castafiore.google.youtube;

import java.io.IOException;
import java.net.URL;

import org.castafiore.google.docs.ui.SimpleGoogleDocViewer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXApplication;

import sample.youtube.YouTubeReadonlyClient;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

public class TestYoutubeApp extends EXApplication {

	public TestYoutubeApp() {
		super("yt");
		try{
//			EXYoutubePlayer player = new EXYoutubePlayer("player");
//			VideoFeed feed = searchFeed("Castafiore");
//			player.setVideoFeed(feed);
//			addChild(player);
			
			addChild(new SimpleGoogleDocViewer(""));
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	private  VideoFeed searchFeed(String searchTerms) throws IOException,
			ServiceException {
		YouTubeService service = new YouTubeService("gdataSample-YouTube-1");
		YouTubeQuery query = new YouTubeQuery(new URL(YouTubeReadonlyClient.VIDEOS_FEED));
		query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
		query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);
		query.setFullTextQuery(searchTerms);
		VideoFeed videoFeed = service.query(query, VideoFeed.class);
		return videoFeed;
		
	}

}
