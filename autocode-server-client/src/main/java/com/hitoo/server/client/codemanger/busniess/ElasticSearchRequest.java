package com.hitoo.server.client.codemanger.busniess;

import java.util.HashMap;
import java.util.Map;

import com.hitoo.server.client.response.AbsResponseBean;
import com.hitoo.server.client.utils.HttpClientUtil;

public class ElasticSearchRequest {

	private String url;
	
	public ElasticSearchRequest(String url) {
		this.url = url;
	}
	
	/**
	 * 搜索公有代码
	 * @param searchKeyWord
	 * @param urlPath
	 * @return
	 */
	public AbsResponseBean searchPubilcMethod(String searchKeyWord, String urlPath){
		Map<String, String> paramter = new HashMap<>();
		paramter.put("searchText", searchKeyWord);
		
		return HttpClientUtil.post(paramter, url+"/"+urlPath);
	}

	/**
	 * 增加私有代码
	 * @param paramter 参数
	 * @param urlPath 请求方法
	 * @return
	 */
	public AbsResponseBean addPrivateCode(Map<String, String> paramter, String urlPath) {
		return HttpClientUtil.post(paramter, url +"/"+ urlPath);
	}

	/**
	 * 删除私有方法代码
	 * @param methodId 私有代码Id
	 * @param urlPath 请求方法
	 */
	public AbsResponseBean deletePrivateMethod(String methodId, String urlPath) {
		Map<String, String> paramter = new HashMap<>();
		paramter.put("methodId", methodId);
		
		return HttpClientUtil.post(paramter, url+"/"+urlPath);
	}

	/**
	 * 修改私有方法代码
	 * @param paramter
	 * @param urlPath
	 */
	public AbsResponseBean updatePrivateMethod(Map<String, String> paramter, String urlPath) {
		return HttpClientUtil.post(paramter, url+"/"+urlPath);
	}

	/**
	 * 获取所有私有代码
	 * @param userId 用户id
	 * @param urlPath 方法路径
	 * @return
	 */
	public AbsResponseBean getPrivateMethods(String userId, String urlPath) {
		Map<String, String> paramter = new HashMap<>();
		paramter.put("userId", userId);
		
		return HttpClientUtil.post(paramter, url+"/"+urlPath);
	}

	/**
	 * 等领导获取用户id
	 * @param paramter
	 * @param urlPath
	 * @return
	 */
	public AbsResponseBean login(Map<String, String> paramter, String urlPath) {
		return HttpClientUtil.post(paramter, url+"/"+urlPath);
	}
}
