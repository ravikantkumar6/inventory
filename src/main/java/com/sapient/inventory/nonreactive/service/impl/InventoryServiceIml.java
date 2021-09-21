package com.sapient.inventory.nonreactive.service.impl;

import com.sapient.inventory.dto.InventoryDao;
import com.sapient.inventory.model.Inventory;
import com.sapient.inventory.model.enums.Operation;
import com.sapient.inventory.model.enums.OrderStatus;
import com.sapient.inventory.model.request.OrderRequest;
import com.sapient.inventory.nonreactive.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceIml implements InventoryService {

    private final InventoryDao inventoryDao;
    private final RestTemplate restTemplate;

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryDao.findAll();
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
        return inventoryDao.save(inventory);
    }

    @Override
    public Inventory updateInventory(OrderRequest orderRequest, Operation operation) {
        Inventory inventory = inventoryDao.findByProductId(orderRequest.getProductId());
        if (inventory.getInventory() <= 0) {
            orderRequest.setOrderStatus(OrderStatus.CANCEL);
            String url = "http://localhost:9001/api/order/v1/rest/order";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(orderRequest), String.class);
        }
        if (operation.compareTo(Operation.UPDATE) == 0) {
            inventory.setInventory(inventory.getInventory() - 1);
        } else if (operation.compareTo(Operation.CANCEL) == 0) {
            inventory.setInventory(inventory.getInventory() + 1);
        }
        inventory = inventoryDao.save(inventory);
        if (operation.compareTo(Operation.CANCEL) != 0) {
            String url = "http://localhost:9003/api/shipping/v1/rest/shipping";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(orderRequest), String.class);
        }
        return inventory;
    }
}
