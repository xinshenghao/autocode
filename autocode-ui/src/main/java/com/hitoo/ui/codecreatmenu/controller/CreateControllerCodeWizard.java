package com.hitoo.ui.codecreatmenu.controller;

import org.eclipse.jface.wizard.Wizard;

import com.hitoo.config.FilePathBean;
import com.hitoo.config.createcode.AbsCodeCreater;
import com.hitoo.config.createcode.controller.ControllerCodeCreater;
import com.hitoo.config.createcode.service.ServiceCodeCreater;
import com.hitoo.ui.codecreatmenu.SelectDomainPage;

public class CreateControllerCodeWizard extends Wizard {
	
	private SelectDomainPage selectDomainPage;
	private SelectControTemplatePage selectControTemplatePage;
	private AbsCodeCreater codeCreater;

	public CreateControllerCodeWizard() {
		setWindowTitle("createControllerCodeWizard");
		selectDomainPage = new SelectDomainPage();
		selectControTemplatePage = new SelectControTemplatePage();
	}

	@Override
	public void addPages() {
		this.addPage(selectDomainPage);
		this.addPage(selectControTemplatePage);
	}

	@Override
	public boolean performFinish() {
		String[] domains = selectDomainPage.getSelectedDomains();
		String templateName = selectControTemplatePage.getTemplatePath();
		
		String outputPath = FilePathBean.getProjectPath()+"autocode-ui/tmp/controller";
		codeCreater = new ControllerCodeCreater(templateName, outputPath);
		codeCreater.createCodes(domains);
		
		
		return true;
	}

	@Override
	public boolean canFinish() {
		return this.getContainer().getCurrentPage() == selectControTemplatePage;
	}
}
