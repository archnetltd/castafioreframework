package org.castafiore.webwizard.impl;

import org.castafiore.webwizard.WizardForm;
import org.castafiore.webwizard.forms.impl.EXCompanyInfoWizardForm;
import org.castafiore.webwizard.forms.impl.EXFinalStepWizardForm;
import org.castafiore.webwizard.forms.impl.EXSelectModulesWizardForm;
import org.castafiore.webwizard.forms.impl.EXSelectTemplateWizardForm;
import org.castafiore.webwizard.forms.impl.EXTermAndAgreementWizardForm;

public class EXMinimalWizard extends EXWizard{

	public EXMinimalWizard(String name) {
		super(name);
	}
	
	public void next() {
		
//		WizardForm form = getCurrentWizardForm();
//		
//		
//		if(form.validate()){
//			if(form instanceof EXCompanyInfoWizardForm){
//				company = ((EXCompanyInfoWizardForm)form).getUser();
//			}else if(form instanceof EXSelectModulesWizardForm){
//				selectedModules = ((EXSelectModulesWizardForm)form).getSelectedModules();
//			}else if( form instanceof EXSelectTemplateWizardForm){
//				selectedTemplate = ((EXSelectTemplateWizardForm)form).getSelectedTemplate();
//			}else if( form instanceof EXTermAndAgreementWizardForm){
//				boolean hasAgreed = ((EXTermAndAgreementWizardForm)form).hasAgreed();
//				if(!userCreated && hasAgreed){
//					//create user
//					//create userprofile
//					//create folder templates in folder userprofile
//					//build portal from template
//					//generate portal into directory
//					userCreated = createCompany();
//					getDescendentOfType(EXFinalStepWizardForm.class).getDescendentByName("portalLink").setAttribute("href","/casta-ui/portal.jsp?username=" + company.getUsername());
//				}
//			}
//		}
		
		
		super.next();
	}

}
