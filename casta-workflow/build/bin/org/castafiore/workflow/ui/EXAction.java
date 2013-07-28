package org.castafiore.workflow.ui;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.Map;

import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Draggable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.workflow.Action;
import org.castafiore.workflow.Actor;
import org.castafiore.workflow.InternalWorkflowException;
import org.castafiore.workflow.State;
import org.castafiore.workflow.WorkflowContext;

public class EXAction extends AbstractWorkflowContainer implements Action, Draggable, Event{

	public EXAction(String name) {
		super(name);
		addEvent(this, Event.END_DRAG);
		//setStyle("width", "250px").setStyle("height", "20px").setStyle("padding", "3px 0").setStyle("cursor", "pointer").setStyle("text-align", "center").setStyle("z-index", "6000");
	}

	@Override
	public State execute(WorkflowContext context) {
		
		String script = getAttribute("script");
		if(StringUtil.isNotEmpty(script)){
			Binding b = new Binding(context);
			b.setProperty("state", getAncestorOfType(EXWorkflow.class).getCurrentState());
			b.setProperty("actor", getAncestorOfType(EXWorkflow.class).getCurrentActor());
			GroovyShell shell = new GroovyShell(b);
			
			
			shell.evaluate(script);
			return (State)b.getProperty("result");
		}
		return null;
	}

	@Override
	public Container getButtonType(String type) {
		
		try{
		String file= getAttribute("type");
		
		BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(file, Util.getRemoteUser());
		
		Container c =DesignableUtil.buildContainer(bf.getInputStream(), false);
		return c;
		}catch(Exception e){
			throw new InternalWorkflowException(e);
		}
	}

	@Override
	public Actor getActor() {
		return getDescendentOfType(EXActor.class);
	}

	@Override
	public JMap getDraggableOptions() {
		return new JMap();
	}
	
	

	@Override
	public void ClientAction(ClientProxy container) {
		JMap opt = new JMap().put("x", container.getStyle("left"));
		opt.put("y", container.getStyle("top"));
		container.makeServerRequest(opt,this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		container.setStyle("left", request.get("x")).setStyle("top",request.get("y"));
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
		
	}

}
