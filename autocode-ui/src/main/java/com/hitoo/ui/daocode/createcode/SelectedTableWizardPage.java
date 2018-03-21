package com.hitoo.ui.daocode.createcode;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import com.hitoo.config.common.CommParaBeanOperationer;
import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.db.DBHelper;
import com.hitoo.config.model.DBConnection;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.RuntimeParamter;
import com.hitoo.ui.table.SimpleTableLabelProvider;
import com.hitoo.ui.table.SimpleTableNameContentProvider;

public class SelectedTableWizardPage extends WizardPage {
	
	private CheckboxTableViewer checkboxTableViewer = null;
	
	private List<String> tables = null;
	
	public static final String[] COLUMN_NAME = {"表名"};
	
	private DBConnection dbConnection = null;

	/**
	 * Create the wizard.
	 */
	public SelectedTableWizardPage() {
		super("wizardPage");
		setTitle("选择数据表");
		setDescription("请选择要生成实体的数据表");
	}
	
	

	public SelectedTableWizardPage(CommonParameter commonParameter) {
		this();
		List<DBConnection> dbConnections = commonParameter.getDbConnections();
		for (DBConnection dbConnection : dbConnections) {
			if(dbConnection.getSelect()) {
				this.dbConnection = dbConnection;
			}
		}
	}



	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		RuntimeParamter runtimeParamter = (RuntimeParamter) ApplicationContextHelper.getBean("runtimeParamter"); 
		tables = getTablesName(runtimeParamter.getSelectedDataBase());
		
		
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
		checkboxTableViewer.setInput(tables);
		//设置全选
		checkboxTableViewer.setAllChecked(true);
	}
	/**
	 * 获取选中的数据库中的表名
	 * @return
	 */
	private List<String> getTablesName(String dbName) {
		List<String> result = new ArrayList<>();
		CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
		CommParaBeanOperationer commParaBeanOperationer = (CommParaBeanOperationer) ApplicationContextHelper.getBean("commParaBeanOperationer");
		
		DBHelper dbHelper = new DBHelper(commonParamterOperationer, commParaBeanOperationer, dbConnection);
		result = dbHelper.getTablesName(dbName);
		return result;
	}

	/**
	 * 获取选中的表格
	 * 
	 * @return
	 */
	public List<String> getSelectedTablesName() {
		// 获取所有checkbox被选中的列 返回一个Object数组
		Object[] objects = checkboxTableViewer.getCheckedElements();
		// 创建List容器
		List<String> list = new ArrayList<String>();
		// 循环Object数组
		for (Object obj : objects) {
			// 判断obj是否属于?对象
			if (obj instanceof String) {
				// 强制转换放入list中
				list.add((String) obj);
			}
		}
		return list;
	}
}