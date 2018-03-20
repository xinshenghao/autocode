package com.hitoo.general.folderscanner.rule;

public class NoEndWithRule implements ScannerRuleI {
	
	private String end;
	

	public NoEndWithRule(String end) {
		this.end = end;
	}

	@Override
	public boolean accept(String name) {
		int lengthCount = name.length();
		int lengthKey = end.length();
		if(lengthKey > lengthCount) {
			return true;
		}
		String tmp = name.substring(lengthCount-lengthKey, lengthCount);
		if(tmp.equals(end)) {
			return false;
		}
		
		return true;
	}

}
