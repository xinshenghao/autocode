package com.hitoo.ui.daocode.table;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.hitoo.config.common.CommParaBeanOperationer;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.model.TableConfig;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.utils.StringUtil;
import com.hitoo.ui.widget.MessageUtil;
/**
 * 表格生成策略内容
 * @author xsh
 *
 */
public class TableConfigInforDialog extends Dialog {
	
	Text name;
	
	Button enableInsertT,enableInsertF,enableSelectByPrimaryKeyT,enableSelectByPrimaryKeyF;
	Button enableSelectByExampleT,enableSelectByExampleF,enableUpdateByPrimaryKeyT,enableUpdateByPrimaryKeyF;
	Button enableDeleteByPrimaryKeyT,enableDeleteByPrimaryKeyF,enableDeleteByExampleT,enableDeleteByExampleF;
	Button enableCountByExampleT,enableCountByExampleF,enableUpdateByExampleT,enableUpdateByExampleF;
	
	private CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
	private CommParaBeanOperationer commParaBeanOperationer = (CommParaBeanOperationer) ApplicationContextHelper.getBean("commParaBeanOperationer");
	
	TableConfig tableConfig = null;
	
	private static final int DELETE_ID = 0x110;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public TableConfigInforDialog(Shell parentShell, TableConfig tableConfig) {
		super(parentShell);
		this.tableConfig = tableConfig;
	}

	/* 
     5，enableInsert（默认true）：指定是否生成insert语句；
     6，enableSelectByPrimaryKey（默认true）：指定是否生成按照主键查询对象的语句（就是getById或get）；
     7，enableSelectByExample（默认true）：MyBatis3Simple为false，指定是否生成动态查询语句；
     8，enableUpdateByPrimaryKey（默认true）：指定是否生成按照主键修改对象的语句（即update)；
     9，enableDeleteByPrimaryKey（默认true）：指定是否生成按照主键删除对象的语句（即delete）；
     10，enableDeleteByExample（默认true）：MyBatis3Simple为false，指定是否生成动态删除语句；
     11，enableCountByExample（默认true）：MyBatis3Simple为false，指定是否生成动态查询总条数语句（用于分页的总条数查询）；
     12，enableUpdateByExample（默认true）：MyBatis3Simple为false，指定是否生成动态修改语句（只修改对象中不为空的属性）；
	 */

	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(2,false);
		gridLayout.marginTop = 10;
		gridLayout.marginLeft = 10;
		container.setLayout(gridLayout);
		
