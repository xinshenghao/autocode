package com.hitoo.ui.daocode.createcode;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.hitoo.config.mbgconfig.MBGConfig;

public class SelectMBGConfigWizardPage extends WizardPage {
	
	private List mbgConfigList = null;
	private java.util.List<MBGConfig> mbgConfigs = null;  
	
	/**
	 * Create the wizard.
	 */
	public SelectMBGConfigWizardPage(java.util.List<MBGConfig> mbgConfigs) {
		super("选择配置文件");
		this.mbgConfigs = mbgConfigs;
		setTitle("选择配置文件");
		setDescription("选择Dao配置文件");
	}
	
	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		mbgConfigList = new List(container, SWT.BORDER|SWT.SINGLE);
		for(int i=0; i<mbgConfigs.size(); i++) {
			MBGConfig tmpMbgConfig = mbgConfigs.get(i);
			mbgConfigList.add(tmpMbgConfig.getPath());
			if(tmpMbgConfig.getSelect()) {
				mbgConfigList.setSelection(i);
			}
		}
	}
	/**
	 * 获取选中的MBGConfig
	 * @return
	 */
	public MBGConfig getSelectedMBGConfig() {
		MBGConfig result = null;
		String selected = mbgConfigList.getSelection()[0];
		for (MBGConfig mbgConfig : mbgConfigs) {
			if(selected.equals(mbgConfig.getPath())) {
				result = mbgConfig;
			}
		}
		return result;
	}

}
