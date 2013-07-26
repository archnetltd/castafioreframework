package org.castafiore.shoppingmall.orders;

import org.castafiore.ui.Container;
import org.castafiore.wfs.types.File;

public class DefaultGUIReactor implements GUIReactor{

	@Override
	public void react(Container source, String actor, String organization, File file, OrdersWorkflow caller) {
		if(source.getAncestorOfType(WorkflowForm.class) != null){
			String id = source.getAttribute("source");
			Container parent = source.getRoot().getDescendentById(id);
			source.getAncestorOfType(WorkflowForm.class).remove();
			caller.addButtons(parent,  file.getStatus(), actor, organization, file.getAbsolutePath());
		}else{
			source.getParent().getChildren().clear();
			source.getParent().setRendered(false);
			caller.addButtons(source.getParent(), file.getStatus(), source.getAttribute("actor"),source.getAttribute("organization"), file.getAbsolutePath());
		}
		
	}

}
