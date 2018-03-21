package com.hitoo.config.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.hitoo.config.mbgconfig.MBGConfig;
import com.hitoo.config.model.DBConnection;
import com.hitoo.config.model.ServiceTemplate;
import com.hitoo.config.model.TableConfig;
import com.hitoo.config.utils.ResourceUtil;
import com.hitoo.config.utils.XmlUtil;

@Component
public class CommonParameter {
	//默认工作区域
	private String workSpace = null;
	//所有工作区域
	private List<String> workSpaces = null;
	//数据库连接信息
	private List<DBConnection> dbConnections = null;
	//mbg配置文件信息
	private List<MBGConfig> mbgConfigs = null;
	//mbg Table策略
	private List<TableConfig> tableConfigs = null;
	//Service代码生成模板
	private List<ServiceTemplate> serviceTemplates = null;
	
	private XmlUtil xmlUtil = null;
	
	public CommonParameter() {
		xmlUtil = new XmlUtil(CommParaKey.COMMENT_PARAMTER_XML_PATH);
		xmlTrains2Bean();
	}
	
	/**
	 * 配置文件转换为Bean
	 */
	private void xmlTrains2Bean() {
		Element root = xmlUtil.getRootElement();
		this.workSpace = getDefaultWorkSpace(root); 
		this.workSpaces = getAllWorkSpaces(root); 
		this.dbConnections = getDBConnectionList(root);
		this.mbgConfigs = getMBGConfigs(root);
		this.tableConfigs = getTableConfigs(root);
		this.serviceTemplates = getServiceTemplate(root);
	}
	/**
	 * 获取Service层代码生成模板信息
	 * @param root
	 * @return
	 */
	private List<ServiceTemplate> getServiceTemplate(Element root) {
		List<ServiceTemplate> result = null;
		List<Element> serviceTemplates = root.element(CommParaKey.SERVICE_TEMPLATES).elements(CommParaKey.TEMPLATE_TEMPLATE);
		if( serviceTemplates != null && serviceTemplates.size() != 0 ) {
			result = new ArrayList<>();
			for (Element element : serviceTemplates) {
				ServiceTemplate tmp = new ServiceTemplate();
				tmp.setName(element.elementText(CommParaKey.TEMPLATE_NAME));
				tmp.setPath(element.elementText(CommParaKey.TEMPLATE_PATH));
				tmp.setSelect(Boolean.parseBoolean(element.elementText(CommParaKey.TEMPLATE_SELECT)));
				result.add(tmp);
			}
		}
		return result;
	}

	/**
	 * 获取mbg Tabel实体策略
	 * @param root
	 * @return
	 */
	private List<TableConfig> getTableConfigs(Element root) {
		List<TableConfig> result = null;
		List<Element> tableElements = root.element(CommParaKey.MBGTABLES).elements(CommParaKey.MBGTABLE);
		if(mbgConfigs.size()!=0 && mbgConfigs!=null) {
			result = new ArrayList<TableConfig>();
			for (Element element : tableElements) {
				TableConfig tmp = new TableConfig();
				tmp.setName(element.elementText(CommParaKey.MBGTABLE_NAME));
				tmp.setSelect(Boolean.valueOf(element.elementText(CommParaKey.MBGTABLE_SELECT)));
				tmp.setInsertStatementEnabled(Boolean.valueOf(element.elementText(CommParaKey.ENABLE_INSERT)));
				tmp.setSelectByPrimaryKeyStatementEnabled(Boolean.valueOf(element.elementText(CommParaKey.ENABLE_SELECTBYPRIMARYKEY)));
				tmp.setSelectByExampleStatementEnabled(Boolean.valueOf(element.elementText(CommParaKey.ENABLE_SELECTBYEXAMPLE)));
				tmp.setUpdateByPrimaryKeyStatementEnabled(Boolean.valueOf(element.elementText(CommParaKey.ENABLE_UPDATEBYPRIMARYKEY)));
				tmp.setDeleteByPrimaryKeyStatementEnabled(Boolean.valueOf(element.elementText(CommParaKey.ENABLE_DELETEBYPRIMARYKEY)));
				tmp.setDeleteByExampleStatementEnabled(Boolean.valueOf(element.elementText(CommParaKey.ENABLE_DELETEBYEXAMPLE)));
				tmp.setCountByExampleStatementEnabled(Boolean.valueOf(element.elementText(CommParaKey.ENABLE_COUNTBYEXAMPLE)));
				tmp.setUpdateByExampleStatementEnabled(Boolean.valueOf(element.elementText(CommParaKey.ENABLE_UPDATEBYEXAMPLE)));
				result.add(tmp);
			}
		}
		return result;
	}

