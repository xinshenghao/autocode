package com.hitoo.ui.projecttree.model;

import java.util.List;

import com.hitoo.ui.projecttree.mvc.ProjectTreeLabelProvider;

public class FileNode implements ProjectTreeElement {
	private String name;
	private String path;
	private FileType type;
	
	public FileNode(String name, String path) {
		this.name = name;
		this.path = path;
		this.type = getFileType(name);
	}

	private FileType getFileType(String fileName) {
		String[] tmp = fileName.split("\\.");
		String tmpStr = tmp[tmp.length-1];
		switch (tmpStr) {
		case "java":
			return FileType.JAVA;
		case "xml":
			return FileType.XML;
		case "yml":
			return FileType.YML;
		default:
			return FileType.UNKNOW;
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public List<ProjectTreeElement> getChildren() {
		return null;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public void setChildren(List<ProjectTreeElement> children) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getIcon() {
		return ProjectTreeLabelProvider.IMG_FILE;
	}
	
	public FileType getFileType() {
		return this.type;
	}
	

}
