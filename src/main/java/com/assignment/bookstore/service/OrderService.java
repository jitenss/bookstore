package com.assignment.bookstore.service;

import com.assignment.bookstore.dto.request.OrderRequestDto;
import com.assignment.bookstore.dto.response.OrderResponseDto;
import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.entity.Inventory;
import com.assignment.bookstore.entity.Order;
import com.assignment.bookstore.exception.BadRequestException;
import com.assignment.bookstore.repository.OrderRepository;
import com.assignment.bookstore.utilities.Constants;
import com.assignment.bookstore.utilities.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@Service
public class OrderService {

    @Autowired
    private Converters converters;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private BooksService booksService;

    @Autowired
    private OrderRepository orderRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    /*Applied pessimistic lock on the inventory in the inventory repository*/
    @Transactional(rollbackFor = Exception.class)
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

        Order order = converters.convertOrderRequestDtoToOrder(orderRequestDto);

        Inventory inventory = inventoryService.findInventoryByBookId(order.getBookId());
        if(inventory==null){
            LOGGER.error("Inventory not present with book id {}", order.getBookId());
            throw new BadRequestException("No inventory with given book id in database");
        }

        int inventoryQuantity = inventory.getQuantity();
        if(inventoryQuantity==0){
            inventoryQuantity = Constants.INVENTORY_QUANTITY_ADD;
            inventory.setQuantity(inventoryQuantity);
        }

        if(order.getQuantity() > inventoryQuantity){
            LOGGER.error("Inventory exceeded for book id {}", order.getBookId());
            throw new BadRequestException(inventoryQuantity + " books available in the inventory. Quantity expected exceeded");
        }

        Book book = booksService.findBookById(order.getId());

        order.setAmount(order.getQuantity() * book.getPrice());
        inventory.setQuantity(inventoryQuantity - order.getQuantity());

        inventoryService.updateInventory(inventory);
        orderRepository.save(order);

        LOGGER.info("Successfully placed order for bookId: {}", order.getBookId());
        return converters.convertOrderToOrderResponseDto(order);
    }
}
