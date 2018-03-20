package com.hitoo.config.pom;

import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.hitoo.config.utils.XmlUtil;

@Component
public class Pom {
	
	private XmlUtil xmlUtil = null;
	private Element root = null;
	private String filePath = null;
	
	private String groupId;
	private String artifactId;
	
	public void init(String filePath) {
		this.filePath = filePath;
		xmlUtil = new XmlUtil(filePath);
		root = xmlUtil.getRootElement();
		
		this.groupId = root.elementText(PomFileKey.GROUP_ID);
		this.groupId = root.elementText(PomFileKey.ARTIFACTID);
	}
	
	
	
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	
	
	
}
