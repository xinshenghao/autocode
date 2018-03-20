package com.hitoo.editor.scanner.java;

import com.hitoo.editor.base.java.JavaLexer;
import com.hitoo.editor.scanner.KeyWordScanner;

public class JavaKeyWordScanner extends KeyWordScanner{

	public JavaKeyWordScanner(String path) {
		super(path);
		this.lexer = new JavaLexer(getCharStream());
	}
}
