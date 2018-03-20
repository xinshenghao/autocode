package com.hitoo.config.model;

public class DBConnection {
	private Boolean select = false;
	private String type = "mysql";
	private String connectionName = null;
	private String hostName = null ;
	private String port = null;
	private String userName = null;
	private String password = null;
	public Boolean getSelect() {
		return select;
	}
	public void setSelect(Boolean select) {
		this.select = select;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getConnectionName() {
		return connectionName;
	}
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("连接名：").append(connectionName)
			.append("，连接数据库类型").append(type)
			.append(",主机：").append(hostName)
			.append(",端口：").append(port)
			.append(",用户名").append(userName)
			.append(",密码：").append(password)
			.append("，是否被设为默认连接").append(select);
		return sb.toString();
	}
	
	
}
