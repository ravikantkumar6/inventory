// 9fbef606107a605d69c0edbcd8029e5d
package com.sapient.inventory.reactive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom DeSerializer Implementation
 *
 */
@SuppressWarnings("rawtypes")
public class KafkaJsonDeserializer<T> implements Deserializer {


    /**
     * The Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(KafkaJsonDeserializer.class);

    private Class<T> type;

    public KafkaJsonDeserializer() {
    }

    @SuppressWarnings({ "unchecked" })
    public KafkaJsonDeserializer(Class type) {
        this.type = type;
    }

    /**
     * custom deserializer for object being passed in the topic
     *
     * @param s
     * @param bytes
     * @return
     */
    @Override
    public Object deserialize(String s, byte[] bytes) {
        logger.debug("deserializing item from topic");
        ObjectMapper mapper = new ObjectMapper();
        T obj = null;
        try {
            obj = mapper.readValue(bytes, type);
        } catch (Exception e) {

            logger.error(" Error deserializing object =>{} and exception is {}", new String(bytes), e.getStackTrace());
        }

        return obj;
    }

    @Override
    public void close() {
        logger.debug("Closing Kafka Deserializer");
    }
}

