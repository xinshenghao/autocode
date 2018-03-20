package com.hitoo.config.utils;

/**
 * 生成XML字符串
 * @author xsh
 *
 */
public class XMLStringUtil {
	private StringBuilder sb = new StringBuilder();
	
	public void addHeader(String header) {
		sb.append(header);
	}
	/**
	 * 增加节点
	 * @param elementName　节点名称
	 * @param name　属性名数组
	 * @param value　属性值数组
	 * @param canEnd 是否结束节点
	 */
	public void addElement(String elementName, String[] name, String[] value, boolean canEnd) {
		if(name.length != value.length) {
			try {
				throw new Exception("属性数组和属性值的数组长度不一致");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.append("\r\n").append("<").append(elementName).append(" ");
		for(int i=0; i< name.length; i++) {
			sb.append(name[i]).append("=").append("\"").append(value[i]).append("\"").append(" ");
		}
		if(canEnd) {
			sb.append("/>");
		}else{
			sb.append(">");
		}
	}
	
	/**
	 * 增加节点开始
	 * <XXXX
	 * @param elementName 
	 */
	public void addElementStart(String elementName) {
		sb.append("\r\n");
		sb.append("<").append(elementName).append(" ");
	}
	/**
	 * 增加内容
	 * @param str
	 */
	public void addString(String str) {
		sb.append(str);
	}
	/**
	 * 增加节点结尾一
	 * />
	 */
	public void addElementEnd() {
		sb.append("/>");
	}
	/**
	 * 增加节点结尾二
	 * </elementName>
	 * @param elementName
	 */
	public void addElementEnd(String elementName) {
		sb.append("\r\n").append("</").append(elementName).append(">");
	}
	
	/**
	 * 增加属性
	 *  attributeName="attributeValue"
	 * @param attributeName
	 * @param attributeValue
	 */
	public void addAttribute(String attributeName, String attributeValue) {
		sb.append(" ").append(attributeName).append("=").append("\"").append(attributeValue).append("\"");
	}
	
	public String transToString() {
		return sb.toString();
	}
}
