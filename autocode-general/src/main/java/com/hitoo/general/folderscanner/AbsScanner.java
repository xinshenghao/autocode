package com.hitoo.general.folderscanner;

import java.util.ArrayList;
import java.util.List;

import com.hitoo.general.folderscanner.rule.FileTypeRule;
import com.hitoo.general.folderscanner.rule.FileTypeRule.Type;
import com.hitoo.general.folderscanner.rule.ScannerRuleI;
import com.hitoo.general.projectmodel.FileType;
import com.hitoo.general.projectmodel.FolderNode;

/**
 * 抽象扫描类
 * @author xsh
 *
 */
public abstract class AbsScanner {
	
	String path;
	List<ScannerRuleI> fileRules,folderRules;
	
	public AbsScanner(String path) {
		this.path = path;
		this.fileRules = new ArrayList<>();
		fileRules.add(new FileTypeRule(Type.FILE));
		this.folderRules = new ArrayList<>();
		folderRules.add(new FileTypeRule(Type.FOLDER));
	}
	
	public AbsScanner addFileRule(ScannerRuleI rule) {
		this.fileRules.add(rule);
		return this;
	}
	
	public AbsScanner addFolderRult(ScannerRuleI rule) {
		this.folderRules.add(rule);
		return this;
	}
	
	public abstract FolderNode scan();
	
	
	
}
