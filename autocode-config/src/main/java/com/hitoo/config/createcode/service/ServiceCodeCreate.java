package com.hitoo.config.createcode.service;

import com.hitoo.config.ConfigFilePath;
import com.hitoo.config.createcode.AbsCodeCreater;

public class ServiceCodeCreate extends AbsCodeCreater {

	private boolean isCreateInterface;

	public ServiceCodeCreate(String templateName, String outputPath, boolean isCreateInterface) {
		super(templateName, outputPath);
		this.isCreateInterface = isCreateInterface;
	}
	
	@Override
	public void createCodes(String[] domains) {
		super.createCodes(domains);
		if(isCreateInterface) {
			createInterfaceCode(domains);
		}
	}
	
	/**
	 * 创建接口文件
	 * @param domains
	 */
	private void createInterfaceCode(String[] domains) {
		setInterfacePath();
		for (String str : domains) {
			createCode(str);
		}
	}

	/**
	 * 设置接口模板文件路径
	 */
	private void setInterfacePath() {
		velocityHelper.setTemplateFilePath(ConfigFilePath.SERVICE_TEMPLATE_PATH+"/interface/"+templateName);
	}

	@Override
	public void createCode(String domain) {
		velocityHelper.addData("name", domain);
		velocityHelper.outputMegerFile(outputPath+"/"+domain+"ServiceImpl.java");
	}

	@Override
	public void setTemplatePath() {
		velocityHelper.setTemplateFilePath(ConfigFilePath.SERVICE_TEMPLATE_PATH+"/impl/"+templateName);
	}

}
