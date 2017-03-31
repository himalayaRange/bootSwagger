/** 
 * Project Name:bootSwagger 
 * File Name:GenerateUtil.java 
 * Package Name:org.spring.swagger.mvc.util 
 * Date:2017-3-16上午9:29:29 
 * Copyright (c) 2017, wangyi2071764@sina.com All Rights Reserved. 
*/ 
package org.spring.swagger.mvc.util;

import java.util.Map;

import org.spring.swagger.mvc.support.security.coder.RSACoder;

public class GenerateUtil {
	
	/**
	 * 对字符串盐值加密
	 * @param reource
	 * @return
	 */
	public static String encryptSalt(String reource){
		
		return reource;
	}
	
	/**
	 * 字符串盐值解密
	 * @param resource
	 * @return
	 *
	 */
	public static String decryptSalt(String resource){
		
		return	resource;
	}
	
	public static String createRSAKey() throws Exception{
		Map<String, Object> key = RSACoder.initKey();
		String publicKey  = SecurityUtil.encryptBASE64(RSACoder.getPublicKey(key));
		String privateKey = SecurityUtil.encryptBASE64(RSACoder.getPrivateKey(key));
		String result = "公钥："+publicKey+",私钥："+privateKey;
		System.out.println(result);
		return result;
	}
	
	public static void main(String[] args) {
		try {
			createRSAKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
