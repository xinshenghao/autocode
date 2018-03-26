package com.hitoo.ui.codemanager.privatecode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action
;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Display;

import com.alibaba.fastjson.JSON;
import com.hitoo.config.FilePathBean;
import com.hitoo.general.utils.PropertiesUtil;
import com.hitoo.server.client.codemanger.busniess.ElasticSearchRequest;
import com.hitoo.server.client.codemanger.domain.PrivateMethod;
import com.hitoo.server.client.response.AbsResponseBean;
import com.hitoo.server.client.response.ResponseType;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;
import com.hitoo.ui.start.RuntimeParamter;
import com.hitoo.ui.widget.MessageUtil;

public class PrivateCodeManagerAction extends Action {
	private static final String GETPRIVATECODE = "/privateMethodCode/getPrivateCodeByUserId.do";
	
	
	private RuntimeParamter runtimeParamter = (RuntimeParamter) ApplicationContextHelper.getBean("runtimeParamter"); 
	
	private String url = PropertiesUtil.getValue(FilePathBean.getClientParamterFilePath(), "es.requestURL");
	private ElasticSearchRequest elasticSearchRequest;
	
	String userId = null;
	
	public PrivateCodeManagerAction() {
		setText("私有代码管理");
		this.elasticSearchRequest = new ElasticSearchRequest(url);
	}
	
	@Override
	public void run() {
		
		userId = runtimeParamter.getUserId();
		if(null != userId) {
			ArrayList<PrivateMethod> privateMethods = getPrivateMethodCodes();
			
			if(null != privateMethods) {
				createTabView(privateMethods);			
			}
		}else {
			MessageUtil.error("登录失败，请检查您的账号密码");
		}
		
	}
	/**
	 * 创建Tab视图
	 * @param privateMethods
	 */
	private void createTabView(ArrayList<PrivateMethod> privateMethods) {
		CTabFolder tabFolder = AutoCode.getTabFolder();
		
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("私有代码管理");
		PrivateCodeManagerComposite privateCodeManagerComposite = new PrivateCodeManagerComposite(tabFolder, SWT.NONE, privateMethods);
		
		tabItem.setControl(privateCodeManagerComposite);
		
		tabFolder.setSelection(tabFolder.getItemCount()-1);
	}

	/**
	 * 获取私有代码
	 * @return
	 */
	private ArrayList<PrivateMethod> getPrivateMethodCodes() {
		Map<String, String> paramter = new HashMap<>();
		paramter.put("userId", userId);
		
		AbsResponseBean resp = elasticSearchRequest.getPrivateMethods(userId, GETPRIVATECODE);
		if(resp.getResponseType().equals(ResponseType.ERROR)) {
			MessageUtil.error(Display.getDefault().getActiveShell(), resp.getContent());
			return null;
		}else {
			List<PrivateMethod> result = JSON.parseArray(resp.getContent(), PrivateMethod.class);
			return new ArrayList<>(result);
		}
	}

}
