package com.hitoo.general.folderscanner.rule;

public class NoStartWithRule implements ScannerRuleI {
	
	private String prefix;
	
	
	public NoStartWithRule(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public boolean accept(String name) {
		if(name.startsWith(prefix)) {
			return false;
		}
		return true;
	}

}
