package com.hitoo.general.folderscanner;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hitoo.general.folderscanner.rule.ScannerRuleI;
import com.hitoo.general.projectmodel.FileNode;
import com.hitoo.general.projectmodel.FolderNode;
/**
 * 单个文件夹下扫描一层
 * @author xsh
 *
 */
public class FolderScanner extends AbsScanner{

	
	public FolderScanner(String path) {
		super(path);
	}

	@Override
	public FolderNode scan() {
		FolderNode result = new FolderNode("", path);
		
		File file = new File(path);
		if(file.isFile()) {
			return null;
		}
		
		String[] names = file.list(new FileNameRuleFilter(fileRules));
		
		for (String name : names) {
			result.add(new FileNode(name, path+name));
		}
		
		return result;
	}

	/*public List<String> scan(){
		File file = new File(folderPath);
		if(file.isFile()) {
			return null;
		}
		
		String[] names = file.list(new FileNameRuleFilter(rules));
		
		return Arrays.asList(names);
	}*/
	
}
