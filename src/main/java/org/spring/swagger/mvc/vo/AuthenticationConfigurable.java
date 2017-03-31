package org.spring.swagger.mvc.vo;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import org.spring.swagger.mvc.util.PropertiesUtil;
import org.springframework.beans.factory.InitializingBean;

/**服务端授权的用户集合*/
public class AuthenticationConfigurable implements InitializingBean {
	
	private Map<String,String> allowedFromTokens;
	
	@SuppressWarnings("static-access")
	@Override
	public void afterPropertiesSet() throws Exception {
		Properties props =new Properties();
		InputStream in = this.getClass().getResourceAsStream("/system.properties");
		props.load(in);
		PropertiesUtil configurable = new PropertiesUtil();
		configurable.loadProperties(props);
		setAllowedFromTokens(configurable.getCtxPropertiesMap());
		props.clear();
	}

	public void setAllowedFromTokens(Map<String, String> allowedFromTokens) {
		this.allowedFromTokens = allowedFromTokens;
	}

	public Map<String, String> getAllowedFromTokens() {
		return allowedFromTokens;
	}
	
}
