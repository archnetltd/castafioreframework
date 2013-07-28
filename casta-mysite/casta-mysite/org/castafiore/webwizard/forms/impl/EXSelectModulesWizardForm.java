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

import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.webwizard.WizardForm;

public class EXSelectModulesWizardForm extends EXPanel implements WizardForm{

	public EXSelectModulesWizardForm() {
		super("EXSelectModulesWizardForm", "Select the modules you will want");
		setDraggable(false);
		setShowCloseButton(false);
		setWidth(Dimension.parse("100%"));
	}


	public void initForm(List<WizardForm> previousForms) {
		setBody(new EXSelectModule(""));
	}


	public DynaForm addButton(Button button) {
		return null;
	}


	public DynaForm addField(String label, StatefullComponent input) {
		return null;
	}


	public StatefullComponent getField(String name) {
		return null;
	}


	public List<StatefullComponent> getFields() {
		return null;
	}


	public Map<String, StatefullComponent> getFieldsMap() {
		return null;
	}
	
	
	public List<String> getSelectedModules(){
		return getDescendentOfType(EXSelectModule.class).getSelectedModules();
	}


	
	public boolean validate() {
		if(getSelectedModules().size() == 0){
			return false;
			
		}else{
			return true;
		}
	}


	/* (non-Javadoc)
	 * @see org.castafiore.ui.ex.dynaform.DynaForm#setLabelFor(java.lang.String, org.castafiore.ui.StatefullComponent)
	 */
	@Override
	public void setLabelFor(String label, StatefullComponent c) {
		// TODO Auto-generated method stub
		
	}

}
