package com.hitoo.config.projectinfor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 项目信息配置文件操作
 * @author xsh
 *
 */
@Component
public class ProjectInforBeanOperationer {
	@Autowired
	private ProjectInfor projectInfor;
	
	/**
	 * 设置项目名
	 * @param projectName 项目名
	 * @param isInit 是否是新建项目
	 */
	public void setProjectName(String projectName) {
		projectInfor.setProjectName(projectName);
	}
	/**
	 * 设置GroupId
	 * @param groupId
	 */
	public void setGroupId(String groupId) {
		projectInfor.setGroupId(groupId);
	}
}
