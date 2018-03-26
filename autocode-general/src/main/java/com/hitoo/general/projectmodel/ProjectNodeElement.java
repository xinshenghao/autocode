package com.hitoo.general.projectmodel;

import java.util.List;

/**
 * 项目节点接口类
 * @author xsh
 *
 */
public interface ProjectNodeElement {
	/**
	 * 获取节点名称
	 * @return
	 */
	public String getName();
 
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
	public List<ProjectNodeElement> getChildren();
	/**
	 * 设置子节点
	 * @param children
	 */
	public void setChildren(List<ProjectNodeElement> children);
}
