package com.assignment.bookstore.service;

import com.assignment.bookstore.entity.Inventory;
import com.assignment.bookstore.exception.BadRequestException;
import com.assignment.bookstore.repository.InventoryRepository;
import com.assignment.bookstore.utilities.TestConstants;
import com.assignment.bookstore.utilities.TestEntities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@RunWith(MockitoJUnitRunner.Silent.class)
public class InventoryServiceTests {

    @Mock
    InventoryRepository inventoryRepository;

    @Mock
    EntityManager entityManager;

    @InjectMocks
    InventoryService inventoryService;

    @Test
    public void addInventoryTests() {
        Inventory beforeSaveInventory = TestEntities.getInventory();
        Inventory afterSaveInventory = TestEntities.getInventory();
        Mockito.when(inventoryRepository.save(beforeSaveInventory)).thenReturn(afterSaveInventory);
        Inventory answer = inventoryService.addInventory(beforeSaveInventory.getBookId(), beforeSaveInventory.getQuantity());
        Assert.assertEquals(afterSaveInventory.getBookId(), answer.getBookId());
    }

    @Test
    public void findInventoryByBookId() {
        Inventory inventory = TestEntities.getInventory();
        Mockito.when(inventoryRepository.findByBookId(TestConstants.BOOK_ID)).thenReturn(inventory);
        Inventory answer = inventoryService.findInventoryByBookId(TestConstants.BOOK_ID);
        Assert.assertEquals(inventory, answer);
    }

    @Test
    public void updateTestWithIdTest() {
        Inventory inventory = TestEntities.getInventory();
        Inventory updatedInventory = TestEntities.getInventory();
        Mockito.when(inventoryRepository.findInventoryById(TestConstants.ID)).thenReturn(inventory);
        Mockito.doNothing().when(entityManager).lock(inventory, LockModeType.PESSIMISTIC_WRITE);
        Mockito.when(inventoryRepository.save(inventory)).thenReturn(updatedInventory);
        Inventory answer = inventoryService.updateInventory(inventory);
        Assert.assertEquals(updatedInventory, answer);
    }

    @Test
    public void updateInventoryTest(){
        Inventory originalInventory = TestEntities.getInventory();
        Inventory updatedInventory = TestEntities.getInventory();
        Mockito.when(inventoryRepository.save(originalInventory)).thenReturn(updatedInventory);
        Inventory answer = inventoryService.updateInventory(originalInventory);
        Assert.assertEquals(updatedInventory, answer);
    }
}
