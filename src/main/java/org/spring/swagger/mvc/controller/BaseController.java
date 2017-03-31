package org.spring.swagger.mvc.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.swagger.mvc.common.Constants;
import org.spring.swagger.mvc.vo.AuthenticationConfigurable;
import org.spring.swagger.mvc.vo.BaseResultVo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * ClassName: BaseController  
 * Function: 控制器基类
   <br>
 * @author wangyi
   <br>
 * @date: 2017-3-10 下午4:29:53 
   <br> 
 * @version  
   <br>
 * @since JDK 1.7
 */
public class BaseController implements ApplicationContextAware{
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public Map<String,String> allowedFromTokens = new ConcurrentHashMap<String,String>();
    
    SerializerFeature[] feature = {
	    		SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullListAsEmpty,
	            SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse,
	            SerializerFeature.WriteMapNullValue };

    /**
     * @description: 构造成功返回结果
     * @author:wangyi
     * @param resultData
     *            : 需要返回的数据，可选
     * @return
     */
    protected String buildSuccessResultInfo(Object resultData){
        LOGGER.debug(String.format("enter function, %s", resultData));
        BaseResultVo resultVo = new BaseResultVo();
        resultVo.setResultCode(Constants.RESULTCODE_0);
        resultVo.setResultMessage(Constants.RESULTMESSAGE_SUCCESS_0);
        resultVo.setResultData(resultData);
        return JSON.toJSONString(resultVo, feature);
    }

    /**
     * 构造未登录的返回结果
     */
    protected String buildUnloginedResultInfo(Object resultData){
        LOGGER.debug(String.format("unlogined..., %s", resultData));
        BaseResultVo resultVo = new BaseResultVo();
        resultVo.setResultCode(Constants.RESULTCODE_1);
        resultVo.setResultMessage(Constants.RESULTMESSAGE_ULOGINED_1);
        resultVo.setResultData(resultData);
        return JSON.toJSONString(resultVo, feature);
    }
    
    
    /**
     * @description: 构造失败返回结果
     * @author: wangyi
     * @param resultCode
     *            :任意非0数字，代表失败
     * @param failedMsg
     *            ：失败信息
     * @return
     */
    protected String buildFailedResultInfo(String resultCode, String failedMsg){
        BaseResultVo resultVo = new BaseResultVo();
        resultVo.setResultCode(resultCode);
        resultVo.setResultMessage(failedMsg);
        return JSON.toJSONString(resultVo, feature);
    }

    // 授权用户获取
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		AuthenticationConfigurable configurable = (AuthenticationConfigurable)applicationContext.getBean(AuthenticationConfigurable.class);
		allowedFromTokens = configurable.getAllowedFromTokens();
	}

}
