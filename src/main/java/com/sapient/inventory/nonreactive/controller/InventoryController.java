package com.sapient.inventory.nonreactive.controller;

import com.sapient.inventory.dto.Inventory;
import com.sapient.inventory.nonreactive.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/get")
    public ResponseEntity<List<Inventory>> getInventory() {
        return new ResponseEntity<List<Inventory>>(inventoryService.getAllInventory(), HttpStatus.OK);
    }
}
