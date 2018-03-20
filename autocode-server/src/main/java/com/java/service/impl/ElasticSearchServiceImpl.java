package com.java.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.network.Cidrs;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.java.mybatis.domain.PublicMethod;
import com.java.service.ElasticSearchService;
import com.java.utils.PropertiesUtil;

public class ElasticSearchServiceImpl implements ElasticSearchService {
	
	private static String host = PropertiesUtil.getValue(PropertiesUtil.PARAMTER_PATH, "esurl");
	private static String portS = PropertiesUtil.getValue(PropertiesUtil.PARAMTER_PATH, "esport");
	private static int port = Integer.parseInt(portS);
	
	private TransportClient client;
	
	public ElasticSearchServiceImpl() {
		try {
			client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public IndexResponse addIndex(String index, String type, JSONObject jsonObject) {
		IndexResponse response =client.prepareIndex(index, type)
	            .setSource(jsonObject.toJSONString(),XContentType.JSON)
	            .get();
		client.close();
		return response;
	}

	/**
	 * 根据关键字搜索索引
	 */
	@Override
	public String searchByCondition(String index, String type, String[] fieldNames, String text) {
		SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
		SearchResponse sr = srb.setQuery(QueryBuilders.multiMatchQuery(text, fieldNames)).execute().actionGet();			
		client.close();
		
		JSONArray result = new JSONArray();
		
		SearchHits hits = sr.getHits();
		for (SearchHit searchHit : hits) {
			JSONObject tmp = JSON.parseObject(searchHit.getSourceAsString());
			result.add(tmp);
		}
		
		return result.toJSONString();
	}
	
}
