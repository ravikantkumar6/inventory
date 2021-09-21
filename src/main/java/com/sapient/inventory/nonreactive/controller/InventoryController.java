package com.sapient.inventory.nonreactive.controller;

import com.sapient.inventory.model.Inventory;
import com.sapient.inventory.model.enums.Operation;
import com.sapient.inventory.model.request.OrderRequest;
import com.sapient.inventory.nonreactive.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/inventories")
    public ResponseEntity<List<Inventory>> getInventory() {
        return new ResponseEntity<List<Inventory>>(inventoryService.getAllInventory(), HttpStatus.OK);
    }

    @PostMapping("/inventory")
    public ResponseEntity<Inventory> saveInventory(@RequestBody Inventory inventory) {
        return new ResponseEntity<Inventory>(inventoryService.saveInventory(inventory), HttpStatus.OK);
    }

    @PutMapping("/inventory/operation/{operation}")
    public ResponseEntity<Inventory> updateInventory(@RequestBody OrderRequest orderRequest, @PathVariable("operation") Operation operation) {
        return new ResponseEntity<Inventory>(inventoryService.updateInventory(orderRequest, operation), HttpStatus.OK);
    }
}
