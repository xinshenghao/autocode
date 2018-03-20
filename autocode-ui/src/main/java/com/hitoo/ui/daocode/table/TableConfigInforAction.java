package com.hitoo.ui.daocode.table;

import org.eclipse.jface.action.Action;

import com.hitoo.config.common.CommParaBeanOperationer;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.model.TableConfig;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;

public class TableConfigInforAction extends Action {
	
	private CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
	private CommParaBeanOperationer commParaBeanOperationer = (CommParaBeanOperationer) ApplicationContextHelper.getBean("commParaBeanOperationer");
	
	private TableConfig tableConfig = null;
	
	public TableConfigInforAction(TableConfig tableConfig) {
		super("默认",Action.AS_RADIO_BUTTON);
		this.tableConfig = tableConfig;
		this.setText(tableConfig.getName());
		this.setChecked(tableConfig.isSelect());
	}
	
	@Override
	public void run() {
		commonParamterOperationer.setSelectedTableConfig(tableConfig);
		commParaBeanOperationer.setSelectedTableConfig(tableConfig);
		AutoCode.initTableConfigeMenu();
	}
}
