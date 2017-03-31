package org.spring.swagger.mvc.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.spring.swagger.mvc.common.Constants;
import org.spring.swagger.mvc.util.SecurityUtil;

public class CheckSign {
	private static boolean isDevModule = Constants.MODULE_DEV ;
	
	public static Map<String,Object> checkGETSign(HttpServletRequest request,Map<String,String> allowedFromTokens){
		Map<String,Object> result = new HashMap<String,Object>();
		if (isDevModule){
			 result.put("access", true);
			 return result;
		}
		String sign = request.getHeader("sign"); // 从请求的Header中获取签名
        String timestamp = request.getHeader("timestamp");
        long date;
        if(timestamp != null){
        	 date = Long.parseLong(timestamp); // 获取时间戳
        }else{
        	 date = System.currentTimeMillis();
        }
        
        if((System.currentTimeMillis()-date) > Constants.THRESHOLD){ // 超过时间阈值则过期
        	result.put("access", false);
        	result.put("errcode", Constants.RESULTCODE_5);
        	result.put("emsg" , Constants.RESULTMESSAGE_FAILED_5);
        	return  result;
        }
        
        if(StringUtils.isEmpty(sign)){
        	result.put("access", false);
        	result.put("errcode", Constants.RESULTCODE_4);
        	result.put("emsg" , "拒绝当前请求");
        	return  result;
        }
        String[] signStr = sign.split(":");
        if(signStr != null && signStr.length == 2){
        	String app_id = signStr[0];
        	String	reqSign = signStr[1];
        	if(allowedFromTokens.containsKey(app_id)){
        		AccessToken accessToken = new AccessToken();
        		String app_key = allowedFromTokens.get(app_id);
        		accessToken.setApp_id(app_id);
        		accessToken.setApp_key(app_key);
        		String httpMethod = request.getMethod();
        		if("GET".equalsIgnoreCase(httpMethod)){
        			accessToken.setHttpMethod("GET");
        		}else{
        			accessToken.setHttpMethod("POST");
        		}
        		String requestURI = request.getRequestURI();
        		try {
        			// 解决URL中文问题
        			requestURI = URLDecoder.decode(new String(requestURI.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
					accessToken.setUri(requestURI);
					accessToken.setUrl(request.getRequestURL().toString());
        		} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
        		accessToken.setTimestamp(date);
        		accessToken.setCharset(request.getHeader("charset"));
        		accessToken.setSign_type(request.getHeader("sign_type"));
        		accessToken.setVersion(request.getHeader("version"));
        		if(request.getHeader("sign_type").toUpperCase().equals("MD5")){
        			//MD5
        			String serverSign = Sign.createSign(accessToken); // 服务端签名
            		if(Objects.equals(sign, serverSign)){
            			 result.put("access", true);
            			 return result;
            		}else{
            			// 签名不正确
            			result.put("access", false);
            			result.put("errcode", Constants.RESULTCODE_3);
            			result.put("emsg", Constants.RESULTMESSAGE_FAILED_3);
            			return result;
            		}
        		}else if(request.getHeader("sign_type").toUpperCase().equals("DES")){
        			//  DES
        			//	解密后的数据
        			byte[] decryptBASE64 = SecurityUtil.decryptBASE64(reqSign);
        			String decryptDes = SecurityUtil.decryptDes(new String(decryptBASE64));
        			// 源数据规则
        			String	resourceDes = Sign.ruleSign(accessToken);
        			if(Objects.equals(decryptDes, resourceDes)){
        				result.put("access", true);
           			 	return result;
        			}else{
        				// 签名不正确
            			result.put("access", false);
            			result.put("errcode", Constants.RESULTCODE_3);
            			result.put("emsg", Constants.RESULTMESSAGE_FAILED_3);
            			return result;
        			}
        		}else if(request.getHeader("sign_type").toUpperCase().equals("RSA")){
        			String publicKey = app_id;
        			String privateKey = app_key; //服务端认证的秘钥
        			String[] signData = reqSign.split("@"); //签名数据的组成： 签名数据源@私钥加密的签名
        			if(signData != null && signData.length ==2){
        				String data = new String(SecurityUtil.decryptBASE64(signData[0]));
        				String rsaSign = signData[1];
        				String encrypt  = SecurityUtil.encryptRSAPrivate(data, privateKey);
        				String org = SecurityUtil.decryptRSAPublic(encrypt, publicKey);
        				boolean verifyRSA = SecurityUtil.verifyRSA(org, publicKey, rsaSign);
        				if(verifyRSA){
        					result.put("access", true);
               			 	return result;
        				}else{
        					// 签名不正确
                			result.put("access", false);
                			result.put("errcode", Constants.RESULTCODE_3);
                			result.put("emsg", Constants.RESULTMESSAGE_FAILED_3);
                			return result;
        				}
        			}else{
        				// 签名不正确
            			result.put("access", false);
            			result.put("errcode", Constants.RESULTCODE_3);
            			result.put("emsg", Constants.RESULTMESSAGE_FAILED_3);
            			return result;
        			}
        		}else{
        			result.put("access", false);
            		result.put("errcode", Constants.RESULTCODE_4);
            		result.put("emsg", "请求数据已被篡改，系统拒绝请求！");
            		return result;
        		}
        		
        	}else{
        		// 未授权的用户
        		result.put("access", false);
        		result.put("errcode", Constants.RESULTCODE_4);
        		result.put("emsg", "未授权的用户！");
        		return result;
        	}
        }else{
        	// 请求头参数不正确
        	result.put("access", false);
        	result.put("errcode", Constants.RESULTCODE_4);
        	result.put("emsg", "请求参数不正确！");
        	return result;
        }
	} 
	
	

	public static Map<String,Object> checkGETPOST(HttpServletRequest request,Map<String,String> allowedFromTokens){
		Map<String,Object> result = new HashMap<String,Object>();
		if (isDevModule){
			 result.put("access", true);
			 return result;
		}
		String sign = request.getHeader("sign"); // 从请求的Header中获取签名
        String timestamp = request.getHeader("timestamp");
        long date;
        if(timestamp != null){
        	 date = Long.parseLong(timestamp); // 获取时间戳
        }else{
        	 date = System.currentTimeMillis();
        }
        
        if((System.currentTimeMillis()-date) > Constants.THRESHOLD){ // 超过时间阈值则过期
        	result.put("access", false);
        	result.put("errcode", Constants.RESULTCODE_5);
        	result.put("emsg" , Constants.RESULTMESSAGE_FAILED_5);
        	return  result;
        }
        
        if(StringUtils.isEmpty(sign)){
        	result.put("access", false);
        	result.put("errcode", Constants.RESULTCODE_4);
        	result.put("emsg" , "拒绝当前请求");
        	return  result;
        }
        String[] signStr = sign.split(":");
        if(signStr != null && signStr.length == 2){
        	String app_id = signStr[0];
        	String reqSign = signStr[1];
        	if(allowedFromTokens.containsKey(app_id)){
        		AccessToken accessToken = new AccessToken();
        		String app_key = allowedFromTokens.get(app_id);
        		accessToken.setApp_id(app_id);
        		accessToken.setApp_key(app_key);
        		String httpMethod = request.getMethod();
        		if("GET".equalsIgnoreCase(httpMethod)){
        			accessToken.setHttpMethod("GET");
        		}else{
        			accessToken.setHttpMethod("POST");
        		}
        		String requestURI = request.getRequestURI();
        		String requestURL = request.getRequestURL().toString();
        		try {
        			// 解决URL中文问题
        			requestURI = URLDecoder.decode(new String(requestURI.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
        			requestURL = URLDecoder.decode(new String(requestURL.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
					accessToken.setUri(requestURI);
					accessToken.setUrl(requestURL);
        		} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
        		accessToken.setTimestamp(date);
        		accessToken.setCharset(request.getHeader("charset"));
        		accessToken.setSign_type(request.getHeader("sign_type"));
        		accessToken.setVersion(request.getHeader("version"));
        		if(request.getHeader("sign_type").toUpperCase().equals("MD5")){
        			//MD5
        			String serverSign = Sign.createSign(accessToken); // 服务端签名
            		if(Objects.equals(sign, serverSign)){
            			 result.put("access", true);
            			 return result;
            		}else{
            			// 签名不正确
            			result.put("access", false);
            			result.put("errcode", Constants.RESULTCODE_3);
            			result.put("emsg", Constants.RESULTMESSAGE_FAILED_3);
            			return result;
            		}
        		}else if(request.getHeader("sign_type").toUpperCase().equals("DES")){
        			//  DES
        			//	解密后的数据
        			byte[] decryptBASE64 = SecurityUtil.decryptBASE64(reqSign);
        			String decryptDes = SecurityUtil.decryptDes(new String(decryptBASE64));
        			// 源数据规则
        			String	resourceDes = Sign.ruleSign(accessToken);
        			if(Objects.equals(decryptDes, resourceDes)){
        				result.put("access", true);
           			 return result;
        			}else{
        				// 签名不正确
            			result.put("access", false);
            			result.put("errcode", Constants.RESULTCODE_3);
            			result.put("emsg", Constants.RESULTMESSAGE_FAILED_3);
            			return result;
        			}
        		}else if(request.getHeader("sign_type").toUpperCase().equals("RSA")){
        			String publicKey = app_id;
        			String privateKey = app_key; //服务端认证的秘钥
        			String[] signData = reqSign.split("@"); //签名数据的组成： 签名数据源@私钥加密的签名
        			if(signData != null && signData.length ==2){
        				String data = new String(SecurityUtil.decryptBASE64(signData[0]));
        				String rsaSign = signData[1];
        				String encrypt  = SecurityUtil.encryptRSAPrivate(data, privateKey);
        				String org = SecurityUtil.decryptRSAPublic(encrypt, publicKey);
        				boolean verifyRSA = SecurityUtil.verifyRSA(org, publicKey, rsaSign);
        				if(verifyRSA){
        					result.put("access", true);
               			 	return result;
        				}else{
        					// 签名不正确
                			result.put("access", false);
                			result.put("errcode", Constants.RESULTCODE_3);
                			result.put("emsg", Constants.RESULTMESSAGE_FAILED_3);
                			return result;
        				}
        			}else{
        				// 签名不正确
            			result.put("access", false);
            			result.put("errcode", Constants.RESULTCODE_3);
            			result.put("emsg", Constants.RESULTMESSAGE_FAILED_3);
            			return result;
        			}
        		}else{
        			result.put("access", false);
            		result.put("errcode", Constants.RESULTCODE_4);
            		result.put("emsg", "请求数据已被篡改，系统拒绝请求！");
            		return result;
        		}
        		
        	}else{
        		// 未授权的用户
        		result.put("access", false);
        		result.put("errcode", Constants.RESULTCODE_4);
        		result.put("emsg", "未授权的用户！");
        		return result;
        	}
        }else{
        	// 请求头参数不正确
        	result.put("access", false);
        	result.put("errcode", Constants.RESULTCODE_4);
        	result.put("emsg", "请求参数不正确！");
        	return result;
        }
	} 
	
}
