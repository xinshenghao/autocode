package com.hitoo.ui.codecreatmenu;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TableColumn;

import com.hitoo.general.folderscanner.FolderScanner;
import com.hitoo.general.folderscanner.rule.NoEndWithRule;
import com.hitoo.ui.table.SimpleTableLabelProvider;
import com.hitoo.ui.table.SimpleTableNameContentProvider;

public class SelectDomainPage extends WizardPage {
	private FolderScanner scanner;
	
	private CheckboxTableViewer checkboxTableViewer = null;
	private static String[] COLUMN_NAME = {"实体名"};
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
		
		checkboxTableViewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER | SWT.FULL_SELECTION);
		//设置表头
		for(int i=0; i<COLUMN_NAME.length; i++) {
			new TableColumn(checkboxTableViewer.getTable(), SWT.LEFT).setText(COLUMN_NAME[i]);
			checkboxTableViewer.getTable().getColumn(i).pack();
		}
		//设置表头可见
		checkboxTableViewer.getTable().setHeaderVisible(true);
		//设置表格线可见
		checkboxTableViewer.getTable().setLinesVisible(true);
		//设置数据
		checkboxTableViewer.setContentProvider(new SimpleTableNameContentProvider());
		//设置视图
		checkboxTableViewer.setLabelProvider(new SimpleTableLabelProvider());
		//设置表格数据对象
		checkboxTableViewer.setInput(scanner.scan());
		//设置全选
		checkboxTableViewer.setAllChecked(true);
	}

	private void initView() {
		java.util.List<String> domains = scanner.scan();
		String[] names = new String[domains.size()];
		for(int i=0; i<domains.size(); i++) {
			String tmp = domains.get(i);
			names[i] = tmp.substring(0, tmp.length()-5);
		}
	}
	
	public String[] getSelectedDomains() {
		Object[] tmp = checkboxTableViewer.getCheckedElements();
		String[] result = new String[tmp.length];
		for(int i=0; i< tmp.length; i++) {
			String s = (String)tmp[i];
			result[i] = s.substring(0, s.length()-5);
		}
		return result;
	}
}
