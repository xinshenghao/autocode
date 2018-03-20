package com.hitoo.ui.projecttree.mvc;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.hitoo.ui.projecttree.model.ProjectTreeElement;
import com.hitoo.ui.utils.PropertiesUtil;

public class ProjectTreeLabelProvider implements ILabelProvider{
	
	private String imgsFolderPath = PropertiesUtil.getValue(PropertiesUtil.FILE_PATH, "imgsFolder");
	public static final String IMG_FOLDER = "folder";
	public static final String IMG_FILE = "file";
	public static final String IMG_SPRINGBOOT_APPLI = "springboot_appli";
	public static final String IMG_MAVEN_POM = "pom_file";
	
	private ImageRegistry ir = null;
	
	public ProjectTreeLabelProvider() {
		ir = new ImageRegistry();
		ImageData folderImage = new ImageData(imgsFolderPath+"folder.png");
		ir.put(IMG_FOLDER, ImageDescriptor.createFromImageData(folderImage));
		ImageData javaFileImage = new ImageData(imgsFolderPath+"javaFile.png");
		ir.put(IMG_FILE, ImageDescriptor.createFromImageData(javaFileImage));
		ImageData springBootApplication = new ImageData(imgsFolderPath+"springboot_application.png");
		ir.put(IMG_SPRINGBOOT_APPLI, ImageDescriptor.createFromImageData(springBootApplication));
		ImageData mavePomFile = new ImageData(imgsFolderPath+"maven_pom.png");
		ir.put(IMG_MAVEN_POM, ImageDescriptor.createFromImageData(mavePomFile));
	}

	@Override
	public void addListener(ILabelProviderListener element) {
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getImage(Object element) {
		ProjectTreeElement ele = (ProjectTreeElement)element;
		if (ele.getName().equals("application.properties")) {
			return ir.get(IMG_SPRINGBOOT_APPLI);
		}else if(ele.getName().equals("pom.xml")) {
			return ir.get(IMG_MAVEN_POM);
		}
		return ir.get(ele.getIcon());
	}

	@Override
	public String getText(Object element) {
		return ((ProjectTreeElement)element).getName();
	}

}
