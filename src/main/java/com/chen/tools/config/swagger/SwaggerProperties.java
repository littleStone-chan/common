package com.chen.tools.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: yeyc
 * @Date: 2020/4/26 9:47
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String openGroupName = "";

    private String authGroupName = "";

    private String controllerPath = "";

    private String title = "";

    private String openDescription = "";

    private String authDescription = "";

    private String version = "";

}
