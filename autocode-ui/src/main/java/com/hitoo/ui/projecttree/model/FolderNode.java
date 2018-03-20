package com.hitoo.ui.projecttree.model;

import java.util.ArrayList;
import java.util.List;

import com.hitoo.ui.projecttree.mvc.ProjectTreeLabelProvider;
/**
 * 树的文件夹节点
 * @author xsh
 *
 */
public class FolderNode implements ProjectTreeElement {
	private String name;
	private String path;
	private List<ProjectTreeElement> list;
	
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
	public List<ProjectTreeElement> getChildren() {
		return this.list;
	}
	/**
	 * 增加节点
	 * @param element
	 */
	public void add(ProjectTreeElement element) {
		list.add(element);
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public void setChildren(List<ProjectTreeElement> children) {
		this.list = children;
	}

	@Override
	public String getIcon() {
		return ProjectTreeLabelProvider.IMG_FOLDER;
	}

}
