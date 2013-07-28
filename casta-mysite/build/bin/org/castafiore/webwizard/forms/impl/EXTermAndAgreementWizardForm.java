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

import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.IOUtil;
import org.castafiore.webwizard.WizardForm;

public class EXTermAndAgreementWizardForm extends EXPanel implements WizardForm {

	public EXTermAndAgreementWizardForm() {
		super("EXTermAndAgreementWizardForm", "Terms and agreement");
		addClass("ui-wizard-terms");
		try{
			String text = IOUtil.getStreamContentAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/webwizard/terms.properties"));
			EXTextArea a =new EXTextArea("agreement");
			a.setValue(text);
			getBodyContainer().addChild(new EXContainer("sd", "h3").setText("Please read carefully the terms and agreement"));
			getBodyContainer().addChild(a);
			getBodyContainer().addChild(new EXCheckBox("agree"));
			getBodyContainer().addChild(new EXContainer("label", "label").setText("I agree with the terms and condition"));
			setDraggable(false);
			setShowCloseButton(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	public void initForm(List<WizardForm> previousForms) {
		
		
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
	
	public boolean hasAgreed(){
		return getDescendentOfType(EXCheckBox.class).isChecked();
	}



	public boolean validate() {
		return hasAgreed();
	}


	/* (non-Javadoc)
	 * @see org.castafiore.ui.ex.dynaform.DynaForm#setLabelFor(java.lang.String, org.castafiore.ui.StatefullComponent)
	 */
	@Override
	public void setLabelFor(String label, StatefullComponent c) {
		// TODO Auto-generated method stub
		
	}

}
