package org.castafiore.designer.newportal.body;

import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.newportal.EXNewPortal;
import org.castafiore.designer.newportal.NewPortal;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXSelectFolderBody extends AbstractWizardBody implements FileFilter, SelectFileHandler{

	public EXSelectFolderBody(String name) {
		super(name);
		
		
		//addChild(new EXContainer("", "h5").setText("Select the folder to save the file").setStyle("margin", "12px 24px 0px"));
		EXFinder finder = new EXFinder("ff", this,this , "/root/users/" + Util.getLoggedOrganization());
		addChild(finder);
		finder.getDescendentByName("finderTitle").setDisplay(false);
		finder.getDescendentByName("Close").setDisplay(false);
		finder.getDescendentByName("finder").removeClass("ui-widget-header");
		addChild(new EXContainer("", "label").setText("Enter the name of the site").setStyle("margin", "12px 24px 0px"));
		addChild(new EXInput("portalName").setStyle("margin", "2px 23px").setStyle("width", "245px"));
		finder.getDescendentByName("header").setDisplay(false);
	}
	
	@Override
	public boolean accept(File pathname) {
		return Directory.class.getName().equals(pathname.getClazz());
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
				if(!name.endsWith(".ptl")){
					name = name + ".ptl";
				}
				
				NewPortal p = getAncestorOfType(EXNewPortal.class).getNewPortal();
				p.setName(name);
				p.setPortalDir(selectedFolder);
				BinaryFile bf = p.generate((Application)getRoot());
				EXDesigner designer = getAncestorOfType(EXDesigner.class);
				getAncestorOfType(EXNewPortal.class).getParent().setRendered(false);
				getAncestorOfType(EXNewPortal.class).remove();
				
				designer.open(bf);
			}else if(button.getName().equals("back")){
				return new EXSelectFooterBody("EXSelectFooterBody");
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		return null;
	}

}
