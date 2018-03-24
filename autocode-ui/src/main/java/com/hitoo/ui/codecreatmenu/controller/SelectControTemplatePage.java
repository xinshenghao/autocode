package com.hitoo.ui.codecreatmenu.controller;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.model.ControllerTemplate;
import com.hitoo.config.model.ServiceTemplate;
import com.hitoo.ui.start.ApplicationContextHelper;

public class SelectControTemplatePage extends WizardPage {
	private List list;
	private java.util.List<ControllerTemplate> templates;
	/**
	 * Create the wizard.
	 */
	public SelectControTemplatePage() {
		super("selectControllerTemplatePage");
		setTitle("选择模板文件");
		setDescription("选择模板文件生成代码");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = 5;
		layout.marginRight = 5;
		layout.marginBottom = 5;
		layout.marginLeft = 5;
		container.setLayout(layout);
		
		list = new List(container, SWT.SINGLE);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		list.setLayoutData(data);
		
		setData();
	}

	private void setData() {
		CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter"); 
		templates = commonParameter.getControllerTemplates();
		if(templates != null) {
			for (ControllerTemplate template : templates) {
				list.add(template.getName());
				if(template.isSelect()) {
					list.setSelection(new String[] {template.getName()});
				}
			}
		}
	}
	
	/**
	 * 获取模板名字
	 * @return
	 */
	public String getTemplatePath() {
		int index = list.getSelectionIndex();
		return templates.get(index).getPath();
	}

}
