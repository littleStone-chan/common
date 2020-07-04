package com.o2osys.tools.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        // 获取源站
        String origin = request.getHeader("origin");
        //支持全域名访问，不安全，部署后需要固定限制为客户端网址
        response.setHeader("Access-Control-Allow-Origin", origin);
        //支持的http 动作
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        //响应头 请按照自己需求添加。
        response.setHeader("Access-Control-Allow-Headers", "X-uid,X-did,X-token,X-departmentId,X-store,Content-Type,responseType");
        // 响应头表示是否可以将对请求的响应暴露给页面
        response.setHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(req, res);
    }
    @Override
    public void init(FilterConfig filterConfig) {}
    @Override
    public void destroy() {}
}
