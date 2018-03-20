package com.hitoo.ui.utils;

public class StringUtil {

	/**
	 * 是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null || "".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断多个字符串是否为空,有一个为空就返回true
	 * @param strs
	 * @return
	 */
	public static boolean isEmpty(String... strs) {
		for (String str : strs) {
			if(isEmpty(str)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNotEmpty(String str){
		if(str!=null && !"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 首字母大写
	 * @author: 辛晟昊   
	 * @param: @param value
	 * @param: @return      
	 * @return: String
	 */
	public static String capture(String value){
		char[] cs=value.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
	}
	/**
	 * 首字母小写
	 * @author: 辛晟昊   
	 * @param: @param value
	 * @param: @return      
	 * @return: String
	 */
	public static String lowercase(String value){
		char[] cs=value.toCharArray();
        cs[0]+=32;
        return String.valueOf(cs);
	}
	
	
}
