package org.castafiore.workflow.ui.designable;

import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.newportal.EXNewPortal;
import org.castafiore.designer.newportal.NewPortal;
import org.castafiore.designer.newportal.body.AbstractWizardBody;
import org.castafiore.designer.newportal.body.EXSelectFolderBody;
import org.castafiore.designer.newportal.body.EXSelectFooterBody;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXWorkflowSelectFolderBody extends EXSelectFolderBody{

	public EXWorkflowSelectFolderBody(String name) {
		super(name);
		
	}
	
	
	@Override
	public AbstractWizardBody clickButton(Container button) {
		try{
			if(button.getName().equals("next")){
				String selectedFolder = getDescendentOfType(EXFinder.class).getSelectedFile();
				String name = getDescendentOfType(EXInput.class).getValue().toString();
				if(!name.endsWith(".wkf")){
					name = name + ".wkf";
				}
				
				String username = Util.getRemoteUser();
				RepositoryService service = SpringUtil.getRepositoryService();
				Directory dir = service.getDirectory(selectedFolder ,username);
				
				BinaryFile bf  = dir.createFile(name, BinaryFile.class);
				String emptyPortal = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/workflow/ui/designable/workflow.xml"));
			
				emptyPortal  = emptyPortal.replace("${path}", bf.getAbsolutePath());
				bf.write(emptyPortal.getBytes());
				EXDesigner designer = getAncestorOfType(EXDesigner.class);
				getAncestorOfType(EXNewPortal.class).getParent().setRendered(false);
				getAncestorOfType(EXNewPortal.class).remove();
				
				designer.open(bf);
			}else if(button.getName().equals("back")){
				Container parent = getAncestorOfType(EXNewPortal.class).getParent();
				getAncestorOfType(EXNewPortal.class).remove();
				parent.setRendered(false);
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		return null;
	}

}
