package com.assignment.bookstore.controller;

import com.assignment.bookstore.entity.Inventory;
import com.assignment.bookstore.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PutMapping("/{id}")
    public Inventory updateInventory(@PathVariable String id, @RequestParam int quantity) {
        return inventoryService.updateInventory(UUID.fromString(id), quantity);
    }
}
