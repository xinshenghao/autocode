package com.hitoo.ui.daocode.config;

import org.eclipse.jface.action.Action;

import com.hitoo.ui.start.AutoCode;

public class MBGConfigManagerAction extends Action {
	
	public MBGConfigManagerAction() {
		setText("环境配置管理");
	}

	@Override
	public void run() {
		new MBGConfigManagerDialog(AutoCode.getApp().getShell()).open();
	}
	

}
