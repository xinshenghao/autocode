package com.hitoo.editor.scanner;

import com.hitoo.editor.scanner.java.JavaKeyWordScanner;
import com.hitoo.editor.scanner.xml.XmlKeyWordScanner;

public class KeyWordScannerFactory {
	
	public static KeyWordScanner createScanner(String path) {
		String[] tmp = path.split("\\.");
		String type = tmp[tmp.length-1];
		switch (type) {
		case "java":
			return new JavaKeyWordScanner(path);
		case "xml":
			return new XmlKeyWordScanner(path);
		default:
			return null;
		}
	}

}
