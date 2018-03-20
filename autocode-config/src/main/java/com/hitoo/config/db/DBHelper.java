package com.hitoo.config.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hitoo.config.common.CommParaBeanOperationer;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.model.DBConnection;
import com.mysql.jdbc.PreparedStatement;

import jdk.nashorn.tools.Shell;

/**
 * 所有关于数据库的操作通过该对象来进行操作
 * @author xsh
 */
public class DBHelper {
	private static String MYSQL_DRIVE = "com.mysql.jdbc.Driver";
	
	private DBConnection dbConnection;
	private Connection connection = null;
	
	private CommonParamterOperationer commonParamterOperationer ;
	private CommParaBeanOperationer commParaBeanOperationer;
	
	public DBHelper(CommonParamterOperationer commonParamterOperationer, CommParaBeanOperationer commParaBeanOperationer) {
		this.commonParamterOperationer = commonParamterOperationer;
		this.commParaBeanOperationer = commParaBeanOperationer;
	}
	
	public DBHelper(CommonParamterOperationer commonParamterOperationer, CommParaBeanOperationer commParaBeanOperationer, DBConnection dbConnection) {
		this(commonParamterOperationer, commParaBeanOperationer);
		this.dbConnection = dbConnection;
	}


	/**
	 * 是否可以连接到数据库系统
	 * @param isShowMessage 成功连接后是否显示连接成功弹出框
	 * @param shell
	 * @return
	 */
	public String canConnToDBSys() {
		String url = "jdbc:mysql://"+dbConnection.getHostName()+":"+dbConnection.getPort();
		Connection connection =null;
		try {
			Class.forName(MYSQL_DRIVE); //classLoader,加载对应驱动
			connection = (Connection) DriverManager.getConnection(url, dbConnection.getUserName(), dbConnection.getPassword());
			return null;
		} catch (ClassNotFoundException | SQLException e) {
			//e.printStackTrace();
			//连接失败
			String errorInfo = e.getMessage();
			return errorInfo;
		} finally {
			if(null != connection) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		}
	}
	

	public DBConnection getDbConnection() {
		return dbConnection;
	}


	public void setDbConnection(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
	private Connection getDBSystemConnection(){
		String url = "jdbc:mysql://"+dbConnection.getHostName()+":"+dbConnection.getPort();
		try {
			Class.forName(MYSQL_DRIVE); //classLoader,加载对应驱动
			this.connection = (Connection) DriverManager.getConnection(url, dbConnection.getUserName(), dbConnection.getPassword());
			return this.connection;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Connection getDBConnection(String dbName) {
		String url = "jdbc:mysql://"+dbConnection.getHostName()+":"+dbConnection.getPort()+"/"+dbName;
		try {
			Class.forName(MYSQL_DRIVE); //classLoader,加载对应驱动
			this.connection = (Connection) DriverManager.getConnection(url, dbConnection.getUserName(), dbConnection.getPassword());
			return this.connection;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	/**
	 * 保存连接信息
	 */
	public void saveConnectionInfor() {
		dbConnection = commParaBeanOperationer.addDBConntion(dbConnection);
		//写入配置文件
		commonParamterOperationer.addDBConnInfor(dbConnection);
		commonParamterOperationer.writeToFile();
	}
	/**
	 * 删除连接信息
	 */
	public void deleteDBConnection() {
		commParaBeanOperationer.deleteDBConnection(dbConnection);
		commonParamterOperationer.deleteDBConnection(dbConnection);
	}
	/**
	 * 修改连接信息
	 * @param oldConnection
	 */
	public void updateDBConnection(DBConnection oldConnection) {
		commParaBeanOperationer.deleteDBConnection(oldConnection);
		commParaBeanOperationer.addDBConntion(dbConnection);
		commonParamterOperationer.updateDBConn(oldConnection, dbConnection);
	}
	/**
	 * 获取指定数据库系统中的数据库名
	 * @return
	 */
	public List<String> getDataBasesName() {
		List<String> result = new ArrayList<>();
		String defaultName0 = "information_schema";
		String defaultName1 = "mysql";
		String defaultName2 = "performance_schema";
		String defaultName3 = "sys";
		
		
		Connection conn = getDBSystemConnection();
		String sql = "SELECT `SCHEMA_NAME` FROM `information_schema`.`SCHEMATA`";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement)conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String dataBaseName = rs.getString(1);
				if(dataBaseName.equals(defaultName0) 
						|| dataBaseName.equals(defaultName1) 
						|| dataBaseName.equals(defaultName2) 
						|| dataBaseName.equals(defaultName3)) {
					continue ;
				}
				result.add(dataBaseName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取选中的数据库的数据表名
	 * @param dbName 
	 * @return
	 */
	public List<String> getTablesName(String dbName) {
		List<String> result = new ArrayList<>();
		Connection conn = this.getDBConnection(dbName);
		String sql = "show tables;";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement)conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(rs.getString(1));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
