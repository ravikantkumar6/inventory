package com.sapient.inventory.reactive.event;

import com.sapient.inventory.dto.Inventory;
import com.sapient.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryUpdate {

    private final InventoryRepository inventoryRepository;

//    @KafkaListener(topics = "inventory_create" , groupId = "inventory")
    @KafkaListener(groupId = "inventory",
            topicPartitions = @TopicPartition(
                    topic = "inventory_create",
                    partitionOffsets = { @PartitionOffset(
                            partition = "0",
                            initialOffset = "0") }))
    public void consume(ConsumerRecord<String, Message> message){
        log.info("In App Config Listener");
        try{
           // ObjectMapper mapper = new ObjectMapper();
                log.info("Inventory ProductId : " + message.value());
            inventoryRepository.save( Inventory.builder().productId(message.value()+"").inventory("50")
                    .createdDate(LocalDateTime.now(ZoneOffset.UTC))
                    .updatedDate(LocalDateTime.now(ZoneOffset.UTC)).build());
        }catch (Exception ex){
            log.error("Exception occured in parsing the message: " + ex);
        }
    }
}
