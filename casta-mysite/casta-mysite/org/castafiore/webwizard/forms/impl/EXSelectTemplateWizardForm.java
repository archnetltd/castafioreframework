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

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.webwizard.Template;
import org.castafiore.webwizard.WebWizardService;
import org.castafiore.webwizard.WizardForm;

public class EXSelectTemplateWizardForm extends EXPanel implements WizardForm{

	public EXSelectTemplateWizardForm() {
		super("EXSelectTemplateWizardForm", "Choose the template for your website");
		setBody(new EXSelectTemplate("EXSelectTemplate", getTemplates()));
		setDraggable(false);
		setShowCloseButton(false);
		setWidth(Dimension.parse("100%"));
	}

	public List<Template> getTemplates(){
		return SpringUtil.getBeanOfType(WebWizardService.class).getTemplates();
	}
	
	public String getSelectedTemplate(){
		return getDescendentOfType(EXSelectTemplate.class).getSelectedTemplate();
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
		return getSelectedTemplate() != null;
	}

	/* (non-Javadoc)
	 * @see org.castafiore.ui.ex.dynaform.DynaForm#setLabelFor(java.lang.String, org.castafiore.ui.StatefullComponent)
	 */
	@Override
	public void setLabelFor(String label, StatefullComponent c) {
		// TODO Auto-generated method stub
		
	}

}
