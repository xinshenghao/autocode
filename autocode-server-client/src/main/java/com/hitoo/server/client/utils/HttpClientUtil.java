package com.hitoo.server.client.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.hitoo.server.client.response.AbsResponseBean;
import com.hitoo.server.client.response.ErrorResponse;
import com.hitoo.server.client.response.SuccessResponse;

/**
 * HttpClient帮助类
 * @author xsh
 *
 */
public class HttpClientUtil {
	
	private HttpClientUtil() {}
	
	private static CloseableHttpClient httpClient = HttpClients.createDefault();
	/**
	 * 发送GET请求
	 * @param url 请求地址
	 */
	public static String get(String url) {
		StringBuilder entityStringBuilder=new StringBuilder();
		//创建连接客户端
		//生成请求
		HttpGet get = new HttpGet(url);
		//创建
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//得到httpResponse的状态响应码
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
        	HttpEntity httpEntity = response.getEntity();
        	if(null!=httpClient) {
        		BufferedReader reader = null;
        		try {
					reader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"),8*1024);
					String line = null;
					while ((line=reader.readLine())!=null) {
                        entityStringBuilder.append(line);
                    }
				} catch (UnsupportedOperationException | IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return entityStringBuilder.toString();
	}
	
	public static AbsResponseBean post(Map<String, String> paramsHashMap, String url) {
        //创建httpClient连接
        List<NameValuePair> nameValuePairArrayList = new ArrayList<NameValuePair>();
        // 将传过来的参数添加到List<NameValuePair>中
        if (paramsHashMap != null && !paramsHashMap.isEmpty()) {
            //遍历map
            for (Map.Entry<String, String> entry : paramsHashMap.entrySet()) {
                nameValuePairArrayList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        UrlEncodedFormEntity entity = null;
        try {
            // 利用List<NameValuePair>生成Post请求的实体数据
            // UrlEncodedFormEntity 把输入数据编码成合适的内容
            entity = new UrlEncodedFormEntity(nameValuePairArrayList, "UTF-8");
            HttpPost httpPost = new HttpPost(url);
            // 为HttpPost设置实体数据
            httpPost.setEntity(entity);
            // HttpClient 发送Post请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // CloseableHttpResponse
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"), 10 * 1024);
                        StringBuilder strBuilder = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            strBuilder.append(line);
                        }
                        JSONObject json = JSONObject.parseObject(strBuilder.toString());
                        if(json.getString("responseType").equals("SUCCESS")) {
                        	return new SuccessResponse(json.getString("content"));
                        }else if(json.getString("responseType").equals("ERROR")) {
                        	return new ErrorResponse(json.getString("content"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                //关闭流
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse("网络连接错误,无法连接到指定服务器");
        }
		return null;
    }
}
