package com.o2osys.tools.interceptor;

import com.alibaba.fastjson.JSON;
import com.o2osys.tools.commons.RespMsg;
import com.o2osys.tools.commons.enums.StatusEnum;
import com.o2osys.tools.token.AccessToken;
import com.o2osys.tools.token.AccessTokenUtil;
import com.o2osys.tools.util.StrUtil;
import com.o2osys.tools.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器
 *
 * @Description:
 * @Author:Sine Chen
 * @Date:Mar 30, 2016 3:57:13 PM
 * @Copyright: All Rights Reserved. Copyright(c) 2016
 */
public class TokenAuthcInterceptor extends HandlerInterceptorAdapter {

    @Value("${app.developer.version:false}")
    private boolean isDeveloperVersion;

    private static Logger logger = LoggerFactory.getLogger(TokenAuthcInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        try {
            logger.debug("header,uid:" + request.getHeader("X-uid"));
            AccessToken accessToken = new AccessToken(request);

            logger.debug("preHandle() " + request.getRequestURL().toString() + ";accessToken:" + accessToken);
            if (isDeveloperVersion) {
                WebUtil.putAccessToken(accessToken);
                return true;
            }

            String token = accessToken.getToken();
            String redisToken = null;

            if (token == null) {
                RespMsg respMsg = new RespMsg();
                respMsg.setStatus(StatusEnum.NEED_LOGIN.status());
                respMsg.setMsg(StatusEnum.NEED_LOGIN.msg());
                response.getWriter().print(JSON.toJSONString(respMsg));
                return false;
            }

            if (token.startsWith(AccessTokenUtil.USER_TOKEN_PREFIX_DIRECT)) {
                redisToken = AccessTokenUtil.getToken(accessToken, AccessTokenUtil.USER_TOKEN_PREFIX_DIRECT);
            } else {
                redisToken = AccessTokenUtil.getToken(accessToken, null);
            }

            logger.debug("accessToken:" + accessToken + "accessToken.getUid():" + accessToken.getUid() + ";redisToken:" + redisToken);

            /********token为空，则提示请先登录，不为空 但是错了，提示在其他地方被登陆*********/
            if (accessToken == null
                    || (accessToken.getUid() == null && accessToken.getUi() == null)
                    || (
                    (accessToken.getUid() != null && accessToken.getUid().equals("")) &&
                            (accessToken.getUi() != null && accessToken.getUi().equals("")))
                    || StrUtil.isBlank(accessToken.getToken())
                    || StrUtil.isBlank(redisToken)
                    ) {
                RespMsg respMsg = new RespMsg();
                respMsg.setStatus(StatusEnum.NEED_LOGIN.status());
                respMsg.setMsg(StatusEnum.NEED_LOGIN.msg());
                response.getWriter().print(JSON.toJSONString(respMsg));
                logger.debug("preHandle() " + request.getRequestURL().toString() + "  result is false ");
                return false;
            } else if (!accessToken.getToken().equals(redisToken)) {
                RespMsg respMsg = new RespMsg();
                respMsg.setStatus(StatusEnum.NEED_LOGIN_AGAIN.status());
                respMsg.setMsg(StatusEnum.NEED_LOGIN_AGAIN.msg());
                response.getWriter().print(JSON.toJSONString(respMsg));
                logger.debug("preHandle() " + request.getRequestURL().toString() + "  result is false ");
                return false;
            }

            WebUtil.putAccessToken(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
            RespMsg respMsg = new RespMsg();
            respMsg.setStatus(StatusEnum.SERVER_ERROR.status());
            respMsg.setMsg(StatusEnum.SERVER_ERROR.msg());
            response.getWriter().print(JSON.toJSONString(respMsg));
            return false;
        }
        return true;
    }

}