package com.hitoo.ui.daocode.createcode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.mbgconfig.MBGConfig;
import com.hitoo.config.mbgconfig.MBGConfigFileKey;
import com.hitoo.config.mbgconfig.MBGConfigOperationer;
import com.hitoo.config.model.DBConnection;
import com.hitoo.config.model.TableConfig;
import com.hitoo.config.projectconfig.SpringProjectConfigFileOperationer;
import com.hitoo.config.projectinfor.ProjectInfor;
import com.hitoo.config.utils.XmlUtil;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;
import com.hitoo.ui.start.RuntimeParamter;
import com.hitoo.ui.utils.FileUtil;
import com.hitoo.ui.utils.PropertiesUtil;

public class CreateDaoCodeWizard extends Wizard {
	
	//选择表格
	private SelectedTableWizardPage selectedTableWizardPage;
	//选择MBG配置文件
	private SelectMBGConfigWizardPage selectMBGConfigWizardPage;
	//选择表格实体生成策略
	private SelectTableConfigWizardPage selectTableConfigWizardPage;
	
	private CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
	private RuntimeParamter runtimeParamter = (RuntimeParamter) ApplicationContextHelper.getBean("runtimeParamter");
	private ProjectInfor projectInfor = (ProjectInfor) ApplicationContextHelper.getBean("projectInfor");
	private SpringProjectConfigFileOperationer springProjectConfigFileOperationer = (SpringProjectConfigFileOperationer) ApplicationContextHelper.getBean("springProjectConfigFileOperationer");
	
	private List<String> selectedTables;
	private TableConfig tableConfig;
	private MBGConfig mbgConfig;
	
	public CreateDaoCodeWizard() {
		setWindowTitle("Dao层代码创建");
		selectedTableWizardPage = new SelectedTableWizardPage(commonParameter);
		selectMBGConfigWizardPage = new SelectMBGConfigWizardPage(commonParameter.getMbgConfigs());
		selectTableConfigWizardPage = new SelectTableConfigWizardPage(commonParameter.getTableConfigs());
	}

	@Override
	public void addPages() {
		this.addPage(selectedTableWizardPage);
		this.addPage(selectMBGConfigWizardPage);
		this.addPage(selectTableConfigWizardPage);
	}

