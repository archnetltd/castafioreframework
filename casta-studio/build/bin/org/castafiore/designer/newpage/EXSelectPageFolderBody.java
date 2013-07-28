package org.castafiore.designer.newpage;

import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.newportal.body.AbstractWizardBody;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;

public class EXSelectPageFolderBody extends AbstractWizardBody implements FileFilter, SelectFileHandler{

	public EXSelectPageFolderBody(String name, String portal) {
		super(name);
		EXFinder finder = new EXFinder("ff", this,this , portal + "/pages");
		addChild(finder);
		finder.getDescendentByName("finderTitle").setDisplay(false);
		finder.getDescendentByName("Close").setDisplay(false);
		finder.getDescendentByName("finder").removeClass("ui-widget-header");
		addChild(new EXContainer("", "label").setText("Enter the name of the page").setStyle("margin", "12px 24px 0px"));
		addChild(new EXInput("pageName").setStyle("margin", "2px 23px").setStyle("width", "245px"));
		finder.getDescendentByName("header").setDisplay(false);
	}
	
	@Override
	public boolean accept(File pathname) {
		//return Directory.class.getName().equals(pathname.getClazz());
		return true;
	}

	@Override
	public void onSelectFile(File file, EXFinder finder) {
	}

	@Override
	public AbstractWizardBody clickButton(Container button) {
		try{
			if(button.getName().equals("next")){
				String selectedFolder = getDescendentOfType(EXFinder.class).getSelectedFile();
				String name = getDescendentOfType(EXInput.class).getValue().toString();

				NewPage p = getAncestorOfType(EXNewPage.class).getNewPage();
				p.setWidth( ((EXInput)getAncestorOfType(EXNewPage.class).getChild("pageWidth")).getValue().toString());
				p.setPortalDir(getAncestorOfType(EXNewPage.class).getAttribute("portalPath"));
				p.setName(name);
				p.setDir(selectedFolder);
				BinaryFile bf = p.generate();
				EXDesigner designer = getAncestorOfType(EXDesigner.class);
				getAncestorOfType(EXNewPage.class).getParent().setRendered(false);
				getAncestorOfType(EXNewPage.class).remove();
				
				designer.openPage(bf);
			}else if(button.getName().equals("back")){
				return new EXNewPageBody("EXNewPageBody");
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		return null;
	}

}
