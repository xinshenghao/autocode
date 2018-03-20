package com.hitoo.ui.filemenu.createproject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;

import com.hitoo.config.common.CommonParameter;
import com.hitoo.config.pom.Pom;
import com.hitoo.config.projectinfor.ProjectInfor;
import com.hitoo.config.projectinfor.ProjectInforBeanOperationer;
import com.hitoo.config.projectinfor.ProjectInforKey;
import com.hitoo.config.projectinfor.ProjectInforOperationer;
import com.hitoo.ui.start.ApplicationContextHelper;
import com.hitoo.ui.start.AutoCode;
import com.hitoo.ui.utils.FileUtil;
import com.hitoo.ui.utils.ZipUtil;
/**
 * 创建项目
 * @author xsh
 *
 */
public class CreateProjectWizard extends Wizard {
	
	private importZipFileWizardPage importZipFileWizardPage = new importZipFileWizardPage();

	
	private CommonParameter commonParameter = (CommonParameter) ApplicationContextHelper.getBean("commonParameter");
	private ProjectInfor projectInfor = (ProjectInfor) ApplicationContextHelper.getBean("projectInfor");
	private Pom pom = (Pom) ApplicationContextHelper.getBean("pom");
	
	public CreateProjectWizard() {
		setWindowTitle("创建项目");
	}

	@Override
	public void addPages() {
		addPage(importZipFileWizardPage);
	}

	@Override
	public boolean performFinish() {
		String workSpace = commonParameter.getWorkSpace()+"/";
		String zipFile = importZipFileWizardPage.getSelectedFilePath();
		String zipName = new File(zipFile).getName().replace(".zip", "");
		
		projectInfor.setProjectName(zipName);
		
		unzip(workSpace, zipFile);
		pom.init(commonParameter.getWorkSpace()+"/"+projectInfor.getProjectName()+"/pom.xml");
		
		createConfigFolder(workSpace, zipName);
		
		return true;
	}


	private void createConfigFolder(String workSpace, String zipName) {
		File file = new File(workSpace+"/.ac");
		file.mkdir();
		File configFile = new File(workSpace+"/.ac/"+ProjectInforKey.CONFIG_FILE_NAME);
		FileWriter fw = null;
		try {
			fw = new FileWriter(configFile);
			fw.write("<root>\r\n</root>");
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ProjectInforBeanOperationer projectInforBeanOperationer = (ProjectInforBeanOperationer) ApplicationContextHelper.getBean("projectInforBeanOperationer");
		projectInforBeanOperationer.setProjectName(zipName);
		projectInforBeanOperationer.setGroupId(pom.getGroupId());
		ProjectInforOperationer projectInforOperationer = (ProjectInforOperationer) ApplicationContextHelper.getBean("projectInforOperationer");
		projectInforOperationer.setProjectName(zipName);
		projectInforOperationer.setGroupId(pom.getGroupId());
	}

	private void unzip(String workSpace, String zipFile) {
		if(FileUtil.hasChildFile(workSpace)) {
			boolean isDelete = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "询问", "工作空间下有文件，是否删除");
			if(isDelete) {
				FileUtil.deleteDir(new File(workSpace));
				new File(workSpace).mkdirs();
			}else {
				return;
			}
		}
		
		ZipUtil.unZip(zipFile, workSpace);
		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "成功", "解压文件成功!!!");
		
		AutoCode.reflushTreeView();
	}
}
