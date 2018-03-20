package com.hitoo.general.folderscanner.rule;

public class StartWithRule implements ScannerRuleI {
	
	private String prefix;
	
	
	public StartWithRule(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public boolean accept(String name) {
		if(name.startsWith(prefix)) {
			return true;
		}
		return false;
	}

}
