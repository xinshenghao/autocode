package com.hitoo.general.folderscanner.rule;

import java.io.File;

public class FileTypeRule implements ScannerRuleI{
	public static enum Type{
		FILE,FOLDER
	};
	
	private Type type;
	private String folderPath;
	
	
	public FileTypeRule(String folderPath, Type type) {
		this.folderPath = folderPath;
		this.type = type;
	}

	@Override
	public boolean accept(String name) {
		File file = new File(folderPath+"/"+name);
		if(this.type.equals(Type.FILE)) {
			return file.isFile();
		}else {
			return file.isDirectory();
		}
	}

}
