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
 package org.castafiore.webwizard;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.DescriptibleApplication;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.webwizard.forms.impl.EXCompanyInfoWizardForm;
import org.castafiore.webwizard.forms.impl.EXFinalStepWizardForm;
import org.castafiore.webwizard.forms.impl.EXSelectModulesWizardForm;
import org.castafiore.webwizard.forms.impl.EXSelectTemplateWizardForm;
import org.castafiore.webwizard.forms.impl.EXTermAndAgreementWizardForm;
import org.castafiore.webwizard.impl.EXWebWizardWizard;
import org.castafiore.webwizard.impl.EXWizard;

public class WebWizardApp extends EXApplication implements DescriptibleApplication {

	public WebWizardApp() {
		super("Web-Wizard");
		try{
			SpringUtil.getSecurityService().login("anonymous", "anonymous");
		}catch(Exception e){
			
		}
		EXWizard wizard = new EXWebWizardWizard("")
		.addForm(new EXCompanyInfoWizardForm())
		.addForm(new EXSelectModulesWizardForm())
		.addForm(new EXSelectTemplateWizardForm())
		.addForm(new EXTermAndAgreementWizardForm())
		.addForm(new EXFinalStepWizardForm(""));
		
		wizard.init(this);
		wizard.setWidth(Dimension.parse("700px"));
		addChild(wizard);
	}


	public String getDescription() {
		return "The web wizard";
	}

}
