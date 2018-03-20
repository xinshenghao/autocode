package com.hitoo.ui.dbmenu.opendb;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.model.DBConnection;
import com.hitoo.ui.dbmenu.connmanager.CreateNewDBConnWizard;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;

public class OpenDBAction extends Action {

	public OpenDBAction(){
		setText("打开数据库");
	}

	@Override
	public void run() {
		//判断有无默认的数据库系统连接
		DBConnection dbConn = null;
		CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter"); 
		List<DBConnection> conns = commonParameter.getDbConnections();
		if(conns!=null) {
			for (DBConnection dbConnection : conns) {
				if(dbConnection.getSelect()) {
					dbConn = dbConnection;
					break;
				}
			}			
		}
		if(null != dbConn) {
			new OpenDBDialog(AutoCode.getApp().getShell(),dbConn).open();
		}else {
			boolean isCreate = MessageDialog.openQuestion(AutoCode.getApp().getShell(), "警告", "没有默认的数据库连接，是否要立刻新建数据库连接？");
			if(isCreate) {
				WizardDialog dlg = new WizardDialog(AutoCode.getApp().getShell(), new CreateNewDBConnWizard());
				dlg.setPageSize(600, 380);
				dlg.open();
			}
		}
	}
	
}
