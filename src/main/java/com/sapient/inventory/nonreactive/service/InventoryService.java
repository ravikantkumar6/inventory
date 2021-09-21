package com.sapient.inventory.nonreactive.service;

import com.sapient.inventory.model.Inventory;
import com.sapient.inventory.model.enums.Operation;
import com.sapient.inventory.model.request.OrderRequest;

import java.util.List;

public interface InventoryService {
    List<Inventory> getAllInventory();

    Inventory saveInventory(Inventory inventory);

    Inventory updateInventory(OrderRequest orderRequest, Operation operation);
}
