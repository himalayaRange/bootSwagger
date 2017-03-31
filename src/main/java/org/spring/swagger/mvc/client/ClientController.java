package org.spring.swagger.mvc.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.spring.swagger.mvc.common.Constants;
import org.spring.swagger.mvc.util.HttpUtil;
import org.spring.swagger.mvc.vo.AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value = "clientCtr", description="客户端请求rest测试")
@Controller
public class ClientController {
	
	private static final String requestPrefix = "http://localhost:8080"; //	请求前缀
	private static String requestURI = "/bootSwagger/queryUserById/%s";  //	请求URI
	private static String loginURI = "/bootSwagger/login/%s/%s";		 //	请求URI
	
	@RequestMapping(value = "/clientGET" , method = RequestMethod.GET)
	@ApiOperation(value="客户端请求REST GET测试" , httpMethod="GET", tags="客户端",notes="client request test")
	public void clientGET(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		requestURI = String.format(requestURI, "纳兰性德");
		
		AccessToken accessToken = new AccessToken();
		accessToken.setApp_id(Constants.APP_ID); //必须
		accessToken.setApp_key(Constants.APP_KEY);//必须
		accessToken.setHttpMethod("GET");//必须
		accessToken.setUri(requestURI);//必须
		accessToken.setUrl(requestPrefix+requestURI);//必须
		accessToken.setCharset("utf-8");//必须
		accessToken.setSign_type("RSA");//必须
		accessToken.setTimestamp(System.currentTimeMillis());//必须
		accessToken.setBiz_content(null);//无参数时候传入null或者""
		accessToken.setFormat("json");//非必须，默认utf-8
		accessToken.setVersion("1.0");//非必须，默认1.0
		
		//可以添加请求参数，传入键-值得Json,建议使用fastjson
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", "080210121");
		params.put("role", "管理员");
		String result = HttpUtil.httpClientGet(accessToken,params);
		System.out.println(result);
		response.getWriter().write(result);
	}
	
	@RequestMapping(value = "/clientPOST" , method = RequestMethod.POST)
	@ApiOperation(value="客户端请求REST POST测试" , httpMethod="POST", tags="客户端",notes="client request test")
	public void clientPOST(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		loginURI = String.format(loginURI, "亚亚" ,"123456");
		
		AccessToken accessToken = new AccessToken();
		accessToken.setApp_id(Constants.APP_ID); //必须
		accessToken.setApp_key(Constants.APP_KEY);//必须
		accessToken.setHttpMethod("POST");//必须
		accessToken.setUri(loginURI);//必须
		accessToken.setUrl(requestPrefix+loginURI);//必须
		accessToken.setCharset("utf-8");//必须
		accessToken.setSign_type("RSA");//必须
		accessToken.setTimestamp(System.currentTimeMillis());//必须
		accessToken.setFormat("json");//非必须，默认utf-8
		accessToken.setVersion("1.0");//非必须，默认1.0
		//设置请求参数
		accessToken.setParamMap(request.getParameterMap());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("systemName", "REST API 管理平台");
		map.put("userId", "080210231");
		String result;
		try {
			result = HttpUtil.httpClientPost(accessToken,map);
			System.out.println(result);
			response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
