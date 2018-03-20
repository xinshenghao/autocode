package com.java.service;

import org.elasticsearch.action.index.IndexResponse;
import com.alibaba.fastjson.JSONObject;

public interface ElasticSearchService {
	
	/**
	 * 增加索引
	 * @param index
	 * @param type
	 * @param jsonObject
	 * @return
	 */
	IndexResponse addIndex(String index, String type, JSONObject jsonObject);
	/**
	 * 条件查询
	 * @param index
	 * @param type
	 * @param fieldNames
	 * @param text
	 * @return Json格式的返回值
	 */
	String searchByCondition(String index, String type, String[] fieldNames, String text);
}
