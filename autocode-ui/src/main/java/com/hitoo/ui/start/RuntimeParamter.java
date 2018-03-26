package com.hitoo.ui.start;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.PreferenceStore;

import com.hitoo.config.FilePathBean;
import com.hitoo.general.utils.PropertiesUtil;
import com.hitoo.server.client.codemanger.busniess.ElasticSearchRequest;
import com.hitoo.server.client.response.AbsResponseBean;
import com.hitoo.server.client.response.ResponseType;
import com.hitoo.ui.preference.PreferenceStoreHelper;
import com.hitoo.ui.preference.PreferenceStoreKey;
import com.hitoo.ui.widget.MessageUtil;

/**
 * 程序运行时用到的参数
 * @author xsh
 *
 */
public class RuntimeParamter {
	
	private static final String LOGIN = "/user/login.do";
	private String url = PropertiesUtil.getValue(FilePathBean.getClientParamterFilePath(), "es.requestURL");
	private ElasticSearchRequest request;
	
	//选中的数据库
	private String selectedDataBase = null;
	//用户的ID
	private String userId = null;
	
	public RuntimeParamter() {
		this.request = new ElasticSearchRequest(url);
	}

	public String getSelectedDataBase() {
		return selectedDataBase;
	}

	public void setSelectedDataBase(String selectedDataBase) {
		this.selectedDataBase = selectedDataBase;
	}

	public String getUserId() {
		if(null != this.userId) {
			return userId;			
		}
		
		PreferenceStore preferenceStore = PreferenceStoreHelper.getPreferenceStore();
		String userName = preferenceStore.getString(PreferenceStoreKey.CODEMANAGER_USERNAME);
		String password = preferenceStore.getString(PreferenceStoreKey.CODEMANAGER_PASSWD);
		String url = PropertiesUtil.getValue(FilePathBean.getClientParamterFilePath(), "es.requestURL");
		
		Map<String, String> paramter = new HashMap<>();
		paramter.put("userName", userName);
		paramter.put("password", password);
		
		AbsResponseBean respon = request.login(paramter, LOGIN);
		if(respon.getResponseType().equals(ResponseType.ERROR)) {
			MessageUtil.error(respon);
			return null;
		}else {
			if("".equals(respon.getContent())) {
				return null;
			}
			return respon.getContent();
		}
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
