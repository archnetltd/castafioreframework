/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.webwizard.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.webwizard.Wizard;
import org.castafiore.webwizard.WizardForm;

public  class EXWizard extends EXPanel implements Wizard, Event {
	
	private int activeStep= 0;

	public EXWizard(String name) {
		super(name, "");
		setShowHeader(false);
		setShowFooter(true).setDraggable(false);
		setStyleClass("ui-wizard-panel");
		getBodyContainer().setStyleClass("ui-wizard-body");
		getFooterContainer()
			.setStyleClass("ui-widget-content ui-wizard-footer ui-corner-all")
			.addChild(new EXButton("cancel", "Cancel"))
			.addChild(new EXButton("next", "Next"))
			.addChild(new EXButton("previous", "Previous"))
			
			;
	}
	public void setForms(List<WizardForm> forms){
		getBodyContainer().getChildren().clear();
		getBodyContainer().setRendered(false);

		for(WizardForm form : forms){
			form.initForm(null);
			getBodyContainer().addChild(form);
		}
	}
	
	public EXWizard addForm(WizardForm form){
		form.setWidth(Dimension.parse("98%"));
		form.initForm(null);
		getBodyContainer().addChild(form);
		return this;
	}
	public List<WizardForm> getForms(){
		List container = new ArrayList();
		ComponentUtil.getDescendentsOfType(this, container, WizardForm.class);
		return container;
	}
	public void cancel() {
		this.remove();
	}


	public  void finish(){
		
	}


	public WizardForm getCurrentWizardForm() {
		return getForms().get(activeStep);
	}

	public WizardForm getWizardForm(int step) {
		return getForms().get(step);
	}


	public void init(Object context) {
		activeStep=0;
		refreshState();
		List<Container> buttons = new ArrayList<Container>(); 
		ComponentUtil.getDescendentsOfType(this, buttons, EXButton.class);
		for(Container button : buttons){
			button.addEvent(this, Event.CLICK);
		}
		
		
	}
	protected void refreshState(){
		List<WizardForm> subForms = getForms();
		for(int i =0; i < subForms.size(); i++){
			//form.setDraggable(false);
			if(i == activeStep){
				if(!subForms.get(i).isVisible()){
					subForms.get(i).setDisplay(true);
				}
			}else{
				if(subForms.get(i).isVisible()){
					subForms.get(i).setDisplay(false);
				}
			}
		}
		
		if(activeStep == 0){
			getDescendentByName("previous").setDisplay(false);
			getDescendentByName("cancel").setText("Cancel");
		}else if(activeStep == getForms().size()-1){
			getDescendentByName("cancel").setText("Finish");
			getDescendentByName("previous").setDisplay(true);
		}else{
			getDescendentByName("previous").setDisplay(true);
			getDescendentByName("cancel").setText("Cancel");
		}
	}

	public void next() {
		if(getCurrentWizardForm().validate()){
			activeStep++;
			
			refreshState();
		}
		
		
	}


	public void previous() {
		activeStep--;
		refreshState();
	}

	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(getClass()));
		container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String caller = container.getName();
		if(caller.equals("next")){
			next();
		}else if(caller.equals("previous")){
			previous();
		}else if(caller.equals("cancel")){
			cancel();
		}else if(caller.equals("finish")){
			finish();
		}
		
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
