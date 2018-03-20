package com.hitoo.config.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期帮助类
 * @author xsh
 *
 */
public class DateUtil {
	/**
	 * 获取数字型的时间
	 * 如：2017年12月12日13点14分15秒返回20171212131415
	 * @return
	 */
	public static String getNumTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(currentTime);
	}
	
	public static void main(String[] args) {
		System.out.println(getNumTime());
	}
}
