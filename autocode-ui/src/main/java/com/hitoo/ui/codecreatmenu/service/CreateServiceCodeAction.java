package com.hitoo.ui.codecreatmenu.service;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

public class CreateServiceCodeAction extends Action{

	public CreateServiceCodeAction() {
		setText("生成Service层代码");
	}
	
	@Override
	public void run() {
		WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), new CreateServiceCodeWizard());
		dialog.setPageSize(250, 300);
		dialog.open();
	}
}
