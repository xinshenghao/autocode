package com.hitoo.config.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hitoo.config.FilePathBean;
import com.hitoo.config.mbgconfig.MBGConfig;
import com.hitoo.config.model.DBConnection;
import com.hitoo.config.model.TableConfig;
import com.hitoo.config.utils.ResourceUtil;
import com.hitoo.config.utils.XmlUtil;

/**
 * 通用参数配置文件操作类,是对配置文件进行操作的类
 * @author xsh
 *
 */
@Component
public class CommonParamterOperationer {
	private XmlUtil xmlUtil = null;
	private Element root = null;
	
	@Autowired
	private CommonParameter commonParameter = null;

	public CommonParamterOperationer() {
		xmlUtil = new XmlUtil(FilePathBean.getCommonParameter());
		root = xmlUtil.getRootElement();
	}
	/**
	 * 设置默认工作空间
	 * @param defaultWorkSpace
	 */
	public void setDefaultWorkSpace(String defaultWorkSpaceValue) {
		Element workSpace = root.element(CommParaKey.WORKSPACE);
		Attribute defWorkSpace = workSpace.attribute(CommParaKey.DEFAUL);
		if(defWorkSpace != null) {
			workSpace.remove(defWorkSpace);			
		} 
		if(null != defaultWorkSpaceValue) {
			workSpace.addAttribute(CommParaKey.DEFAUL, defaultWorkSpaceValue);
		}
		writeToFile();
	}
	/**
	 * 增加工作空间
	 * @param address
	 */
	public void addWorkSpace(String address) {
		
		Element workSpace = root.element(CommParaKey.WORKSPACE);
		List<Element> addresses = workSpace.elements();
		Element element = DocumentHelper.createElement(CommParaKey.ADDRESS);
		element.setText(address);
		addresses.add(0, element);
		for(int i = 1;i<addresses.size();i++){
			if(address.equals(addresses.get(i).getText())){
				addresses.remove(i);
			}
		}
		writeToFile();
	}

	/**
	 * 增加数据库连接信息
	 * @param dbConnection 
	 */
	public void addDBConnInfor(DBConnection dbConnection) {
		Element connsElement = root.element(CommParaKey.DB_CONNS);
		//是否设为默认
		boolean isSetDefault = dbConnection.getSelect();
		//如果设为默认就将其他的设为不默认
		if(isSetDefault) {
			List<Element> conns = connsElement.elements(CommParaKey.DB_CONN);
			for (Element element : conns) {
				Element select = element.element(CommParaKey.SELECT);
				select.setText("false");				
			}
		}
		//增加新增数据库信息
		Element dbConn = DocumentHelper.createElement(CommParaKey.DB_CONN);
		dbConn.addElement(CommParaKey.SELECT).setText(dbConnection.getSelect().toString());
		dbConn.addElement(CommParaKey.TYPE).setText(dbConnection.getType());
		dbConn.addElement(CommParaKey.CONN_NAME).setText(dbConnection.getConnectionName());
		dbConn.addElement(CommParaKey.HOST_NAME).setText(dbConnection.getHostName());
		dbConn.addElement(CommParaKey.PORT).setText(dbConnection.getPort());
		dbConn.addElement(CommParaKey.USER_NAME).setText(dbConnection.getUserName());
		dbConn.addElement(CommParaKey.PASSWORD).setText(dbConnection.getPassword());
		connsElement.add(dbConn);
	}
	/**
	 * 删除配置文件中的连接信息
	 * @param oldConnection
	 */
	public void deleteDBConnection(DBConnection oldConnection) {
		Element connsElement = root.element(CommParaKey.DB_CONNS);
		List<Element> connElements = connsElement.elements(CommParaKey.DB_CONN);
		Element delete = null;
		for (Element element : connElements) {
			String elementConnName = element.element(CommParaKey.CONN_NAME).getText();
			if(elementConnName.equals(oldConnection.getConnectionName())) {
				delete = element;
				break ;
			}
		}
		if(delete != null) {
			connsElement.remove(delete);
		}
		writeToFile();
	}
	/**
	 * 修改数据库连接
	 * @param oldConnection 要修改的数据库连接
	 * @param newConnection 修改后的数据库连接
	 */
	public void updateDBConn(DBConnection oldConnection, DBConnection newConnection) {
		deleteDBConnection(oldConnection);
		addDBConnInfor(newConnection);
		writeToFile();
	}
	
	/**
	 * 设置选中的DBConnection
	 * @param dbConnection
	 */
	public void setSelectedDBConnection(DBConnection dbConnection) {
		List<Element> connElements = root.element(CommParaKey.DB_CONNS).elements(CommParaKey.DB_CONN);

		for (Element element : connElements) {
			Element name = element.element(CommParaKey.CONN_NAME);
			if(name.getText().equals(dbConnection.getConnectionName())) {
				element.element(CommParaKey.SELECT).setText("true");
			}else {
				element.element(CommParaKey.SELECT).setText("false");
			}
		}
		writeToFile();
	}
	
