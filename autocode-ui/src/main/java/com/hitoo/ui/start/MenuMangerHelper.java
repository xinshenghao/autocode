package com.hitoo.ui.start;

import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.mbgconfig.MBGConfig;
import com.hitoo.config.model.DBConnection;
import com.hitoo.config.model.TableConfig;
import com.hitoo.ui.codecreatmenu.controller.CreateControllerCodeAction;
import com.hitoo.ui.codecreatmenu.service.CreateServiceCodeAction;
import com.hitoo.ui.codemanager.privatecode.PrivateCodeManagerAction;
import com.hitoo.ui.codemanager.search.SearchCodeAction;
import com.hitoo.ui.codemanager.uploadcode.UploadCodeAction;
import com.hitoo.ui.daocode.config.MBGConfigInforAction;
import com.hitoo.ui.daocode.config.MBGConfigManagerAction;
import com.hitoo.ui.daocode.createcode.DaoCodeCreaterAction;
import com.hitoo.ui.daocode.table.TableConfigInforAction;
import com.hitoo.ui.daocode.table.TableConfigManagerAction;
import com.hitoo.ui.dbmenu.CreateDBAction;
import com.hitoo.ui.dbmenu.connmanager.ConnDBInforAction;
import com.hitoo.ui.dbmenu.connmanager.DBConnManagerAction;
import com.hitoo.ui.dbmenu.opendb.OpenDBAction;
import com.hitoo.ui.filemenu.ExitAction;
import com.hitoo.ui.filemenu.ExportToEclipseWorkSpaceAction;
import com.hitoo.ui.filemenu.PreferenceAction;
import com.hitoo.ui.filemenu.createproject.CreateEclipseProjectAction;

public class MenuMangerHelper {
	private CommonParameter commonParameter;
	
	//首选项
	private static PreferenceAction preferenceAction;
	//退出
	private static ExitAction exitAction;
	
	//数据库连接菜单
	private static MenuManager connDBMenu;
	//mbg配置文件管理
	private static MenuManager mbgConfigManagerMenu;
	//mbg表格生成策略管理
	private static MenuManager mbgTableConfManagerMenu;
	
	public MenuMangerHelper(CommonParameter commonParameter) {
		this.commonParameter = commonParameter;
	}

	public MenuManager createMenuBar() {
		MenuManager menuManager = new MenuManager();
		//文件菜单
		MenuManager fileMenu = createFileMenu();
		menuManager.add(fileMenu);
		//数据库菜单
		MenuManager dbMenu = createDBMenu();
		menuManager.add(dbMenu);
		//Dao层代码生成
		MenuManager createDaoCodeMenu=createDaoCodeMenu( );
		menuManager.add(createDaoCodeMenu);
		//公共代码管理
		MenuManager codeManagerMenu = createCodeManagerMenu();
		menuManager.add(codeManagerMenu);
		//其他层代码生成
		MenuManager createCodeMangerMenu = createMenu();
		menuManager.add(createCodeMangerMenu);
		
		return menuManager;
	}
	
	private MenuManager createFileMenu() {
		//文件
		MenuManager fileMenu=new MenuManager("文件");
		//文件--新建
		MenuManager createProjectMenu=new MenuManager("新建");
		CreateEclipseProjectAction createEclipseProjectAction = new CreateEclipseProjectAction();
		createProjectMenu.add(createEclipseProjectAction);
		fileMenu.add(createProjectMenu);
		//文件--导出
		MenuManager exportProjectMenu=new MenuManager("导出");
		ExportToEclipseWorkSpaceAction exportToEclipseWorkSpaceAction = new ExportToEclipseWorkSpaceAction();
		exportProjectMenu.add(exportToEclipseWorkSpaceAction);
		fileMenu.add(exportProjectMenu);
		//文件--首选项
		preferenceAction = new PreferenceAction();
		fileMenu.add(preferenceAction);
		//文件--退出
		exitAction = new ExitAction(AutoCode.getApp()); 
		fileMenu.add(exitAction);
		return fileMenu;
	}

