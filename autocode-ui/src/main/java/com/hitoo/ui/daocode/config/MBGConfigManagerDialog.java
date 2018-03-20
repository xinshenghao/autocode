package com.hitoo.ui.daocode.config;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Point;
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
import com.hitoo.config.mbgconfig.MBGConfig;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;

public class MBGConfigManagerDialog extends Dialog {
	
	private List configsList = null;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public MBGConfigManagerDialog(Shell parentShell) {
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
		
		Group grpSd = new Group(container, SWT.NONE);
		grpSd.setText("环境配置管理");
		FormData fd_grpSd = new FormData();
		fd_grpSd.top = new FormAttachment(0, 10);
		fd_grpSd.left = new FormAttachment(0, 10);
		fd_grpSd.right = new FormAttachment(0, 590);
		grpSd.setLayoutData(fd_grpSd);
		
		Label lblNewLabel = new Label(grpSd, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 558, 50);
		lblNewLabel.setText("  MBG环境配置管理，选择新建环境配置进行环境配置的新建;选择已经存在的环境\n配置进行修改或删除操作。");
		
		configsList = new List(container, SWT.BORDER);
		fd_grpSd.bottom = new FormAttachment(configsList, -6);
		FormData fd_configsList = new FormData();
		fd_configsList.top = new FormAttachment(0, 100);
		fd_configsList.bottom = new FormAttachment(100, -4);
		fd_configsList.left = new FormAttachment(0, 10);
		fd_configsList.right = new FormAttachment(100, -8);
		configsList.setLayoutData(fd_configsList);
		//添加现有配置文件、分割线、新建环境配置选择
		CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
		java.util.List<MBGConfig> mbgConfigs = commonParameter.getMbgConfigs();
		if(mbgConfigs!= null && mbgConfigs.size()!=0) {
			for (MBGConfig mbgConfig : mbgConfigs) {
				configsList.add(mbgConfig.getPath());
			}
		}
		configsList.add("新建环境配置");
		configsList.select(0);
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
		if(buttonId == IDialogConstants.OK_ID) {
			String selected = configsList.getSelection()[0];
			CTabFolder ctabFolder = AutoCode.getTabFolder();
			if("新建环境配置".equals(selected)) {
				addTabItem(ctabFolder,null);
			}else {
				CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
				java.util.List<MBGConfig> mbgConfigs = commonParameter.getMbgConfigs();
				MBGConfig tmp = null;
				for (MBGConfig mbgConfig : mbgConfigs) {
					if(mbgConfig.getPath().equals(selected)) {
						tmp = mbgConfig;
					}
				}
				addTabItem(ctabFolder,tmp);
			}
			this.close();
		} else {
			this.close();
		}
	}
	
	/**
	 * 增加TabItem
	 * @param ctabFolder
	 * @param config 
	 */
	private void addTabItem(CTabFolder ctabFolder, MBGConfig config) {
		CTabItem createNewMBGTab = new CTabItem(ctabFolder, SWT.CLOSE);
		createNewMBGTab.setText("新建环境配置");
		MBGConfigUIView configUIView = new MBGConfigUIView(AutoCode.getTabFolder(), SWT.NONE, config);
		createNewMBGTab.setControl(configUIView);
		
		ctabFolder.setSelection(ctabFolder.getItemCount()-1);
		
		/*TabFolder tabFolder = new TabFolder(AutoCode.getTabFolder(), SWT.BOTTOM);
		tabFolder.setLayout(new FillLayout());
		TabItem two = new TabItem(tabFolder, SWT.NONE);
		two.setText("源代码");
		MBGConfigXMLView xmlView = new MBGConfigXMLView(tabFolder, SWT.NONE);
		two.setControl(xmlView);
		TabItem one = new TabItem(tabFolder, SWT.NONE);
		one.setText("设计");
		MBGConfigUIView uiView = new MBGConfigUIView(tabFolder, SWT.NONE);
		one.setControl(uiView);
		tabFolder.pack();
		
		tabFolder.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				TabItem tabItem = (TabItem) e.item;
				if(tabItem == one) {
					uiView.setDataToController(xmlView.getMBGContext());
				}else {
				 	MBGContext tmp = uiView.getDataFromController();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		createNewMBGTab.setControl(tabFolder);*/
		
	}
	
	
	
	
}
