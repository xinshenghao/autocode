package com.hitoo.ui.dbmenu.connmanager;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.hitoo.config.common.CommParaBeanOperationer;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.db.DBHelper;
import com.hitoo.config.model.DBConnection;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;
/**
 * 修改或者删除数据库连接
 * @author xsh
 *
 */
public class DBConnUpdateOrDeleDialog extends Dialog {
	private Text connName;
	private Text hostName;
	private Text port;
	private Text userName;
	private Text password;
	private Button setDefault;
	private DBConnection oldConnection = null;
	private DBConnection newConnection = null;
	private DBHelper dbHelper = null; 
	private CommonParamterOperationer commonParamterOperationer = null;
	

	/**
	 * Create the dialog.
	 * @param parentShell
	 * @wbp.parser.constructor
	 */
	public DBConnUpdateOrDeleDialog(Shell parentShell) {
		super(parentShell);
		commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
	}

	public DBConnUpdateOrDeleDialog(Shell parentShell, DBConnection oldConnection) {
		this(parentShell);
		this.oldConnection = oldConnection;
		CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
		CommParaBeanOperationer commParaBeanOperationer = (CommParaBeanOperationer) ApplicationContextHelper.getBean("commParaBeanOperationer");
		dbHelper = new DBHelper(commonParamterOperationer, commParaBeanOperationer, oldConnection);
	}
	
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());
		
		Group group = new Group(container, SWT.NONE);
		group.setText("修改数据库连接");
		FormData fd_group = new FormData();
		fd_group.top = new FormAttachment(0, 10);
		fd_group.left = new FormAttachment(0, 10);
		fd_group.bottom = new FormAttachment(0, 73);
		fd_group.right = new FormAttachment(0, 690);
		group.setLayoutData(fd_group);
		
		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 658, 19);
		lblNewLabel.setText("修改已经存在的数据库连接或者直接删除不想要的数据库连接");
		
		Composite composite = new Composite(container, SWT.BORDER);
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(group, 0, SWT.RIGHT);
		fd_composite.bottom = new FormAttachment(group, 374, SWT.BOTTOM);
		fd_composite.top = new FormAttachment(group, 7);
		fd_composite.left = new FormAttachment(group, 0, SWT.LEFT);
		composite.setLayoutData(fd_composite);
		
		composite.setLayout(new GridLayout(3, false));
		
		Label connlabel = new Label(composite, SWT.NONE);
		connlabel.setText("连接名:");
		new Label(composite, SWT.NONE);
		
		connName = new Text(composite, SWT.BORDER);
		connName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setText("主机名或IP地址：");
		new Label(composite, SWT.NONE);
		
		hostName = new Text(composite, SWT.BORDER);
		hostName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setText("端口：");
		new Label(composite, SWT.NONE);
		
		port = new Text(composite, SWT.BORDER);
		port.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_3 = new Label(composite, SWT.NONE);
		lblNewLabel_3.setText("用户名：");
		new Label(composite, SWT.NONE);
		
		userName = new Text(composite, SWT.BORDER);
		userName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_4 = new Label(composite, SWT.NONE);
		lblNewLabel_4.setText("密码：");
		new Label(composite, SWT.NONE);
		
		password = new Text(composite, SWT.BORDER);
		password.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		setDefault = new Button(composite, SWT.CHECK);
		setDefault.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		setDefault.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		setDefault.setSelection(true);
		setDefault.setText("是否设为默认");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Button test = new Button(composite, SWT.NONE);
		test.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dbHelper.setDbConnection(getDBConnection());
				String result = dbHelper.canConnToDBSys();
				if(null == result) {
					MessageDialog.openInformation(getParentShell(), "成功", "连接数据库成功");
				}else {
					MessageDialog.openError(getParentShell(), "错误", "连接数据库错误，错误信息:"+result);
				}
			}
		});
		test.setText("测试连接");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Button deleteBtn = new Button(composite, SWT.NONE);
		deleteBtn.setText("删除连接");
		deleteBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isDelete = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "是否删除", "是否确认要删除此连接信息？");
				if(isDelete) {
					dbHelper.setDbConnection(oldConnection);
					dbHelper.deleteDBConnection();
					AutoCode.initConnDBMenu();
					MessageDialog.openInformation(getParentShell(), "成功", "删除成功");
					close();
				}
			}
		});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		initData();
		return container;
	}
	/**
	 * 设置初始值
	 */
	private void initData() {
		connName.setText(oldConnection.getConnectionName());
		hostName.setText(oldConnection.getHostName());
		port.setText(oldConnection.getPort());
		userName.setText(oldConnection.getUserName());
		password.setText(oldConnection.getPassword());
		setDefault.setSelection(oldConnection.getSelect());
	}
	/**
	 * 获取用户输入的数据库连接信息
	 * @return
	 */
	private DBConnection getDBConnection() {
		DBConnection result = new DBConnection();
		result.setConnectionName(connName.getText());
		result.setHostName(hostName.getText());
		result.setPort(port.getText());
		result.setUserName(userName.getText());
		result.setPassword(password.getText());
		result.setSelect(setDefault.getSelection());
		return result;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(700, 550);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if(buttonId == IDialogConstants.OK_ID) {
			newConnection = getDBConnection();
			dbHelper.setDbConnection(newConnection);
			String result = dbHelper.canConnToDBSys();
			if(null == result) {
				dbHelper.updateDBConnection(oldConnection);
				MessageDialog.openInformation(getParentShell(), "成功", "修改数据库信息成功");
				AutoCode.initConnDBMenu();
				close();
			}else {
				MessageDialog.openError(getParentShell(), "错误", "连接数据库错误，错误信息:"+result);
			}
		}else {
			close();			
		}
	}
	
	
}
