package org.spring.swagger.mvc.controller.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.spring.swagger.mvc.controller.BaseController;
import org.spring.swagger.mvc.vo.BaseResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Api(value = "studentCtrl",description="学生控制控制器")
@Controller(value="studentCtrl")
public class StudentController extends BaseController{
    private static final Log LOGGER = LogFactory.getLog(StudentController.class);
    
    @ResponseBody
    @RequestMapping(value = "updateStudent/{postData}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ApiOperation(value = "修改学生信息", httpMethod = "POST", notes = "学生信息以json格式传递", response = BaseResultVo.class)
    public  String updateStudent(@ApiParam(required = true) @PathVariable(value="postData") String postData)
    {
        LOGGER.debug(String.format("enter function, %s", postData));
        return buildSuccessResultInfo("学生信息更新成功");
    }

}
