package com.hitoo.ui.projecttree.action;

import org.eclipse.jface.action.Action;

import com.hitoo.ui.start.AutoCode;

public class ReflushFileTreeAction extends Action {

	public ReflushFileTreeAction() {
		setText("刷新");
	}
	
	@Override
	public void run() {
		AutoCode.reflushTreeView();
	}
}
