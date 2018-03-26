package com.hitoo.config;

import com.hitoo.general.utils.PropertiesUtil;

/**
 * 对应filePath.properties文件的实体
 * @author xsh
 *
 */
public class FilePathBean {
	//项目路径
	private static final String PROJECTPATH = "/home/xsh/git/autocode/";
	//filePath.properties文件位置
	private static final String FILEPATH_PROPERTIES_PATH = "classpath:filePath.properties";
	//client_paramter.properties文件位置
	private static final String CLIENT_PARAMTER_FILE_PATH = "classpath:client_paramter.properties";
	
	//项目通用配置文件信息
	private static String commonParameter = null;
	//MBG通用文件位置
	private static String mbgCommonConfigFile = null;
	//MBG配置文件存放目录
	private static String mbgConfigFile= null;
	//配置文件临时目录
	private static String mbgConfigFileTmp= null;
	//首选项配置文件
	private static String preferenceStore= null;
	//图片文件夹
	private static String imgsFolder= null;
	//Service模板文件位置
	private static String service_templates = null;
	//Controller模板文件位置
	private static String controller_templates = null;
	
	public static String getProjectPath() {
		return PROJECTPATH;
	}
	public static String getClientParamterFilePath() {
		return CLIENT_PARAMTER_FILE_PATH;
	}
	
	public static String getCommonParameter() {
		if(null == commonParameter) {
			commonParameter = getPropertiesValue("commonParameter");
		}
		return commonParameter;
	}
	public static String getMbgCommonConfigFile() {
		if(null == mbgCommonConfigFile) {
			mbgCommonConfigFile = getPropertiesValue("mbgCommonConfigFile");
		}
		return mbgCommonConfigFile;
	}
	public static String getMbgConfigFile() {
		if(null == mbgConfigFile) {
			mbgConfigFile = getPropertiesValue("mbgConfigFile");
		}
		return mbgConfigFile;
	}
	public static String getMbgConfigFileTmp() {
		if(null == mbgConfigFileTmp) {
			mbgConfigFileTmp = getPropertiesValue("mbgConfigFileTmp");
		}
		return mbgConfigFileTmp;
	}
	public static String getPreferenceStore() {
		if(null == preferenceStore) {
			preferenceStore = getPropertiesValue("preferenceStore");
		}
		return preferenceStore;
	}
	public static String getImgsFolder() {
		if(null == imgsFolder) {
			imgsFolder = getPropertiesValue("imgsFolder");
		}
		return imgsFolder;
	}
	public static String getService_templates() {
		if(null == service_templates) {
			service_templates = PropertiesUtil.getValue(FILEPATH_PROPERTIES_PATH, "service_templates");
		}
		return service_templates;
	}
	public static String getController_templates() {
		if(null == controller_templates) {
			controller_templates = PropertiesUtil.getValue(FILEPATH_PROPERTIES_PATH, "controller_templates");
		}
		return controller_templates;
	}
	private static String getPropertiesValue(String key) {
		String tmp = PropertiesUtil.getValue(FILEPATH_PROPERTIES_PATH, key);
		return PROJECTPATH+"/"+tmp;
	}
}
