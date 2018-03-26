package com.hitoo.config.createcode.controller;

import com.hitoo.config.FilePathBean;
import com.hitoo.config.createcode.AbsCodeCreater;

public class ControllerCodeCreater extends AbsCodeCreater{

	public ControllerCodeCreater(String templateName, String outputPath) {
		super(templateName, outputPath);
	}

	@Override
	public void createCodes(String[] domains) {
		velocityHelper.setTemplateFilePath(FilePathBean.getController_templates()+"/"+templateName);
		for (String domain : domains) {
			velocityHelper.addData("name", domain);
			velocityHelper.outputMegerFile(outputPath, domain+"Controller.java");
		}
	}

}
