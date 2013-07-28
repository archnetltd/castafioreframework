package org.castafiore.google.youtube;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.EventUtil;

import com.google.gdata.data.media.mediarss.MediaThumbnail;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;

public class EXYoutubeListItem extends EXContainer implements YoutubeListItem, Event{
	
	private VideoEntry entry;

	public EXYoutubeListItem(String name) {
		super(name, "li");
		Container a = new EXContainer("a", "a").setAttribute("href", "#").addChild(new EXContainer("img", "img").setAttribute("width", "50").setAttribute("height", "38"));
		addChild(a);
		Container div = new EXContainer("div", "div").addClass("block").addChild(new EXContainer("title", "h2"));
		div.addChild(new EXContainer("date", "small"));
		addChild(div);
		addEvent(this, org.castafiore.ui.events.Event.CLICK);
		setStyle("width", "261px");
		//setAttribute("method", "openThis").setAttribute("ancestor", getClass().getName());
	}
	
	
	
	public void setVideoEntry(VideoEntry entry){
		String title = entry.getTitle().getPlainText();
		getChild("a").setAttribute("title", title);
		YouTubeMediaGroup group = entry.getMediaGroup();
		List<MediaThumbnail> mts =  group.getThumbnails();
		String thumbnail = null;
		if(mts != null && mts.size() > 0){
			thumbnail = mts.get(0).getUrl();
		}
		String date =  new SimpleDateFormat("dd/MM/yyyy").format(new Date(group.getUploaded().getValue()));
		if(title.length() > 25){
			title = title.substring(0, 22) + "...";
		}
		getDescendentByName("title").setText(title);
		getDescendentByName("img").setAttribute("src", thumbnail);
		getDescendentByName("date").setText(date);
		this.entry = entry;
		
	}



	public VideoEntry getCurrentEntry() {
		return entry;
	}
	
	public void openThis(){
		
		String url = "http://www.youtube-nocookie.com/v/"+  getCurrentEntry().getMediaGroup().getVideoId();
		getAncestorOfType(EXYoutubePlayer.class).getDescendentOfType(EXVideoViewer.class).setVideoUrl(url);
	}



	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}



	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		openThis();
		return true;
	}



	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
