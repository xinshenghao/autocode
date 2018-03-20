package com.hitoo.ui.projecttree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hitoo.general.folderscanner.FolderScanner;
import com.hitoo.ui.projecttree.model.FileNode;
import com.hitoo.ui.projecttree.model.FolderNode;
import com.hitoo.ui.projecttree.model.ProjectTreeElement;

public class TreeViewDataHelper {
	private final static String[] EXCLUDE_FOLDER= new String[] {
			"target"
	}; 
	private final static String[] EXCLUDE_FILE_SUFFIX = new String[] {
			"cmd"
	};
	private final static String[] EXCLUED_FILE_PREFIX = new String[] {
			"."
	};
	private final static String[] EXCLUED_FILE_NAME = new String[] {
			"mvnw"
	};
	
	public FolderNode getFileTreeData(String path, FolderNode root, boolean isRoot) {
		root = getFileTree(path, new FolderNode("根目录", path), true);
		if(root.getChildren().size() > 0) {
			root = sortTree((FolderNode) root.getChildren().get(0));
		}
		return root;
	}
	
	
	/**
	 * 获取项目文件夹的内容
	 * @param path
	 * @param element
	 * @param isRoot
	 */
	public FolderNode getFileTree(String path, FolderNode element, boolean isRoot) {
		File file = new File(path);

		if (!file.isDirectory()) {
			
			if(canReadFile(file.getName())) {
				FileNode fileNode = new FileNode(file.getName(),file.getPath());
				if (null != element) {
					element.add(fileNode);
				}				
			}
		} else {
			if(canReadFolder(file.getName())) {
				File[] files = file.listFiles();
				for (File tmpFile : files) {
					if (tmpFile.isDirectory()) {
						if(canReadFolder(tmpFile.getName())) {
							FolderNode folderNode = new FolderNode(tmpFile.getName(),tmpFile.getPath());
							element.add(folderNode);
							getFileTree(path + "/" + tmpFile.getName(), folderNode, false);							
						}
					} else {
						if(canReadFile(tmpFile.getName())) {
							FileNode tmpFileNode = new FileNode(tmpFile.getName(), tmpFile.getPath());
							element.add(tmpFileNode);							
						}
					}
				}
			}
		}
		return element;
	}
	/**
	 * 对文件树节点进行排序
	 * @param folderNode
	 * @return
	 */
	private FolderNode sortTree(FolderNode folderNode) {
		
		List<ProjectTreeElement> list = folderNode.getChildren();
		
		List<FolderNode> folderNodes = new ArrayList<>();
		List<FileNode> fileNodes = new ArrayList<>();

		for (ProjectTreeElement element : list) {
			if(element instanceof FileNode) {
				fileNodes.add((FileNode) element);
			}else if(element instanceof FolderNode) {
				folderNodes.add(sortTree((FolderNode) element));
			}
		}
		list = new ArrayList<>();
		
		list.addAll(folderNodes);
		list.addAll(fileNodes);
		
		folderNode.setChildren(list);
		return folderNode;
	}

	/**
	 * 是否可以读取此文件
	 * @param name
	 * @return
	 */
	private boolean canReadFile(String name) {
		for (String str : EXCLUED_FILE_NAME) {
			if(str.equals(name)) {
				return false;
			}
		}
		
		String[] tmp = name.split("\\.");
		String suffix = tmp[tmp.length-1];
		for (String str : EXCLUDE_FILE_SUFFIX) {
			if(suffix.equals(str)) {
				return false;
			}
		}
		
		for (String str : EXCLUED_FILE_PREFIX) {
			if(name.startsWith(str)) {
				return false;
			}
		}
		
		return true;
	}
	/**
	 * 返回是否可以读取文件夹
	 * @param name
	 * @return
	 */
	private boolean canReadFolder(String name) {
		if(name.startsWith(".")) {
			return false;
		}
		for (String folderName : EXCLUDE_FOLDER) {
			if(name.equals(folderName)) {
				return false;
			}
		}
		return true;
	}
	
	
}
