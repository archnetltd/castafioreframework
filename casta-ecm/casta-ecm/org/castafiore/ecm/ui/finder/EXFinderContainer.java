package org.castafiore.ecm.ui.finder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

public class EXFinderContainer extends EXContainer implements Event{
	
	private FileFilter selectableFileFilter;

	public EXFinderContainer(String name) {
		super(name, "div");
		addClass("ui-finder").addClass("ui-widget").addClass("ui-widget-header");
		
		Container title = new EXContainer("finderTitle", "div").addClass("ui-finder-title");
		addChild(title);
		Container span = new EXContainer("title", "span");
		title.addChild(span);
		
		Container header = new EXContainer("header", "div").addClass("ui-finder-header");
		addChild(header);
		
		Container wrapper = new EXContainer("wrapper", "div").addClass("ui-finder-wrapper").addClass("ui-widget-content");
		addChild(wrapper);
		
		Container finderContainer = new EXContainer("finderContainer", "div").addClass("ui-finder-container");
		wrapper.addChild(finderContainer);
		
		addButton("Close", "Close", "ui-icon-closethick", true,new FinderCloseEvent());
		addButton("Select", "Select file", "ui-icon-circle-check", false,new FinderFireSelectFileHandler());
		addButton("Delete", "Delete", "ui-icon-trash", false,new FinderDeleteFileEvent());
		addButton("Edit", "Edit", "ui-icon-pencil",false, new FinderEditFileEvent());
		addButton("Upload", "Upload", "ui-icon-arrowreturnthick-1-n", false,new FinderShowUploadFileForm());
		addButton("New", "New text file", "ui-icon-document", false,new FinderNewTextFileEvent());
		addButton("New Folder", "Creates a new folder", "ui-icon-folder", false,new FinderNewFolderEvent());
		
		//addButton("Flicker", "Browser in flickr", "ui-icon-folder", false,this);
		
		//addButton("New Folder", "Creates a new folder", "ui-icon-folder", false,new FinderNewFolderEvent());
		
		getDescendentByName("Delete").setDisplay(false);
		getDescendentByName("Edit").setDisplay(false);
		getDescendentByName("Upload").setDisplay(false);
		getDescendentByName("New").setDisplay(false);
		getDescendentByName("Select").setDisplay(false);
		getDescendentByName("New Folder").setDisplay(false);
		//getDescendentByName("Flicker").getChildByIndex(0).setStyle("background-image", "url('https://www.divitup.com/images/ext/flickr.png')");
		//addChild(new EXContainer("popupContainer", "div").setStyle("position", "absolute"));
		
	}
	
	
	public void setRootDir(String dir){
		setAttribute("rootDir", dir);
		setTitle( dir);
		addDirectory(dir);
		
	}
	
	
	
	
	public FileFilter getSelectableFileFilter() {
		return selectableFileFilter;
	}


	public void setSelectableFileFilter(FileFilter selectableFileFilter) {
		this.selectableFileFilter = selectableFileFilter;
	}


	/**
	 * Sets the title of the finder
	 * @param title
	 */
	public void setTitle(String title){
		getChild("finderTitle").getChild("title").setText(title);
		getChild("finderTitle").setRendered(false);
	}

	public Container getHeader(){
		return getChild("header");
	}
		
	public void addButton(String name,String title, String iconCss, boolean left,Event event){
		Container div = new EXContainer(name, "div");
		if(left){
			div.setStyle("float", "left");
		}
		div.addClass("ui-finder-button ui-state-default").setAttribute("title", title);
		Container span = new EXContainer("", "span").addClass("ui-icon").addClass(iconCss);
		div.addChild(span);
		getHeader().addChild(div);
		if(event != null){
			div.addEvent(event, Event.CLICK);
		}
	}
	
	public void addDirectory(String directory){
		setAttribute("selectedFile", directory);
		getDescendentByName("finderContainer").addChild(new EXFinderColumn("", directory));
	}
	
	public void addNewFile(File file){
		int size = getDescendentByName("finderContainer").getChildren().size();
		EXFinderColumn col = (EXFinderColumn)getDescendentByName("finderContainer").getChildByIndex(size-1);
		col.addFile(file);
	}
	
	
	public void setFlickr()throws IOException{
		Container c =getDescendentByName("finderContainer");
		c.getChildren().clear();
		c.setRendered(false);
		Container imgs = new EXContainer("", "div");
		imgs.addClass("ui-finder-column").addClass("ui-widget-content");
		
		Container finderContent = new EXContainer("finderContent", "div").addClass("ui-finder-content");
		imgs.addChild(finderContent);
		
		Container finder = new EXContainer("finder", "ol");
		finderContent.addChild(finder);
		
		imgs.setStyle("display", "block").setStyle("left", "0").setStyle("right", "0").setStyle("overflow-y", "scroll").setStyle("z-index", "2").setStyle("visibility", "visible");
		
		List<String> ii =getFlickrImages("");
		
		for(String s : ii){
			final Container img = new EXContainer("", "img").setAttribute("src", s).setStyle("padding", "5").setStyle("margin", "5px").setStyle("width", "100px").setStyle("height", "100px");
			finder.addChild(img.addEvent(new Event(){

				@Override
				public void ClientAction(ClientProxy container) {
					container.makeServerRequest(this);
					
				}

				@Override
				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					BinaryFile bf = new BinaryFile();
					bf.setName(img.getAttribute("src"));
					
					container.getAncestorOfType(EXFinder.class).getHandler().onSelectFile(bf, container.getAncestorOfType(EXFinder.class));
					return true;
				}

				@Override
				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
			}, Event.CLICK));
		}
		c.addChild(finderContent);
	}

		private List<String> getFlickrImages(String userid)throws IOException{
			
			List<String> result = new ArrayList<String>();
			String req = "http://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20flickr.people.publicphotos(0%2C5)%20WHERE%20user_id%3D'60594171%40N02'%20AND%20extras%3D'url_sq'";
			
			Source s = new Source(new URL(req));
			
			for(Element e : s.getAllElements("photo"))
			{
				result.add(e.getAttributeValue("url_sq").replace("_s.jpg", ".jpg"));
				//System.out.println(e.getStartTag());
			}
			
			return result;
			
		
		
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try
		{
		setFlickr();
		}catch(Exception e){
			throw new UIException(e);
		}
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}


	

}
