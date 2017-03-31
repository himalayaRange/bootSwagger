package org.spring.swagger.mvc.controller.demo;

import java.io.IOException;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.httpclient.util.URIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.swagger.mvc.common.Constants;
import org.spring.swagger.mvc.controller.BaseController;
import org.spring.swagger.mvc.po.UserInfo;
import org.spring.swagger.mvc.service.UserService;
import org.spring.swagger.mvc.vo.BaseResultVo;
import org.spring.swagger.mvc.vo.CheckSign;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Api(value = "userCtrl", description="用户控制器")
@Controller(value = "userCtrl")
public class UserCtroller extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCtroller.class);

    @Resource
    UserService userService;

    /**
     * 添加用户
     * @param postData
     * @param token
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addUser/{postData}/{token}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ApiOperation(value = "添加用户", httpMethod = "POST", response = BaseResultVo.class, notes = "add user")
    public String addUser(@ApiParam(required = true, name = "postData", value = "添加信息") @PathVariable(value="postData") String postData, 
    		              @ApiParam(required = true, name = "token", value = "user token")@PathVariable(value="token") String token, 
    		              HttpServletRequest request){
        LOGGER.debug(String.format("at function, %s", postData));
        if (null == postData || postData.isEmpty()){
            return super.buildFailedResultInfo(Constants.RESULTCODE_2, Constants.RESULTMESSAGE_FAILED_2);
        }
        UserInfo user = JSON.parseObject(postData, UserInfo.class);
        int result = userService.addUser(user);
        return buildSuccessResultInfo(result); 
    }

    /**
     * 查询用户
     * @param userId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryUserById/{username}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ApiOperation(value = "根据用户ID查询用户信息")
    public String queryUserById(@ApiParam(required = true, name = "username", value = "用户名") @PathVariable(value="username") String username,
    		 					HttpServletRequest request){
        UserInfo info = userService.queryUserByName(username);
        // 签名验证
        Map<String, Object> result = CheckSign.checkGETSign(request, allowedFromTokens);
        String userId = request.getParameter("userId");
        String role = request.getParameter("role");
        System.out.println("请求参数. userId:"+userId+" ,role:"+role);
        if((boolean) result.get("access")){
        	return buildSuccessResultInfo(info);
        }else{
        	return buildFailedResultInfo((String)result.get("errcode"), (String)result.get("emsg"));
        }
    }
    
    /**
     * 登录
     * @param name
     * @param passwd
     * @param request
     * @return
     * @throws ServletException 
     * @throws IOException 
     */
    @ResponseBody
    @RequestMapping(value = "login/{name}/{passwd}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ApiOperation(value = "用户登录")
    public String login(
    		@ApiParam(required = true, name = "name", value = "用户名") @PathVariable(value="name") String name,
    		@ApiParam(required = true, name = "passwd", value = "登录密码")@PathVariable(value="passwd") String passwd,
    		@RequestBody(required=false) String requestBody,UserInfo userInfo,
    		HttpServletRequest request) throws IOException, ServletException{
    	
    	 String loginInfo = "登录成功！";
    	 // 签名验证
         Map<String, Object> result = CheckSign.checkGETPOST(request, allowedFromTokens);
      
         // 请求消息的内容
         if(requestBody != null){
        	 String decode = URIUtil.decode(requestBody, "UTF-8");
        	 System.out.println(decode);
         }
        
         String id =  request.getParameter("id");
         String username = request.getParameter("name");
         String age= request.getParameter("age");
         System.out.println(id + " " +username +" "+age);
       
         String systemName  =  (String) request.getParameter("systemName");
		 String userId  =  (String)request.getParameter("userId");
		 System.out.println("请求参数. systemName:"+systemName+" ,userId:"+userId);
		
		 if((boolean) result.get("access")){
        	return buildSuccessResultInfo(loginInfo);
         }else{
        	return buildFailedResultInfo((String)result.get("errcode"), (String)result.get("emsg"));
         }
    }
    
}
