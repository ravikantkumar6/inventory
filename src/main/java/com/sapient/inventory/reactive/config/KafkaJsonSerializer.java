// 9fbef606107a605d69c0edbcd8029e5d
package com.sapient.inventory.reactive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom Serializer Implementation
 *
 */
@SuppressWarnings("rawtypes")
public class KafkaJsonSerializer implements Serializer {

    /**
     * The Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(KafkaJsonSerializer.class);

    /**
     * generalize the serialization to be used with different object type
     *
     * @param s
     * @param o
     * @return
     */
    @Override
    public byte[] serialize(String s, Object o) {
    	logger.debug("Inside serialize()::{} {}",s,o);
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsBytes(o);
        } catch (Exception e) {
            logger.error(" Error serializing object =>{} and exception is {}", new String(retVal), e.getStackTrace());
        }

        return retVal;
    }

    @Override
    public void close() {
        logger.debug("Closing Kafka Serializer");
    }
}
