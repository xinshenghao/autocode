package com.hitoo.config.mbgconfig;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hitoo.config.model.DBConnection;
import com.hitoo.config.model.TableConfig;
import com.hitoo.config.utils.XmlUtil;

/**
 * MBG配置文件操作类
 * @author xsh
 *
 */
public class MBGConfigOperationer {	
	XmlUtil xmlUtil = null;
	Element root = null;
	Element contextElement = null;
	
	public MBGConfigOperationer(String filePath) {
		xmlUtil = new XmlUtil(filePath);
		root = xmlUtil.getRootElement();
		contextElement = root.element("context");		
	}
	/**
	 * 获取格式化后的xml字符串
	 * @return
	 */
	/*public String getFormatXmlString() {
		return xmlUtil.getFormateXMLString();
	}*/
	
	/**
	 * 增加jdbcConnection节点
	 * @param dbConnection
	 * @param dataBaseName
	 */
	public void addJdbcConnectionElement(DBConnection dbConnection, String dataBaseName) {
		List<Element> elements = contextElement.content();
		Element jdbcConnection = DocumentHelper.createElement("jdbcConnection"); 
		jdbcConnection.addAttribute("driverClass", "com.mysql.jdbc.Driver");
		String connectionURL = createConnectionURL(dbConnection,dataBaseName);
		jdbcConnection.addAttribute("connectionURL", connectionURL);
		jdbcConnection.addAttribute("userId", dbConnection.getUserName());
		jdbcConnection.addAttribute("password", dbConnection.getPassword());
		elements.add(5, jdbcConnection);
		contextElement.setContent(elements);
	}
	/**
	 * 获取连接信息
	 * @param dbConnection
	 * @param dataBaseName
	 * @return
	 */
	private String createConnectionURL(DBConnection dbConnection, String dataBaseName) {
		StringBuilder result = new StringBuilder();
		result.append("jdbc:mysql://");
		result.append(dbConnection.getHostName());
		result.append(":");
		result.append(dbConnection.getPort());
		result.append("/");
		result.append(dataBaseName);
		return result.toString();
	}
	/**
	 * 增加table节点
	 * @param selectedTables 要生成实体的table
	 */
	public void addTableElement(List<String> selectedTables, TableConfig tableConfig) {
		for (String table : selectedTables) {
			Element tableElement = contextElement.addElement("table");
			tableElement.addAttribute("tableName", table);
			tableElement.addAttribute("enableInsert", tableConfig.isInsertStatementEnabled()+"");
			tableElement.addAttribute("enableSelectByPrimaryKey", tableConfig.isSelectByPrimaryKeyStatementEnabled()+"");  
			tableElement.addAttribute("enableSelectByExample", tableConfig.isSelectByExampleStatementEnabled()+"");
			tableElement.addAttribute("enableUpdateByPrimaryKey", tableConfig.isUpdateByPrimaryKeyStatementEnabled()+"");
			tableElement.addAttribute("enableDeleteByPrimaryKey", tableConfig.isDeleteByPrimaryKeyStatementEnabled()+"");
			tableElement.addAttribute("enableDeleteByExample", tableConfig.isDeleteByExampleStatementEnabled()+"");
			tableElement.addAttribute("enableCountByExample", tableConfig.isCountByExampleStatementEnabled()+"");
			tableElement.addAttribute("enableUpdateByExample", tableConfig.isUpdateByExampleStatementEnabled()+"");
		}
	}
	
	/**
	 * 写入文件
	 * @param filePath 文件路径
	 */
	public void writeToFile(String filePath) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(filePath);
			xmlUtil.writeXml(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}






