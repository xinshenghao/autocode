package com.hitoo.ui.daocode.config;


import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;

import com.hitoo.config.common.CommParaBeanOperationer;
import com.hitoo.config.common.CommonParamterOperationer;
import com.hitoo.config.mbgconfig.MBGConfig;
import com.hitoo.config.mbgconfig.MBGConfigBeanOperationer;
import com.hitoo.config.model.MBGContext;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;
import com.hitoo.ui.utils.FileUtil;
import com.hitoo.ui.utils.PropertiesUtil;

public class MBGConfigUIView extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private ScrolledForm form = null;
	
	private String[] defaultModelTypeStr = new String[] {"conditional","flat","hierarchical"};
	private String[] targetRuntimeStr = new String[]{"MyBatis3","MyBatis3Simple"};
	private String[] booleanStr = new String[] {"true","false"};
	private String[] typeStr =new String[] {"ANNOTATEDMAPPER","MIXEDMAPPER","XMLMAPPER"};

	GridData gridData_fill_horiz = new GridData(GridData.FILL_HORIZONTAL);
	
	private Text id,javaFileEncoding,beginningDelimiter,endingDelimiter;
	private Combo defaultModelType,targetRuntime,autoDelimitKeywords;
	
	private Text jmg_targetPackage,jmg_targetProject;
	private Combo trimStrings,jmg_enableSubPackages;
	
	private Text smg_targetPackage,smg_targetProject;
	private Combo smg_enableSubPackages;
	
	private Text jcg_targetPackage,jcg_targetProject;
	private Combo type,jcg_enableSubPackages;
	
	private MBGConfigBeanOperationer operationer = null;
	private MBGConfig mbgConfig = null;
	private boolean isCreate = true;
	
	private Text name;
	
	IRunnableWithProgress runnable = new IRunnableWithProgress() {
		
		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			monitor.beginTask("开始执行任务", 100);
			monitor.subTask("获取初始化配置");
			monitor.worked(10);
			Thread.sleep(500);
			monitor.subTask("获取初始配置文件位置");
			monitor.worked(10);
			String filePath = null;
			if(null == mbgConfig) {
				filePath =  PropertiesUtil.getValue(PropertiesUtil.FILE_PATH, "mbgCommonConfigFile");				
			}else {
				filePath = PropertiesUtil.getValue(PropertiesUtil.FILE_PATH, "mbgConfigFile")+"/"+mbgConfig.getPath();
			}
			
			if(filePath==null) {
				MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误", "初始化配置文件路径不存在");
			}
			Thread.sleep(500);
			monitor.worked(10);
			operationer = new MBGConfigBeanOperationer(filePath);
			monitor.worked(50);
			monitor.subTask("设置初始内容");
			//任务完成
			monitor.done();
			//如果此时用户取消操作
			if (monitor.isCanceled()) {
				operationer = null;
			}
		}
	};
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MBGConfigUIView(Composite parent, int style, final MBGConfig mbgConfig) {
		super(parent, style);
		if(mbgConfig!=null) {
			this.mbgConfig = mbgConfig;
			isCreate = false;
		}
		/*addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);*/
		
		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
		try {
			progressDialog.run(true, true, runnable);
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		
		this.setLayout(new GridLayout(1, true));
		
		form = toolkit.createScrolledForm(this);
		form.setLayoutData(new GridData(GridData.FILL_BOTH));
		form.setText("设置");
		ColumnLayout layout = new ColumnLayout();
		layout.maxNumColumns = 1;
		layout.minNumColumns = 1;
		form.getBody().setLayout(layout);
		
		addContextElement();
		adJavaModelGenerator();
		addSqlMapGenerator();
		addJavaClientGenerator();
		
		Composite composite = toolkit.createComposite(form.getBody());
		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		rowLayout.fill = true;
		composite.setLayout(rowLayout);
		if(null != mbgConfig) {
			String path = mbgConfig.getPath();
			path = path.substring(0, path.length()-4);
			name = toolkit.createText(composite, path);
		}else {
			name = toolkit.createText(composite, "请在此处输入配置文件名");			
		}
		name.setLayoutData(new RowData(300, 30));
		//保存按钮
		Button save = toolkit.createButton(composite, "保存", SWT.NONE);
		save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveMBGConfig(mbgConfig);
			}
		});
		save.setLayoutData(new RowData(50, 20));
		//如果是新建就不显示删除按钮
		if(!isCreate) {
			Button delete = toolkit.createButton(composite, "删除", SWT.NONE);
			delete.setLayoutData(new RowData(50, 20));
			delete.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					delete(mbgConfig);
				}
			});			
		}
		if(operationer != null) {
			setDataToController(operationer.getMbgContext());			
		}
	}
	
	/**
	 * 获取控件的属性
	 * @return
	 */
	public MBGContext getDataFromController() {
		MBGContext result = new MBGContext();
		//基本属性
		result.setId(id.getText());
		result.setDefaultModelType(defaultModelType.getText());
		result.setTargetRuntime(targetRuntime.getText());
		result.addProperty("autoDelimitKeywords", autoDelimitKeywords.getText());
		result.addProperty("javaFileEncoding", javaFileEncoding.getText());
		result.addProperty("beginningDelimiter", beginningDelimiter.getText());
		result.addProperty("endingDelimiter", endingDelimiter.getText());
		//javaModelGenerator
		JavaModelGeneratorConfiguration javaModelGenerator = new JavaModelGeneratorConfiguration();
		javaModelGenerator.setTargetPackage(jmg_targetPackage.getText());
		javaModelGenerator.setTargetProject(jmg_targetProject.getText());
		javaModelGenerator.addProperty("enableSubPackages", jmg_enableSubPackages.getText());
		javaModelGenerator.addProperty("trimStrings", trimStrings.getText());
		result.setJavaModelGeneratorConfiguration(javaModelGenerator);
		//sqlMapGenerator
		SqlMapGeneratorConfiguration sqlMapGenerator = new SqlMapGeneratorConfiguration();
		sqlMapGenerator.setTargetPackage(smg_targetPackage.getText());
		sqlMapGenerator.setTargetProject(smg_targetProject.getText());
		sqlMapGenerator.addProperty("enableSubPackages", smg_enableSubPackages.getText());
		result.setSqlMapGeneratorConfiguration(sqlMapGenerator);
		//javaClientGenerator
		JavaClientGeneratorConfiguration javaClientGenerator = new JavaClientGeneratorConfiguration();
		javaClientGenerator.setTargetPackage(jcg_targetPackage.getText());
		javaClientGenerator.setConfigurationType(type.getText());
		javaClientGenerator.setTargetProject(jcg_targetProject.getText());
		javaClientGenerator.addProperty("enableSubPackages", jcg_enableSubPackages.getText());
		result.setJavaClientGeneratorConfiguration(javaClientGenerator);
		return result;
	}
	
	/**
	 * 将数据设置到控件上
	 * @param context
	 */
	public void setDataToController(MBGContext context) {
		id.setText(context.getId());
		setSelectItem(defaultModelType,defaultModelTypeStr,context.getDefaultModelType());
		setSelectItem(targetRuntime, targetRuntimeStr, context.getTargetRuntime());
		setSelectItem(autoDelimitKeywords, booleanStr, context.getAutoDelimitKeywords().toString());
		javaFileEncoding.setText(context.getJavaFileEncoding());
		beginningDelimiter.setText(context.getBeginningDelimiter());
		endingDelimiter.setText(context.getEndingDelimiter());
		
		jmg_targetPackage.setText(context.getJavaModelGeneratorConfiguration().getTargetPackage());
		jmg_targetProject.setText(context.getJavaModelGeneratorConfiguration().getTargetProject());
		setSelectItem(jmg_enableSubPackages, booleanStr, context.getJavaModelGeneratorConfiguration().getProperty("enableSubPackages"));
		setSelectItem(trimStrings, booleanStr, context.getJavaModelGeneratorConfiguration().getProperty("trimStrings"));
		
		smg_targetPackage.setText(context.getSqlMapGeneratorConfiguration().getTargetPackage());
		smg_targetProject.setText(context.getSqlMapGeneratorConfiguration().getTargetProject());
		setSelectItem(smg_enableSubPackages, booleanStr, context.getSqlMapGeneratorConfiguration().getProperty("enableSubPackages"));
		
		jcg_targetPackage.setText(context.getJavaClientGeneratorConfiguration().getTargetPackage());
		jcg_targetProject.setText(context.getJavaClientGeneratorConfiguration().getTargetProject());
		setSelectItem(type, typeStr, context.getJavaClientGeneratorConfiguration().getConfigurationType());
		setSelectItem(jcg_enableSubPackages, booleanStr, context.getJavaClientGeneratorConfiguration().getProperty("enableSubPackages"));
		
	}
	/**
	 * 设置下拉框的选项
	 * @param combo 
	 * @param items 
	 * @param selectedItem 
	 */
	private void setSelectItem(Combo combo, String[] items, String selectedItem) {
		for (int i=0; i<items.length; i++) {
			if (selectedItem.equals(items[i])) {
				combo.select(i);
				break;
			}
		}
	}

	/**
	 * 创建javaClientGenerator节点
	 */
	private void addJavaClientGenerator() {
		Section javaClientGeneratorSection = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		toolkit.paintBordersFor(javaClientGeneratorSection);
		//设置标题文字
		javaClientGeneratorSection.setText("javaClientGenerator");
		//创建面板，作为内容折叠区放置的控件
		Composite sectionClient = toolkit.createComposite(javaClientGeneratorSection);
		sectionClient.setLayout(new GridLayout(2, false));
		
		Label label1 = new Label(sectionClient, SWT.NONE);
		label1.setText("targetPackage:");
		jcg_targetPackage = new Text(sectionClient, SWT.NONE);
		jcg_targetPackage.setLayoutData(gridData_fill_horiz);
		
		Label label2 = new Label(sectionClient, SWT.NONE);
		label2.setText("targetProject:");
		jcg_targetProject = new Text(sectionClient, SWT.NONE);
		jcg_targetProject.setLayoutData(gridData_fill_horiz);
		
		Label label3 = new Label(sectionClient, SWT.NONE);
		label3.setText("type:");
		type = new Combo(sectionClient, SWT.NONE);
		type.setItems(typeStr);
		
		Label label4 = new Label(sectionClient, SWT.NONE);
		label4.setText("enableSubPackages:");
		jcg_enableSubPackages = new Combo(sectionClient, SWT.NONE);
		jcg_enableSubPackages.setItems(booleanStr);
		
		javaClientGeneratorSection.setClient(sectionClient);
		
		addListener(javaClientGeneratorSection);
	}
	/**
	 * 增加控件监听
	 * @param javaClientGeneratorSection
	 */
	private void addListener(Section section) {
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
	}
	/**
	 * 创建sqlMapGenerator节点
	 */
	private void addSqlMapGenerator() {
		Section sqlMapGeneratorSection = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		toolkit.paintBordersFor(sqlMapGeneratorSection);
		//设置标题文字
		sqlMapGeneratorSection.setText("sqlMapGenerator");
		//创建面板，作为内容折叠区放置的控件
		Composite sectionClient = toolkit.createComposite(sqlMapGeneratorSection);
		sectionClient.setLayout(new GridLayout(2, false));
		
		Label label1 = new Label(sectionClient, SWT.NONE);
		label1.setText("targetPackage:");
		smg_targetPackage = new Text(sectionClient, SWT.NONE);
		smg_targetPackage.setLayoutData(gridData_fill_horiz);
		
		Label label2 = new Label(sectionClient, SWT.NONE);
		label2.setText("targetProject:");
		smg_targetProject = new Text(sectionClient, SWT.NONE);
		smg_targetProject.setLayoutData(gridData_fill_horiz);
		
		Label label3 = new Label(sectionClient, SWT.NONE);
		label3.setText("enableSubPackages:");
		smg_enableSubPackages = new Combo(sectionClient, SWT.NONE);
		smg_enableSubPackages.setItems(booleanStr);
		
		sqlMapGeneratorSection.setClient(sectionClient);
		addListener(sqlMapGeneratorSection);
	}
	/**
	 * 创建javaModelGenerator节点
	 */
	private void adJavaModelGenerator() {
		Section javaModelGeneratorSection = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		toolkit.paintBordersFor(javaModelGeneratorSection);
		//设置标题文字
		javaModelGeneratorSection.setText("javaModelGenerator");
		//创建面板，作为内容折叠区放置的控件
		Composite sectionClient = toolkit.createComposite(javaModelGeneratorSection);
		sectionClient.setLayout(new GridLayout(2, false));
		
		Label label1 = new Label(sectionClient, SWT.NONE);
		label1.setText("targetPackage:");
		jmg_targetPackage = new Text(sectionClient, SWT.NONE);
		jmg_targetPackage.setLayoutData(gridData_fill_horiz);
		
		Label label2 = new Label(sectionClient, SWT.NONE);
		label2.setText("targetProject:");
		jmg_targetProject = new Text(sectionClient, SWT.NONE);
		jmg_targetProject.setLayoutData(gridData_fill_horiz);
		
		Label label3 = new Label(sectionClient, SWT.NONE);
		label3.setText("trimStrings:");
		trimStrings = new Combo(sectionClient, SWT.NONE);
		trimStrings.setItems(booleanStr);
		
		Label label4 = new Label(sectionClient, SWT.NONE);
		label4.setText("enableSubPackages:");
		jmg_enableSubPackages = new Combo(sectionClient, SWT.NONE);
		jmg_enableSubPackages.setItems(booleanStr);
		
		javaModelGeneratorSection.setClient(sectionClient);
		addListener(javaModelGeneratorSection);
		
	}
	/**
	 * Context节点的属性以及property
	 */
	private void addContextElement() {
		Section contextSection = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		toolkit.paintBordersFor(contextSection);
		//设置标题文字
		contextSection.setText("context");
		//创建面板，作为内容折叠区放置的控件
		Composite sectionClient = toolkit.createComposite(contextSection);
		sectionClient.setLayout(new GridLayout(2, false));
		
		Label label1 = new Label(sectionClient, SWT.NONE);
		label1.setText("id:");
		id = new Text(sectionClient, SWT.NONE);
		id.setLayoutData(gridData_fill_horiz);
		
		Label label2 = new Label(sectionClient, SWT.NONE);
		label2.setText("defaultModelType:");
		defaultModelType = new Combo(sectionClient, SWT.SIMPLE);
		defaultModelType.setItems(defaultModelTypeStr);
		
		Label label3 = new Label(sectionClient, SWT.NONE);
		label3.setText("defaultModelType:");
		targetRuntime = new Combo(sectionClient, SWT.SIMPLE);
		targetRuntime.setItems(targetRuntimeStr);
		
		Label label4 = new Label(sectionClient, SWT.NONE);
		label4.setText("autoDelimitKeywords:");
		autoDelimitKeywords = new Combo(sectionClient, SWT.NONE);
		autoDelimitKeywords.setItems(booleanStr);
		
		Label label5 = new Label(sectionClient, SWT.NONE);
		label5.setText("javaFileEncoding:");
		javaFileEncoding = new Text(sectionClient, SWT.NONE);
		javaFileEncoding.setLayoutData(gridData_fill_horiz);
		
		Label label6 = new Label(sectionClient, SWT.NONE);
		label6.setText("beginningDelimiter:");
		beginningDelimiter = new Text(sectionClient, SWT.NONE);
		beginningDelimiter.setText("`");
		beginningDelimiter.setLayoutData(gridData_fill_horiz);
		
		Label label7 = new Label(sectionClient, SWT.NONE);
		label7.setText("endingDelimiter:");
		endingDelimiter = new Text(sectionClient, SWT.NONE);
		endingDelimiter.setText("`");
		endingDelimiter.setLayoutData(gridData_fill_horiz);
		
		
		contextSection.setClient(sectionClient);
		addListener(contextSection);
	}

	/**
	 * 保存MBGConfig配置
	 * @param mbgConfig 
	 */
	private void saveMBGConfig(MBGConfig mbgConfig) {
		CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
		CommParaBeanOperationer commParaBeanOperationer = (CommParaBeanOperationer) ApplicationContextHelper.getBean("commParaBeanOperationer");
		//获取文件名
		String nameS = name.getText(); 
		String path = PropertiesUtil.getValue(PropertiesUtil.FILE_PATH, "mbgConfigFile")+"/"+nameS+".xml";
		//如果是修改，先删除原来的文件
		if(null != mbgConfig) {
			commonParamterOperationer.deleteMBGConfig(mbgConfig);
			commParaBeanOperationer.deleteMBGConfig(mbgConfig);
		}
		//保存至文件中
		operationer.setMbgContext(getDataFromController());
		operationer.saveToFile(path);
		//构建实体
		MBGConfig tmp = new MBGConfig();
		tmp.setCanDelete(true);
		tmp.setSelect(true);
		tmp.setPath(nameS+".xml");
		//新增到持久化容器的实体中
		commParaBeanOperationer.addMBGConfig(tmp);
		//新增到配置文件中
		commonParamterOperationer.addMBGConfig(tmp);
		//刷新视图
		AutoCode.initCreateDaoCodeMenu();
		//显示信息
		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "成功", "配置文件保存成功！！");
		//关闭Tabitem
		AutoCode.getTabFolder().getSelection().dispose();
	}

	private void delete(MBGConfig mbgConfig) {
		if(!mbgConfig.getCanDelete()) {
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "警告", "无法删除系统默认配置文件");
		}else {
			boolean b = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "确认", "是否删除"+mbgConfig.getPath()+"配置文件");
			if(b) {
				String path = PropertiesUtil.getValue(PropertiesUtil.FILE_PATH, "mbgConfigFile")+"/"+mbgConfig.getPath();
				//删除文件
				FileUtil.deleteFile(path);
				//删除持久化容器中的实体
				CommParaBeanOperationer commParaBeanOperationer = (CommParaBeanOperationer) ApplicationContextHelper.getBean("commParaBeanOperationer");
				commParaBeanOperationer.deleteMBGConfig(mbgConfig);
				//删除配置文件中的配置
				CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
				commonParamterOperationer.deleteMBGConfig(mbgConfig);
				//显示信息
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "成功", "删除文件成功！！");
				//关闭Tabitem
				AutoCode.getTabFolder().getSelection().dispose();
				//刷新主视图
				AutoCode.initCreateDaoCodeMenu();
			}
		}
	}
	
	

}
