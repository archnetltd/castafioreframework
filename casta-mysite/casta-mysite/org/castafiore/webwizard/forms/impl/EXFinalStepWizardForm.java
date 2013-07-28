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
 package org.castafiore.webwizard.forms.impl;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.webwizard.WizardForm;

public class EXFinalStepWizardForm extends EXPanel implements WizardForm {

	public EXFinalStepWizardForm(String name) {
		super(name, "Congratulations! You are ready to start using your online system");
		Container bc = new EXContainer("", "div").addClass("ui-final-step-body-container");
		Container body1 = getBodyItem("Your online website", "The link below points to your online website that your customers can access. This is denoted as your front-office<br>", "#", "Click here to access your font office /online website");	
		bc.addChild(body1);
		
		Container body2 = getBodyItem("Your web operating system", "The link below point to your web operating system, where you find tools to manage and modify content of your website. You will also find tools to manage users etc etc", "webos.jsp", "Click here to access your back office/ web operating system<br>Your username is your e-mail and password is castafiore2010");
		body1.getDescendentByName("link").setName("portalLink");
		bc.addChild(body2);
		setBody(bc);
		setDraggable(false);
		setShowCloseButton(false);
	}
	
	private Container getBodyItem(String stitle, String sDescription, String surl, String surlTitle){
		Container body = new EXContainer("body", "div").addClass("ui-final-step-body");
		
		Container title = new EXContainer("title", "h3");
		title.setText(stitle);
		body.addChild(title);
		
		Container p = new EXContainer("p", "p");
		p.setText(sDescription);
		body.addChild(p);
		
		Container link = new EXContainer("link", "a");
		link.setAttribute("href", surl);
		link.setAttribute("target", "_blank");
		link.setText(surlTitle);
		body.addChild(link);
		
		return body;
	}

	public void initForm(List<WizardForm> previousForms) {
		// TODO Auto-generated method stub
		
	}

	public DynaForm addButton(Button button) {
		// TODO Auto-generated method stub
		return null;
	}

	public DynaForm addField(String label, StatefullComponent input) {
		// TODO Auto-generated method stub
		return null;
	}

	public StatefullComponent getField(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<StatefullComponent> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, StatefullComponent> getFieldsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean validate() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.castafiore.ui.ex.dynaform.DynaForm#setLabelFor(java.lang.String, org.castafiore.ui.StatefullComponent)
	 */
	@Override
	public void setLabelFor(String label, StatefullComponent c) {
		// TODO Auto-generated method stub
		
	}

}
