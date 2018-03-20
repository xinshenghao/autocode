package com.hitoo.config.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.jar.JarOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
    private Document document;

    public XmlUtil(InputStream in) {
        try {
            document=new SAXReader().read(in);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    
    public XmlUtil(String readPath) {
        File file=new File(readPath);
        try {
            document=new SAXReader().read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

	/**
     * 获取根节点
     * @return
     */
    public Element getRootElement(){
        return document.getRootElement();
    }

	 public void writeXml(OutputStream output){
        //把生成的xml文档存放在硬盘上  true代表是否换行
       /* OutputFormat format = new OutputFormat("    ",true);
        format.setEncoding("UTF-8"); // 设置XML文档的编码类型
        format.setSuppressDeclaration(true);*/
    	OutputFormat format= OutputFormat.createPrettyPrint();
    	format.setEncoding("UTF-8");
        XMLWriter xmlWriter = null;
        try {
            xmlWriter = new XMLWriter(output,format);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  catch (IOException e) {
			e.printStackTrace();
		}
        try {
            xmlWriter.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
