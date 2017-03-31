package org.spring.swagger.mvc.common.config;

import org.spring.swagger.mvc.common.Constants;
import com.mangofactory.swagger.paths.SwaggerPathProvider;

public class SwaggerPathProviderAdapter extends SwaggerPathProvider {
	 public String applicationPath() {  
         return Constants.LAST_URL;  
     }  
     public String getDocumentationPath() {  
         return Constants.LAST_URL;  
     }  
}
