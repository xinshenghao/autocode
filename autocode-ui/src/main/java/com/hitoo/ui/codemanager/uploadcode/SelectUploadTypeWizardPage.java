package com.hitoo.ui.codemanager.uploadcode;


import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
/**
 * 选择上传代码类型（类代码、方法代码）
 * @author xsh
 *
 */
public class SelectUploadTypeWizardPage extends WizardPage {
	
	private Button classCode = null;
	private Button methodCode = null;
	
	public SelectUploadTypeWizardPage() {
		super("selectUploadTypeWizardPage");
		setTitle("选择代码类型");
		setDescription("选择要上传的代码的类型");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		container.setLayout(new FormLayout());
		
		Group group = new Group(container, SWT.NONE);
		group.setLayout(new FillLayout(SWT.VERTICAL));
		FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(0, 94);
		fd_group.right = new FormAttachment(0, 580);
		fd_group.top = new FormAttachment(0, 32);
		fd_group.left = new FormAttachment(0, 10);
		group.setLayoutData(fd_group);
		
		classCode = new Button(group, SWT.RADIO);
		classCode.setText("类代码");
		classCode.setSelection(true);
		
		methodCode = new Button(group, SWT.RADIO);
		methodCode.setText("方法代码");
		setControl(container);
	}
	/**
	 * 是否是新增类代码
	 * @return
	 */
	public boolean isAddClassCode() {
		return classCode.getSelection();
	}

	@Override
	public IWizardPage getNextPage() {
		if(isAddClassCode()) {
			return super.getNextPage();			
		}
		return getWizard().getPage(UploadCodeWizard.WRITEMETHODCODEWIZARDPAGE);
	}
	
	
	
}
