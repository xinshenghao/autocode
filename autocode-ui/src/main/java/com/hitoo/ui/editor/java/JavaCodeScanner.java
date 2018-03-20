package com.hitoo.ui.editor.java;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;

import com.hitoo.editor.scanner.KeyWordScannerFactory;
import com.hitoo.editor.scanner.java.JavaKeyWordScanner;
import com.hitoo.ui.editor.KeywordDetector;
import com.hitoo.ui.preference.PreferenceStoreKey;
import com.hitoo.ui.resource.ColorResourceManager;

public class JavaCodeScanner extends RuleBasedScanner{
	private JavaKeyWordScanner javaKeyWordScanner;
	
	private String path;
	private String[] keywordsArray;
	
	private TextAttribute keywords;//关键字
	private TextAttribute string; //字符串的文本属性
	private TextAttribute object; //内置对象的文本属性
	private TextAttribute comment; //注释部分的文本属性
	
	public JavaCodeScanner(String path) {
		this.path = path;
		keywords = new TextAttribute(ColorResourceManager.getColor(PreferenceStoreKey.JAVA_EDITOR_COLOR_KEYWORDS), null, SWT.BOLD);
		string = new TextAttribute(ColorResourceManager.getColor(PreferenceStoreKey.JAVA_EDITOR_COLOR_STRING));
		object = new TextAttribute(ColorResourceManager.getColor(PreferenceStoreKey.JAVA_EDITOR_COLOR_OBJECT));
		comment = new TextAttribute(ColorResourceManager.getColor(PreferenceStoreKey.JAVA_EDITOR_COLOR_COMMON));
		
		javaKeyWordScanner = (JavaKeyWordScanner) KeyWordScannerFactory.createScanner(path);
		keywordsArray = javaKeyWordScanner.getKeyWordArray();
		
		//设置代码的规则
		setupRules();
	}

	private void setupRules() {
		//用一个List集合对象保存所有的规则
		List<IRule> rules = new ArrayList<>();
		//字符串规则
		rules.add(new SingleLineRule("\"", "\"", new Token(string), '\\'));
		rules.add(new SingleLineRule("'", "'", new Token(string), '\\'));
		//注释的规则
		rules.add(new MultiLineRule("/*", "*/", new Token(comment)));
		rules.add(new EndOfLineRule("//", new Token(comment)));
		//空格的规则
		rules.add(new WhitespaceRule(new IWhitespaceDetector() {
			@Override
			public boolean isWhitespace(char c) {
				return Character.isWhitespace(c);
			}
		}));
		//关键字的规则
		WordRule keywordRule = new WordRule(new KeywordDetector(keywordsArray));
		for(int i=0;i<keywordsArray.length; i++) {
			keywordRule.addWord(keywordsArray[i], new Token(keywords));
		}
		rules.add(keywordRule);
		//内置对象的规则
		WordRule objRule = new WordRule(new KeywordDetector(JavaCodeKey.key));
		for(int i=0;i<JavaCodeKey.key.length; i++) {
			objRule.addWord(JavaCodeKey.key[i], new Token(object));
		}
		rules.add(objRule);
		//集合中的保存的规则转化为数组
		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		//调用父类的方法，设置规则
		//此方法非常重要
		setRules(result);
	}
}




