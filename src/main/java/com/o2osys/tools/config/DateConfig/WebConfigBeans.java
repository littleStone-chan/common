package com.o2osys.tools.config.DateConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 解决时间传递的时候出现14h 的时间差
 */
@Configuration
public class WebConfigBeans {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 增加字符串转日期的功能
     */
    @PostConstruct
    public void initEditableValidation() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
                .getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer
                    .getConversionService();
            genericConversionService.addConverter(String.class, Date.class, new String2DateConverter());
        }
    }


    private class String2DateConverter implements Converter<String, Date> {

        @Override
        public Date convert(String source) {
            if (source == null) {
                return null;
            }
            ParsePosition pos = new ParsePosition(0);
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source, pos);
            if (date == null)
                date = new SimpleDateFormat("yyyy-MM-dd").parse(source, pos);
            if(date == null)
                date = new SimpleDateFormat("yyyy.MM.dd").parse(source,pos);
            if (date == null)
                date = new SimpleDateFormat("HH:mm:ss").parse(source, pos);
            if (date == null)
                date = new SimpleDateFormat("yyyy-MM").parse(source, pos);
            if (date == null)
                date = new SimpleDateFormat("yyyy").parse(source, pos);
            return date;
        }

    }

}