package com.hitoo.editor.scanner.xml;

import com.hitoo.editor.base.xml.XMLLexer;
import com.hitoo.editor.scanner.KeyWordScanner;

public class XmlKeyWordScanner extends KeyWordScanner {

	public XmlKeyWordScanner(String path) {
		super(path);
		this.lexer = new XMLLexer(getCharStream());
	}

}
