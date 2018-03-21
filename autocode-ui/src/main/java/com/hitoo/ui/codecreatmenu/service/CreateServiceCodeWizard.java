package com.hitoo.ui.codecreatmenu.service;

import org.eclipse.jface.wizard.Wizard;

import com.hitoo.config.ConfigFilePath;
import com.hitoo.config.createcode.service.ServiceCodeCreate;

public class CreateServiceCodeWizard extends Wizard {
	private SelectDomainPage selectDomainPage;
	private SelectTemplatePage selectTemplatePage;
	private ServiceCodeCreate serviceCodeCreate;

	public CreateServiceCodeWizard() {
		setWindowTitle("Service层代码生成");
		selectDomainPage = new SelectDomainPage();
		selectTemplatePage = new SelectTemplatePage();
	}

	@Override
	public void addPages() {
		addPage(selectDomainPage);
		addPage(selectTemplatePage);
	}
	
	@Override
	public boolean canFinish() {
		if(this.getContainer().getCurrentPage() == selectTemplatePage) {
			return true;		
		}
		return false;
	}

	@Override
	public boolean performFinish() {
		String[] domains = selectDomainPage.getSelectedDomains();
		String templateName = selectTemplatePage.getTemplatePath();
		boolean isCreateInter = selectTemplatePage.getIsCreateInterface();
		System.out.println(isCreateInter);
		
		
		
		
		String outputPath = "/home/xsh/tmp/";
		serviceCodeCreate = new ServiceCodeCreate(templateName, outputPath, isCreateInter);
		serviceCodeCreate.createCodes(domains);
		
		return true;
	}

}
