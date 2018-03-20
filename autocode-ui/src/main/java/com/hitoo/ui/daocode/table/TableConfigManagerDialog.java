package com.hitoo.ui.daocode.table;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.model.TableConfig;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;

public class TableConfigManagerDialog extends Dialog {
	
	List tableConfig = null;

	public TableConfigManagerDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());
		
		Group grpSd = new Group(container, SWT.NONE);
		grpSd.setText("实体生成策略管理");
		FormData fd_grpSd = new FormData();
		fd_grpSd.top = new FormAttachment(0, 10);
		fd_grpSd.left = new FormAttachment(0, 10);
		fd_grpSd.right = new FormAttachment(0, 590);
		grpSd.setLayoutData(fd_grpSd);
		
		Label lblNewLabel = new Label(grpSd, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 558, 46);
		lblNewLabel.setText("  MBG实体生成策略管理，选择新建实体生成策略进行实体生成策略的新建;选择已\n经存在的策略进行修改或删除操作。");
		
		tableConfig = new List(container, SWT.BORDER);
		fd_grpSd.bottom = new FormAttachment(100, -208);
		FormData fd_configsList = new FormData();
		fd_configsList.top = new FormAttachment(grpSd, 6);
		fd_configsList.bottom = new FormAttachment(100, -4);
		fd_configsList.left = new FormAttachment(0, 10);
		fd_configsList.right = new FormAttachment(100, -8);
		tableConfig.setLayoutData(fd_configsList);
		//添加现有配置文件、分割线、新建环境配置选择
		CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
		java.util.List<TableConfig> tableConfigs = commonParameter.getTableConfigs();
		if(tableConfigs!= null && tableConfigs.size()!=0) {
			for (TableConfig tmp : tableConfigs) {
				tableConfig.add(tmp.getName());
			}
		}
		tableConfig.add("新建实体生成策略");
		tableConfig.select(0);
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
		if(buttonId == IDialogConstants.CANCEL_ID) {
			this.close();
		}
		
		String selected = tableConfig.getSelection()[0];
		if("新建实体生成策略".equals(selected)) {
			TableConfigInforDialog dialog = new TableConfigInforDialog(Display.getCurrent().getActiveShell(), null);
			this.close();
			dialog.open();
		}else {
			CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
			java.util.List<TableConfig> list = commonParameter.getTableConfigs();
			TableConfig tmp = null;
			for (TableConfig tableConfig : list) {
				if(tableConfig.getName().equals(selected)) {
					tmp = tableConfig;
				}
			}
			if(null!= tmp) {
				TableConfigInforDialog dialog = new TableConfigInforDialog(Display.getCurrent().getActiveShell(), tmp);
				this.close();
				dialog.open();
			}
		}
		AutoCode.initTableConfigeMenu();
	}

}
