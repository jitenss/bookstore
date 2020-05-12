package com.assignment.bookstore.service;

import com.assignment.bookstore.entity.Inventory;
import com.assignment.bookstore.exception.BadRequestException;
import com.assignment.bookstore.repository.InventoryRepository;
import com.assignment.bookstore.utilities.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.UUID;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BooksService booksService;

    @Autowired
    private Converters converters;

    @Autowired
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    public Inventory addInventory(UUID bookId, int quantity){
        Inventory inventory = new Inventory();
        inventory.setBookId(bookId);
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);

        LOGGER.info("Inventory has been added for bookid: {}", bookId);

        return inventory;
    }

    public Inventory findInventoryByBookId(UUID bookId) {
        return inventoryRepository.findByBookId(bookId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Inventory updateInventory(UUID id, int quantity) throws BadRequestException {
        Inventory inventory= inventoryRepository.findInventoryById(id);

        if(inventory==null) {
            LOGGER.info("Invalid inventory id for update: {}", id);
            throw new BadRequestException("Invalid Inventory Id");
        }

        entityManager.lock(inventory, LockModeType.PESSIMISTIC_WRITE);
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);

        LOGGER.info("Inventory has been updated for id: {}", inventory.getId());

        return inventory;
    }

    public Inventory updateInventory(Inventory inventory) {
        LOGGER.info("Inventory has been updated for id: {}", inventory.getId());
        return inventoryRepository.save(inventory);
    }
}
