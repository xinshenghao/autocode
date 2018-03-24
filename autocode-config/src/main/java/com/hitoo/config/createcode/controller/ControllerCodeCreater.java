package com.hitoo.config.createcode.controller;

import com.hitoo.config.ConfigFilePath;
import com.hitoo.config.createcode.AbsCodeCreater;

public class ControllerCodeCreater extends AbsCodeCreater{

	public ControllerCodeCreater(String templateName, String outputPath) {
		super(templateName, outputPath);
	}

	@Override
	public void createCodes(String[] domains) {
		velocityHelper.setTemplateFilePath(ConfigFilePath.CONTROLLER_TEMPLATE_PATH+"/"+templateName);
		for (String domain : domains) {
			velocityHelper.addData("name", domain);
			velocityHelper.outputMegerFile(outputPath, domain+"Controller.java");
		}
	}

}
