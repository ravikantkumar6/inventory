// 9fbef606107a605d69c0edbcd8029e5d
package com.sapient.inventory.reactive.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/***
 * Configuration class for Kafka Consumer
 *
 *
 */
@EnableKafka
public class BaseKafkaConsumerConfig {

    /**
     * The Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseKafkaConsumerConfig.class);

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.auto.commit.interval.ms}")
    private String autoCommitInterval;

    @Value(value = "${spring.kafka.consumer.timeout.ms:10000}")
    private String timeout;

    @Value(value = "${spring.kafka.max.poll.records}")
    private String pollRecords;

    @Value(value = "${spring.kafka.groupId}")
    private String groupId;

    /**
     * Creates a Consumer Factory for a Cart kind of a message. Consumer Factory is
     * made available as a bean named consumerFactory in Application Context
     *
     * @return ConsumerFactory
     */
    public ConsumerFactory<String, Object> consumerFactory() {

        return new DefaultKafkaConsumerFactory<>(configs(), new StringDeserializer(),
                new KafkaJsonDeserializer<>(Map.class));
    }

    /**
     * configurations for the consumer
     *
     * @return
     */
    public Map<String, Object> configs() {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        configMap.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, timeout);
        configMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, pollRecords);
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonDeserializer.class);
        logger.debug("Initializing the configurations for the kafka consumer");
        return configMap;
    }

    /**
     * Creates a Kafka Listener Container Factory for Map type messages. Listener
     * Factory is made available as a bean named kafkaListenerContainerFactory in
     * Application Context. Uses consumerFactory() for the creating the Map listener
     * factory.
     *
     * @return Kafka Map Listener Container Factory
     */
    @Bean
    @Lazy(true)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
