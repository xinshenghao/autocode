package com.hitoo.config.createcode;

/**
 * 代码生成抽象类
 * @author xsh
 *
 */
public abstract class AbsCodeCreater {
	protected String templateName;
	protected String outputPath;
	
	protected VelocityHelper velocityHelper;

	public AbsCodeCreater(String templateName, String outputPath) {
		this.templateName = templateName;
		this.outputPath = outputPath;
		this.velocityHelper = new VelocityHelper();
	}
	
	public abstract void createCodes(String[] domains);
}
