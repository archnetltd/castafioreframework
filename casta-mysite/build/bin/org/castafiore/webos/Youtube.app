import sample.youtube.YouTubeReadonlyClient;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;
import org.castafiore.google.youtube.EXYoutubePlayer;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;

try{
	YouTubeService service = new YouTubeService("gdataSample-YouTube-1");
	YouTubeQuery query = new YouTubeQuery(new URL(YouTubeReadonlyClient.VIDEOS_FEED));
	query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
	query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);
	query.setFullTextQuery("Castafiore");
	VideoFeed videoFeed = service.query(query, VideoFeed.class);
	EXYoutubePlayer player = new EXYoutubePlayer("player");
	player.setVideoFeed(videoFeed);
	root.addChild(player);
}catch(Exception e){
	throw new UIException(e);
}