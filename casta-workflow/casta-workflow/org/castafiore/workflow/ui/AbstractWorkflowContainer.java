package org.castafiore.workflow.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designer.EXComponentInput;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.layout.EXDroppableXYLayoutContainer;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.workflow.State;

public class AbstractWorkflowContainer extends EXDroppableXYLayoutContainer {

	public AbstractWorkflowContainer(String name) {
		super(name, "div");
		setStyle("width", "135px").setStyle("height", "200px").setStyle("padding", "20px 0").setStyle("cursor", "pointer").setStyle("text-align", "center");
		addChild(new EXContainer("label", "h5").setStyle("background", "white"));
		addChild(new EXContainer("description", "p").setStyle("background", "white"));
		addClass(getClass().getSimpleName());
	}
	
	private final static Event EVENT = new Event() {
		
		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean ServerAction(Container container, Map<String, String> request)
				throws UIException {
			Container c = container.getRoot().getDescendentById(container.getAncestorOfType(EXDynaformPanel.class).getAttribute("componentid"));
			
			((AbstractWorkflowContainer)c).execute(container, request);
			
			return true;
		}
		
		@Override
		public void ClientAction(ClientProxy container) {
			container.mask().makeServerRequest(this);
			
		}
	}; 
	

	
	public void execute(Container source, Map<String, String> request){
		
		
		DesignableService service = SpringUtil.getBeanOfType(DesignableService.class);
		String id = source.getAncestorOfType(EXDynaformPanel.class).getAttribute("componentid");
		Container c =source.getAncestorOfType(EXDesigner.class).getDescendentById(id);
		
		String des = source.getAncestorOfType(EXDynaformPanel.class).getAttribute("des");
		
		DesignableFactory fact = service.getDesignable(des);
		fact.applyAttribute(c, source.getName(),  ((StatefullComponent)source).getValue().toString());
	

	}
	
	public String getDescription() {
		return getChild("description").getText();
	}


	public String getLabel() {
		return getChild("label").getText();
	}
	
	public void setDescription(String description){
		 getChild("description").setText(description);
	}
	
	public void setLabel(String label){
		getChild("label").setText(label);
	}

	public void onAddComponent(Container component) {
		EXDynaformPanel panel = new EXDynaformPanel("opts", "Action options");
		preDecorate(component, panel);
		if(component instanceof EXAction){
			decorationForAction((EXAction)component, panel);
		}else if(component instanceof EXState){
			decorationForState((EXState)component, panel);
		}else if(component instanceof EXActor){
			decorationForActor((EXActor)component, panel);
		}
		
		forDecorate(component, panel);
		getAncestorOfType(PopupContainer.class).addPopup(panel);
	}
	
	private void preDecorate(Container component, EXDynaformPanel panel){
		panel.setStyle("width", "500px").setStyle("z-index", "7000");
		panel.addField("Label : ", new EXInput("label"));
		panel.addField("Description :", new EXTextArea("description"));
		panel.getDescendentByName("description").setStyle("width", "300px").setStyle("height", "225px");
		panel.addField("Color :", new EXColorPicker("background-color"));
		panel.setAttribute("componentid", component.getId());
		
	}
	
	
	private void decorationForActor(EXActor state, EXDynaformPanel panel){
		panel.setTitle("Actor options");
		panel.addField("Groups :", new EXInput("value"));
		panel.setAttribute("des", "workflow:actor");
	}
	
	private void decorationForState(EXState state, EXDynaformPanel panel){
		panel.setTitle("State options");
		panel.addField("Value :", new EXInput("value"));
		panel.setAttribute("des", "workflow:state");
	}
	
	private void forDecorate(Container container, EXDynaformPanel panel){
		ComponentUtil.iterateOverDescendentsOfType(panel, StatefullComponent.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				c.addEvent(EVENT, Event.BLUR);
				
			}
		});
		
	}
	
	private void decorationForAction(EXAction action, EXDynaformPanel panel){
		panel.setTitle("Action options");
		final List<String> dict = new ArrayList<String>();
		
		ComponentUtil.iterateOverDescendentsOfType(getAncestorOfType(EXWorkflow.class), State.class,new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				State st = (State)c;
				if(st.getStateLabel().equals(getAncestorOfType(EXState.class).getLabel())){
					
				}else{
					dict.add(st.getStateLabel());
				}
				
			}
		});
		
		
		panel.addField("Resulting State :", new EXAutoComplete("nextState","", dict));
		panel.addField("Output form : ", new EXComponentInput("form"));
		panel.setAttribute("des", "workflow:action");
	}
}
