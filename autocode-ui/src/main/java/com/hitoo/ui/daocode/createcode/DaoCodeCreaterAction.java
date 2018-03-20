package com.hitoo.ui.daocode.createcode;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

import com.hitoo.ui.dbmenu.opendb.OpenDBAction;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;
import com.hitoo.ui.start.RuntimeParamter;

public class DaoCodeCreaterAction extends Action {
	
	
	public DaoCodeCreaterAction() {
		setText("代码生成");
	}

	@Override
	public void run() {
		//检查有没有指定要生成的数据库名
		RuntimeParamter runtimeParamter = (RuntimeParamter) ApplicationContextHelper.getBean("runtimeParamter");
		String selectedDBName = runtimeParamter.getSelectedDataBase();
		if(null == selectedDBName) {
			boolean isCreate =MessageDialog.openQuestion(AutoCode.getApp().getShell(), "警告", "没有指定要生成实体的数据库名，是否立刻指定？");
			if(isCreate) {
				new OpenDBAction().run();
			}
		}
		//打开选项
		WizardDialog createDaoCode = new WizardDialog(Display.getCurrent().getActiveShell(), new CreateDaoCodeWizard());
		createDaoCode.open();
	}
}
