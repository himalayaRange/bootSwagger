package org.spring.swagger.mvc.common.config;

import org.spring.swagger.mvc.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
public class MySwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    /**
     * Required to autowire SpringSwaggerConfig
     */
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig){
        this.springSwaggerConfig = springSwaggerConfig;
    }

    /**
     * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
     * framework - allowing for multiple swagger groups i.e. same code base
     * multiple swagger resource listings.
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation(){
        //return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(".*?");
    	return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo())  
           .includePatterns(".*")  
           .apiVersion("1.0.0")  
           .swaggerGroup("bootSwagger");  
    	//.pathProvider(new SwaggerPathProviderAdapter())  
    }

    private ApiInfo apiInfo(){
        ApiInfo apiInfo = new ApiInfo(
                "Boot + Swagger API接口管理", 
                "各个controller下对应相应业务接口",
                "My Apps API terms of service", 
                "任何问题请联系：xxxxxxxxx@163.com",
                "My License",
                Constants.RESTFUL_URL);
        return apiInfo;
    }
    
}