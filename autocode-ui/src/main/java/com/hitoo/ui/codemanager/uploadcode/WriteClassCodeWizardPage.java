package com.hitoo.ui.codemanager.uploadcode;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.ui.start.ApplicationContextHelper;
/**
 * 填写类代码内容
 * @author xsh
 *
 */
public class WriteClassCodeWizardPage extends WizardPage {
	private Text filePath;
	private Button selectFile = null;
	private Button writeCode = null;
	private Button selectFileBtn = null;
	private Text codeText;
	
	SelectionAdapter selectJavaFile = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
			dialog.setFilterExtensions(new String[] {"*.java"});
			CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
			String workSpace = commonParameter.getWorkSpace();
			dialog.setFilterPath(workSpace);
			String file = dialog.open();
		}
	};
	/**
	 * Create the wizard.
	 * @param selectUploadTypeWizardPage 
	 * @param selectUploadTypeWizardPage 
	 */
	public WriteClassCodeWizardPage( ) {
		super("writeClassCodeWizardPage");
		setTitle("填写代码");
		setDescription("填写要上传的代码");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.marginTop = 5;
		layout.marginBottom = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		container.setLayout(layout);
		
		selectFile = new Button(container, SWT.RADIO);
		selectFile.setSelection(true);
		selectFile.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				codeText.setEnabled(false);
				filePath.setEnabled(true);
				selectFileBtn.setEnabled(true);
			}
			
		});
		
		filePath = new Text(container, SWT.BORDER);
		filePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		selectFileBtn = new Button(container, SWT.NONE);
		selectFileBtn.setText("选择文件");
		selectFileBtn.addSelectionListener(selectJavaFile);
		
		
		writeCode = new Button(container, SWT.RADIO);
		writeCode.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				codeText.setEnabled(true);
				filePath.setEnabled(false);
				selectFileBtn.setEnabled(false);
			}
			
		});
		
		Label label = new Label(container, SWT.NONE);
		label.setText("设置代码");
		GridData gridData0 = new GridData(GridData.FILL_HORIZONTAL);
		gridData0.horizontalSpan = 2;
		new Label(container, SWT.NONE);
		
		
		new Label(container, SWT.NONE);
		
		codeText = new Text(container, SWT.WRAP|SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		codeText.setLayoutData(gridData);
		new Label(container, SWT.NONE);
		
		codeText.setEnabled(false);
		
		setControl(container);
	}

	@Override
	public IWizardPage getNextPage() {
		return getWizard().getPage(UploadCodeWizard.WRITECODEINFORWIZARDPAGE);
	}
	

}
