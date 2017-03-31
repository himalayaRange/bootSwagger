package org.spring.swagger.mvc.client;

import java.util.HashMap;
import java.util.Map;
import org.spring.swagger.mvc.common.Constants;
import org.spring.swagger.mvc.util.HttpUtil;
import org.spring.swagger.mvc.vo.AccessToken;

public class HttpClient {
	private static final String requestPrefix = "http://localhost:8080"; //	请求前缀
	private static String requestURI = "/bootSwagger/queryUserById/%s";  //	请求URI
	public static void main(String[] args) {
		requestURI = String.format(requestURI, "汪谊");
		AccessToken accessToken = new AccessToken();
		accessToken.setApp_id(Constants.APP_ID); //必须
		accessToken.setApp_key(Constants.APP_KEY);//必须
		accessToken.setHttpMethod("GET");//必须
		accessToken.setUri(requestURI);//必须
		accessToken.setUrl(requestPrefix+requestURI);//必须
		accessToken.setCharset("utf-8");//必须
		accessToken.setSign_type("RSA");//必须,可以是MD5,DES,RSA
		accessToken.setTimestamp(System.currentTimeMillis());//必须
		accessToken.setBiz_content(null);//无参数时候传入null或者""
		accessToken.setFormat("json");//非必须，默认utf-8
		accessToken.setVersion("1.0");//非必须，默认1.0
		//可以添加请求参数，传入键-值得Json,建议使用fastjson
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", "080210121");
		params.put("role", "管理员");
		String result = HttpUtil.httpClientGet(accessToken,params); //不传入参数传入null或者""
		System.out.println(result);
	}
}
