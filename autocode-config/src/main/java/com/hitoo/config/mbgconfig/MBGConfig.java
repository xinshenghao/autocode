package com.hitoo.config.mbgconfig;
/**
 * 通用配置文件中的mbgconfig节点类
 * @author xsh
 *
 */
public class MBGConfig {
	private String path;
	private Boolean select;
	private Boolean canDelete;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Boolean getSelect() {
		return select;
	}
	public void setSelect(Boolean select) {
		this.select = select;
	}
	public Boolean getCanDelete() {
		return canDelete;
	}
	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}
}
