package com.hitoo.config.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hitoo.config.mbgconfig.MBGConfig;
import com.hitoo.config.model.DBConnection;
import com.hitoo.config.model.TableConfig;
import com.hitoo.config.utils.DateUtil;

/**
 * 对持久化容器中的通用参数类的操作
 * @author xsh
 *
 */
@Component
public class CommParaBeanOperationer {
	/**
	 * 持久化容器中的通用参数类
	 */
	@Autowired
	private CommonParameter commonParameter;

	/**
	 * 向持久化容器中增加数据库连接信息
	 * @param dbConnection
	 * @return 返回数据库连接信息实体（可能是原实体可能是重名后添加日期后缀的实体）
	 */
	public DBConnection addDBConntion(DBConnection addDbConnection) {
		List<DBConnection> conns = commonParameter.getDbConnections();
		if(conns==null) {
			conns = new ArrayList<DBConnection>();
			commonParameter.setDbConnections(conns);
		}
		for (DBConnection conn : conns) {
			//如果连接名重复的话就添加日期后缀
			String tmpConnName = conn.getConnectionName();
			if(tmpConnName.equals(addDbConnection.getConnectionName())) {
				addDbConnection.setConnectionName(addDbConnection.getConnectionName()+DateUtil.getNumTime());
			}
			conn.setSelect(false);
		}			
		conns.add(addDbConnection);
		return addDbConnection;
	}
	/**
	 * 删除持久化容器中的数据库连接信息实体
	 * @param dbConnection
	 */
	public void deleteDBConnection(DBConnection delDbConnection) {
		//在所有连接信息中删除
		List<DBConnection> conns = commonParameter.getDbConnections();
		List<DBConnection> newConns = new ArrayList<DBConnection>();
		for (DBConnection conn : conns) {
			if(!conn.getConnectionName().equals(delDbConnection.getConnectionName())){
				newConns.add(conn);							
			}
		}
		commonParameter.setDbConnections(newConns);
	}
	
	/**
	 * 增加MBGConfig
	 * @param tmp
	 */
	public void addMBGConfig(MBGConfig config) {
		//将其他的设为不选中
		List<MBGConfig> list = commonParameter.getMbgConfigs();
		if(list==null) {
			list = new ArrayList<MBGConfig>();
		}
		for (MBGConfig item : list) {
			if(item.getPath().equals(config.getPath())) {
				config.setPath(config.getPath()+"1");
			}
			item.setSelect(false);
		}
		//增加新的
		list.add(config);
		commonParameter.setMbgConfigs(list);
	}
	
	/**
	 * 删除MBGConfig实体
	 * @param mbgConfig
	 */
	public void deleteMBGConfig(MBGConfig mbgConfig) {
		List<MBGConfig> list = commonParameter.getMbgConfigs();
		Iterator<MBGConfig> it = list.iterator();
		while(it.hasNext()) {
			MBGConfig tmp = it.next();
			if(tmp.getPath().equals(mbgConfig.getPath())) {
				it.remove();
			}
		}
		commonParameter.setMbgConfigs(list);
	}
	/**
	 * 设置选中的数据库连接
	 * @param dbConnection
	 */
	public void setSelectedDBConnection(DBConnection dbConnection) {
		List<DBConnection> list = commonParameter.getDbConnections();
		for (DBConnection item : list) {
			if(item.getConnectionName().equals(dbConnection.getConnectionName())) {
				item.setSelect(true);
			}else {
				item.setSelect(false);
			}
		}
		commonParameter.setDbConnections(list);
	}
	/**
	 * 设置选中的MBGConfig配置
	 * @param mbgConfig
	 */
	public void setSelectedMBGConfig(MBGConfig mbgConfig) {
		List<MBGConfig> list = commonParameter.getMbgConfigs();
		for (MBGConfig item : list) {
			if(item.getPath().equals(mbgConfig.getPath())) {
				item.setSelect(true);
			}else {
				item.setSelect(false);
			}
		}
	}
	/**
	 * 增加TableConfig
	 * @param tableConfig
	 */
	public void addTableConfig(TableConfig tableConfig) {
		List<TableConfig> list = commonParameter.getTableConfigs();
		if(null == list) {
			list = new ArrayList<TableConfig>();
		}
		//如果设置为默认，将其他的设为false
		if(tableConfig.isSelect()) {
			for (TableConfig item : list) {
				item.setSelect(false);
			}
		}
		list.add(tableConfig);
		commonParameter.setTableConfigs(list);
	}
	/**
	 * 删除TableConfig
	 * @param tableConfig
	 */
	public void deleteTableConfig(TableConfig tableConfig) {
		List<TableConfig> list = commonParameter.getTableConfigs();
		Iterator<TableConfig> it = list.iterator();
		while(it.hasNext()) {
			TableConfig tmp = it.next();
			if(tmp.getName().equals(tableConfig.getName())) {
				it.remove();
			}
		}
		commonParameter.setTableConfigs(list);
	}
	/**
	 * 设置Table生成策略为选中
	 * @param config
	 */
	public void setSelectedTableConfig(TableConfig config) {
		List<TableConfig> list = commonParameter.getTableConfigs();
		for (TableConfig item : list) {
			if(item.getName().equals(config.getName())) {
				item.setSelect(true);
			}else {
				item.setSelect(false);
			}
		}
	}
	
	
	
}
