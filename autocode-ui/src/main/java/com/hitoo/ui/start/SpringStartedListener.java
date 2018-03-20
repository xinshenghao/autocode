package com.hitoo.ui.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.pom.Pom;
import com.hitoo.config.projectconfig.SpringProjectConfigFileOperationer;
import com.hitoo.config.projectinfor.ProjectInfor;
import com.hitoo.ui.utils.PropertiesUtil;

public class SpringStartedListener{

	@Autowired
	private CommonParameter commonParameter;
	@Autowired
	private  ProjectInfor projectInfor;
	@Autowired
	private Pom pom;
	@Autowired
	private SpringProjectConfigFileOperationer springProjectConfigFileOperationer;
	
	public void init( ) {
		System.out.println("===============容器启动成功=================");
		//初始化项目信息
		projectInfor.xml2Bean();
		
		if(null != projectInfor.getProjectName()) {			
			//初始化项目的pom文件信息
			String pomFilePath = commonParameter.getWorkSpace()+"/"+projectInfor.getProjectName()+"/pom.xml";
			pom.init(pomFilePath);
			
			//初始化项目配合文件信息（Spring boot 的applicationContext.properties）
			String configFilePath = commonParameter.getWorkSpace()+"/"+projectInfor.getProjectName()+"/src/main/resources/application.properties";
			springProjectConfigFileOperationer.init(configFilePath);
		}
		System.out.println("===============容器初始化完成================");
	}

}