	@Override
	public boolean performFinish() {
		selectedTables = selectedTableWizardPage.getSelectedTablesName();
		if(selectedTables==null || selectedTables.size()==0) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误", "请选择要生成实体的表!!!");
			return false;
		}
		mbgConfig = selectMBGConfigWizardPage.getSelectedMBGConfig();
		if(null == mbgConfig) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误", "请选择要使用的配置文件!!!");
			return false;
		}
		tableConfig = selectTableConfigWizardPage.getSelectedTableConfig();
		if(null == tableConfig) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "错误", "请选择要使用的表格实体生成策略!!!");
		}
		
		ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
		try {
			progressMonitorDialog.run(true, true, runnable);
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

	IRunnableWithProgress runnable = new IRunnableWithProgress() {
		
		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			// 设置显示在UI界面上的线程信息
			monitor.beginTask("生成Dao层代码", 100);
			
			monitor.subTask("获取配置文件信息");
			sleep();
			monitor.worked(10);
			
			monitor.subTask("获取数据库连接信息");
			sleep();
			monitor.worked(10);
			DBConnection dbConnection = getSelectedDBConnection();
			String dataBaseName = runtimeParamter.getSelectedDataBase();
			
			monitor.subTask("获取表格实体生成策略");
			sleep();
			monitor.worked(10);
			
			monitor.subTask("生成临时配置文件");
			sleep();
			monitor.worked(10);
			createMBGConfigFile(mbgConfig, tableConfig, dbConnection, dataBaseName, selectedTables);
			
			monitor.subTask("生成Dao层代码");
			sleep();
			monitor.worked(10);
			createDaoCode();
			
			monitor.subTask("复制代码至项目");
			sleep();
			monitor.worked(10);
			copeFile();
			
			monitor.subTask("设置项目配置文件");
			sleep();
			monitor.worked(10);
			setSpringProjectConfigurtion(dbConnection, dataBaseName);
			
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					AutoCode.reflushTreeView();
				}
			});
			
			//任务完成后设置此任务已经完成
			monitor.done();
			//如果此时为用户取消的操作
			if (monitor.isCanceled()) {
				throw new InterruptedException("用户取消了操作");
			}
		}

	};
	/**
	 * 配置springboot项目的数据库连接
	 * @param dataBaseName 
	 * @param dbConnection 
	 */
	private void setSpringProjectConfigurtion(DBConnection dbConnection, String dataBaseName) {
		springProjectConfigFileOperationer.setDbConnection(dbConnection, dataBaseName);
	}
	/**
	 * 复制代码到项目中
	 */
	private void copeFile() {
		XmlUtil xml = new XmlUtil("tmp/generatorConfig.xml");
		Element context = xml.getRootElement().element(MBGConfigFileKey.ELE_CONTEXT);
		
		String domain = context.element(MBGConfigFileKey.ELE_JAVAMODELGENERATOR).attribute(MBGConfigFileKey.ATTR_TARGETPACKAGE).getText();
		copyBaseFolder(domain, "/src/main/java/", false);
		
		String dao = context.element(MBGConfigFileKey.ELE_JAVACLIENTGENERATOR).attributeValue(MBGConfigFileKey.ATTR_TARGETPACKAGE);
		copyBaseFolder(dao, "/src/main/java/", false);
		
		String map = context.element(MBGConfigFileKey.ELE_SQLMAPGENERATOR).attributeValue(MBGConfigFileKey.ATTR_TARGETPACKAGE);
		copyBaseFolder(map, "/src/main/resources/", true);
	}

	private void copyBaseFolder(String packageName, String basePackage, boolean isMapping) {
		String fromPath = getCopyFromFolder(packageName);
		String toPath = getCopyToFolder(fromPath, basePackage, isMapping);
		fromPath = "tmp/"+fromPath;
		FileUtil.copy(fromPath, toPath);
	}

	private String getCopyToFolder(String fromPath, String copyToPackage, boolean isMapping) {
		String toPath = commonParameter.getWorkSpace()+"/"+projectInfor.getProjectName()+copyToPackage;
		if(!isMapping) {
			String[] tmp = projectInfor.getGroupId().split("\\.");
			for (String string : tmp) {
				toPath += string+"/";
			}
			toPath += projectInfor.getProjectName() +"/";
		}
		toPath += fromPath;
		return toPath;
	}
	/**
	 * 获取复制
	 * @param domain
	 * @return
	 */
	private String getCopyFromFolder(String domain) {
		String[] paths = domain.split("\\.");
		String path = "";
		for (String str : paths) {
			path += str+"/";
		}
		path = path.substring(0, path.length()-1);
		return path;
	}
	/**
	 * 线程休眠半秒方便获取信息
	 */
	private void sleep() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成MBG配置文件
	 * @param selectedTables 
	 * @param dataBaseName 
	 * @param dbConnection 
	 * @param mbgConfig 
	 * @param tableConfig 
	 */
	private void createMBGConfigFile(MBGConfig mbgConfig, TableConfig tableConfig, DBConnection dbConnection, String dataBaseName, List<String> selectedTables) {
		String configFilePath = PropertiesUtil.getValue(PropertiesUtil.FILE_PATH, "mbgConfigFile")+"/"+mbgConfig.getPath();
		MBGConfigOperationer mbgConfigOperationer = new MBGConfigOperationer(configFilePath);
		mbgConfigOperationer.addJdbcConnectionElement(dbConnection,dataBaseName);
		mbgConfigOperationer.addTableElement(selectedTables, tableConfig);
		String tmpConfigFilePath = PropertiesUtil.getValue(PropertiesUtil.FILE_PATH, "mbgConfigFileTmp");
		mbgConfigOperationer.writeToFile(tmpConfigFilePath);
	}

	/**
	 * 生成代码
	 */
	private void createDaoCode() {
		String configFilePath = PropertiesUtil.getValue(PropertiesUtil.FILE_PATH, "mbgConfigFileTmp");
		try {
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			File configFile = new File(configFilePath);
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
		} catch (IOException | XMLParserException | InvalidConfigurationException | SQLException
				| InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取选中的数据库连接信息
	 * @return
	 */
	private DBConnection getSelectedDBConnection() {
		DBConnection result = null ;
		List<DBConnection> list = commonParameter.getDbConnections();
		for (DBConnection dbConnection : list) {
			if(dbConnection.getSelect()) {
				result = dbConnection;
			}
		}
		return result;
	}


	@Override
	public boolean canFinish() {
		IWizardPage tmpPage = this.getContainer().getCurrentPage();
		if(tmpPage == selectTableConfigWizardPage) {
			return true;
		}else {
			return false;
		}
	}

	
}
