package com.hitoo.ui.filemenu.createproject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

public class CreateEclipseProjectAction extends Action {
	
	public CreateEclipseProjectAction(){
		setText("创建boot项目");
	}
	
	@Override
	public void run() {
		WizardDialog wd = new WizardDialog(Display.getCurrent().getActiveShell(), new CreateProjectWizard());
		wd.open();
	}
}
