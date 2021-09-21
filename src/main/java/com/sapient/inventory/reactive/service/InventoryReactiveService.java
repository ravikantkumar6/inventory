package com.sapient.inventory.reactive.service;

import com.sapient.inventory.dto.InventoryDao;
import com.sapient.inventory.model.Inventory;
import com.sapient.inventory.model.enums.OrderStatus;
import com.sapient.inventory.model.request.OrderRequest;
import com.sapient.inventory.reactive.util.ServiceCommunication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryReactiveService {

    private final InventoryDao inventoryDao;
    private final ServiceCommunication serviceCommunication;

    public Inventory saveInventory(Inventory inventory) {
        log.info("Saving Inventory Header");
        return inventoryDao.save(inventory);
    }

    public List<Inventory> getInventory() {
        return inventoryDao.findAll();
    }

    public Inventory updateInventory(OrderRequest orderRequest, Boolean isEvent) {
        Inventory inventory = inventoryDao.findByProductId(orderRequest.getProductId());
        if (inventory.getInventory() <= 0) {
            serviceCommunication.orderCancel(orderRequest, isEvent);
            return inventory;
        }
        if (orderRequest.getOrderStatus().compareTo(OrderStatus.CREATED) == 0) {
            inventory.setInventory(inventory.getInventory() - 1);
        } else if (orderRequest.getOrderStatus().compareTo(OrderStatus.CANCEL) == 0) {
            inventory.setInventory(inventory.getInventory() + 1);
        }
        inventory = inventoryDao.save(inventory);
        serviceCommunication.saveShipping(orderRequest, isEvent);
        return inventory;
    }
}
