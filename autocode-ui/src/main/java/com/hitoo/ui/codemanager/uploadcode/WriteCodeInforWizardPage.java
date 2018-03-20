package com.hitoo.ui.codemanager.uploadcode;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.hitoo.ui.utils.StringUtil;
import org.eclipse.swt.widgets.Combo;

public class WriteCodeInforWizardPage extends WizardPage {
	private Text title;
	private Text describe;
	
	/**
	 * Create the wizard.
	 */
	public WriteCodeInforWizardPage() {
		super("writeCodeInforWizardPage");
		setTitle("填写信息");
		setDescription("填写代码的基本信息");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		
		Label titleLabel = new Label(container, SWT.NONE);
		titleLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		titleLabel.setText("标题：");
		
		title = new Text(container, SWT.BORDER);
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label repertoryLabel = new Label(container, SWT.NONE);
		repertoryLabel.setText("仓库位置：");
		
		Group group = new Group(container, SWT.NONE);
		group.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_group = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_group.heightHint = 31;
		gd_group.widthHint = 176;
		group.setLayoutData(gd_group);
		
		Combo combo = new Combo(group, SWT.NONE);
		
		Label describeLabel = new Label(container, SWT.NONE);
		describeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		describeLabel.setText("描述：");
		
		describe = new Text(container, SWT.WRAP|SWT.V_SCROLL);
		describe.setLayoutData(new GridData(GridData.FILL_BOTH));
		setControl(container);
	}
	/**
	 * 获取描述
	 * @return
	 */
	public String getCodeDescr() {
		String result = describe.getText();
		if(StringUtil.isEmpty(result)) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误", "描述不能为空");
			return null;
		}
		return result;
	}
	/**
	 * 获取标题
	 */
	public String getCodeTitle() {
		String result = title.getText();
		if(StringUtil.isEmpty(result)) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误", "标题不能为空");
			return null;
		}
		return result;
	}
}
