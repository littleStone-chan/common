package com.o2osys.tools.util;


import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.springframework.util.ObjectUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 简单封装Dozer, 实现深度转换Bean<->Bean的Mapper.实现:
 * <p>
 * 1. 持有Mapper的单例.
 * 2. 返回值类型转换.
 * 3. 批量转换Collection中的所有对象.
 * 4. 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
 *
 * @author calvin
 */
public class BeanMapper {

    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<T>();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * 基于Dozer将对象A的值拷贝到对象B中.
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

    /**
     * 设置dozer的具备匹配规则
     *
     * @param src xml文件位置
     */
    public static void setDozerMap(String src) {
        List<String> list = Lists.newArrayList();
        list.add(src);
        dozer.setMappingFiles(list);
    }


    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static <T> Map<String, Object> transBean2Map(T obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //List<Object>  --->   List<Map<String,Object>>
    public static <T> List<Map<String,Object>> transBeanList2MapList(List<T> objectList){
        if (ObjectUtils.isEmpty(objectList)){
            return new ArrayList<>();
        }
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Object o:objectList) {
            mapList.add(transBean2Map(o));
        }
        return mapList;
    }

}