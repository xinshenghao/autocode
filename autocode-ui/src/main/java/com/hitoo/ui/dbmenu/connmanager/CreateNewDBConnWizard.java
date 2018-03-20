package com.hitoo.ui.dbmenu.connmanager;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

import com.hitoo.config.db.DBHelper;
import com.hitoo.ui.start.AutoCode;
/**
 * 管理选择数据库类型和新建数据库连接两个引导页的wizard
 * @author xsh
 *
 */
public class CreateNewDBConnWizard extends Wizard {
	public static String SELECT_DB_TYPE_WP = "selectDBType";
	public static String CREATE_DB_CONN_WP = "createDBType";
	
	private SelecteDBTypeWizardPage selecteDBTypeWizardPage;
	private CreateDBConnWizardPage createDBConnWizardPage;

	public CreateNewDBConnWizard() {
		//创建两个引导页面
		selecteDBTypeWizardPage = new SelecteDBTypeWizardPage();
		createDBConnWizardPage = new CreateDBConnWizardPage();
		//添加页面
		this.addPage(selecteDBTypeWizardPage);
		this.addPage(createDBConnWizardPage);
		setWindowTitle("新建数据库连接");
	}
	@Override
	public boolean canFinish() {
		if(this.getContainer().getCurrentPage() == createDBConnWizardPage) {
			return true;
		}else{
			return false;
		}
	}


	@Override
	public boolean performFinish() {
		//测试能否连接成功
		DBHelper dbHelper = createDBConnWizardPage.getDbHelper();
		String result = dbHelper.canConnToDBSys();
		if(null == result) {
			MessageDialog.openInformation(getShell(), "成功", "新建成功");
			dbHelper.saveConnectionInfor();
			AutoCode.initConnDBMenu();
			return true;
		}else {
			MessageDialog.openError(getShell(), "错误", "无法连接到数据库，错误信息："+result);
			return false;
		}
	}

}
