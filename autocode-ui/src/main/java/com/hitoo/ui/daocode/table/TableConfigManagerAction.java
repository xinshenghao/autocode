package com.hitoo.ui.daocode.table;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;

public class TableConfigManagerAction extends Action {
	
	public TableConfigManagerAction( ) {
		setText("表格实体生成策略");
	}
	
	@Override
	public void run() {
		TableConfigManagerDialog dlg = new TableConfigManagerDialog(Display.getCurrent().getActiveShell());
		dlg.open();
	}
}
