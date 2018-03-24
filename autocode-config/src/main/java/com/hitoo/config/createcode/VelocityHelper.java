package com.hitoo.config.createcode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityHelper {
	
	private String templateFilePath = null;
	
	private VelocityEngine ve = new VelocityEngine();
	private VelocityContext context ;
	
	
	public VelocityHelper() {
		
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		
		context = new VelocityContext();
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}
	
	public void addData(String key, Object value) {
		context.put(key, value);
	}
	
	/**
	 * 初始化
	 */
	public void init() {
		context = new VelocityContext();
		templateFilePath = null;
	}
	
	/**
	 * 输出合并数据后的模板文件
	 * @param filePath
	 */
	public void outputMegerFile(String filePath, String fileName) {
		if( null == this.templateFilePath) {
			throw new RuntimeException("请设置模板文件位置");
		}
		
		File file = new File(filePath);
		if(!file.exists() || file.isDirectory()) {
			file.mkdirs();
		}
		
		Template template = ve.getTemplate(templateFilePath);
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath+"/"+fileName);
			template.merge(context, fw);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
