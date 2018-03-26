package com.hitoo.ui.filemenu.createproject;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import com.hitoo.general.utils.BrowseUtil;

public class importZipFileWizardPage extends WizardPage {
	private Text filePathText;
	private Button selectFileBtn;
	
	private String filePath;

	/**
	 * Create the wizard.
	 */
	public importZipFileWizardPage() {
		super("importZipFileWizardPage");
		setTitle("导入文件");
		setDescription("导入文件");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		
		createTitle(container);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 49, 81, 30);
		lblNewLabel.setText("导入:");
		
		filePathText = new Text(container, SWT.BORDER);
		filePathText.setText("");
		filePathText.setBounds(71, 49, 391, 30);
		
		selectFileBtn = new Button(container, SWT.NONE);
		selectFileBtn.setBounds(476, 49, 104, 30);
		selectFileBtn.setText("选择文件");
		
		selectFileBtn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
				dialog.setFilterPath("java.home");
				dialog.setFilterExtensions(new String[] {"*.zip"});
				dialog.setFilterNames(new String[] {"压缩文件(*.zip)"});
				filePath = dialog.open();
				if(null != filePath) {
					filePathText.setText(filePath);
				}
			}
			
		});
	}
	/**
	 * 获取选中的文件的路径
	 * @return
	 */
	public String getSelectedFilePath() {
		return filePathText.getText();
	}
	

	private void createTitle(Composite container) {
		Link link = new Link(container, SWT.NONE);
		link.setBounds(10, 10, 570, 33);
		link.setText("点此<a>构建项目</a>");
		link.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String site = "http://start.spring.io/";
				try {
					BrowseUtil.browse(site);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
