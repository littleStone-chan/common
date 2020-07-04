package com.o2osys.tools.util;

import com.o2osys.tools.annotation.BeanFieldIndex;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BeanRefUtils {

    public static void main(String[] args) throws Exception {
    }

    /**
     * 通过{@BeanFieldIndex}来进行排序,调用实体的get方法取值
     *
     * @param beanObj 实体
     * @return returnList        returnList返回的数据
     * @throws Exception
     */
    public static <T> List<Object> getProperty(T beanObj) throws Exception {
        Class<? extends Object> clazz = beanObj.getClass();
        Field[] fields = clazz.getDeclaredFields();//获得属性
        //记录BeanFieldIndex的长度,必须从0开始
        int index = 0;
        ArrayList<Object> returnList = new ArrayList<Object>();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            BeanFieldIndex beanFieldIndex = field.getAnnotation(BeanFieldIndex.class);
            if (beanFieldIndex != null && index == beanFieldIndex.index()) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                if (getMethod != null) {
                    String fieldType = field.getType().getSimpleName();
                    Object value = getMethod.invoke(beanObj);
                    String result = "";
                    if (value != null) {
                        if ("Date".equals(fieldType)) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            result = sdf.format(value);
                        } else {
                            result = String.valueOf(value);
                        }
                    }
                    returnList.add(result);
                    index++;
                }
            }
        }
        return returnList;
    }


    /**
     * 简单测试
     *
     * @param beanObj
     * @param value
     * @throws Exception
     */
    public static void setProperty(Object beanObj, Object value) throws Exception {
        Class<? extends Object> clazz = beanObj.getClass();
        Field[] fields = clazz.getDeclaredFields();//获得属性
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 此处应该判断beanObj,property不为null
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), beanObj.getClass());
            Method setMethod = pd.getWriteMethod();
            if (setMethod != null) {
                //这里注意实体类中set方法中的参数类型，如果不是String类型则进行相对应的转换
                setMethod.invoke(beanObj, value);//invoke是执行set方法
            }
        }
    }


    /**
     * 通过{@BeanFieldIndex}来进行排序,调用实体的set方法赋值
     *
     * @param beanObj 实体
     * @param list
     * @throws Exception
     */
    public static void setProperty(Object beanObj, List<String> list) throws Exception {
        Class<? extends Object> clazz = beanObj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            BeanFieldIndex radiusIndex = field.getAnnotation(BeanFieldIndex.class);
            if (radiusIndex != null) {
                int index = radiusIndex.index();
                String value = list.get(index);
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method setMethod = pd.getWriteMethod();
                if (setMethod != null) {
                    if (null != value && !"".equals(value)) {
                        String fieldType = field.getType().getSimpleName();
                        if ("String".equals(fieldType)) {
                            setMethod.invoke(beanObj, value);
                        } else if ("Date".equals(fieldType)) {
                            Date temp = parseDate(value);
                            setMethod.invoke(beanObj, temp);
                        } else if ("Integer".equals(fieldType)
                                || "int".equals(fieldType)) {
                            Integer intval = Integer.parseInt(value);
                            setMethod.invoke(beanObj, intval);
                        } else if ("Long".equalsIgnoreCase(fieldType)) {
                            Long temp = Long.parseLong(value);
                            setMethod.invoke(beanObj, temp);
                        } else if ("Double".equalsIgnoreCase(fieldType)) {
                            Double temp = Double.parseDouble(value);
                            setMethod.invoke(beanObj, temp);
                        } else if ("Boolean".equalsIgnoreCase(fieldType)) {
                            Boolean temp = parseBoolean(value);
                            setMethod.invoke(beanObj, temp);
                        } else {
                            System.out.println("不支持的类型" + fieldType);
                        }
                    }
                }
            }
        }
    }


    /**
     * 将String转化为boolean
     */
    private static boolean parseBoolean(String value) {
        if ("1".equals(value) || "true".equalsIgnoreCase(value)) {
            return true;
        }
        return false;
    }

    /**
     * 将String转化为Date
     */
    private static Date parseDate(String value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(value);
        } catch (Exception e) {
        }
        return date;
    }

}