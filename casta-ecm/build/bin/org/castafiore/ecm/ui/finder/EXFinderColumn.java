package org.castafiore.ecm.ui.finder;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Drive;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;

public class EXFinderColumn extends EXContainer {
	


	public EXFinderColumn(String name,String directory) {
		super(name, "div");
		addClass("ui-finder-column").addClass("ui-widget-content");
		
		Container finderContent = new EXContainer("finderContent", "div").addClass("ui-finder-content");
		addChild(finderContent);
		
		Container finder = new EXContainer("finder", "ol");
		finderContent.addChild(finder);
		setAttribute("directory", directory);
		setStyle("display", "block").setStyle("left", "0").setStyle("right", "0").setStyle("overflow-y", "scroll").setStyle("z-index", "2").setStyle("visibility", "visible");
		
		doInit();
	}
	public void setDirectory(String directory){
		setAttribute("directory", directory);
		doInit();
	}
	
	public String getDirectory(){
		return  getAttribute("directory");
	}
	
	public void doInit(){
		getChild("finderContent").getChild("finder").getChildren().clear();
		getChild("finderContent").getChild("finder").setRendered(false);
		String directory = getDirectory();
		File f =SpringUtil.getRepositoryService().getFile(directory, Util.getRemoteUser());
		
		if( f instanceof Directory){
			Directory dir = (Directory)f;//SpringUtil.getRepositoryService().getDirectory(directory, Util.getRemoteUser());
			FileIterator iter = dir.getFiles();
			if(dir instanceof BinaryFile){
				BinaryFile bf = (BinaryFile)dir;
				if(bf.getMimeType() != null && bf.getMimeType().contains("image")){
					addImage(bf);
				}
			}
			while(iter.hasNext()){
				addFile(iter.next());
			}
		}else if(f instanceof Shortcut){
			File ref = ((Shortcut)f).getReferencedFile();
			if(ref instanceof Directory){
				Directory dir = (Directory)ref;//SpringUtil.getRepositoryService().getDirectory(directory, Util.getRemoteUser());
				FileIterator iter = dir.getFiles();
				if(dir instanceof BinaryFile){
					BinaryFile bf = (BinaryFile)dir;
					if(bf.getMimeType() != null && bf.getMimeType().contains("image")){
						addImage(bf);
					}
				}
				while(iter.hasNext()){
					addFile(iter.next());
				}
			}
		}
		
		
	}
	public void addImage(File file){
		Container image = new EXContainer("img", "img").setStyle("width", "256px").setStyle("height", "256px").setAttribute("src", ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath()));
		Container li = new EXContainer(file.getAbsolutePath(), "li");
		image.setAttribute("alt", ResourceUtil.getDownloadURL("ecm", file.getAbsolutePath()));
		li.addChild(image);
		getChild("finderContent").getChild("finder").addChild(li);
		
	}
	
	
	public void setThumbnailView(){
		Container finder = getChild("finderContent").getChild("finder");
		for(Container li : finder.getChildren()){
			li.setStyleClass(" ");
			li.setStyle("width", "48px").setStyle("padding", "10px");
			li.setStyle("float", "left");
			
			Container a = li.getChild("a");
			a.getChildren().clear();
			a.setText(" ");
			a.setStyleClass(" ");
			a.setStyle("float", "left");
			
			Container img = new EXContainer("img", "img").setAttribute("width", "48").setAttribute("height", "48");
			img.setAttribute("src", ResourceUtil.getDownloadURL("ecm", li.getName()));
			a.addChild(img);
			Container arrow = li.getChild("arrow");
			arrow.setText(a.getAttribute("title"));
			arrow.setStyle("float", "left");
			arrow.setStyleClass(" ");
			
			
		}
	}
	
	public void addFile(File file){
		
		Container li = new EXContainer(file.getAbsolutePath(), "li");
		Container a = new EXContainer("a", "a");
		a.setAttribute("href", "#");
		a.setAttribute("title", file.getName());
		
		li.addChild(a);
		if(file instanceof Drive || file.getClazz().equals(Directory.class.getName())){
			
			li.addClass("ui-finder-folder ui-finder-list-item").setStyle("border-color", "silver");
			a.setText(""+ file.getName()+"<span class=\"ui-icon ui-icon-folder-collapsed ui-finder-icon ui-finder-icon-folder\" />");
			
			
		}else{
			li.addClass("ui-finder-file ui-finder-list-item").setStyle("border-color", "silver");
			a.setText(""+ file.getName()+"<span class=\"ui-icon ui-icon-document ui-finder-icon ui-finder-icon-file\" />");
		}
		
		
		a.addEvent(new FinderSelectFileEvent(), Event.CLICK);
		
		
		Container arrow = new EXContainer("arrow", "span").addClass("ui-icon").addClass("ui-icon-triangle-1-e").addClass("ui-finder-icon").addClass("ui-finder-icon-arrow");
		li.addChild(arrow);
		getChild("finderContent").getChild("finder").addChild(li);
		a.setAttribute("file", file.getAbsolutePath());
		
	}
	
	public String getSelectedFile(){
		return getAncestorOfType(EXFinderContainer.class).getAttribute("selectedFile");
	}
	
	public EXFinderColumn openFile(String fileName){
		return selectFile(getSelectedFile() + "/" + fileName); 
	}
	
	public EXFinderColumn selectFile(String directory){
		boolean showUpload = false;
		boolean showNewFile = false;
		boolean showEditFile = false;
		boolean showSelectFile = true;
		getAncestorOfType(EXFinderContainer.class).setTitle(directory);
		getAncestorOfType(EXFinderContainer.class).setAttribute("selectedFile", directory);
		boolean showChildren = true;
		File dir = SpringUtil.getRepositoryService().getFile(directory, Util.getRemoteUser());
		if(getAncestorOfType(EXFinderContainer.class).getSelectableFileFilter() != null)
			showSelectFile = getAncestorOfType(EXFinderContainer.class).getSelectableFileFilter().accept(dir);
		
		if(!(dir instanceof Directory) && !(dir instanceof Shortcut)){
			showChildren = false;
		}else{
			showUpload = true;
			showNewFile = true;
			
			if(dir instanceof BinaryFile){
				showEditFile = true;
			}
		}
		
		
		
		
		getAncestorOfType(EXFinderContainer.class).getDescendentByName("Edit").setDisplay(showEditFile);
		getAncestorOfType(EXFinderContainer.class).getDescendentByName("New").setDisplay(showNewFile);
		getAncestorOfType(EXFinderContainer.class).getDescendentByName("Upload").setDisplay(showUpload);
		getAncestorOfType(EXFinderContainer.class).getDescendentByName("Select").setDisplay(showSelectFile);
		getAncestorOfType(EXFinderContainer.class).getDescendentByName("Delete").setDisplay(true);
		getAncestorOfType(EXFinderContainer.class).getDescendentByName("New Folder").setDisplay(showNewFile);
		
		
		Container finder = getDescendentByName("finder");
		for(Container c : finder.getChildren()){
			if(c.getName().equals(directory)){
				c.addClass("ui-state-default");
			}else{
				c.removeClass("ui-state-default");
			}
		}
		
		Container container = getParent();
		int thisIndex = container.getChildren().indexOf(this);
		List<Container> toHide = new ArrayList<Container>();
 		for(int i = (thisIndex+1); i < container.getChildren().size(); i ++){
			toHide.add(container.getChildren().get(i));
		}
 		EXFinderColumn column = null;
 		if(toHide.size() > 0){
 			if(showChildren){
	 			column = (EXFinderColumn)toHide.remove(0);
	 			column.setDirectory(directory);
 			}
 			
 			for(Container c : toHide){
 				c.remove();
 			}
 		}else{
 			if(showChildren){
 				column = new EXFinderColumn("", directory);
 				container.addChild(column);
 			}
 		}
 		
 		
 		List<Container> children = container.getChildren();
 		
 		
 		
 		if(children.size() == 1){
 			
 		}
 		else if(children.size() == 2){
			children.get(0).setStyle("width", "290px");
			children.get(1).setStyle("left", "290px");
			children.get(1).setStyle("width", "auto");
			getAncestorByName("finderContainer").setStyle("width", "auto");
		}else if(children.size() == 3){
			children.get(1).setStyle("width", "269px");
			children.get(2).setStyle("left", "559px");
			children.get(2).setStyle("width", "auto");
			getAncestorByName("finderContainer").setStyle("width", "auto");
		}else if(children.size() == 4){
			children.get(2).setStyle("width", "269px");
			children.get(3).setStyle("left", "828px");
			children.get(3).setStyle("width", "auto");
			getAncestorByName("finderContainer").setStyle("width", "1097px");
		}else if(children.size() == 5){
			children.get(3).setStyle("width", "269px");
			children.get(4).setStyle("left", "1097px");
			children.get(4).setStyle("width", "auto");
			getAncestorByName("finderContainer").setStyle("width", "1354px");
		}else if(children.size() == 6){
			children.get(4).setStyle("width", "269px");
			children.get(5).setStyle("left", "1366px");
			children.get(5).setStyle("width", "auto");
			getAncestorByName("finderContainer").setStyle("width", "1625px");
		}
		else if(children.size() == 7){
			children.get(5).setStyle("width", "269px");
			children.get(6).setStyle("left", "1635px");
			children.get(6).setStyle("width", "auto");
			getAncestorByName("finderContainer").setStyle("width", "1896px");
		}
		else if(children.size() == 8){
			children.get(6).setStyle("width", "269px");
			children.get(7).setStyle("left", "1904px");
			children.get(7).setStyle("width", "auto");
			getAncestorByName("finderContainer").setStyle("width", "2167px");
		}else{
			children.get(children.size()-2).setStyle("width", "269px");
			children.get(children.size()-1).setStyle("left", (1904 + ((children.size()-8)*269)) + "px");
			children.get(children.size()-1).setStyle("width", "auto");
			getAncestorByName("finderContainer").setStyle("width",  (2167 + ((children.size()-8)*271)) + "px");
		}
 		
 		return column;
	}
	
	
	

}
