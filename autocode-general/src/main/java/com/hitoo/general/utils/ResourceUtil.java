package com.hitoo.general.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;


public class ResourceUtil {
	private static ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	/**
	 * 获取资源
	 * @param resourcePath
	 * @return
	 */
	public static InputStream getResource(String resourcePath) {
		Resource resources[] = null;
		try {
			resources = resolver.getResources(resourcePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(resources.length>1) {
			throw new IllegalAccessError("对应的资源表达式["+resourcePath+"]有多个资源");
		}
		try {
			return resources[0].getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}













