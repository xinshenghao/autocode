package com.hitoo.config.createcode.service;

import com.hitoo.config.ConfigFilePath;
import com.hitoo.config.createcode.AbsCodeCreater;

public class ServiceCodeCreater extends AbsCodeCreater {

	/**
	 * 生成Service层代码
	 * @param templateName
	 * @param outputPath 代码生成位置
	 */
	public ServiceCodeCreater(String templateName, String outputPath) {
		super(templateName, outputPath);
	}
	
	@Override
	public void createCodes(String[] domains) {
		//生成接口类文件
		createInterfaceCode(domains);
		//生成实现类文件
		createImplCode(domains);
	}

	private void createImplCode(String[] domains) {
		velocityHelper.setTemplateFilePath(ConfigFilePath.SERVICE_TEMPLATE_PATH+"/impl/"+templateName);
		for (String domain : domains) {
			velocityHelper.addData("name", domain);
			velocityHelper.outputMegerFile(outputPath+"/impl", domain+"ServiceImpl.java");
		}
	}

	private void createInterfaceCode(String[] domains) {
		velocityHelper.setTemplateFilePath(ConfigFilePath.SERVICE_TEMPLATE_PATH+"/interface/"+templateName);
		for (String domain : domains) {
			velocityHelper.addData("name", domain);
			velocityHelper.outputMegerFile(outputPath, domain+"Service.java");
		}
	}

}
