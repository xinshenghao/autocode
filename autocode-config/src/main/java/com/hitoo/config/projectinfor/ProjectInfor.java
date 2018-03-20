package com.hitoo.config.projectinfor;

import java.io.File;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.utils.XmlUtil;

/**
 * 项目的信息
 * @author xsh
 *
 */
@Component
public class ProjectInfor {
	
	@Autowired
	private CommonParameter commonParameter ;
	
	XmlUtil xmlUtil = null;
	/**
	 * 组织
	 */
	private String groupId;
	/**
	 * 项目名
	 */
	private String projectName;
	
	public ProjectInfor() {
	}
	
	/**
	 * 将配置文件转换为bean
	 */
	public void xml2Bean() {
		String workSpace = commonParameter.getWorkSpace();
		File file = new File(workSpace+"/.ac/"+ProjectInforKey.CONFIG_FILE_NAME);
		if(file.exists()) {
			xmlUtil = new XmlUtil(workSpace+"/.ac/"+ProjectInforKey.CONFIG_FILE_NAME);
			Element root = xmlUtil.getRootElement();
			this.setProjectName(root.elementText(ProjectInforKey.PROJECT_NAME));			
			this.setGroupId(root.elementText(ProjectInforKey.GROUP_ID));
		}
	}



	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
