package com.hitoo.ui.start;

import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.internal.menus.MenuHelper;

import com.hitoo.config.FilePathBean;
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
import com.hitoo.ui.projecttree.ProjectTreeViewComposite;

import swing2swt.layout.BorderLayout;
/**
 * 主页面
 * @author xsh
 *
 */
public class AutoCode extends ApplicationWindow {
	
	private ExitAction exitAction = null;
	
	private static AutoCode app; //主程序窗口
 	private static CTabFolder tabFolder; 
 	private static MenuManager connDBMenu = null;//数据库管理菜单
 	private static MenuManager mbgConfigManagerMenu = null;
 	private static MenuManager mbgTableConfManagerMenu = null;
 	private static ProjectTreeViewComposite treeViewComposite = null;
 	
 	//图片资源
 	private ImageRegistry imageRegistry = new ImageRegistry();
 	private String imagePath = FilePathBean.getImgsFolder();
 	private static final String IMG_PROJECT = "project";
 	
 	private static CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
 	
 	private static MenuMangerHelper menuMangerHelper = new MenuMangerHelper(commonParameter);
	
	/**
	 * Create the application window.
	 */
	public AutoCode() {
		super(null);
		app = this;
		initProject();
		initResource();
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}
	/**
	 * 初始化项目
	 */
	private void initProject() {
		SpringStartedListener s = (SpringStartedListener) ApplicationContextHelper.getBean("springStartedListener");
		s.init();
	}
	/**
	 * 初始化资源
	 */
	private void initResource() {
		imageRegistry.put(IMG_PROJECT, ImageDescriptor.createFromImageData(new ImageData(imagePath+"/project.png")));
	}
	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new BorderLayout(0, 0));

		SashForm form = new SashForm(container, SWT.HORIZONTAL|SWT.BORDER);
		form.setLayout(new FillLayout());
		
		Composite left = new Composite(form, SWT.NONE);
		left.setLayout(new FillLayout());
		createLeftSashForm(left);//创建左侧选项卡区域
		
		
		Composite right = new Composite(form, SWT.NONE);
		right.setLayout(new FillLayout());
		createRightSashForm(right); //创建右侧选项卡区域
		//设置初始占比
		form.setWeights(new int[]{30,70});
		return container;
	}
	/**
	 * 创建左侧选项卡区域
	 * @param left
	 */
	private void createLeftSashForm(Composite left) {
		TabFolder leftTabFolder = new TabFolder(left, SWT.BORDER);
		leftTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		TabItem projItem = new TabItem(leftTabFolder, SWT.NONE);
		projItem.setText(" 项目 ");
		
		treeViewComposite = new ProjectTreeViewComposite(leftTabFolder, SWT.NONE);
		
		projItem.setControl(treeViewComposite);
		projItem.setImage(imageRegistry.get(IMG_PROJECT));
	}
	/**
	 * 刷新文件树视图
	 */
	public static void reflushTreeView() {
		treeViewComposite.reflushTreeView();
	}
	
	
	/**
	 * 创建右侧选项卡区域
	 * @param composite
	 */
	private void createRightSashForm(Composite composite) {
		//右侧选项卡区域
		tabFolder = new CTabFolder(composite, SWT.BORDER | SWT.CLOSE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//带圆角的选项卡标签
		tabFolder.setSimple(false);
		//设置未选中标签，关闭按钮状态
		tabFolder.setUnselectedCloseVisible(true);
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
		exitAction = new ExitAction(this);
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		return menuMangerHelper.createMenuBar();
	}
	
	/**
	 * 初始化Dao层代码表格配置管理菜单
	 */
	public static void initTableConfigeMenu() {
		menuMangerHelper.initTableConfigeMenu();
	}
	/**
	 * 初始化Dao层代码生成环境配置管理菜单
	 */
	public static void initCreateDaoCodeMenu() {
		menuMangerHelper.initCreateDaoCodeMenu();
	}
	/**
	 * 初始化数据库连接管理子菜单
	 */
	public static void initConnDBMenu() {
		menuMangerHelper.initConnDBMenu();
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		toolBarManager.add(exitAction);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		statusLineManager.setMessage("欢迎使用");
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			AutoCode window = new AutoCode();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("AutoCode");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(1200, 700);
	}
	
	public static AutoCode getApp() {
		return app;
	}
	public static CTabFolder getTabFolder() {
		return tabFolder;
	}

}
