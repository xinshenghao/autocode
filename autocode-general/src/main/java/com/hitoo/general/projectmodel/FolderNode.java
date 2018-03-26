package com.hitoo.general.projectmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * 树的文件夹节点
 * @author xsh
 *
 */
public class FolderNode implements ProjectNodeElement {
	private String name;
	private String path;
	private List<ProjectNodeElement> list;
	
	public FolderNode(String name, String path) {
		this.name = name;
		this.path = path;
		list = new ArrayList<>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasChildren() {
		if(this.list.size()>0) {
			return true;
		}
		return false;
	}

	@Override
	public List<ProjectNodeElement> getChildren() {
		return this.list;
	}
	/**
	 * 增加节点
	 * @param element
	 */
	public void add(ProjectNodeElement element) {
		list.add(element);
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public void setChildren(List<ProjectNodeElement> children) {
		this.list = children;
	}

}
