package com.sapient.inventory.dto;

import com.sapient.inventory.model.Inventory;
import com.sapient.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryDao {

    private final InventoryRepository inventoryRepository;

    public Inventory save(Inventory inventory) {
        if (inventory.getId() == null) {
            inventory.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
        }
        inventory.setUpdatedDate(LocalDateTime.now(ZoneOffset.UTC));
        return inventoryRepository.save(inventory);
    }

    public Inventory findByProductId(String productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }
}
