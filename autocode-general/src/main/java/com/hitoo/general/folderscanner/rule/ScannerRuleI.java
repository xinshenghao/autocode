package com.hitoo.general.folderscanner.rule;

public interface ScannerRuleI {

	/**
	 * 是否通过
	 * @param name
	 * @return
	 */
	boolean accept(String name);

	
}
