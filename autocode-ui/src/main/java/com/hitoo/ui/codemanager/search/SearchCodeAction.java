package com.hitoo.ui.codemanager.search;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;

public class SearchCodeAction extends Action {
	
	public SearchCodeAction() {
		setText("代码搜索");
	}
	
	@Override
	public void run() {
		SearchCodeDialog searchCodeDialog = new SearchCodeDialog(Display.getCurrent().getActiveShell());
		searchCodeDialog.open();
	}
}
