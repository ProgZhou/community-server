package com.progzhou.community.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class KafkaProducerUtil {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message, String traceId) {
        kafkaTemplate.send(topic, message).addCallback(
                result -> log.info("kafka消息发送成功，traceId: {} topic:{}", traceId, topic),
                ex -> {
                    //TODO 添加异常处理，将消息保存至数据库后定时处理
                    log.error("kafka消息发送异常，traceId: {} topic: {} error:{} message: {}", traceId, topic, ex.getMessage(), message);
                }
        );
    }

}
