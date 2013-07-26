package org.racingtips.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.racingtips.mtc.MTCDTO;

import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

public class EXSlider extends EXXHTMLFragment implements Event{

	List<VideoEntry> ents = new ArrayList<VideoEntry>();
	public EXSlider(String name)throws Exception {
		super(name, "templates/racingtips/EXSlider.xhtml");
		createSlider();
		
		
	}
	
	private void createSlider()throws Exception{
//		EXVideoViewer viewer = new EXVideoViewer("viewer");
//		addChild(viewer);
//		addChild(new EXContainer("pagination", "ul").addClass("pagination"));
//		VideoFeed feed = searchFeed();
//		List<VideoEntry> entries = feed.getEntries();
//		int index = 0;
//		for(VideoEntry entry : entries){
//			if(index < 4){
//				EXXHTMLFragment fragment = new EXXHTMLFragment(index + "", "templates/racingtips/EXSliderTabItem.xhtml");
//				String title = entry.getTitle().getPlainText();
//				
//				String thumbnail = null;
//				YouTubeMediaGroup group = entry.getMediaGroup();
//				String date =  new SimpleDateFormat("MMM dd, yyyy").format(new Date(group.getUploaded().getValue()));
//				List<MediaThumbnail> mts =  group.getThumbnails();
//				//<strong>JUL 18, 2010</strong><br></br> my nibh euismod tincidunt ut laoreet dolore
//				String text = "<strong>"+date+"</strong><br> " + title;
//				
//				Container exTitle = new EXContainer("title", "span").setText(text);
//				if(mts != null && mts.size() > 0){
//					thumbnail = mts.get(0).getUrl();
//				}
//				fragment.addChild(new EXContainer("thumbnail", "img").setAttribute("src", thumbnail).setStyle("width", "163px").setStyle("height", "109px"));
//				fragment.addChild(exTitle);
//				fragment.addEvent(this, Event.CLICK).setAttribute("imgIndex", index + "");
//				getChild("pagination").addChild(fragment);
//				ents.add(entry);
//				
//			}else{
//				break;
//			}
//			index++;
//			
//		}
//		openThis(getChild("pagination").getChildByIndex(0));
	} 
	
	private  VideoFeed searchFeed() throws IOException,
	ServiceException {
		return SpringUtil.getBeanOfType(MTCDTO.class).searchFeed();
	
	}
	
	public void openThis(Container source){
//		int index = Integer.parseInt(source.getAttribute("imgIndex"));
//		String url = "http://www.youtube-nocookie.com/v/"+  ents.get(index).getMediaGroup().getVideoId();
//		getDescendentOfType(EXVideoViewer.class).setVideoUrl(url);
//		source.setStyleClass("active");
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		Container pagination = container.getParent();
		for(Container c : pagination.getChildren()){
			if(c.getName().equals(container.getName())){
				c.setStyleClass("active");
			}else{
				c.setStyleClass(" ");
			}
		}
		openThis(container);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
