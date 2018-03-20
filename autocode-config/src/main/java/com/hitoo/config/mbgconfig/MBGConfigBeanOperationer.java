package com.hitoo.config.mbgconfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Element;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;

import com.hitoo.config.model.MBGContext;
import com.hitoo.config.utils.XMLStringUtil;
import com.hitoo.config.utils.XmlUtil;

/**
 *用于新建配置文件时切换视图的操作
 * @author xsh
 *
 */
public class MBGConfigBeanOperationer {

	private MBGContext mbgContext = null;
	
	public MBGConfigBeanOperationer(String filePath) {
		XmlUtil xmlUtil = new XmlUtil(filePath);
		Element root = xmlUtil.getRootElement();
		Element contextElement = root.element("context");
		this.mbgContext = parseBeanFromXML(contextElement);
	}
	
	/**
	 * 将MBGContent实体转换为xml字符串
	 * @return
	 */
	public String parseBeanToXMLString() {
		XMLStringUtil xmlString = new XMLStringUtil();
		xmlString.addHeader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<!DOCTYPE generatorConfiguration\n" + 
				"  PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\"\n" + 
				"\"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">");
		xmlString.addElementStart("generatorConfiguration");
		xmlString.addString(">");
		xmlString.addElement(MBGConfigFileKey.ELE_CONTEXT, new String[] {
				MBGConfigFileKey.ATTR_ID, MBGConfigFileKey.ATTR_DEFAULTMODELTYPE, MBGConfigFileKey.ATTR_TARGETRUNTIME
		}, new String[] {
				mbgContext.getId(), mbgContext.getDefaultModelType(),mbgContext.getTargetRuntime()
		}, false);
		String[] names = new String[] {MBGConfigFileKey.ATTR_NAME, MBGConfigFileKey.ATTR_VALUE};
		xmlString.addElement(MBGConfigFileKey.ELE_PROPERTY, names, new String[] {
				"autoDelimitKeywords", mbgContext.getProperty("autoDelimitKeywords")
		}, true);
		xmlString.addElement(MBGConfigFileKey.ELE_PROPERTY, names, new String[] {
				"javaFileEncoding", mbgContext.getProperty("javaFileEncoding")
		}, true);
		xmlString.addElement(MBGConfigFileKey.ELE_PROPERTY, names, new String[] {
				"beginningDelimiter", mbgContext.getProperty("beginningDelimiter")
		}, true);
		xmlString.addElement(MBGConfigFileKey.ELE_PROPERTY, names, new String[] {
				"endingDelimiter", mbgContext.getProperty("endingDelimiter")
		}, true);
		String[] target = new String[]{"targetPackage","targetProject"};
		//javaModelGenerator节点
		xmlString.addElement(MBGConfigFileKey.ELE_JAVAMODELGENERATOR, target, new String[] {
				mbgContext.getJavaModelGeneratorConfiguration().getTargetPackage(),
				mbgContext.getJavaModelGeneratorConfiguration().getTargetProject()
		}, false);
		xmlString.addElement(MBGConfigFileKey.ELE_PROPERTY, names, new String[] {
				"enableSubPackages",mbgContext.getJavaModelGeneratorConfiguration().getProperty("enableSubPackages")
		}, true);
		xmlString.addElement(MBGConfigFileKey.ELE_PROPERTY, names, new String[] {
				"trimStrings",mbgContext.getJavaModelGeneratorConfiguration().getProperty("trimStrings")
		}, true);
		xmlString.addElementEnd(MBGConfigFileKey.ELE_JAVAMODELGENERATOR);
		//javaModelGenerator节点
		//sqlMapGenerator节点
		xmlString.addElement(MBGConfigFileKey.ELE_SQLMAPGENERATOR, target, new String[] {
				mbgContext.getSqlMapGeneratorConfiguration().getTargetPackage(),
				mbgContext.getSqlMapGeneratorConfiguration().getTargetProject()
		}, false);
		xmlString.addElement(MBGConfigFileKey.ELE_PROPERTY, names, new String[] {
				"enableSubPackages",mbgContext.getJavaModelGeneratorConfiguration().getProperty("enableSubPackages")
		}, true);
		xmlString.addElementEnd(MBGConfigFileKey.ELE_SQLMAPGENERATOR);
		//sqlMapGenerator节点
		//javaClientGenerator节点
		String[] target2 = new String[] {"targetPackage","type","targetProject"};
		xmlString.addElement(MBGConfigFileKey.ELE_JAVACLIENTGENERATOR, target2, new String[] {
				mbgContext.getJavaClientGeneratorConfiguration().getTargetPackage(),
				mbgContext.getDefaultModelType(),
				mbgContext.getJavaClientGeneratorConfiguration().getTargetProject()
		}, false);
		xmlString.addElement(MBGConfigFileKey.ELE_PROPERTY, names, new String[] {
				"enableSubPackages",mbgContext.getJavaModelGeneratorConfiguration().getProperty("enableSubPackages")
		}, true);
		xmlString.addElementEnd(MBGConfigFileKey.ELE_JAVACLIENTGENERATOR);
		//javaClientGenerator节点
		xmlString.addElementEnd(MBGConfigFileKey.ELE_CONTEXT);
		xmlString.addElementEnd("generatorConfiguration");
		return xmlString.transToString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 从xml文件中解析出Context节点
	 * @param contextElement
	 * @return
	 */
	private MBGContext parseBeanFromXML(Element contextElement) {
		MBGContext result = XMLTranstoBean(contextElement);
		return result;
	}
	
	/**
	 * 将ｘｍｌ文件转换为MBGConfig对象
	 * @return
	 */
	private MBGContext XMLTranstoBean(Element contextElement) {
		MBGContext result = new MBGContext();
		result = addBaseAttribute(result,contextElement);
		result.setJavaModelGeneratorConfiguration(getJavaModelGenerator(contextElement));
		result.setSqlMapGeneratorConfiguration(getSqlMapGenerator(contextElement));
		result.setJavaClientGeneratorConfiguration(getJavaClientGenerator(contextElement));
		return result;
	}
	/**
	 * 从xml中解析出javaClientGenerator节点
	 * @return
	 */
	private JavaClientGeneratorConfiguration getJavaClientGenerator(Element contextElement) {
		JavaClientGeneratorConfiguration result = new JavaClientGeneratorConfiguration();
		Element element = contextElement.element(MBGConfigFileKey.ELE_JAVACLIENTGENERATOR);
		result.setTargetPackage(element.attributeValue(MBGConfigFileKey.ATTR_TARGETPACKAGE));
		result.setTargetProject(element.attributeValue(MBGConfigFileKey.ATTR_TARGETPROJECT));
		result.setConfigurationType(element.attributeValue(MBGConfigFileKey.ATTR_TYPE));
		List<Element> propertys = element.elements("property");
		for (Element property : propertys) {
			String name = property.attributeValue(MBGConfigFileKey.ATTR_NAME);
			String value = property.attributeValue(MBGConfigFileKey.ATTR_VALUE);
			result.addProperty(name, value);
		}
		return result;
	}
	/**
	 * 从xml中解析sqlMapGenerator对象
	 * @return
	 */
	private SqlMapGeneratorConfiguration getSqlMapGenerator(Element contextElement) {
		SqlMapGeneratorConfiguration result = new SqlMapGeneratorConfiguration();
		Element element = contextElement.element(MBGConfigFileKey.ELE_SQLMAPGENERATOR);
		result.setTargetPackage(element.attributeValue(MBGConfigFileKey.ATTR_TARGETPACKAGE));
		result.setTargetProject(element.attributeValue(MBGConfigFileKey.ATTR_TARGETPROJECT));
		List<Element> propertys = element.elements("property");
		for (Element property : propertys) {
			String name = property.attributeValue(MBGConfigFileKey.ATTR_NAME);
			String value = property.attributeValue(MBGConfigFileKey.ATTR_VALUE);
			result.addProperty(name, value);
		}
		return result;
	}
	/**
	 * 从xml中解析JavaModelGenerator对象
	 * @return
	 */
	private JavaModelGeneratorConfiguration getJavaModelGenerator(Element contextElement) {
		Element element = contextElement.element(MBGConfigFileKey.ELE_JAVAMODELGENERATOR);
		JavaModelGeneratorConfiguration result = new JavaModelGeneratorConfiguration();
		result.setTargetPackage(element.attributeValue(MBGConfigFileKey.ATTR_TARGETPACKAGE));
		result.setTargetProject(element.attributeValue(MBGConfigFileKey.ATTR_TARGETPROJECT));
		List<Element> propertys = element.elements("property");
		for (Element property : propertys) {
			String name = property.attributeValue(MBGConfigFileKey.ATTR_NAME);
			String value = property.attributeValue(MBGConfigFileKey.ATTR_VALUE);
			result.addProperty(name, value);
		}
		return result;
	}
	
	/**
	 * 增加context节点的基本信息
	 * @param result
	 * @return
	 */
	private MBGContext addBaseAttribute(MBGContext result, Element contextElement) {
		result.setId(contextElement.attributeValue(MBGConfigFileKey.ATTR_ID));
		result.setDefaultModelType(contextElement.attributeValue(MBGConfigFileKey.ATTR_DEFAULTMODELTYPE));
		result.setTargetRuntime(contextElement.attributeValue(MBGConfigFileKey.ATTR_TARGETRUNTIME));
		List<Element> elements = contextElement.elements("property");
		for (Element element : elements) {
			String name = element.attributeValue(MBGConfigFileKey.ATTR_NAME);
			if(name==null) continue;
			String value = element.attributeValue(MBGConfigFileKey.ATTR_VALUE);
			switch (name) {
			case "autoDelimitKeywords":
				result.setAutoDelimitKeywords(Boolean.valueOf(value));
				break;
			case "javaFileEncoding":
				result.setJavaFileEncoding(value);
				break;
			case "beginningDelimiter":
				result.setBeginningDelimiter(value);
				break;
			case "endingDelimiter":
				result.setEndingDelimiter(value);
				break;
			default:
				break;
			}
		}
		return result;
	}
	
	public MBGContext getMbgContext() {
		return mbgContext;
	}
	public void setMbgContext(MBGContext mbgContext) {
		this.mbgContext = mbgContext;
	}
	/**
	 * 保存到文件中
	 * @param path
	 */
	public void saveToFile(String path) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(path));
			writer.write(parseBeanToXMLString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
