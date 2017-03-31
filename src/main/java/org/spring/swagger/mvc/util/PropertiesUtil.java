package org.spring.swagger.mvc.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesUtil extends PropertyPlaceholderConfigurer {
	
	private static final byte[] KEY = {9, -1, 0, 5, 39, 8, 6, 19};
	private static Map<String,String> ctxPropertiesMap;
	private List<String> decryptProperties; //需要解密的key
	
	 @Override
	 public void loadProperties(Properties props) throws IOException {
		 super.loadProperties(props);
		 ctxPropertiesMap = new HashMap<String,String>();
		 for(Object key : props.keySet()){
			String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            if (decryptProperties != null && decryptProperties.contains(keyStr)) {
                value = SecurityUtil.decryptDes(value, KEY);
                props.setProperty(keyStr, value);
            }
            ctxPropertiesMap.put(keyStr, value);
		 }
	 }
	 
	/**
     * @param decryptPropertiesMap the decryptPropertiesMap to set
     */
    public void setDecryptProperties(List<String> decryptProperties) {
        this.decryptProperties = decryptProperties;
    }

    /**
     * Get a value based on key , if key does not exist , null is returned
     * 
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            return ctxPropertiesMap.get(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key获取值
     * 
     * @param key
     * @return
     */
    public static int getInt(String key) {
        return Integer.parseInt(ctxPropertiesMap.get(key));
    }

    /**
     * 根据key获取值
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        String value = ctxPropertiesMap.get(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    /**
     * 根据key获取值
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = ctxPropertiesMap.get(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return new Boolean(value);
    }
    
   /**
    * 返回配置文件的key-value
    * @return
    *
    */
    public static Map<String,String> getCtxPropertiesMap(){
    	
    	return ctxPropertiesMap;
    }

    public static void main(String[] args) {
        String encrypt = SecurityUtil.encryptDes("buzhidao", KEY);
        System.out.println(encrypt);
        System.out.println(SecurityUtil.decryptDes(encrypt, KEY));
    }
}
