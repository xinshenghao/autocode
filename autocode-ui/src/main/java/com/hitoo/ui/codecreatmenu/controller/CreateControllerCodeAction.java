package com.hitoo.ui.codecreatmenu.controller;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

public class CreateControllerCodeAction extends Action{
	
	public CreateControllerCodeAction() {
		setText("生成Controller层代码");
	}
	
	@Override
	public void run() {
		WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), new CreateControllerCodeWizard());
		dialog.setPageSize(250, 300);
		dialog.open();
	}
}
