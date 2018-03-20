package com.hitoo.ui.dbmenu.connmanager;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.List;
/**
 * 选择数据库管理系统类型引导页
 * @author xsh
 *
 */
public class SelecteDBTypeWizardPage extends WizardPage {
	List list = null;
	/**
	 * Create the wizard.
	 */
	public SelecteDBTypeWizardPage() {
		super("selectDBType");
		setTitle("选择数据库类型");
		setDescription("请选择要创建连接的数据库类型(目前只支持Mysql)");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		list = new List(container, SWT.BORDER);
		list.add("Mysql");
		list.add("Oracle");
		list.add("DB2");
		list.setSelection(0);
		setControl(container);
	}

	@Override
	public IWizardPage getNextPage() {
		String selected = list.getSelection()[0];
		if("Mysql".equals(selected)) {
			return getWizard().getPage(CreateNewDBConnWizard.CREATE_DB_CONN_WP);
		}else{
			return super.getNextPage();			
		}
	}
	
	
}
