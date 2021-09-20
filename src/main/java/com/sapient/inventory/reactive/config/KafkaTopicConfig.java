// 9fbef606107a605d69c0edbcd8029e5d
package com.sapient.inventory.reactive.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class to create kafka topics
 *
 */
@Slf4j
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.message.topic.name}")
    private String topicName;

    /***
     * Bean to get Kafka Admin
     *
     * @return
     */
    @Bean
    @Lazy(true)
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put("allow.auto.create.topics", true);
        return new KafkaAdmin(configs);
    }

    /***
     * Bean to Create Topics
     *
     * @return
     */
    @Bean
    @Lazy(true)
    public NewTopic newTopic() {
        log.info("Creating new topic");
        return new NewTopic(topicName, 3, (short) 1);

    }
}