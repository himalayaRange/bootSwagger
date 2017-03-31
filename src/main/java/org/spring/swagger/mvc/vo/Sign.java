package org.spring.swagger.mvc.vo;

import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.spring.swagger.mvc.common.Constants;
import org.spring.swagger.mvc.util.SecurityUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Sign {
	 private static SerializerFeature[] feature = {
	    		SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullListAsEmpty,
	            SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse,
	            SerializerFeature.WriteMapNullValue };

	 
	/** 校验参数是否正确 */
	public static final String createSign(AccessToken accessToken){
		if(Constants.MODULE_DEV){
			System.out.println(accessToken.toString());
		}
		String app_id = accessToken.getApp_id();
		String app_key = accessToken.getApp_key();
		String charset = accessToken.getCharset();
		String httpMethod = accessToken.getHttpMethod();
		String sign_type = accessToken.getSign_type();
		Long timestamp = accessToken.getTimestamp();
		String uri = accessToken.getUri();
		String url = accessToken.getUrl();
		if(StringUtils.isEmpty(app_id)){
			return buildFailedResultInfo(Constants.RESULTCODE_2, "参数无效：app_id不能为空！");
		}else if(StringUtils.isEmpty(app_key)){
			return buildFailedResultInfo(Constants.RESULTCODE_2, "参数无效：app_key不能为空！");
		}else if(StringUtils.isEmpty(charset)){
			return buildFailedResultInfo(Constants.RESULTCODE_2, "参数无效：charset不能为空！");
		}else if(StringUtils.isEmpty(httpMethod)){
			return buildFailedResultInfo(Constants.RESULTCODE_2, "参数无效：httpMethod不能为空！");
		}else if(StringUtils.isEmpty(sign_type)){
			return buildFailedResultInfo(Constants.RESULTCODE_2, "参数无效：sign_type不能为空！");
		}else if(timestamp == null){
			return buildFailedResultInfo(Constants.RESULTCODE_2, "参数无效：timestamp不能为空！");
		}else if(StringUtils.isEmpty(url)){
			return buildFailedResultInfo(Constants.RESULTCODE_2, "参数无效：url不能为空！");
		}else if(StringUtils.isEmpty(uri)){
			return buildFailedResultInfo(Constants.RESULTCODE_2, "参数无效：uri不能为空！");
		}else{
			// 参数正确选择对应的算法加密签名
			if("MD5".equals(sign_type.toUpperCase())){
				return MD5Sign(accessToken);
			}else if("RSA".equals(sign_type.toUpperCase())){
				return RSASign(accessToken);
			}else if("DES".equals(sign_type.toUpperCase())){
				return DESSign(accessToken);
			}else{
				//算法不存在
				return buildFailedResultInfo(Constants.RESULTCODE_2, "不支持的算法签名！");
			}
		}
		
	}
	
	private static String MD5Sign(AccessToken accessToken){
		String sign = ruleSign(accessToken);
		// 加密并64位编码，可采用（MD5或者其他的）
		String md5Sign = SecurityUtil.encryptMd5(sign);
		// 最终签名
		return new StringBuffer().append(accessToken.getApp_id()).append(":").append(md5Sign).toString();
	}
	
	private static String RSASign(AccessToken accessToken){
		String sign = ruleRSASign(accessToken);
		if(sign.length()>53){
			sign=sign.substring(0, 53);
		}
		// RSA签名
		String publicKey = accessToken.getApp_id();
		String privateKey = accessToken.getApp_key();
		try {
			String resource = SecurityUtil.encryptBASE64(sign.getBytes());//签名数据源
			String rsaSign = SecurityUtil.signRSA(sign, privateKey); //通过私钥加签名在服务器端进行验证
			String signData = resource+"@"+rsaSign;
			// 最终签名
			return new StringBuffer().append(publicKey).append(":").append(signData).toString();
		} catch (Exception e) {
			return buildFailedResultInfo(Constants.RESULTCODE_2, "公钥私钥无效！");
		}
	}
	
	private static String DESSign(AccessToken accessToken){
		String sign = ruleSign(accessToken);
		// DES签名
		String encryptDes = SecurityUtil.encryptDes(sign);
		// Base64编码保证二进制数据在网络上的正常传输
		String desSign = SecurityUtil.encryptBASE64(encryptDes.getBytes());
		// 最终签名
		return new StringBuffer().append(accessToken.getApp_id()).append(":").append(desSign).toString();
	}
	
	public static  String buildFailedResultInfo(String resultCode, String failedMsg){
        BaseResultVo resultVo = new BaseResultVo();
        resultVo.setResultCode(resultCode);
        resultVo.setResultMessage(failedMsg);
        return JSON.toJSONString(resultVo, feature);
    }
	
	public static String ruleSign(AccessToken accessToken){
		// 签名规则：String sign = method= GET & uri=** & sign_type=** & charset=** &version=** & app_key=**
		String sign = new StringBuffer()
					 .append("method=").append(accessToken.getHttpMethod())
					 .append("&uri=").append(accessToken.getUri())
					 .append("&sign_type=").append(accessToken.getSign_type())
					 .append("&charset=").append(accessToken.getCharset())
					 .append("&version=").append(accessToken.getVersion())
					 .append("&app_key=").append(accessToken.getApp_key()).toString();
		return sign;
	}
	
	public static String ruleRSASign(AccessToken accessToken){
		// 签名规则：String sign = method= GET & uri=** & sign_type=** & charset=** &version=** & app_key=**
		String sign = new StringBuffer()
					 .append("method=").append(accessToken.getHttpMethod())
					 .append("&sign_type=").append(accessToken.getSign_type())
					 .append("&charset=").append(accessToken.getCharset())
					 .append("&version=").append(accessToken.getVersion())
					 .append(UUID.randomUUID().toString()).toString();
		return sign;
	}
}
