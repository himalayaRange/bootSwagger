/** 
 * Project Name:bootSwagger
 * File Name:AccessToken.java 
 * Package Name:org.spring.swagger.mvc.vo 
 * Date:2017-3-13上午11:49:34 
 * Copyright (c) 2017, wangyi2071764@sina.com All Rights Reserved. 
*/ 
package org.spring.swagger.mvc.vo;

import java.util.Map;

public class AccessToken {
	private  String app_id;			     //	商户号
	private  String app_key;		     //	秘钥
	private  String format;              // 格式，仅支持JSON
	private  Long timestamp;             // 时间戳
	private  String httpMethod;          // 请求的方法
	private  String url;				 // 请求的url
	private  String uri;                 // 请求的uri
	private  String charset;             // 请求使用的编码格式
	private  String sign;     		     // 数字签名
	private  String sign_type;        	 // 签名类型
	private  String version;			 // 版本，默认为1.0
	private  String biz_content;		 // 请求参数的集合，需要是JSON格式
	private  Map<String,?> paramMap;     // 请求的数据，Map对象
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String md5) {
		this.sign_type = md5;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getBiz_content() {
		return biz_content;
	}
	public void setBiz_content(String biz_content) {
		this.biz_content = biz_content;
	}
	
	public Map<String, ?> getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map<String, ?> paramMap) {
		this.paramMap = paramMap;
	}
	@Override
	public String toString() {
		return "AccessToken [app_id=" + app_id + ", app_key=" + app_key
				+ ", format=" + format + ", timestamp=" + timestamp
				+ ", httpMethod=" + httpMethod + ", url=" + url + ", uri="
				+ uri + ", charset=" + charset + ", sign=" + sign
				+ ", sign_type=" + sign_type + ", version=" + version
				+ ", biz_content=" + biz_content + "]";
	}
	
	
}
