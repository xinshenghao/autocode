package com.hitoo.ui.projecttree.model;

import java.util.List;

/**
 * 树节点接口类
 * @author xsh
 *
 */
public interface ProjectTreeElement {
	/**
	 * 获取节点名称
	 * @return
	 */
	public String getName();
	/**
	 * 获取图标
	 * @return
	 */
	public String getIcon();
	/**
	 * 获取文件位置
	 * @return
	 */
	public String getPath();
	/**
	 * 是否有子节点
	 * @return
	 */
	public boolean hasChildren();
	/**
	 * 获取所有子节点
	 * @return
	 */
	public List<ProjectTreeElement> getChildren();
	/**
	 * 设置子节点
	 * @param children
	 */
	public void setChildren(List<ProjectTreeElement> children);
}
