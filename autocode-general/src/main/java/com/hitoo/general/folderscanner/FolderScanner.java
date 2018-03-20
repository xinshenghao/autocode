package com.hitoo.general.folderscanner;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hitoo.general.folderscanner.rule.ScannerRuleI;

public class FolderScanner {

	private String folderPath;
	private List<ScannerRuleI> rules ;
	
	public FolderScanner(String folderPath) {
		this.folderPath = folderPath;
		this.rules = new ArrayList<>();
	}
	
	public FolderScanner addRule(ScannerRuleI rule) {
		this.rules.add(rule);
		return this;
	}
	
	public List<String> scan(){
		File file = new File(folderPath);
		if(file.isFile()) {
			return null;
		}
		
		String[] names = file.list(new FileNameRuleFilter(rules));
		
		return Arrays.asList(names);
	}
	
}
