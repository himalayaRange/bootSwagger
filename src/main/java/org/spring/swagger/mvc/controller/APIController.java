package org.spring.swagger.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import org.spring.swagger.mvc.common.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value = "apiCtrl", description="API管理视图")
@Controller
public class APIController {
	
	/** API 管理界面 */
	@RequestMapping(value= "/api" , method = RequestMethod.GET)
	@ApiOperation(value="API管理界面" , httpMethod="GET", tags="API管理",notes="api")
	public String api(HttpServletRequest request,ModelMap model){
		
		model.addAttribute("url", Constants.RESTFUL_URL);
		return "api";
	}
}
