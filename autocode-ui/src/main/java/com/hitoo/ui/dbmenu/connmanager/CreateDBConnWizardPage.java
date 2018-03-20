package com.hitoo.ui.dbmenu.connmanager;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.hitoo.config.common.CommParaBeanOperationer;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.db.DBHelper;
import com.hitoo.config.model.DBConnection;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;
/**
 * 创建数据库连接信息引导页
 * @author xsh
 *
 */
public class CreateDBConnWizardPage extends WizardPage {
	private Text connName;
	private Text hostName;
	private Text port;
	private Text userName;
	private Text password;
	private Button setDefault;
	private DBHelper dbHelper;

	/**
	 * Create the wizard.
	 */
	public CreateDBConnWizardPage() {
		super("createDBType");
		setTitle("创建数据库连接");
		setDescription("创建一个到数据库管理系统的连接");
		 
		CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
		CommParaBeanOperationer commParaBeanOperationer = (CommParaBeanOperationer) ApplicationContextHelper.getBean("commParaBeanOperationer");
		this.dbHelper = new DBHelper(commonParamterOperationer, commParaBeanOperationer);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		
		
		setControl(container);
		container.setLayout(new GridLayout(3, false));
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("连接名:");
		new Label(container, SWT.NONE);
		
		connName = new Text(container, SWT.BORDER);
		connName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setText("主机名或IP地址：");
		new Label(container, SWT.NONE);
		
		hostName = new Text(container, SWT.BORDER);
		hostName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setText("端口：");
		new Label(container, SWT.NONE);
		
		port = new Text(container, SWT.BORDER);
		port.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_3 = new Label(container, SWT.NONE);
		lblNewLabel_3.setText("用户名：");
		new Label(container, SWT.NONE);
		
		userName = new Text(container, SWT.BORDER);
		userName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_4 = new Label(container, SWT.NONE);
		lblNewLabel_4.setText("密码：");
		new Label(container, SWT.NONE);
		
		password = new Text(container, SWT.BORDER);
		password.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		setDefault = new Button(container, SWT.CHECK);
		setDefault.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		setDefault.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		setDefault.setSelection(true);
		setDefault.setText("是否设为默认");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Button test = new Button(container, SWT.NONE);
		test.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dbHelper.setDbConnection(getDBConnection());
				String result = dbHelper.canConnToDBSys();
				if(null == result) {
					MessageDialog.openInformation(getShell(), "成功", "成功连接到数据库系统");
				}else {
					MessageDialog.openError(getShell(), "错误", "连接数据库错误，异常信息:"+result);
				}
			}
		});
		test.setText("测试连接");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
	}
	
	/**
	 * 从输入框中获取数据库连接信息
	 */
	private DBConnection getDBConnection(){
		DBConnection result = new DBConnection();
		result.setConnectionName(connName.getText());
		result.setHostName(hostName.getText());
		result.setPort(port.getText());
		result.setUserName(userName.getText());
		result.setPassword(password.getText());
		result.setSelect(setDefault.getSelection());
		return result;
	}
	
	public DBHelper getDbHelper() {
		dbHelper.setDbConnection(getDBConnection());
		return dbHelper;
	}

}
