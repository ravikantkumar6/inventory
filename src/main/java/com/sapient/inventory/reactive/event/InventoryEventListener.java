package com.sapient.inventory.reactive.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.inventory.model.Inventory;
import com.sapient.inventory.model.request.OrderRequest;
import com.sapient.inventory.reactive.service.InventoryReactiveService;
import com.sapient.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryEventListener {

    public static final String INVENTORY_CREATE = "inventory.create";
    public static final String INVENTORY_UPDATE = "inventory.update";
    public static final String INVENTORY = "inventory";

    private final InventoryRepository inventoryRepository;
    private final InventoryReactiveService inventoryReactiveService;

    @KafkaListener(topics = INVENTORY_CREATE, groupId = INVENTORY)
    public void createInventory(ConsumerRecord<String, Message> message) {
        try {
            log.info("Inventory ProductId : " + message.value());
            Inventory inventory = new Inventory();
            inventory.setProductId(message.value() + "");
            inventory.setInventory(50);
            inventory.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
            inventory.setUpdatedDate(LocalDateTime.now(ZoneOffset.UTC));
            inventoryRepository.save(inventory);
        } catch (Exception ex) {
            log.error("Exception occured in parsing the message: " + ex);
        }
    }

    @KafkaListener(topics = INVENTORY_UPDATE, groupId = INVENTORY)
    public void updateInventory(ConsumerRecord<String, Message> message) {
        try {
            log.info("updateInventory : " + message.value());
            ObjectMapper mapper = new ObjectMapper();
            inventoryReactiveService.updateInventory(mapper.readValue(String.valueOf(message.value()), OrderRequest.class), true);
        } catch (Exception ex) {
            log.error("Exception occured in parsing the message: " + ex);
        }
    }
}
