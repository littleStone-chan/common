package com.chen.tools.util;

import com.chen.tools.base.BaseException;
import com.chen.tools.commons.StringUtils;
import com.chen.tools.commons.StringUtils;
import com.chen.tools.base.BaseException;

/**
 * 数据校验
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BaseException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new BaseException(message);
        }
    }
}