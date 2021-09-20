// 9fbef606107a605d69c0edbcd8029e5d
package com.sapient.inventory.reactive.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/***
 * Configuration Class for Kafka Producer
 *
 *
 */
public class BaseKafkaProducerConfig {

    /**
     * The Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseKafkaProducerConfig.class);

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.acks}")
    private String acknowledge;

    @Value(value = "${spring.kafka.retries}")
    private String retries;

    @Value(value = "${spring.kafka.batch.size}")
    private String batchSize;

    @Value(value = "${spring.kafka.transaction-timeout:10000}")
    private String transactionTimeout;

    @Value(value = "${spring.kafka.request-timeout:10000}")
    private String requestTimeout;

    /**
     * configurations for the producer
     *
     * @return
     */
    private Map<String, Object> configs() {
        logger.debug("configuring kafka producer");
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configMap.put(ProducerConfig.RETRIES_CONFIG, retries);
        configMap.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        configMap.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, transactionTimeout);
        configMap.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
        configMap.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1000);
        configMap.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        configMap.put(ProducerConfig.ACKS_CONFIG,acknowledge);
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSerializer.class);
        return configMap;
    }

    /**
     * ProducerFactory
     *
     * @return
     */
    private ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(configs(), new StringSerializer(),
                new KafkaJsonSerializer());
    }

    /**
     * KafkaTemplate
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    @Lazy(true)
    public KafkaTemplate kafkaTemplate() {
        return new KafkaTemplate(producerFactory(), true);
    }
}