package com.java.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.elasticsearch.action.index.IndexResponse;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.java.mybatis.domain.PublicMethod;
import com.java.mybatis.idao.PublicMethodMapper;
import com.java.service.ElasticSearchService;
import com.java.service.PublicMethodCodeService;
@Service("publicMethodCodeService")
public class PublicMethodCodeServiceImpl implements PublicMethodCodeService {
	
	private static final String ES_INDEX = "method";
	private static final String ES_TYPE = "public_method";
	
	@Resource
	private PublicMethodMapper publicMethodDao;

	@Override
	public String addPublicMethodCode(String jsonStr) {
		//转化为实体
		PublicMethod publicMethod = JSON.parseObject(jsonStr, new TypeReference<PublicMethod>() {});
		//新建主键
		String id = UUID.randomUUID().toString();
		publicMethod.setMethodId(id);
		//保存到数据库
		publicMethodDao.insert(publicMethod);
		//保存到ES
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("methodId", publicMethod.getMethodId());
		jsonObject.put("methodName", publicMethod.getMethodName());
		jsonObject.put("methodTitle", publicMethod.getMethodTitle());
		jsonObject.put("methodDescr", publicMethod.getMethodDescr());
		
		ElasticSearchService elasticSearchService = new ElasticSearchServiceImpl();
		IndexResponse response = elasticSearchService.addIndex(ES_INDEX, ES_TYPE, jsonObject);
		//返回客户端
		return response.status().toString();
	}

	@Override
	public String searchPublicMethodCode(String searchText) {
		ElasticSearchService elasticSearchService = new ElasticSearchServiceImpl();
		String[] fieldNames = new String[] {"methodName", "methodTitle"};
		return elasticSearchService.searchByCondition(ES_INDEX, ES_TYPE, fieldNames, searchText);
	}

}
