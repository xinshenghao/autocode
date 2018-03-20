package com.hitoo.general.folderscanner;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import com.hitoo.general.folderscanner.rule.ScannerRuleI;

public class FileNameRuleFilter implements FilenameFilter{
	
	private List<ScannerRuleI> rules;
	
	public FileNameRuleFilter(List<ScannerRuleI> rules) {
		this.rules = rules;
	}


	@Override
	public boolean accept(File dir, String name) {
		if(null == this.rules || rules.size()==0 ) {
			return true;
		}
		
		for (ScannerRuleI rule : rules) {
			if(!rule.accept(name)) {
				return false;				
			}
		}
		return true;
	}

}
