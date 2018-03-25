package com.hitoo.config.common;


/**
 * commonParameter.xml文件中的key类
 * 没有业务逻辑，单纯存储字符串
 * @author xsh
 *
 */
public abstract class CommParaKey {
	/*******工作区域*********/
	public static String WORKSPACE = "workspace";
	public static String DEFAUL = "default" ;
	public static String ADDRESS = "address";
	/********数据库连接********/
	public static String DB_CONNS = "dbconnections";
	public static String DB_CONN = "dbconnection"; 
	public static String SELECT = "select" ;
	public static String TYPE = "type" ;
	public static String CONN_NAME	= "connectionName" ;
	public static String HOST_NAME	= "hostName" ;
	public static String PORT = "port" ;
	public static String USER_NAME = "userName" ;
	public static String PASSWORD = "password" ;
	/********MBG配置文件信息********/
	public static String MBGCONFIGS = "mbgconfigs";
	public static String MBGCONFIG = "mbgconfig";
	public static String MBGCONFIG_PATH = "path";
	public static String MBGCONFIG_SELECT = "select";
	public static String MBGCONFIG_CANDELETE = "candelete";
	/********MBG表格实体生成策略********/
	public static final String MBGTABLES = "mbgtables";
	public static final String MBGTABLE = "mbgtable";
	public static final String MBGTABLE_NAME = "name";
	public static final String MBGTABLE_SELECT = "select";
	public static final String	 ENABLE_INSERT = "enableInsert";
	public static final String ENABLE_SELECTBYPRIMARYKEY = "enableSelectByPrimaryKey";
	public static final String ENABLE_SELECTBYEXAMPLE = "enableSelectByExample";
	public static final String ENABLE_UPDATEBYPRIMARYKEY = "enableUpdateByPrimaryKey";
	public static final String ENABLE_DELETEBYPRIMARYKEY = "enableDeleteByPrimaryKey";
	public static final String ENABLE_DELETEBYEXAMPLE = "enableDeleteByExample";
	public static final String ENABLE_COUNTBYEXAMPLE = "enableCountByExample";
	public static final String ENABLE_UPDATEBYEXAMPLE = "enableUpdateByExample";
	/*******Service层代码模板*********/
	public static final String SERVICE_TEMPLATES = "service_templates";
	
	public static final String TEMPLATE_TEMPLATE = "template";
	public static final String TEMPLATE_NAME = "name";
	public static final String TEMPLATE_SELECT = "select";
	public static final String TEMPLATE_PATH = "path";

	public static final String CONTROLLER_TEMPLATES = "controller_templates";
	/****************/
	/****************/
			
}
