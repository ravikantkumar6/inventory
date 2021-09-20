package com.sapient.inventory.nonreactive.service.impl;

import com.sapient.inventory.dto.Inventory;
import com.sapient.inventory.nonreactive.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InventoryServiceIml implements InventoryService {

    @Override
    public List<Inventory> getAllInventory() {
        return null;
    }
}