	/**
	 * 增加MBGConfig节点
	 */
	public void addMBGConfig(MBGConfig mbgConfig) {
		Element configsElement = root.element(CommParaKey.MBGCONFIGS);
		boolean isSelected = mbgConfig.getSelect();
		//如果设为默认，则将其他的设为不默认
		if(isSelected) {
			List<Element> configs = configsElement.elements(CommParaKey.MBGCONFIG);
			for (Element config : configs) {
				Element select = config.element(CommParaKey.MBGCONFIG_SELECT);
				select.setText("false");				
			}
		}
		//增加节点
		Element mbgConfigElement = configsElement.addElement(CommParaKey.MBGCONFIG);
		mbgConfigElement.addElement(CommParaKey.MBGCONFIG_PATH).setText(mbgConfig.getPath());
		mbgConfigElement.addElement(CommParaKey.MBGCONFIG_SELECT).setText(mbgConfig.getSelect().toString());
		mbgConfigElement.addElement(CommParaKey.MBGCONFIG_CANDELETE).setText(mbgConfig.getCanDelete().toString());
		writeToFile();
	}
	/**
	 * 设置选中的MBG配置文件
	 * @param mbgConfig
	 */
	public void setSelectedMBGConfig(MBGConfig mbgConfig) {
		Element configsElement = root.element(CommParaKey.MBGCONFIGS);
		List<Element> mbgConfigs = configsElement.elements(CommParaKey.MBGCONFIG);
		for (Element element : mbgConfigs) {
			Element tmpPath = element.element(CommParaKey.MBGCONFIG_PATH); 
			if(tmpPath.getText().equals(mbgConfig.getPath())) {
				element.element(CommParaKey.MBGCONFIG_SELECT).setText("true");
			}else {
				element.element(CommParaKey.MBGCONFIG_SELECT).setText("false");
			}
		}
		writeToFile();
	}
	
	/**
	 * 删除配置文件中的MBGConfig记录
	 * @param mbgConfig
	 */
	public void deleteMBGConfig(MBGConfig mbgConfig) {
		Element configsElement = root.element(CommParaKey.MBGCONFIGS);
		List<Element> mbgConfigs = configsElement.elements(CommParaKey.MBGCONFIG);
		Element tmp = null;
		for (Element element : mbgConfigs) {
			if(element.element(CommParaKey.MBGCONFIG_PATH).getText().equals(mbgConfig.getPath())) {
				tmp = element;
			}
		}
		if(null != tmp) {
			configsElement.remove(tmp);
		}
		writeToFile();
		
		
	}
	
	public void writeToFile() {
		OutputStream os = null;
		try {
			//os = new FileOutputStream(ConfigFilePath.COMMON_PARA_FILE_OUT_PATH);
			os = new FileOutputStream(FilePathBean.getCommonParameter());
			xmlUtil.writeXml(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加表格实体生成策略配置
	 * @param data
	 */
	public void addTableConfig(TableConfig data) {
		Element mbgTablesElement = root.element(CommParaKey.MBGTABLES);
		if(data.isSelect()) {
			List<Element> elements = mbgTablesElement.elements(CommParaKey.MBGTABLE);
			for (Element element : elements) {
				element.element(CommParaKey.MBGTABLE_SELECT).setText("false");
			}
		}
		Element mbgtable = mbgTablesElement.addElement(CommParaKey.MBGTABLE);
		mbgtable.addElement(CommParaKey.MBGTABLE_NAME).setText(data.getName());
		mbgtable.addElement(CommParaKey.MBGTABLE_SELECT).setText(data.isSelect()+"");
		mbgtable.addElement(CommParaKey.ENABLE_INSERT).setText(data.isInsertStatementEnabled()+"");
		mbgtable.addElement(CommParaKey.ENABLE_COUNTBYEXAMPLE).setText(data.isCountByExampleStatementEnabled()+"");
		mbgtable.addElement(CommParaKey.ENABLE_DELETEBYEXAMPLE).setText(data.isDeleteByExampleStatementEnabled()+"");
		mbgtable.addElement(CommParaKey.ENABLE_DELETEBYPRIMARYKEY).setText(data.isDeleteByPrimaryKeyStatementEnabled()+"");
		mbgtable.addElement(CommParaKey.ENABLE_UPDATEBYEXAMPLE).setText(data.isUpdateByExampleStatementEnabled()+"");
		mbgtable.addElement(CommParaKey.ENABLE_UPDATEBYPRIMARYKEY).setText(data.isUpdateByPrimaryKeyStatementEnabled()+"");
		mbgtable.addElement(CommParaKey.ENABLE_SELECTBYEXAMPLE).setText(data.isSelectByExampleStatementEnabled()+"");
		mbgtable.addElement(CommParaKey.ENABLE_SELECTBYPRIMARYKEY).setText(data.isSelectByPrimaryKeyStatementEnabled()+"");
		writeToFile();
	}
	/**
	 * 删除表格实体生成策略配置
	 * @param data
	 */
	public void deleteTableConfig(TableConfig data) {
		Element mbgTables = root.element(CommParaKey.MBGTABLES);
		List<Element> elements = mbgTables.elements(CommParaKey.MBGTABLE);
		Element tmp = null;
		for (Element element : elements) {
			String name = element.elementText(CommParaKey.MBGTABLE_NAME);
			if(name.equals(data.getName())) {
				tmp = element;
			}
		}
		if(null != tmp) {
			mbgTables.remove(tmp);
		}
		writeToFile();
	}
	/**
	 * 设置表格实体生成策略选中
	 * @param tableConfig
	 */
	public void setSelectedTableConfig(TableConfig tableConfig){
		List<Element> elements = root.element(CommParaKey.MBGTABLES).elements(CommParaKey.MBGTABLE);
		for (Element element : elements) {
			String name = element.elementText(CommParaKey.MBGTABLE_NAME);
			if(name.equals(tableConfig.getName())) {
				element.element(CommParaKey.MBGTABLE_SELECT).setText("true");
			}else {
				element.element(CommParaKey.MBGTABLE_SELECT).setText("false");
			}
		}
		writeToFile();
	}
	
}