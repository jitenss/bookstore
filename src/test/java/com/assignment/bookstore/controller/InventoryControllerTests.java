package com.assignment.bookstore.controller;

import com.assignment.bookstore.entity.Inventory;
import com.assignment.bookstore.service.InventoryService;
import com.assignment.bookstore.utilities.TestConstants;
import com.assignment.bookstore.utilities.TestEntities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InventoryControllerTests {

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    InventoryController inventoryController;

    @Test
    public void updateInventoryTests() {
        Inventory updatedInventory = TestEntities.getInventory();
        Mockito.when(inventoryService.updateInventory(TestConstants.ID, TestConstants.QUANTITY)).thenReturn(updatedInventory);
        Inventory answer = inventoryController.updateInventory(TestConstants.ID.toString(), TestConstants.QUANTITY);
        Assert.assertEquals(updatedInventory, answer);
    }
}
