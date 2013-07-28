package org.castafiore.google.youtube;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ResourceUtil;

import com.google.gdata.data.youtube.VideoFeed;

public class EXYoutubePlayer extends EXContainer{

	public EXYoutubePlayer(String name) {
		super(name, "div");
		addClass("main").addClass("container");
		addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/google/youtube/EXYoutubePlayer.css"));
		addChild(new EXVideoViewer("viewer"));
		addChild(new EXYoutubePlayList("playlist"));
	}
	
	
	public void setVideoFeed(VideoFeed feed){
		getDescendentOfType(EXYoutubePlayList.class).setFeed(feed);
	}

}
