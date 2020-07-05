package com.chen.tools.rabbitmq;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chen.tools.commons.StringUtils;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.ObjectUtils;

import java.util.Map;

public class ReceiveMessage<T>  extends GenericMessage {


    public ReceiveMessage(T payload) {
        super(payload);
    }

    public ReceiveMessage(T payload, Map headers) {
        super(payload, headers);
    }

    public ReceiveMessage(T payload, MessageHeaders headers) {
        super(payload, headers);
    }

    public <T2> T2 getPayload(Class<T2> clz) {

        MessageHeaders headers = getHeaders();
        if (!ObjectUtils.isEmpty(headers.get("contentType"))){
            String contentType = (String) headers.get("contentType");
            if (StringUtils.equals(contentType,MessageProperties.CONTENT_TYPE_JSON)){
                return JSON.parseObject(new String((byte[]) this.getPayload()),clz);
            }
        }
        return (T2) getPayload();
    }
}
