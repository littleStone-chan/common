package com.o2osys.tools.config.feign;

import com.o2osys.tools.util.WebUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * Feign请求前添加头文件
 *
 * @author chen
 * @create 2018-09-29 11:05
 **/
@Slf4j
@Configuration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
//            requestTemplate = requestTemplate.decodeSlash(false);
            requestTemplate.header("X-uid", WebUtil.getUid());
            requestTemplate.header("X-did", WebUtil.getDid());
            requestTemplate.header("X-token", WebUtil.getAccessToken().getToken());
            requestTemplate.header("X-store", String.valueOf(WebUtil.getStoreId()));
            requestTemplate.header("X-departmentId", WebUtil.getDepartmentId());
        } catch (Exception e) {
            log.error("FeignConfig在放置头文件的时候错误，可能这个接口不需要token验证！", e.getMessage());
        }
    }
}
