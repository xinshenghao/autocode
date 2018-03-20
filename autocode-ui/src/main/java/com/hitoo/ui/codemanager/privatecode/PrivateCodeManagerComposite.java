package com.hitoo.ui.codemanager.privatecode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.hitoo.server.client.codemanger.busniess.ElasticSearchRequest;
import com.hitoo.server.client.codemanger.domain.PrivateMethod;
import com.hitoo.ui.utils.PropertiesUtil;
import com.hitoo.ui.utils.StringUtil;
import com.hitoo.ui.widget.MessageUtil;

public class PrivateCodeManagerComposite extends Composite {
	
	private String url = PropertiesUtil.getValue(PropertiesUtil.CLIENT_PARAMTER_PATH, "es.requestURL");
	private static final String REQUEST_DELETE = "privateMethodCode/deletePrivateMethod.do";
	private static final String REQUEST_UPDATE = "privateMethodCode/updatePrivateMethod.do";
	private ElasticSearchRequest elasticSearchRequest;

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	private ScrolledForm form;
	private Text methodNameIn;
	private Text methodTitleIn;
	private Text methodDescrIn;
	private Text methodContentIn;
	
	private List list;
	
	private int index = 0;
	private PrivateMethod currentMethod;
	
	SelectionAdapter selectedListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			index = list.getSelectionIndex();
			currentMethod = privateMethods.get(index);
			setDataToForm(currentMethod);
		}
	};
	
	SelectionAdapter deleteListener = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent e) {
			boolean isDelete = MessageUtil.confirm("是否删除"+currentMethod.getMethodName()+"方法");
			if(isDelete) {
				deletePrivateMethod();
			}
		}

		private void deletePrivateMethod() {
			elasticSearchRequest.deletePrivateMethod(currentMethod.getMethodId(), REQUEST_DELETE);
			
			list.remove(index);
			setDataToForm(new PrivateMethod());
			
			MessageUtil.infor("删除成功");
		}
	};
	
	SelectionAdapter saveListener = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent e) {
			updatePrivateMethod();
		}

		private void updatePrivateMethod() {
			PrivateMethod tmp = getDataFromForm();
			
			Map<String, String> paramter = new HashMap<>();
			paramter.put("methodId", currentMethod.getMethodId());
			paramter.put("userId", currentMethod.getUserId());
			paramter.put("methodName", tmp.getMethodName());
			paramter.put("methodTitle", tmp.getMethodTitle());
			paramter.put("methodDescr", tmp.getMethodDescr());
			paramter.put("methodContent", tmp.getMethodContent());

			elasticSearchRequest.updatePrivateMethod(paramter, REQUEST_UPDATE);
			
			MessageUtil.infor("信息修改成功!!");
		}
		
	};

	private java.util.List<PrivateMethod> privateMethods ;
	private Group group_1;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @param privateMethods 
	 */
	public PrivateCodeManagerComposite(Composite parent, int style, ArrayList<PrivateMethod> privateMethods) {
		super(parent, style);
		this.privateMethods=  privateMethods;
		this.elasticSearchRequest = new ElasticSearchRequest(url);
		
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.HORIZONTAL|SWT.BORDER);
		sashForm.setLayout(new FillLayout());
		
		initLeft(sashForm);
		
		initRight(sashForm);
		
		if(privateMethods.size()>0) {
			list.setSelection(0);
			currentMethod = privateMethods.get(0);
			setDataToForm(currentMethod);
		}
		
		toolkit.adapt(sashForm);
		toolkit.paintBordersFor(sashForm);
		sashForm.setWeights(new int[] {30, 70});
	}


	private void initLeft(SashForm sashForm) {
		Group group = new Group(sashForm, SWT.NONE);
		group.setText("概述");
		group.setLayout(new FillLayout());
		list = new List(group, SWT.NONE);
		for (PrivateMethod privateMethod : privateMethods) {
			list.add(privateMethod.getMethodName());
		}
		
		list.addSelectionListener(selectedListener);
	}

	private void initRight(SashForm sashForm) {
		form = toolkit.createScrolledForm(sashForm);
		form.setText("详细情况");
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginTop = 10;
		gridLayout.marginBottom = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginRight = 10;
		form.getBody().setLayout(gridLayout);
		
		Label lblNewLabel = toolkit.createLabel(form.getBody(), "方法名", SWT.NONE);
		
		methodNameIn = toolkit.createText(form.getBody(), null, SWT.NONE);
		methodNameIn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label lblNewLabel_1 = toolkit.createLabel(form.getBody(), "方法标题", SWT.NONE);
		
		methodTitleIn = toolkit.createText(form.getBody(), null, SWT.NONE);
		methodTitleIn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label lblNewLabel_2 = toolkit.createLabel(form.getBody(), "方法概述", SWT.NONE);
		
		methodDescrIn = toolkit.createText(form.getBody(), null, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		methodDescrIn.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label lblNewLabel_3 = toolkit.createLabel(form.getBody(), "方法代码", SWT.NONE);
		
		methodContentIn = toolkit.createText(form.getBody(), null, SWT.MULTI|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		methodContentIn.setLayoutData(new GridData(GridData.FILL_BOTH));
		new Label(form.getBody(), SWT.NONE);
		
		group_1 = new Group(form.getBody(), SWT.SHADOW_NONE);
		group_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(group_1);
		toolkit.paintBordersFor(group_1);
		group_1.setLayout(new RowLayout());
		
		Button deleteBtn = toolkit.createButton(group_1, "删除", SWT.NONE);
		deleteBtn.setBounds(0, 0, 104, 30);
		deleteBtn.addSelectionListener(deleteListener);
		
		
		Button saveBtn = toolkit.createButton(group_1, "保存", SWT.NONE);
		saveBtn.setBounds(0, 0, 104, 30);
		saveBtn.addSelectionListener(saveListener);
		
	}
	/**
	 * 设置数据
	 * @param privateMethod
	 */
	private void setDataToForm(PrivateMethod privateMethod) {
		if(null != privateMethod) {
			methodNameIn.setText(privateMethod.getMethodName());
			methodTitleIn.setText(privateMethod.getMethodTitle());
			methodContentIn.setText(privateMethod.getMethodContent());
			methodDescrIn.setText(privateMethod.getMethodDescr());			
		}else {
			methodNameIn.setText("");
			methodTitleIn.setText("");
			methodContentIn.setText("");
			methodDescrIn.setText("");
		}
	}
	
	private PrivateMethod getDataFromForm() {
		String name = methodNameIn.getText();
		String title = methodTitleIn.getText();
		String descr = methodDescrIn.getText();
		String content = methodContentIn.getText();
		
		if(StringUtil.isEmpty(name, title, descr, content)){
			MessageUtil.error("信息不能有空值!!");
		}
		
		PrivateMethod result = new PrivateMethod();
		result.setMethodId(currentMethod.getMethodId());
		result.setMethodName(name);
		result.setMethodTitle(title);
		result.setMethodDescr(descr);
		result.setMethodContent(content);
		
		return result;
	}
	
}
