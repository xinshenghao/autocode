package com.hitoo.ui.editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;

public class CodeDocument extends Document implements IDocumentListener{
	private String fileName; //打开的文件名
	private boolean dirty; //文件是否修改过
	
	public CodeDocument(String path) {
		this.addDocumentListener(this);
		this.fileName = path;
	}
	/**
	 * 保存到指定的文件
	 */
	public void save() {
		if(fileName == null) {
			throw new IllegalStateException("文件名不能为空");
		}
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(fileName));
			out.write(get());
			dirty = false;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 打开文件
	 */
	public void open() {
		if(fileName == null) {
			throw new IllegalStateException("文件名不能为空");
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fileName));
			StringBuffer buf = new StringBuffer();
			int n;
			while((n = in.read()) != -1) {
				buf.append((char)n);
			}
			set(buf.toString());
			dirty = false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isDirty() {
		return dirty;
	}
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	@Override
	public void documentAboutToBeChanged(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void documentChanged(DocumentEvent arg0) {
		dirty = true;
	}

}
