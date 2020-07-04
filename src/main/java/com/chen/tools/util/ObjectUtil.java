package com.chen.tools.util;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

public class ObjectUtil extends ObjectUtils {

    /**
     * 实体类设置成不为空
     * @param o
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T beNotNull(T o,Class<T> clz){
        if (isEmpty(o)){
            try {
                return clz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return o;
    }



    /**
     * 判断类中每个属性是否都为空
     *
     * @param o
     * @return
     */
    public static boolean allFieldIsNULL(Object o){
        try {
            if (isEmpty(o)){
                return Boolean.TRUE;
            }

            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                Object object = field.get(o);
                if (object instanceof CharSequence) {
                    if (!org.springframework.util.ObjectUtils.isEmpty(object)) {
                        return false;
                    }
                } else {
                    if (null != object) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }



}
