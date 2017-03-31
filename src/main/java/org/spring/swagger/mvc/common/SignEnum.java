package org.spring.swagger.mvc.common;
/** 算法类型  */
public enum SignEnum {
	MD5("MD5"),
	RSA("RSA"),
	DES("DES");
	private  String signName;

	SignEnum(String signName){
		this.signName = signName; 
	}
	
	public String getSignName() {
		return signName;
	}
	
}