	private MenuManager createMenu() {
		MenuManager createCodeMangerMenu = new MenuManager("业务代码生成");
		
		//生成Service层代码
		CreateServiceCodeAction createServiceCodeAction = new CreateServiceCodeAction();
		createCodeMangerMenu.add(createServiceCodeAction);
		//生成Controller层代码
		CreateControllerCodeAction createControllerCodeAction = new CreateControllerCodeAction();
		createCodeMangerMenu.add(createControllerCodeAction);
		return createCodeMangerMenu;
	}
	/**
	 * 公共代码管理菜单项
	 * @return
	 */
	private MenuManager createCodeManagerMenu() {
		MenuManager codeManagerMenu = new MenuManager("公共代码");
		//公共代码--搜索
		SearchCodeAction searchCodeAction = new SearchCodeAction();
		codeManagerMenu.add(searchCodeAction);
		//公共代码－－上传代码
		UploadCodeAction uploadCodeAction = new UploadCodeAction();
		codeManagerMenu.add(uploadCodeAction);
		//公共代码－－私有代码管理
		PrivateCodeManagerAction privateCodeManagerAction = new PrivateCodeManagerAction();
		codeManagerMenu.add(privateCodeManagerAction);
		return codeManagerMenu;
	}
	/**
	 * Dao层代码生成菜单项
	 * @return
	 */
	private MenuManager createDaoCodeMenu() {
		//dao层代码生成
		MenuManager createDaoCodeMenu = new MenuManager("Dao层代码生成");
		//dao层代码生成---环境配置
		mbgConfigManagerMenu = new MenuManager("环境配置");
		initCreateDaoCodeMenu();
		createDaoCodeMenu.add(mbgConfigManagerMenu);
		//dao层代码生产---表生成策略管理
		mbgTableConfManagerMenu = new MenuManager("表生成策略");
		initTableConfigeMenu();
		createDaoCodeMenu.add(mbgTableConfManagerMenu);
		//dao层代码生成---代码生成
		createDaoCodeMenu.add(new DaoCodeCreaterAction());
		return createDaoCodeMenu;
	}
	/**
	 * 初始化Dao层代码表格配置管理菜单
	 */
	public void initTableConfigeMenu() {
		mbgTableConfManagerMenu.removeAll();
		List<TableConfig> tableConfigs = commonParameter.getTableConfigs();
		if(tableConfigs!=null && tableConfigs.size()!=0) {
			for (TableConfig tableConfig : tableConfigs) {
				mbgTableConfManagerMenu.add(new TableConfigInforAction(tableConfig));				
			}
		}
		mbgTableConfManagerMenu.add(new Separator());
		mbgTableConfManagerMenu.add(new TableConfigManagerAction());
	}
	/**
	 * 初始化Dao层代码生成环境配置管理菜单
	 */
	public void initCreateDaoCodeMenu() {
		mbgConfigManagerMenu.removeAll();
		List<MBGConfig> mbgConfigs = commonParameter.getMbgConfigs();
		if(mbgConfigs!=null && mbgConfigs.size()!=0) {
			for (MBGConfig mbgConfig : mbgConfigs) {
				mbgConfigManagerMenu.add(new MBGConfigInforAction(mbgConfig));
			}
		}
		mbgConfigManagerMenu.add(new Separator());
		mbgConfigManagerMenu.add(new MBGConfigManagerAction());
	}
	/**
	 * 数据库菜单项
	 * @return
	 */
	private MenuManager createDBMenu() {
		//数据库
		MenuManager dbMenu=new MenuManager("数据库");
		//数据库--创建数据库
		CreateDBAction createDBAction = new CreateDBAction();
		dbMenu.add(createDBAction);
		//数据库--打开数据库
		OpenDBAction openDBAction = new OpenDBAction();
		dbMenu.add(openDBAction);
		//数据库--数据库连接菜单
		connDBMenu = new MenuManager("数据库连接");
		//获取现有的数据库菜单
		initConnDBMenu();
		dbMenu.add(connDBMenu);
		return dbMenu;
	}

	/**
	 * 初始化数据库连接管理子菜单
	 */
	public void initConnDBMenu() {
		connDBMenu.removeAll();
		List<DBConnection> connections = commonParameter.getDbConnections();
		if(connections != null && connections.size() != 0) {
			for (DBConnection dbConnection : connections) {
				connDBMenu.add(new ConnDBInforAction(dbConnection));
			}
		}
		connDBMenu.add(new Separator());
		connDBMenu.add(new DBConnManagerAction());
	}
}
