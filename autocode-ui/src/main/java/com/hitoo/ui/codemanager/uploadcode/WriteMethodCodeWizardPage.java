package com.hitoo.ui.codemanager.uploadcode;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.hitoo.ui.utils.StringUtil;
/**
 * 填写代码内容
 * @author xsh
 *
 */
public class WriteMethodCodeWizardPage extends WizardPage {
	private Text codeText;
	private Text mothedName;
	
	/**
	 * Create the wizard.
	 * @param selectUploadTypeWizardPage 
	 * @param selectUploadTypeWizardPage 
	 */
	public WriteMethodCodeWizardPage( ) {
		super("writeMethodCodeWizardPage");
		setTitle("填写代码");
		setDescription("填写要上传的代码");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = 5;
		layout.marginBottom = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		container.setLayout(layout);
		
		
		
		Label label = new Label(container, SWT.NONE);
		label.setText("设置代码");
		GridData gridData0 = new GridData(GridData.FILL_HORIZONTAL);
		gridData0.horizontalSpan = 2;
		new Label(container, SWT.NONE);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("方法名:");
		mothedName = new Text(container, SWT.BORDER);
		mothedName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		codeText = new Text(container, SWT.WRAP|SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		codeText.setLayoutData(gridData);
		new Label(container, SWT.NONE);
		
		setControl(container);
		new Label(container, SWT.NONE);
	}
	/**
	 * 获取方法名
	 * @return
	 */
	public String getMethodName() {
		String result = mothedName.getText();
		if(StringUtil.isEmpty(result)) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误", "方法名不能为空!!!");
			return null;
		}
		return result;
	}
	/**
	 * 获取代码
	 * @return
	 */
	public String getCodeContent() {
		String result = codeText.getText();
		if(StringUtil.isEmpty(result)) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误", "代码内容不能为空!!!");
			return null;
		}
		return result;
	}
	

}
