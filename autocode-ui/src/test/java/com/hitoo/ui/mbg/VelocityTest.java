package com.hitoo.ui.mbg;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;

public class VelocityTest {

	@Test
	public void test() {
		//初始化参数  
        Properties properties=new Properties();  
        //设置velocity资源加载方式为file  
        properties.setProperty("resource.loader", "file");  
        //设置velocity资源加载方式为file时的处理类  
        properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");  
        //实例化一个VelocityEngine对象  
        VelocityEngine velocityEngine=new VelocityEngine(properties);  
          
        //实例化一个VelocityContext  
        VelocityContext context=new VelocityContext();  
        //向VelocityContext中放入键值  
        context.put("name", "张三");  
        //实例化一个StringWriter  
        StringWriter writer=new StringWriter();  
        //从vm目录下加载hello.vm模板,在eclipse工程中该vm目录与src目录平级  
        velocityEngine.mergeTemplate("src/main/resources/templates/service/interface/base.vm", "utf-8", context, writer);  
        System.out.println(writer.toString());  
	}

}
