package com.hitoo.ui.editor;

import org.eclipse.jface.text.rules.IWordDetector;

public class KeywordDetector implements IWordDetector {
	
	private String[] keywords = null;
	
	public KeywordDetector(String[] keywords) {
		this.keywords = keywords;
	}
	
	//字符是否是单词的开始
	@Override
	public boolean isWordStart(char c) {
		//循环所有关键字
		//如果有关键字中的第一个字符匹配该字符，则返回true
		for (int i = 0; i<keywords.length; i++) {
			if( c== (keywords[i].charAt(0))) {
				return true;
			}
		}
		return false;
	}
	
	//字符是否是单词的一部分
	@Override
	public boolean isWordPart(char c) {
		//循环所有的关键字
		//如果关键字的字符中有该字符，则返回true
		for(int i=0; i<keywords.length; i++) {
			if (keywords[i].indexOf(c)!= -1) {
				return true;
			}
		}
		return false;
	}

}



















