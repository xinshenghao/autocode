package com.hitoo.general.folderscanner;

import java.io.File;
import java.util.List;

import com.hitoo.general.folderscanner.rule.ScannerRuleI;
import com.hitoo.general.projectmodel.FileNode;
import com.hitoo.general.projectmodel.FolderNode;

/**
 * 递归扫描文件夹
 * @author xsh
 *
 */
public class FoldersScanner extends AbsScanner{

	public FoldersScanner(String path) {
		super(path);
	}

	@Override
	public FolderNode scan() {
		return getFileTree(path, null);
	}

	private FolderNode getFileTree(String path, FolderNode folder) {
		if(null == folder) {
			folder = new FolderNode("root", path);
		}
		File file = new File(path);

		if (!file.isDirectory()) {
			
			if(canReadFile(file.getName(), fileRules)) {
				FileNode fileNode = new FileNode(file.getName(),file.getPath());
				if (null != folder) {
					folder.add(fileNode);
				}				
			}
		} else {
			if(canReadFile(file.getName(), folderRules)) {
				File[] files = file.listFiles();
				for (File tmpFile : files) {
					if (tmpFile.isDirectory()) {
						if(canReadFile(tmpFile.getName(), folderRules)) {
							FolderNode folderNode = new FolderNode(tmpFile.getName(),tmpFile.getPath());
							folder.add(folderNode);
							getFileTree(path + "/" + tmpFile.getName(), folderNode);							
						}
					} else {
						if(canReadFile(tmpFile.getName(), fileRules)) {
							FileNode tmpFileNode = new FileNode(tmpFile.getName(), tmpFile.getPath());
							folder.add(tmpFileNode);							
						}
					}
				}
			}
		}
		return folder;
	}

	/**
	 * 是否可以将此文件添加到返回值中
	 * @param name
	 * @return
	 */
	private boolean canReadFile(String name, List<ScannerRuleI> rules) {
		for (ScannerRuleI rule : rules) {
			if(!rule.accept(name)) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	
	
	
	
}
