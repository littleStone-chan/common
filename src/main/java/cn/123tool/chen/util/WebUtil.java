package com.chen.tools.util;

import com.chen.tools.token.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

    private static Logger logger = LoggerFactory.getLogger(WebUtil.class);

    /**
     * 需要在APP启动时候初始化这个值
     */
    public static String APP_ROOT_PATH = "";
    public static ThreadLocal<AccessToken> accessTokenThreadLocal = new InheritableThreadLocal<>();

    public static void putAccessToken(AccessToken token) {
        accessTokenThreadLocal.set(token);
    }

    public static AccessToken getAccessToken() {
        return accessTokenThreadLocal.get();
    }

    /**
     * 获取request对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            logger.warn("getRequest from RequestContextHolder fail...");
            return null;
        } else {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
    }

    /**
     * 获取request里面的参数
     *
     * @param name
     * @return
     */
    public static String getParameter(String name) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getParameter(name);
        } else {
            logger.warn("getParameter fail,cause:fail to getRequest from RequestContextHolder");
            return null;
        }
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static String getUid() {
        AccessToken accessToken = getAccessToken();
        if (accessToken != null) {
            return accessToken.getUid();
        } else {
            logger.warn("getUid fail,cause:accessToken is not in accessTokenThreadLocal");
            return "";
        }
    }

    /**
     * 获取当前用户设备ID
     *
     * @return
     */
    public static String getDid() {
        AccessToken accessToken = getAccessToken();
        if (accessToken != null) {
            return accessToken.getDid();
        } else {
            logger.warn("getDi fail,cause:accessToken is not in accessTokenThreadLocal");
            return null;
        }
    }

    /**
     * 获取门店
     *
     * @return
     */
    public static Long getStoreId() {
        AccessToken accessToken = getAccessToken();
        if (accessToken != null) {
            return accessToken.getStoreId();
        } else {
            logger.warn("getDi fail,cause:accessToken is not in accessTokenThreadLocal");
            return null;
        }
    }


    /**
     * 获取门店
     *
     * @return
     */
    public static String getDepartmentId() {
        AccessToken accessToken = getAccessToken();
        if (accessToken != null) {
            return accessToken.getDepartmentId();
        } else {
            logger.warn("getDi fail,cause:accessToken is not in accessTokenThreadLocal");
            return null;
        }
    }

}
