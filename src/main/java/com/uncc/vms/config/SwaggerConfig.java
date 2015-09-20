package com.uncc.vms.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sachin on 9/15/2015.
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {
    //    private SpringSwa
    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired

    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {

        this.springSwaggerConfig = springSwaggerConfig;

    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(
                apiInfo()).includePatterns("/user.*");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("vms API", "API for vms",
                "vms API terms of service", "test@gmail.com",
                "vms API Licence Type", "vms API License URL");
        return apiInfo;
    }
}
