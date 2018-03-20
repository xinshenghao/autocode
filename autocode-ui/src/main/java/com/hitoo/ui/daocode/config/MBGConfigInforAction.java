package com.hitoo.ui.daocode.config;

import org.eclipse.jface.action.Action;

import com.hitoo.config.common.CommParaBeanOperationer;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.mbgconfig.MBGConfig;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;

/**
 * MBG配置信息二级菜单
 * 用于显示已有的配置文件信息
 * @author xsh
 *
 */
public class MBGConfigInforAction extends Action{
	
	private MBGConfig mbgConfig = null;
	CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
	CommParaBeanOperationer commParaBeanOperationer = (CommParaBeanOperationer) ApplicationContextHelper.getBean("commParaBeanOperationer");

	public MBGConfigInforAction() {
		
	}

	public MBGConfigInforAction(MBGConfig mbgConfig) {
		super(mbgConfig.getPath(),Action.AS_RADIO_BUTTON);
		this.mbgConfig = mbgConfig;
		this.setChecked(mbgConfig.getSelect());
	}
	
	@Override
	public void run() {
		commonParamterOperationer.setSelectedMBGConfig(mbgConfig);
		commParaBeanOperationer.setSelectedMBGConfig(mbgConfig);
		AutoCode.initCreateDaoCodeMenu();
	}
	
	
}
