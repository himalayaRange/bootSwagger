package org.spring.swagger.mvc.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.swagger.mvc.vo.AccessToken;
import org.spring.swagger.mvc.vo.Sign;

/**
 *	说明：现在使用的httpclient3，如果并发量比较高，建议使用httpclient4.2.x
 */
public class HttpUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	private HttpUtil(){ }
	
	
	/*==============================================================================================================================
	 *                                                    GET  
	 *==============================================================================================================================*/
	
	/**
	 * Http GET 请求
	 * @param accessToken
	 * @param params 参数的Map集合
	 * @return
	 */
	public static final String httpClientGet(AccessToken accessToken ,Map<String,Object> params){
		NameValuePair[] nvps = new NameValuePair[params.size()];
		if(params != null && params.size() != 0){
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
			int i = 0;
			while(iterator.hasNext()){
				Entry<String, Object> entry = iterator.next();
				NameValuePair nvp = new NameValuePair();
				nvp.setName(entry.getKey());
				nvp.setValue(String.valueOf(entry.getValue()));
				nvps[i] = nvp;
				i++;
			} 
		}else{
			nvps = null;
		}
		return httpClientGet(accessToken, nvps);
	}
	
	/**
	 * Http GET 请求
	 * @param accessToken
	 * @param nvps	NameValuePair[]数组
	 * @return
	 */
	public static final String httpClientGet(AccessToken accessToken ,NameValuePair[] nvps){
		String result = "";
		HttpClient client =  new HttpClient();
		GetMethod getMethod = null; 
		try {
			// 以下两行很重要:设置请求参数，对get中的中文进行http编码，汉字编码后自动成为%样式的字符串
			String sign = Sign.createSign(accessToken); // 生成签名
			if(sign.contains("resultCode")){
				return sign;
			}
			getMethod = new GetMethod(URIUtil.encodeQuery(accessToken.getUrl(),accessToken.getCharset()));
			getMethod.setQueryString(nvps);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler()); //使用系统默认的恢复策略
			getMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset="+accessToken.getCharset().toLowerCase());
			getMethod.setRequestHeader("sign", sign); 
			getMethod.setRequestHeader("timestamp", String.valueOf(accessToken.getTimestamp()));
			getMethod.setRequestHeader("sign_type", accessToken.getSign_type()); 
			getMethod.setRequestHeader("charset", accessToken.getCharset()); 
			getMethod.setRequestHeader("version", accessToken.getVersion()); 
			client.executeMethod(getMethod);
			result = getMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("",e);
			throw new RuntimeException(e);
		}finally{
			if(getMethod != null){
				getMethod.releaseConnection();
			}
		}
		return result;
	}
	
	
	/*==============================================================================================================================
	 *                                                    POST  
	 *==============================================================================================================================*/

	
	/**
	 * 发送Http POST 请求
	 * @param accessToken
	 * @param list	附加参数参数的集合
	 * @return
	 * @throws URIException 
	 *
	 */
	public static final String httpClientPost(AccessToken accessToken ,List<NameValuePair> list) throws URIException{
		String result = "";
		HttpClient client = new HttpClient();
		String sign = Sign.createSign(accessToken); // 生成签名
		if(sign.contains("resultCode")){
			return sign;
		}
		PostMethod postMethod = new PostMethod(URIUtil.encodeQuery(accessToken.getUrl(),accessToken.getCharset()));
		try {
			NameValuePair[] params = new NameValuePair[list.size()];
			for(int i=0 ; i<list.size() ; i++){
				params[i] = list.get(i);
			}
			postMethod.addParameters(params); //添加参数
			NameValuePair[] requestParams =  map2NameValuePair(accessToken.getParamMap());
			postMethod.setRequestBody(requestParams); //添加请求参数
			postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset="+accessToken.getCharset().toLowerCase());
			postMethod.setRequestHeader("sign", sign);
			postMethod.setRequestHeader("timestamp", String.valueOf(System.currentTimeMillis()));
			postMethod.setRequestHeader("sign_type", accessToken.getSign_type()); 
			postMethod.setRequestHeader("charset", accessToken.getCharset()); 
			postMethod.setRequestHeader("version", accessToken.getVersion());
			client.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("",e);
			throw new RuntimeException(e);
		}finally{
			if(postMethod != null){
				postMethod.releaseConnection();
			}
		}
		return result;
	}
	
	/**
	 * 发送Http POST请求
	 * @param url       请求URL
	 * @param sParaTemp 请求参数的Map
	 * @param sign		明文签名
	 * @return
	 * @throws Exception
	 *
	 */
	public static String httpClientPost(AccessToken accessToken ,Map<String, Object> sParaTemp) throws Exception{
        HttpClient httpClient = new HttpClient();
        String sign = Sign.createSign(accessToken); // 生成签名
        if(sign.contains("resultCode")){
			return sign;
		}
        PostMethod post = new PostMethod(URIUtil.encodeQuery(accessToken.getUrl(),accessToken.getCharset()));
        String response;
        NameValuePair[] nvps = new NameValuePair[sParaTemp.size()];
		if(sParaTemp != null && sParaTemp.size() != 0){
			Iterator<Entry<String, Object>> iterator = sParaTemp.entrySet().iterator();
			int i = 0;
			while(iterator.hasNext()){
				Entry<String, Object> entry = iterator.next();
				NameValuePair nvp = new NameValuePair();
				nvp.setName(entry.getKey());
				nvp.setValue(String.valueOf(entry.getValue()));
				nvps[i] = nvp;
				i++;
			} 
		}else{
			nvps = null;
		}
		// 构造request json数据
		/*JSONObject json = new JSONObject();
		json.put("id", "322");
		json.put("name", "林恺俊");
		json.put("age", "18");
		json.put("passwd", "654321");
		StringRequestEntity sre = new StringRequestEntity(JSON.toJSONString(json), "application/json", accessToken.getCharset().toLowerCase());
		post.setRequestEntity(sre);
		post.setRequestEntity(requestEntity);*/
		NameValuePair[] requestParams =  map2NameValuePair(accessToken.getParamMap());
		post.setRequestBody(requestParams); //添加请求参数
		post.addParameters(nvps); //添加自定义参数
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler()); //使用系统默认的恢复策略
		post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset="+accessToken.getCharset().toLowerCase());
        post.setRequestHeader("sign", sign);
        post.setRequestHeader("timestamp", String.valueOf(System.currentTimeMillis()));
        post.setRequestHeader("sign_type", accessToken.getSign_type()); 
        post.setRequestHeader("charset", accessToken.getCharset()); 
        post.setRequestHeader("version", accessToken.getVersion());
        try {
        	 httpClient.executeMethod(post);
             response = post.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("",e );
			throw new RuntimeException(e);
		} finally{
			if(post != null){
				post.releaseConnection();
			}
		}
        return response;
    }
	
	/**
	 * map转化成NameValuePair[]来适应http请求
	 * @param paramsTmp
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	public static NameValuePair[] map2NameValuePair(Map<String,?> paramsTmp){
		NameValuePair[] nvps = new NameValuePair[paramsTmp.size()];
		if(paramsTmp != null && paramsTmp.size() != 0){
			Iterator<?> iterator = paramsTmp.entrySet().iterator();
			int i = 0;
			while(iterator.hasNext()){
				Entry<String, ?> entry = (Entry<String, ?>) iterator.next();
				NameValuePair nvp = new NameValuePair();
				nvp.setName(entry.getKey());
				Object[] values = (Object[] )entry.getValue();
				nvp.setValue(String.valueOf(values[0]));
				nvps[i] = nvp;
				i++;
			} 
		}else{
			nvps = null;
		}
		return nvps;
	}
}