	/**
	 * 获取mbg配置文件信息
	 * @param root
	 * @return
	 */
	private List<MBGConfig> getMBGConfigs(Element root) {
		Element configsElement = root.element(CommParaKey.MBGCONFIGS);
		List<MBGConfig> result = null;
		List<Element> mbgConfigs = configsElement.elements(CommParaKey.MBGCONFIG);
		if(mbgConfigs.size()!=0 && mbgConfigs!=null) {
			result = new ArrayList<MBGConfig>();
			for (Element element : mbgConfigs) {
				MBGConfig mbgConfig = new MBGConfig();
				mbgConfig.setPath(element.element(CommParaKey.MBGCONFIG_PATH).getText());
				String selectStr = element.element(CommParaKey.MBGCONFIG_SELECT).getText();
				mbgConfig.setSelect(Boolean.parseBoolean(selectStr));
				String canDeleteStr = element.element(CommParaKey.MBGCONFIG_CANDELETE).getText();
				mbgConfig.setCanDelete(Boolean.parseBoolean(canDeleteStr));
				result.add(mbgConfig);
			}
		}
		return result;
	}
	/**
	 * 获取所有工作空间
	 * @param root
	 */
	private List<String> getAllWorkSpaces(Element root) {
		List<String> result =  null;
		//所有工作空间
		Element workSpace = root.element(CommParaKey.WORKSPACE);
		List<Element> workSpaces = workSpace.elements(CommParaKey.ADDRESS);
		if(workSpaces.size()!= 0 && workSpaces!= null){
			result = new ArrayList<String>();
			for (Element element : workSpaces) {
				result.add(element.getText());
			}
		}
		return result;
	}

	/**
	 * 获取默认工作空间
	 * @param root
	 */
	private String getDefaultWorkSpace(Element root) {
		Element workSpace = root.element(CommParaKey.WORKSPACE);
		//默认工作空间
		Attribute defaultWorkSpace = workSpace.attribute(CommParaKey.DEFAUL);
		if(null == defaultWorkSpace) {
			return null;
		}
		return defaultWorkSpace.getStringValue();
	}
	/**
	 * 获取所有数据库连接信息
	 * @param root
	 * @return
	 */
	private List<DBConnection> getDBConnectionList(Element root) {
		List<DBConnection> result = new LinkedList<DBConnection>();
		List<Element> dBConnElements = root.element(CommParaKey.DB_CONNS).elements(CommParaKey.DB_CONN);
		if(0==dBConnElements.size() || null==dBConnElements ) {
			return null;
		}
		for (Element element : dBConnElements) {
			DBConnection tmp = getDBConnection(element);
			result.add(tmp);
		}
		return result;
	}
	/**
	 * 获取数据库连接
	 * @param element
	 * @return
	 */
	private DBConnection getDBConnection(Element element) {
		DBConnection result = new DBConnection();
		
		result.setSelect(Boolean.parseBoolean(element.elementText(CommParaKey.SELECT)));
		result.setConnectionName(element.elementText(CommParaKey.CONN_NAME));
		result.setHostName(element.elementText(CommParaKey.HOST_NAME));
		result.setPassword(element.elementText(CommParaKey.PASSWORD));
		result.setPort(element.elementText(CommParaKey.PORT));
		result.setType(element.elementText(CommParaKey.TYPE));
		result.setUserName(element.elementText(CommParaKey.USER_NAME));
		
		return result;
	}

	public String getWorkSpace() {
		return workSpace;
	}
	public void setWorkSpace(String workSpace) {
		this.workSpace = workSpace;
	}
	public List<DBConnection> getDbConnections() {
		return dbConnections;
	}
	public void setDbConnections(List<DBConnection> dbConnections) {
		this.dbConnections = dbConnections;
	}
	public List<String> getWorkSpaces() {
		return workSpaces;
	}
	public void setWorkSpaces(List<String> workSpaces) {
		this.workSpaces = workSpaces;
	}
	public List<MBGConfig> getMbgConfigs() {
		return mbgConfigs;
	}
	public void setMbgConfigs(List<MBGConfig> mbgConfigs) {
		this.mbgConfigs = mbgConfigs;
	}
	public List<TableConfig> getTableConfigs() {
		return tableConfigs;
	}
	public void setTableConfigs(List<TableConfig> tableConfigs) {
		this.tableConfigs = tableConfigs;
	}

	public List<ServiceTemplate> getServiceTemplates() {
		return serviceTemplates;
	}

	public void setServiceTemplates(List<ServiceTemplate> serviceTemplates) {
		this.serviceTemplates = serviceTemplates;
	}
	

	
	
}
