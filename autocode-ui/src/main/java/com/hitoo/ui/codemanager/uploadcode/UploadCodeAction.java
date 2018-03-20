package com.hitoo.ui.codemanager.uploadcode;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

public class UploadCodeAction extends Action {

	public UploadCodeAction() {
		setText("代码上传");
	}
	
	@Override
	public void run() {
		WizardDialog dlg = new WizardDialog(Display.getCurrent().getActiveShell(), new UploadCodeWizard());
		dlg.setPageSize(-1, 300);
		dlg.open();
	}
}
