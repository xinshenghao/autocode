package com.hitoo.ui.codemanager.uploadcode;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hitoo.config.FilePathBean;
import com.hitoo.general.utils.PropertiesUtil;
import com.hitoo.server.client.codemanger.busniess.ElasticSearchRequest;
import com.hitoo.server.client.response.AbsResponseBean;
import com.hitoo.server.client.response.ResponseType;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;
import com.hitoo.ui.start.RuntimeParamter;
import com.hitoo.ui.widget.MessageUtil;

public class UploadCodeWizard extends Wizard {
	public static String SELECTUPLOADTYPEWIZARDPAGE = "selectUploadTypeWizardPage";
	public static String WRITECODEWIZARDPAGE = "writeCodeWizardPage";
	public static String WRITEMETHODCODEWIZARDPAGE = "writeMethodCodeWizardPage";
	public static String WRITECODEINFORWIZARDPAGE = "writeCodeInforWizardPage";
	private static final String REQUEST_ADDPRIVATEMETHOD = "privateMethodCode/addPrivateMethod.do";
	
	private SelectUploadTypeWizardPage  selectUploadTypeWizardPage = new SelectUploadTypeWizardPage();
	private WriteClassCodeWizardPage writeClassCodeWizardPage = new WriteClassCodeWizardPage();
	private WriteMethodCodeWizardPage writeMethodCodeWizardPage = new WriteMethodCodeWizardPage();
	private WriteCodeInforWizardPage writeCodeInforWizardPage = new WriteCodeInforWizardPage();
	
	private String codeName, codeContent, title, descri;
	
	private boolean isAddClassCode = true; //是否是增加类代码
	
	String url = PropertiesUtil.getValue(FilePathBean.getClientParamterFilePath(), "es.requestURL");
	private ElasticSearchRequest request = new ElasticSearchRequest(url);
	
	private RuntimeParamter runtimeParamter = (RuntimeParamter) ApplicationContextHelper.getBean("runtimeParamter");

	public UploadCodeWizard() {
		setWindowTitle("上传代码");
	}

	@Override
	public void addPages() {
		this.addPage(selectUploadTypeWizardPage);
		this.addPage(writeClassCodeWizardPage);
		this.addPage(writeMethodCodeWizardPage);
		this.addPage(writeCodeInforWizardPage);
	}

	@Override
	public boolean performFinish() {
		isAddClassCode = selectUploadTypeWizardPage.isAddClassCode();
		if(isAddClassCode) {
			MessageUtil.infor(getShell(), "未实现增加类代码功能");
			//增加类代码
		}else {
			//增加方法代码
			codeName = writeMethodCodeWizardPage.getMethodName();
			codeContent = writeMethodCodeWizardPage.getCodeContent();
			title = writeCodeInforWizardPage.getCodeTitle();
			descri = writeCodeInforWizardPage.getCodeDescr();
			if((null==codeName) || (null==codeContent) || (null==title) || (null==descri)) {
				return false;
			}
			
			ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(AutoCode.getApp().getShell());
			
			try {
				progressDialog.run(true, false, addMethodCode);
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canFinish() {
		IWizardPage current = this.getContainer().getCurrentPage();
		if(current == writeCodeInforWizardPage) {
			return true;
		}
		return false;
	}
	
	IRunnableWithProgress addMethodCode = new IRunnableWithProgress() {
		
		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			monitor.beginTask("新增私有代码", 100);
			
			sendRequestAddPrivateCode(monitor);
			
			monitor.done();
		}
		/**
		 * 增加私有代码
		 * @param monitor
		 */
		private void sendRequestAddPrivateCode(IProgressMonitor monitor) {
			String userId = runtimeParamter.getUserId();
			if(null == userId) {
				return;
			}
			monitor.worked(10);
			monitor.subTask("获取内容");
			
			Map<String, String> paramter = new HashMap<>();
			paramter.put("methodId", UUID.randomUUID().toString());
			paramter.put("userId", userId);
			paramter.put("methodName", codeName);
			paramter.put("methodContent", codeContent);
			paramter.put("methodTitle", title);
			paramter.put("methodDescr", descri);
			
			monitor.subTask("发送请求");
			monitor.worked(30);
			
			AbsResponseBean result = request.addPrivateCode(paramter,REQUEST_ADDPRIVATEMETHOD);
			
			if(result.getResponseType().equals(ResponseType.ERROR)) {
				MessageUtil.error(result);
			}else {
				MessageUtil.infor(result);
			}
		}
	}; 
}
