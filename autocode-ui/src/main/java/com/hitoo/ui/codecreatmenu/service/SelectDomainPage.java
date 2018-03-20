package com.hitoo.ui.codecreatmenu.service;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.hitoo.general.folderscanner.FolderScanner;
import com.hitoo.general.folderscanner.rule.NoEndWithRule;

import org.eclipse.swt.layout.FillLayout;

public class SelectDomainPage extends WizardPage {
	private FolderScanner scanner;
	private List list;
	/**
	 * Create the wizard.
	 */
	public SelectDomainPage() {
		super("selectDomainPage");
		setTitle("选择实体");
		setDescription("选择要生成Service层代码的实体类");
		scanner = new FolderScanner("/home/xsh/autoCode/demo/src/main/java/com/example/demo/mybatis/domain");
		scanner.addRule(new NoEndWithRule("Example.java"));
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		list = new org.eclipse.swt.widgets.List(container, SWT.BORDER);
		
		initView();
	}

	private void initView() {
		java.util.List<String> domains = scanner.scan();
		String[] names = new String[domains.size()];
		for(int i=0; i<domains.size(); i++) {
			String tmp = domains.get(i);
			names[i] = tmp.substring(0, tmp.length()-5);
		}
		list.setItems(names);
	}

}
