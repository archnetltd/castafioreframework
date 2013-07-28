package org.castafiore.google.youtube;

import org.castafiore.ui.Container;

import com.google.gdata.data.youtube.VideoEntry;

public interface YoutubeListItem extends Container{
	
	public VideoEntry getCurrentEntry();

}
