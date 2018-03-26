package com.hitoo.general.projectmodel;

import java.util.List;

public class FileNode implements ProjectNodeElement {
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
	public List<ProjectNodeElement> getChildren() {
		return null;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public void setChildren(List<ProjectNodeElement> children) {
		// TODO Auto-generated method stub
	}
	
	public FileType getFileType() {
		return this.type;
	}
	

}
