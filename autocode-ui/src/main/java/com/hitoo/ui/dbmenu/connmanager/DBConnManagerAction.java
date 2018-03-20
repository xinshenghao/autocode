package com.hitoo.ui.dbmenu.connmanager;

import org.eclipse.jface.action.Action;

import com.hitoo.ui.start.AutoCode;

public class DBConnManagerAction extends Action {
	
	public DBConnManagerAction( ) {
		setText("数据库连接管理");
	}
	
	@Override
	public void run() {
		new DBConnManagerDialog(AutoCode.getApp().getShell()).open();
	}
}
