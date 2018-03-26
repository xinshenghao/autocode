package com.hitoo.general.folderscanner.rule;

import java.io.File;

public class FileTypeRule implements ScannerRuleI{
	public static enum Type{
		FILE,FOLDER
	};
	
	private Type type;
	
	
	public FileTypeRule(Type type) {
		this.type = type;
	}

	@Override
	public boolean accept(String path) {
		File file = new File(path);
		if(this.type.equals(Type.FILE)) {
			return file.isFile();
		}else {
			return file.isDirectory();
		}
	}

}
