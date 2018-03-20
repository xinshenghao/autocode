package com.hitoo.ui.dbmenu.connmanager;

import org.eclipse.jface.action.Action;

import com.hitoo.config.common.CommParaBeanOperationer;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.model.DBConnection;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;
/**
 * 数据库连接信息二级菜单
 * 用于显示已有的数据库连接信息
 * @author xsh
 *
 */
public class ConnDBInforAction extends Action {
	private DBConnection dbConnection = null;
	
	CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
	CommParaBeanOperationer commParaBeanOperationer = (CommParaBeanOperationer) ApplicationContextHelper.getBean("commParaBeanOperationer");
	
	public ConnDBInforAction(DBConnection dbConnection) {
		super(dbConnection.getConnectionName(),Action.AS_RADIO_BUTTON);
		this.dbConnection = dbConnection;
		this.setChecked(dbConnection.getSelect());
	}
	
	
	@Override
	public void run() {
		commonParamterOperationer.setSelectedDBConnection(dbConnection);
		commParaBeanOperationer.setSelectedDBConnection(dbConnection);
		AutoCode.initConnDBMenu();
	}
	
}
