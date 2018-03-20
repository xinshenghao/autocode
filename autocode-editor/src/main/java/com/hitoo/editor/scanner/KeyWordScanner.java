package com.hitoo.editor.scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenSource;

public abstract class KeyWordScanner {
	protected String path;
	protected Lexer lexer;

	public KeyWordScanner(String path) {
		this.path = path;
	}

	public String[] getKeyWordArray() {
		//用词法分析器 lexer 构造一个记号流 tokens                                                                
		CommonTokenStream tokens = new CommonTokenStream(lexer);                                     
		TokenSource ts = tokens.getTokenSource();                                                    
		org.antlr.v4.runtime.Token tmp = ts.nextToken();                                                                  
		Map<String,Integer> map = new HashMap<String, Integer>();                                    
		while (tmp.getType() != org.antlr.v4.runtime.Token.EOF && tmp!=null){            
		    if(tmp.getType()== 111){                                                                 
		        map.put(tmp.getText(),tmp.getType());                                                
		    }                                                                                        
		    tmp = ts.nextToken();                                                                    
		}                                                                                            
		Set<String> set = map.keySet();
		String[] objText = new String[set.size()];
		set.toArray(objText);
		return objText;
	}
	
	protected CodePointCharStream getCharStream( ) {
		FileInputStream fis = null;
		ByteBuffer buf = null;
		try {
			fis = new FileInputStream(new File(path));         
			FileChannel fc = fis.getChannel();                                                           
			                                                                                             
			buf = ByteBuffer.allocate(fis.available()*2);                                     
			                                                                                             
			fc.read(buf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}                                                                                 			
		}                                                                             
		                                                                                             
		CodePointBuffer cpb = CodePointBuffer.withBytes(buf);                                        
		return CodePointCharStream.fromBuffer(cpb);  
	}
}
