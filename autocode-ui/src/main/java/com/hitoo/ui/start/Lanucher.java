package com.hitoo.ui.start;

import java.io.File;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.common.CommonParamterOperationer;
/**
 * 启动器
 * @author xsh
 *
 */
public class Lanucher {

	protected Shell shlAutocodeLauncher;

	private Button okBtn = null;
	private Button cancelBtn = null;
	
	private List<String> workSpaces = null;
	
	private CommonParameter commonParameter = null;
	
	//不再询问选择框
	Button setDefaultCheck = null;
	//下拉列表
	Combo selectWorkSpaceComb = null;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
			Lanucher window = new Lanucher();
			
			CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
			window.commonParameter = commonParameter;
			window.workSpaces = commonParameter.getWorkSpaces();
			
			String defaultWorkSpace = commonParameter.getWorkSpace();
			if(null != defaultWorkSpace) {
				//启动主页面
				AutoCode.main(null);
				return ;
			}
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 /**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlAutocodeLauncher.open();
		shlAutocodeLauncher.layout();
		while (!shlAutocodeLauncher.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlAutocodeLauncher = new Shell(SWT.CLOSE);
		shlAutocodeLauncher.setSize(720, 360);
		shlAutocodeLauncher.setText("autoCode Launcher");
		shlAutocodeLauncher.setLayout(new FormLayout());
		//初始化描述部分
		Group describe = initDescribe();
		//提示信息
		Label lblNewLabel_2 = new Label(shlAutocodeLauncher, SWT.NONE);
		FormData fd_lblNewLabel_2 = new FormData();
		fd_lblNewLabel_2.top = new FormAttachment(describe, 34);
		lblNewLabel_2.setLayoutData(fd_lblNewLabel_2);
		lblNewLabel_2.setText("工作空间：");
		//初始化下拉列表
		Combo selectWorkSpaceComb = initSelectWorkSpaceComb(describe, lblNewLabel_2, fd_lblNewLabel_2);
		//初始化浏览按钮
		initBrowerBtn(selectWorkSpaceComb);
		//初始化不再询问选择框
		initDefaultCheck(lblNewLabel_2, selectWorkSpaceComb);
		//初始化确认按钮
		FormData fd_okBtn = initOkBtn();
		//初始化取消按钮
		initCancelBtn(fd_okBtn);
	}

	/**
	 * 初始化取消按钮
	 * @param fd_okBtn
	 */
	private void initCancelBtn(FormData fd_okBtn) {
		cancelBtn = new Button(shlAutocodeLauncher, SWT.NONE);
		cancelBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shlAutocodeLauncher.close();
			}
		});
		fd_okBtn.left = new FormAttachment(0, 550);
		FormData fd_cancelBtn = new FormData();
		fd_cancelBtn.bottom = new FormAttachment(okBtn, 0, SWT.BOTTOM);
		fd_cancelBtn.right = new FormAttachment(okBtn, -41);
		fd_cancelBtn.left = new FormAttachment(0, 379);
		cancelBtn.setLayoutData(fd_cancelBtn);
		cancelBtn.setText("Cancel");
	}

	/**
	 * 初始化确认按钮
	 * @return
	 */
	private FormData initOkBtn() {
		okBtn = new Button(shlAutocodeLauncher, SWT.NONE);
		okBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				CommonParamterOperationer commonParamterOperationer = (CommonParamterOperationer) ApplicationContextHelper.getBean("commonParamterOperationer");
				
				//获取下拉框中的值
				String directoryPath = selectWorkSpaceComb.getText();
				//获得workspace节点
				//Element workSpace = getWorkSpaceElement();
				//获取是否选中不再询问
				Boolean isSelected = setDefaultCheck.getSelection();
				commonParameter.setWorkSpace(directoryPath);
				if(isSelected){
					commonParamterOperationer.setDefaultWorkSpace(directoryPath);
				}
				commonParamterOperationer.addWorkSpace(directoryPath);
				//查看选中的文件夹是否存在，不存在就新建
				createFoler(directoryPath);
				//打开主页面，关闭Launcher页面
				shlAutocodeLauncher.close();
				AutoCode.main(null);
			}

			private void createFoler(String directoryPath) {
				File workSpace = new File(directoryPath);
				if(!workSpace.exists()) {
					workSpace.mkdirs();
				}
			}
		});
		FormData fd_okBtn = new FormData();
		fd_okBtn.bottom = new FormAttachment(100, -26);
		fd_okBtn.right = new FormAttachment(100, -46);
		okBtn.setLayoutData(fd_okBtn);
		okBtn.setText("OK");
		return fd_okBtn;
	}

	/**
	 * 初始化不再询问选择框
	 * @param lblNewLabel_2
	 * @param selectWorkSpaceComb
	 */
	private void initDefaultCheck(Label lblNewLabel_2, Combo selectWorkSpaceComb) {
		setDefaultCheck = new Button(shlAutocodeLauncher, SWT.CHECK);
		FormData fd_setDefaultCheck = new FormData();
		fd_setDefaultCheck.top = new FormAttachment(selectWorkSpaceComb, 48);
		fd_setDefaultCheck.left = new FormAttachment(lblNewLabel_2, 0, SWT.LEFT);
		setDefaultCheck.setLayoutData(fd_setDefaultCheck);
		setDefaultCheck.setText("使用此空间并且不再询问");
	}

	/**
	 * 初始化浏览按钮
	 * @param selectWorkSpaceComb
	 */
	private void initBrowerBtn(final Combo selectWorkSpaceComb) {
		Button browerBtn = new Button(shlAutocodeLauncher, SWT.NONE);
		browerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String directoryPath = openDirectoryDialog();
				//添加到下拉框并选中
				if(null != directoryPath){
					//判断是否在下拉列表中
					if(-1 == selectWorkSpaceComb.indexOf(directoryPath)){//如果不在下拉框中
						selectWorkSpaceComb.add(directoryPath, 0);
					}
					selectWorkSpaceComb.select(selectWorkSpaceComb.indexOf(directoryPath));
				}
			}
		});
		FormData fd_browerBtn = new FormData();
		fd_browerBtn.top = new FormAttachment(selectWorkSpaceComb, 0, SWT.TOP);
		fd_browerBtn.left = new FormAttachment(selectWorkSpaceComb, 18);
		fd_browerBtn.right = new FormAttachment(100, -10);
		browerBtn.setLayoutData(fd_browerBtn);
		browerBtn.setText("浏览");
	}
	/**
	 * 打开文件选择框
	 */
	private String openDirectoryDialog() {
		DirectoryDialog dialog = new DirectoryDialog(shlAutocodeLauncher, SWT.OPEN);
		String directoryPath = dialog.open();
		return directoryPath;			
	}
	/**
	 * 初始化下拉列表
	 * @param describe
	 * @param lblNewLabel_2
	 * @param fd_lblNewLabel_2
	 * @return
	 */
	private Combo initSelectWorkSpaceComb(Group describe, Label lblNewLabel_2, FormData fd_lblNewLabel_2) {
		selectWorkSpaceComb = new Combo(shlAutocodeLauncher, SWT.NONE);
		//绑定数据
		bindingData(selectWorkSpaceComb);
		fd_lblNewLabel_2.right = new FormAttachment(100, -600);
		FormData fd_selectWorkSpaceComb = new FormData();
		fd_selectWorkSpaceComb.top = new FormAttachment(describe, 27);
		fd_selectWorkSpaceComb.left = new FormAttachment(lblNewLabel_2, 6);
		fd_selectWorkSpaceComb.right = new FormAttachment(100, -133);
		selectWorkSpaceComb.setLayoutData(fd_selectWorkSpaceComb);
		return selectWorkSpaceComb;
	}
	/**
	 * 为下拉框绑定数据
	 * @param selectWorkSpaceComb 下拉框
	 */
	private void bindingData(Combo selectWorkSpaceComb) {
		if(workSpaces!= null && workSpaces.size()!=0){
			for (String workSpace : workSpaces) {
				selectWorkSpaceComb.add(workSpace);
			}			
			selectWorkSpaceComb.select(0);
		}
	}


	/**
	 * 初始化描述部分
	 * @return
	 */
	private Group initDescribe() {
		Group describe = new Group(shlAutocodeLauncher, SWT.NONE);
		FormData fd_describe = new FormData();
		fd_describe.bottom = new FormAttachment(0, 95);
		fd_describe.right = new FormAttachment(0, 710);
		fd_describe.top = new FormAttachment(0, 10);
		fd_describe.left = new FormAttachment(0, 10);
		describe.setLayoutData(fd_describe);
		
		Label lblNewLabel = new Label(describe, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 262, 28);
		lblNewLabel.setText("选择一个文件夹作为工作空间");
		
		Label lblNewLabel_1 = new Label(describe, SWT.NONE);
		lblNewLabel_1.setBounds(24, 54, 580, 19);
		lblNewLabel_1.setText("AutoCode 使用工作空间文件夹来保存代码和配置文件");
		return describe;
	}
}
