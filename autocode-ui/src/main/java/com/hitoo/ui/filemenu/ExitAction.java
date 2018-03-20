package com.hitoo.ui.filemenu;

import java.awt.Dialog;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.DialogMessageArea;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;

public class ExitAction extends Action {
	private ApplicationWindow applicationWindow; 
	
	public ExitAction(ApplicationWindow applicationWindow){
		setText("退出");
		this.applicationWindow = applicationWindow;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		super.run();
		boolean isClose = MessageDialog.openConfirm(applicationWindow.getShell(), "警告", "确认要退出吗?");
		if(isClose){
			applicationWindow.close();			
		}
	}
	
}
