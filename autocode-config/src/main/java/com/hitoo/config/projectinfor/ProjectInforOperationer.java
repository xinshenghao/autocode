package com.hitoo.config.projectinfor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.utils.XmlUtil;

/**
 * 项目信息配置文件操作
 * @author xsh
 *
 */
@Component
public class ProjectInforOperationer {
	
	@Autowired
	private CommonParameter commonParameter;
	
	private String path = null;
	private XmlUtil xmlUtil = null;
	private Element root = null;
	
	public void setProjectName(String projectName) {
		path = commonParameter.getWorkSpace()+"/.ac/"+ProjectInforKey.CONFIG_FILE_NAME;		
		xmlUtil = new XmlUtil(path);
		root = xmlUtil.getRootElement();
		
		Element projectNameEle = root.element(ProjectInforKey.PROJECT_NAME);
		if(null == projectNameEle) {
			projectNameEle = root.addElement(ProjectInforKey.PROJECT_NAME);
			projectNameEle.setText(projectName);
		}
		
		writeToFile();
	}
	
	public void setGroupId(String groupId) {
		Element groupIdEle = root.element(ProjectInforKey.GROUP_ID);
		if(null == groupIdEle) {
			groupIdEle = root.addElement(ProjectInforKey.GROUP_ID);
			groupIdEle.setText(groupId);
		}
		
		writeToFile();
	}
	
	public void writeToFile() {
		FileOutputStream out;
		try {
			out = new FileOutputStream(path);
			xmlUtil.writeXml(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