		Group group = new Group(container, SWT.BORDER);
		group.setText("设置");
		group.setLayout(new FillLayout());
		Label label0 = new Label(group, SWT.NONE);
		label0.setText("\n　　设置实体的生成策略\n");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);
		
		
		Label label1 = new Label(container, SWT.NONE);
		label1.setText("名称:");
		name = new Text(container, SWT.NONE);
		name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label label2 = new Label(container, SWT.NONE);
		label2.setText("enableInsert:");
		Group group2 = new Group(container, SWT.NONE);
		group2.setLayout(new RowLayout(SWT.HORIZONTAL));
		enableInsertT = new Button(group2, SWT.RADIO);
		enableInsertT.setText("true");
		enableInsertT.setSelection(true);
		enableInsertF = new Button(group2, SWT.RADIO);
		enableInsertF.setText("false");
		
		Label label3 = new Label(container, SWT.NONE);
		label3.setText("enableSelectByPrimaryKey:");
		Group group3 = new Group(container, SWT.NONE);
		group3.setLayout(new RowLayout(SWT.HORIZONTAL));
		enableSelectByPrimaryKeyT = new Button(group3, SWT.RADIO);
		enableSelectByPrimaryKeyT.setText("true");
		enableSelectByPrimaryKeyT.setSelection(true);
		enableSelectByPrimaryKeyF = new Button(group3, SWT.RADIO);
		enableSelectByPrimaryKeyF.setText("false");
		
		Label label4 = new Label(container, SWT.NONE);
		label4.setText("enableSelectByExample:");
		Group group4 = new Group(container, SWT.NONE);
		group4.setLayout(new RowLayout(SWT.HORIZONTAL));
		enableSelectByExampleT = new Button(group4, SWT.RADIO);
		enableSelectByExampleT.setText("true");
		enableSelectByExampleT.setSelection(true);
		enableSelectByExampleF = new Button(group4, SWT.RADIO);
		enableSelectByExampleF.setText("false");
		
		Label label5 = new Label(container, SWT.NONE);
		label5.setText("enableUpdateByPrimaryKey:");
		Group group5 = new Group(container, SWT.NONE);
		group5.setLayout(new RowLayout(SWT.HORIZONTAL));
		enableUpdateByPrimaryKeyT = new Button(group5, SWT.RADIO);
		enableUpdateByPrimaryKeyT.setText("true");
		enableUpdateByPrimaryKeyT.setSelection(true);
		enableUpdateByPrimaryKeyF = new Button(group5, SWT.RADIO);
		enableUpdateByPrimaryKeyF.setText("false");
		
		Label label6 = new Label(container, SWT.NONE);
		label6.setText("enableDeleteByPrimaryKey:");
		Group group6 = new Group(container, SWT.NONE);
		group6.setLayout(new RowLayout(SWT.HORIZONTAL));
		enableDeleteByPrimaryKeyT = new Button(group6, SWT.RADIO);
		enableDeleteByPrimaryKeyT.setText("true");
		enableDeleteByPrimaryKeyT.setSelection(true);
		enableDeleteByPrimaryKeyF = new Button(group6, SWT.RADIO);
		enableDeleteByPrimaryKeyF.setText("false");
		
		Label label7 = new Label(container, SWT.NONE);
		label7.setText("enableDeleteByExample:");
		Group group7 = new Group(container, SWT.NONE);
		group7.setLayout(new RowLayout(SWT.HORIZONTAL));
		enableDeleteByExampleT = new Button(group7, SWT.RADIO);
		enableDeleteByExampleT.setText("true");
		enableDeleteByExampleT.setSelection(true);
		enableDeleteByExampleF = new Button(group7, SWT.RADIO);
		enableDeleteByExampleF.setText("false");
		
		Label label8 = new Label(container, SWT.NONE);
		label8.setText("enableCountByExample:");
		Group group8 = new Group(container, SWT.NONE);
		group8.setLayout(new RowLayout(SWT.HORIZONTAL));
		enableCountByExampleT = new Button(group8, SWT.RADIO);
		enableCountByExampleT.setText("true");
		enableCountByExampleT.setSelection(true);
		enableCountByExampleF = new Button(group8, SWT.RADIO);
		enableCountByExampleF.setText("false");
		
		Label label9 = new Label(container, SWT.NONE);
		label9.setText("enableUpdateByExample:");
		Group group9 = new Group(container, SWT.NONE);
		group9.setLayout(new RowLayout(SWT.HORIZONTAL));
		enableUpdateByExampleT = new Button(group9, SWT.RADIO);
		enableUpdateByExampleT.setText("true");
		enableUpdateByExampleT.setSelection(true);
		enableUpdateByExampleF = new Button(group9, SWT.RADIO);
		enableUpdateByExampleF.setText("false");
		if(tableConfig!=null) {
			setData(tableConfig);			
		}
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
		if(tableConfig!=null) {
			createButton(parent, DELETE_ID, "删除", false);
		}
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(480, 500);
	}
	
	private void setData(TableConfig tableConfig) {
		name.setText(tableConfig.getName());
		enableInsertT.setSelection(tableConfig.isInsertStatementEnabled());
		enableInsertF.setSelection(!tableConfig.isInsertStatementEnabled());
		enableSelectByPrimaryKeyT.setSelection(tableConfig.isSelectByPrimaryKeyStatementEnabled());
		enableSelectByPrimaryKeyF.setSelection(!tableConfig.isSelectByPrimaryKeyStatementEnabled());
		enableSelectByExampleT.setSelection(tableConfig.isSelectByExampleStatementEnabled());
		enableSelectByExampleF.setSelection(!tableConfig.isSelectByExampleStatementEnabled());
		enableUpdateByPrimaryKeyT.setSelection(tableConfig.isUpdateByPrimaryKeyStatementEnabled());
		enableUpdateByPrimaryKeyF.setSelection(!tableConfig.isUpdateByPrimaryKeyStatementEnabled());
		enableDeleteByPrimaryKeyT.setSelection(tableConfig.isDeleteByPrimaryKeyStatementEnabled());
		enableDeleteByPrimaryKeyF.setSelection(!tableConfig.isDeleteByPrimaryKeyStatementEnabled());
		enableDeleteByExampleT.setSelection(tableConfig.isDeleteByExampleStatementEnabled());
		enableDeleteByExampleF.setSelection(!tableConfig.isDeleteByExampleStatementEnabled());
		enableCountByExampleT.setSelection(tableConfig.isCountByExampleStatementEnabled());
		enableCountByExampleF.setSelection(!tableConfig.isCountByExampleStatementEnabled());
		enableUpdateByExampleT.setSelection(tableConfig.isUpdateByExampleStatementEnabled());
		enableUpdateByExampleF.setSelection(!tableConfig.isUpdateByExampleStatementEnabled());
	}
	
	private TableConfig getData() {
		TableConfig result = new TableConfig();
		result.setName(name.getText());
		result.setSelect(true);
		result.setInsertStatementEnabled(enableInsertT.getSelection()?true:false);
		result.setSelectByPrimaryKeyStatementEnabled(enableSelectByPrimaryKeyT.getSelection()?true:false);
		result.setSelectByExampleStatementEnabled(enableSelectByExampleT.getSelection()?true:false);
		result.setUpdateByPrimaryKeyStatementEnabled(enableUpdateByPrimaryKeyT.getSelection()?true:false);
		result.setDeleteByPrimaryKeyStatementEnabled(enableDeleteByPrimaryKeyT.getSelection()?true:false);
		result.setDeleteByExampleStatementEnabled(enableDeleteByExampleT.getSelection()?true:false);
		result.setCountByExampleStatementEnabled(enableCountByExampleT.getSelection()?true:false);
		result.setUpdateByExampleStatementEnabled(enableUpdateByExampleT.getSelection()?true:false);
		return result;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		TableConfig config = getData();
		if(buttonId == IDialogConstants.OK_ID) {
			if(StringUtil.isEmpty(config.getName())) {
				MessageUtil.error(getParentShell(), "名称不能为空");
				return ;
			}
			
			if(null != tableConfig) {
				commonParamterOperationer.deleteTableConfig(config);
				commParaBeanOperationer.deleteTableConfig(config);
			}
			commonParamterOperationer.addTableConfig(config);
			commParaBeanOperationer.addTableConfig(config);
		}else if(buttonId == DELETE_ID){
			boolean b = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "警告", "确认删除?");
			if(b) {
				commonParamterOperationer.deleteTableConfig(config);
				commParaBeanOperationer.deleteTableConfig(config);				
			}
		}
		this.close();
	}
	
}
