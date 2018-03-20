package com.hitoo.config.model;


import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.PropertyHolder;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;



/**
 * 基于mbg配置文件Context的model
 * @author xsh
 *
 */
public class MBGContext extends PropertyHolder {
    
    /** The id. */
    private String id;
    
    /** The default model type. */
    private String defaultModelType;
    
    /** The target runtime. */
    private String targetRuntime;
    
    /** The auto delimit keywords. */
    private Boolean autoDelimitKeywords = false;
    
    private String javaFileEncoding = "UTF-8";
    
    /** The beginning delimiter. */
    private String beginningDelimiter = "\""; //$NON-NLS-1$
    
    /** The ending delimiter. */
    private String endingDelimiter = "\""; //$NON-NLS-1$
    
    /** The java model generator configuration. */
    private JavaModelGeneratorConfiguration javaModelGeneratorConfiguration;
    
    /** The sql map generator configuration. */
    private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration;

    /** The java client generator configuration. */
    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDefaultModelType() {
		return defaultModelType;
	}

	public void setDefaultModelType(String defaultModelType) {
		this.defaultModelType = defaultModelType;
	}

	public String getTargetRuntime() {
		return targetRuntime;
	}

	public void setTargetRuntime(String targetRuntime) {
		this.targetRuntime = targetRuntime;
	}

	public Boolean getAutoDelimitKeywords() {
		return autoDelimitKeywords;
	}

	public void setAutoDelimitKeywords(Boolean autoDelimitKeywords) {
		this.autoDelimitKeywords = autoDelimitKeywords;
	}

	public String getJavaFileEncoding() {
		return javaFileEncoding;
	}

	public void setJavaFileEncoding(String javaFileEncoding) {
		this.javaFileEncoding = javaFileEncoding;
	}

	public String getBeginningDelimiter() {
		return beginningDelimiter;
	}

	public void setBeginningDelimiter(String beginningDelimiter) {
		this.beginningDelimiter = beginningDelimiter;
	}

	public String getEndingDelimiter() {
		return endingDelimiter;
	}

	public void setEndingDelimiter(String endingDelimiter) {
		this.endingDelimiter = endingDelimiter;
	}

	public JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration() {
		return javaModelGeneratorConfiguration;
	}

	public void setJavaModelGeneratorConfiguration(JavaModelGeneratorConfiguration javaModelGeneratorConfiguration) {
		this.javaModelGeneratorConfiguration = javaModelGeneratorConfiguration;
	}

	public SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration() {
		return sqlMapGeneratorConfiguration;
	}

	public void setSqlMapGeneratorConfiguration(SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration) {
		this.sqlMapGeneratorConfiguration = sqlMapGeneratorConfiguration;
	}

	public JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration() {
		return javaClientGeneratorConfiguration;
	}

	public void setJavaClientGeneratorConfiguration(JavaClientGeneratorConfiguration javaClientGeneratorConfiguration) {
		this.javaClientGeneratorConfiguration = javaClientGeneratorConfiguration;
	}
    
    
    
    
}
