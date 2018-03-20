package com.hitoo.ui.daocode.createcode;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.hitoo.config.model.TableConfig;

import swing2swt.layout.BorderLayout;

public class SelectTableConfigWizardPage extends WizardPage {
	
	private List tableConfigList = null;
	private java.util.List<TableConfig> tableConfigs = null;

	public SelectTableConfigWizardPage(java.util.List<TableConfig> tableConfigs) {
		super("wizardPage");
		this.tableConfigs = tableConfigs;
		setTitle("选择表格实体生成策略");
		setDescription("选择表格实体的生成策略,表格实体生成策略将决定生成的SQL语句");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new BorderLayout(0, 0));
		
		tableConfigList = new List(container, SWT.BORDER);
		tableConfigList.setLayoutData(BorderLayout.CENTER);
		
		for(int i=0; i<tableConfigs.size(); i++) {
			TableConfig tmp = tableConfigs.get(i);
			tableConfigList.add(tmp.getName());
			if (tmp.isSelect()) {
				tableConfigList.setSelection(i);
			}
		}
	}
	/**
	 * 获取选中的TableConfig
	 * @return
	 */
	public TableConfig getSelectedTableConfig() {
		TableConfig result = null;
		String selected = tableConfigList.getSelection()[0];
		for (TableConfig tableConfig : tableConfigs) {
			if(tableConfig.getName().equals(selected)) {
				result = tableConfig;
			}
		}
		return result;
	}
}
