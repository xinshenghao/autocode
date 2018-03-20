package com.hitoo.config.projectconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.hitoo.config.model.DBConnection;

@Component
public class SpringProjectConfigFileOperationer {

	private String path = null;
	private Properties properties = null;
	
	public void init(String path) {
		this.path = path;
		properties = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void check() {
		if(null == properties) {
			throw new RuntimeException("未初始化["+this.getClass().getName()+"]类");
		}
	}
	
	/**
	 * 配置数据库连接
	 * @param dbConnection
	 * @param dataBaseName
	 * @throws Exception 
	 */
	public void setDbConnection(DBConnection dbConnection, String dataBaseName)  {
		check();
		
		String url = "jdbc:mysql://"+dbConnection.getHostName()+":"+dbConnection.getPort()+"/"+dataBaseName+"?useUnicode=true&characterEncoding=UTF-8";
		String driver = "com.mysql.jdbc.Driver";
		
		properties.setProperty("spring.datasource.url", url);
		properties.setProperty("spring.datasource.driverClassName", driver);
		properties.setProperty("spring.datasource.username", dbConnection.getUserName());
		properties.setProperty("spring.datasource.password", dbConnection.getPassword());
		
		writeProperties(properties);
	}

	private void writeProperties(Properties p) {
		try {
			OutputStream out = new FileOutputStream(path);
			p.store(out, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
