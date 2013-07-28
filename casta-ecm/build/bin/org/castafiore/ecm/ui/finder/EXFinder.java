package org.castafiore.ecm.ui.finder;

import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.File;

public class EXFinder extends EXContainer implements PopupContainer{
	
	private SelectFileHandler handler = null;
	
	private CloseFinderHandler closer = null;

	public EXFinder(String name, FileFilter selectFileFilter,SelectFileHandler handler, String dir) {
		super(name, "div");
		setStyle("width", "850px");
		Container pc = new EXContainer("pc", "div").setStyle("position", "absolute");
		addChild(pc);
		EXFinderContainer finder = new EXFinderContainer("finder");
		finder.setSelectableFileFilter(selectFileFilter);
		finder.setStyle("z-index", "3000");
		finder.setRootDir(dir);
		
		addChild(finder);
		setStyle("z-index", "3000");
		this.handler = handler;
	}
	
	public EXFinder(String name, FileFilter selectFileFilter,SelectFileHandler handler,CloseFinderHandler closer_, String dir) {
		super(name, "div");
		setStyle("width", "850px");
		Container pc = new EXContainer("pc", "div").setStyle("position", "absolute");
		addChild(pc);
		EXFinderContainer finder = new EXFinderContainer("finder");
		finder.setSelectableFileFilter(selectFileFilter);
		finder.setStyle("z-index", "3000");
		finder.setRootDir(dir);
		
		addChild(finder);
		setStyle("z-index", "3000");
		this.handler = handler;
		this.closer = closer_;
	}

	public CloseFinderHandler getCloser() {
		return closer;
	}

	public void setCloser(CloseFinderHandler closer) {
		this.closer = closer;
		setRendered(false);
	}

	@Override
	public void addPopup(Container popup) {
		getChild("pc").addChild(popup.setStyle("top", "10%").setStyle("left", "10%"));
	}
	
	public void setSelectableFileFilter(FileFilter selectableFileFilter){
		this.getDescendentOfType(EXFinder.class).setSelectableFileFilter(selectableFileFilter);
	}
	
	public void addInput(String label){
		Container div = new EXContainer("inpu", "div").addChild(new EXContainer("", "span").setText(label)).addChild(new EXInput("input_added")).setStyle("text-align", "right");
		getChild("finder").addChild(div);
	}
	
	public String getInputValue(){
		if(getDescendentByName("inpu") != null){
			return getDescendentByName("inpu").getDescendentOfType(EXInput.class).getValue().toString();
		}else{
			return "";
		}
	}
	public SelectFileHandler getHandler() {
		return handler;
	}

	public void setHandler(SelectFileHandler handler) {
		this.handler = handler;
	}
	
	public String getSelectedFile(){
		return getDescendentOfType(EXFinderContainer.class).getAttribute("selectedFile");
	}
	
	public void open(String dir){
		if(dir == null || dir.trim().length() <= 0){
			return;
		}
		String currentDir = getCurrentDir();
		dir = dir.replace(currentDir, "");
		if(dir.startsWith("/")){
			dir = dir.substring(1);
		}
		
		String [] parts = StringUtil.split(dir, "/");
		EXFinderColumn col = getDescendentOfType(EXFinderColumn.class);
		for(String name : parts){
			col = col.openFile(name);
		}
		
	}


	public interface SelectFileHandler{
		public void onSelectFile(File file, EXFinder finder);
	}
	
	public interface CloseFinderHandler{
		public void onClose( EXFinder finder);
	}
	
	public String getCurrentDir(){
		return getDescendentOfType(EXFinderContainer.class).getAttribute("selectedFile");
	}
	
	public void addNewFile(File file){
		this.getDescendentOfType(EXFinderContainer.class).addNewFile(file);
	}

}
