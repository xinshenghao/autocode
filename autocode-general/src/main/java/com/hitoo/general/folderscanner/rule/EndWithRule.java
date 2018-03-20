package com.hitoo.general.folderscanner.rule;

public class EndWithRule implements ScannerRuleI {
	
	private String end;
	

	public EndWithRule(String end) {
		this.end = end;
	}

	@Override
	public boolean accept(String name) {
		int lengthCount = name.length();
		int lengthKey = end.length();
		if(lengthKey > lengthCount) {
			return false;
		}
		String tmp = name.substring(lengthCount-lengthKey, lengthCount);
		if(tmp.equals(end)) {
			return true;
		}
		
		return false;
	}

}
