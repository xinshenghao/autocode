package com.hitoo.ui.dbmenu.connmanager;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.model.DBConnection;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;
/**
 * 数据库连接信息管理弹出框（修改数据库连接或者新建数据库连接）
 * @author xsh
 *
 */
public class DBConnManagerDialog extends Dialog {
	List list;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DBConnManagerDialog(Shell parentShell) {
		super(parentShell);
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
		group.setText("数据库连接管理");
		FormData fd_group = new FormData();
		fd_group.top = new FormAttachment(0, 10);
		fd_group.left = new FormAttachment(0, 10);
		fd_group.bottom = new FormAttachment(0, 93);
		fd_group.right = new FormAttachment(0, 590);
		group.setLayoutData(fd_group);
		
		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 558, 43);
		lblNewLabel.setText("    选择已存在的数据库连接进行修改或删除，选择新建连接选项进行数据库连\n接的新建");
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(group, 0, SWT.RIGHT);
		fd_composite.left = new FormAttachment(group, 0, SWT.LEFT);
		fd_composite.bottom = new FormAttachment(group, 204, SWT.BOTTOM);
		fd_composite.top = new FormAttachment(group, 6);
		composite.setLayoutData(fd_composite);
		
		list = new List(composite, SWT.BORDER);
		CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
		CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
		java.util.List<DBConnection> connections = commonParameter.getDbConnections();
		if(connections!=null && connections.size() != 0) {
			for (DBConnection dbConnection : connections) {
				list.add(dbConnection.getConnectionName());
			}
		}
		list.add("新建连接");
		//默认选择第一个
		list.setSelection(0);
		return container;
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
		return new Point(600, 400);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		// TODO Auto-generated method stub
		if(IDialogConstants.OK_ID == buttonId) {
			String selected = list.getSelection()[0];
			if("新建连接".equals(selected)) {
				WizardDialog dlg = new WizardDialog(AutoCode.getApp().getShell(), new CreateNewDBConnWizard());
				this.close();
				dlg.setPageSize(600, 380);
				dlg.open();
			}else {
				CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
				java.util.List<DBConnection> conns = commonParameter.getDbConnections();
				for (DBConnection dbConnection : conns) {
					if(selected.equals(dbConnection.getConnectionName())) {
						DBConnUpdateOrDeleDialog dbConnUpdateOrDeleDialog = new DBConnUpdateOrDeleDialog(AutoCode.getApp().getShell(), dbConnection);
						dbConnUpdateOrDeleDialog.open();
						break ;
					}
				}
				this.close();
			}
		}else {
			this.close();
		}
	}
	
	
	
}
