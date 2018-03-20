package com.hitoo.ui.dbmenu.opendb;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.hitoo.config.common.CommParaBeanOperationer;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.db.DBHelper;
import com.hitoo.config.model.DBConnection;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.RuntimeParamter;
/**
 * 打开数据库（选择数据库管理系统中的数据库）
 * @author xsh
 */
public class OpenDBDialog extends Dialog {
	private List dateBases = null;
	
	private DBHelper dbHelper = null;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public OpenDBDialog(Shell parentShell) {
		super(parentShell);
	}
	
	public OpenDBDialog(Shell parentShell,DBConnection dbConnection) {
		super(parentShell);
		CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
		CommParaBeanOperationer commParaBeanOperationer = (CommParaBeanOperationer) ApplicationContextHelper.getBean("commParaBeanOperationer");
		dbHelper = new DBHelper(commonParamterOperationer, commParaBeanOperationer, dbConnection);
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		java.util.List<String> dateBaseName = dbHelper.getDataBasesName();
		
		dateBases = new List(container, SWT.BORDER|SWT.SINGLE);
		for (String dbName : dateBaseName) {
			dateBases.add(dbName);
		}
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
		return new Point(450, 300);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if(buttonId == IDialogConstants.OK_ID) {
			String selectedDBName = dateBases.getSelection()[0];
			RuntimeParamter runtimeParamter = (RuntimeParamter) ApplicationContextHelper.getBean("runtimeParamter");
			runtimeParamter.setSelectedDataBase(selectedDBName);
			this.close();
		}
	}
	
	

}
