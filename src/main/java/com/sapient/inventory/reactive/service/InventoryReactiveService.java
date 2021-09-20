package com.sapient.inventory.reactive.service;

import com.sapient.inventory.dto.Inventory;
import com.sapient.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryReactiveService {

    private final InventoryRepository inventoryRepository;

    public Inventory saveInventory(Inventory inventory) {
        log.info("Saving Inventory Header");
//        inventory.setOrderId("Ord-"+ (int)( Math.random() * 100000));
        if (inventory.getCreatedDate() == null) {
            inventory.setUpdatedDate(LocalDateTime.now(ZoneOffset.UTC));
            inventory.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
        }
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getInventory() {
        return inventoryRepository.findAll();
    }
}
