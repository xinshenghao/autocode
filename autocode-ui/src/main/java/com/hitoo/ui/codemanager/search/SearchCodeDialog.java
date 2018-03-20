package com.hitoo.ui.codemanager.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hitoo.server.client.codemanger.busniess.ElasticSearchRequest;
import com.hitoo.server.client.codemanger.domain.PublicMethod;
import com.hitoo.server.client.response.AbsResponseBean;
import com.hitoo.server.client.response.ResponseType;
import com.hitoo.ui.utils.PropertiesUtil;
import com.hitoo.ui.utils.StringUtil;
import com.hitoo.ui.widget.MessageUtil;

import swing2swt.layout.FlowLayout;

public class SearchCodeDialog extends Dialog {
	
	private String url = PropertiesUtil.getValue(PropertiesUtil.CLIENT_PARAMTER_PATH, "es.requestURL");
	private ElasticSearchRequest elasticSearchRequest;
	
	private Text searchText;
	private Button searchBtn,isPrivate, isPublic;
	private List methodList;
	private ProgressBar progressBar;
	
	private ArrayList<PublicMethod> publicMethods;
	
	private SelectionAdapter selectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent e) {
			
			searchBtn.setEnabled(false);
			searchText.setEnabled(false);
			
			progressBar.setSelection(20);
			
			Display.getDefault().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					String searchTextStr = searchText.getText();
					if(StringUtil.isEmpty(searchTextStr)) {
						MessageUtil.error(getParentShell(), "搜索关键字不能为空");
						searchBtn.setEnabled(true);
						searchText.setEnabled(true);
						return;
					}
					
					AbsResponseBean response = elasticSearchRequest.searchPubilcMethod(searchTextStr, "/publicMethodCode/searchPublicMethodCode.do");
					
					if(ResponseType.ERROR.equals(response.getResponseType())) {
						MessageUtil.error(getParentShell(), response.getContent());
						searchBtn.setEnabled(true);
						searchText.setEnabled(true);
						progressBar.setSelection(0);
						return ;
					}
					
					analysisResponse(response.getContent());
					
					searchBtn.setEnabled(true);
					searchText.setEnabled(true);
					
					progressBar.setSelection(100);
				}
				/**
				 * 解析返回的字符串
				 * @param responseStr
				 */
				private void analysisResponse(String responseStr) {
					JSONArray jsonArray = JSON.parseArray(responseStr);
					
					publicMethods = new ArrayList<>();
					for(int i=0;i<jsonArray.size(); i++) {
						JSONObject tmp = jsonArray.getJSONObject(i);
						PublicMethod tmpPM = JSON.toJavaObject(tmp, PublicMethod.class);
						publicMethods.add(tmpPM);
					}
					
					progressBar.setSelection(60);
					
					initListView();
				}
			});
			
			
			
		}
		
	};
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SearchCodeDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.RESIZE);
		elasticSearchRequest = new ElasticSearchRequest(url);
	}
	/**
	 * 初始化列表视图
	 */
	protected void initListView() {
		methodList.removeAll();
		if(publicMethods.isEmpty()) {
			methodList.add("未搜索到结果");
		}
		for (PublicMethod publicMethod : publicMethods) {
			methodList.add(publicMethod.getMethodName());
		}
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = 10;
		layout.marginBottom = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		container.setLayout(layout);
		
		
		Label label = new Label(container, SWT.NONE);
		label.setText("搜索代码");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);
		
		
		
		searchText = new Text(container, SWT.NONE);
		searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchBtn = new Button(container, SWT.NONE);
		searchBtn.setText(" 搜 索 ");
		searchBtn.addSelectionListener(selectionAdapter);

		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		GridData gd5 = new GridData(GridData.FILL_HORIZONTAL);
		gd5.horizontalAlignment = SWT.RIGHT;
		gd5.horizontalSpan = 2;
		composite.setLayoutData(gd5);
		
		isPrivate = new Button(composite, SWT.RADIO);
		isPrivate.setSelection(true);
		isPrivate.setText("搜索私有代码库");
		
		isPublic = new Button(composite, SWT.RADIO);
		isPublic.setText("搜索公有代码库");
		
		methodList = new List(container, SWT.NONE);
		GridData gd2 = new GridData(GridData.FILL_BOTH);
		gd2.horizontalSpan = 2;
		methodList.setLayoutData(gd2);
		
		progressBar = new ProgressBar(container, SWT.NONE);
		GridData gd4 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd4.horizontalSpan = 2;
		progressBar.setLayoutData(gd4);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(580, 600);
	}
}
