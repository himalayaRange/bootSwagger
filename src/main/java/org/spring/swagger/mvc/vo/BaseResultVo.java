package org.spring.swagger.mvc.vo;

import java.io.Serializable;
/**
 * ClassName: BaseResultVo  
 * Function: 前台基础vo对象
   <br>
 * @author wangyi
   <br>
 * @date: 2017-3-10 下午4:10:18 
   <br> 
 * @version  
   <br>
 * @since JDK 1.7
 */
public class BaseResultVo implements Serializable {

    private static final long serialVersionUID = -7978635757653362784L;

    // 返回码，0表示成功，非0表示失败
    private String resultCode;

    // 返回消息，成功为“success”，失败为具体失败信息
    private String resultMessage;

    // 返回数据
    private Object resultData;
    
    //请求url的凭证
    private String token;
    
    public BaseResultVo()
    {
        
    }
    
    public BaseResultVo(String resultMessage, Object resultData)
    {
        this.resultMessage = resultMessage;
        this.resultData = resultData;
    }
    
    public BaseResultVo(String resultCode, String resultMessage)
    {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
    
    public BaseResultVo(String resultCode, String resultMessage,String token)
    {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.token = token;
    }

    public String getResultCode()
    {
        return resultCode;
    }

    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getResultMessage()
    {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage)
    {
        this.resultMessage = resultMessage;
    }

    public Object getResultData()
    {
        return resultData;
    }

    public void setResultData(Object resultData)
    {
        this.resultData = resultData;
    }

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
  
	@Override
    public String toString()
    {
        return "BaseResultVo [resultCode=" + resultCode + ", resultMessage=" + resultMessage
                + ", resultData=" + resultData + "]";
    }

}
