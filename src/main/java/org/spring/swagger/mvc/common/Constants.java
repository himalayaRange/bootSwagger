package org.spring.swagger.mvc.common;

public class Constants {
	
	/** 客户端请求的公钥和私钥  **/
	//public static final String APP_ID = "WANGYI";
	//public static final String APP_KEY = "GRqNFrzWRSX123&@N$_)JF"; 
	// RSA公钥私钥
	public static final String APP_ID = "mfMMdqyjkEzi8L3naqebbqadsMaMsajbakq9QNWqarVG1bwp2K43ybPsJCaoZiJsIvQhPYqA76tzom+o/u6XILqTSjk9nbDAV7F9Vqd8GUrXu1RGxFRn2ZmcaMeaaq==";
	public static final String APP_KEY = "miibvqibadanb7AG8A9gZMQbaqefaascatYM77eXa7eaaAeaFclthFabhCFEfyZKVRN7hnkSyaXQ9RkNxq6pNcsbZnAUPUXZr/KOZd6yAGiQg5tCcClBaogH8hJrHwF5DuRLQMidaqabaAaOcIansgb5YF7HqGdq6NrWBs+LvX/JhB76a6KNvgq5PMfJeLiHr82pPJvV66BBPzF/T2EXvk9oW1TG3nAfX3RFa9eaW0t+NiyCQuzJ/stX9GGgqZVUxfy/NLBfUSIQaT6zCWYciqcP1kVjXdkT7ksy7zsZm2HQqmMn2oUJ8Mq52kB+oF8dhqi7y3eaTvii/1N8SbAj0juAouKL/qF1Qf6wT43K/iKxD9Qciqc0ra7xPzlVSR/KstUktAZmkaKjPc+tPKkUHtENxtf0iqi8akcFcpjv2jifeuXmFdzOLxpVYBjRPJKuv0aXfS8xzrVN"; 
	public static final long THRESHOLD = 1000 * 30 ; // 最长请求时间30s 
	/** 是否是开发模式，true表示开发模式，不进行签名验证，Swagger可以访问，false表示正式环境，需要进行签名验证，Swagger拒绝请求 */
	public static final boolean MODULE_DEV = true ; 
	
	/** 发布的url */
	public static String RESTFUL_URL="http://localhost:8080/bootSwagger/api-docs";
	public static String LAST_URL="/api-docs";

	/** 请求结果：成功 */
	public static String RESULTCODE_0="0";
	public static String RESULTMESSAGE_SUCCESS_0="success";
	
	/** 请求结果：未登录 **/
	public static String RESULTCODE_1="1";
	public static String RESULTMESSAGE_ULOGINED_1="unlogined";
	
	/** 定义失败的返回码，对应请求失败原因  **/
	public static String RESULTCODE_2="2";
	public static String RESULTMESSAGE_FAILED_2="请求的数据为空！";
	
	public static String RESULTCODE_3="3";
	public static String RESULTMESSAGE_FAILED_3="用户凭证失败，请先获取凭证！";
	
	public static String RESULTCODE_4="4";
	public static String RESULTMESSAGE_FAILED_4="系统繁忙，请稍后再试！";
	
	public static String RESULTCODE_5="5";
	public static String RESULTMESSAGE_FAILED_5="请求超时，请稍后再试！";
}
