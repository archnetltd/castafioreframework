package org.castafiore.designer.newportal.body;

import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.newportal.EXNewPortal;
import org.castafiore.designer.newportal.NewPortal;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.wfs.types.BinaryFile;

public class EXSelectFooterBody extends AbstractWizardBody  {

	public EXSelectFooterBody(String name) {
		super(name);
		addClass("EXSelectFooterBody");
		addChild(new EXContainer("h5", "h5").setText("Enter text for the footer").setStyle("color", "white"));
		addChild(new EXTextArea("footertext"));
	}

	@Override
	public AbstractWizardBody clickButton(Container button) {
		if(button.getName().equals("next")){
			String text = getDescendentOfType(EXTextArea.class).getValue().toString();
			getAncestorOfType(EXNewPortal.class).getNewPortal().setFooterText(text);
			getAncestorOfType(EXNewPortal.class).getDescendentByName("next").setText("Finish");
			
		
			return new EXSelectFolderBody("EXSelectFolderBody");
		}else if(button.getName().equals("back")){
			getAncestorOfType(EXNewPortal.class).getDescendentByName("next").setText("Next");
			return new EXSelectMenuBody("EXSelectMenuBody");
		}return null;
	}
	
	
	
	
	public void youdo(){
		String selectedFolder = getDescendentOfType(EXFinder.class).getSelectedFile();
		String name = getDescendentOfType(EXInput.class).getValue().toString();
		if(!name.endsWith(".ptl")){
			name = name + ".ptl";
		}
		
		NewPortal p = getAncestorOfType(EXNewPortal.class).getNewPortal();
		p.setName(name);
		p.setPortalDir(selectedFolder);
		//BinaryFile bf = p.generate((Application)getRoot());
//		EXDesigner designer = getAncestorOfType(EXDesigner.class);
//		getAncestorOfType(EXNewPortal.class).getParent().setRendered(false);
//		getAncestorOfType(EXNewPortal.class).remove();
//		
//		designer.open(bf);
	}

	

}
