package com.assignment.bookstore.service;

import com.assignment.bookstore.dto.request.OrderRequestDto;
import com.assignment.bookstore.dto.response.OrderResponseDto;
import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.entity.Inventory;
import com.assignment.bookstore.entity.Order;
import com.assignment.bookstore.exception.BadRequestException;
import com.assignment.bookstore.repository.OrderRepository;
import com.assignment.bookstore.utilities.Converters;
import com.assignment.bookstore.utilities.TestConstants;
import com.assignment.bookstore.utilities.TestEntities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTests {

    @Mock
    Converters converters;

    @Mock
    InventoryService inventoryService;

    @Mock
    BooksService booksService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    EntityManager entityManager;

    @InjectMocks
    OrderService orderService;

    @Test
    public void createOrderTests() throws BadRequestException {
        OrderRequestDto orderRequestDto = TestEntities.getOrderRequestDto();
        orderRequestDto.setQuantity(TestConstants.QUANTITY);
        OrderResponseDto orderResponseDto = TestEntities.getOrderResponseDto();
        Order order = TestEntities.getOrder();
        Order updatedOrder = TestEntities.getOrder();
        Book book = TestEntities.getBook();
        Inventory inventory = TestEntities.getInventory();
        Inventory updatedInventory = TestEntities.getInventory();
        updatedInventory.setQuantity(inventory.getQuantity() - orderRequestDto.getQuantity());

        Mockito.when(converters.convertOrderRequestDtoToOrder(orderRequestDto)).thenReturn(order);
        Mockito.when(inventoryService.findInventoryByBookId(order.getBookId())).thenReturn(inventory);
        Mockito.doNothing().when(entityManager).lock(inventory, LockModeType.PESSIMISTIC_WRITE);
        Mockito.when(booksService.findBookById(order.getId())).thenReturn(book);
        Mockito.when(inventoryService.updateInventory(inventory)).thenReturn(updatedInventory);
        Mockito.when(orderRepository.save(order)).thenReturn(updatedOrder);
        Mockito.when(converters.convertOrderToOrderResponseDto(order)).thenReturn(orderResponseDto);

        OrderResponseDto answerOrder = orderService.createOrder(orderRequestDto);

        Assert.assertEquals(orderResponseDto, answerOrder);
    }

    @Test
    public void createOrderInvalidIdException() {
        OrderRequestDto orderRequestDto = TestEntities.getOrderRequestDto();
        orderRequestDto.setQuantity(1);
        Order order = TestEntities.getOrder();

        Mockito.when(converters.convertOrderRequestDtoToOrder(orderRequestDto)).thenReturn(order);
        Mockito.when(inventoryService.findInventoryByBookId(order.getBookId())).thenReturn(null);

        Assertions.assertThrows(BadRequestException.class, () -> orderService.createOrder(orderRequestDto));
    }

    @Test
    public void createOrderInvalidQuantityException() {
        OrderRequestDto orderRequestDto = TestEntities.getOrderRequestDto();
        orderRequestDto.setQuantity(1);
        Order order = TestEntities.getOrder();
        order.setQuantity(100);
        Inventory inventory = TestEntities.getInventory();

        Mockito.when(converters.convertOrderRequestDtoToOrder(orderRequestDto)).thenReturn(order);
        Mockito.when(inventoryService.findInventoryByBookId(order.getBookId())).thenReturn(inventory);
        Mockito.doNothing().when(entityManager).lock(inventory, LockModeType.PESSIMISTIC_WRITE);

        Assertions.assertThrows(BadRequestException.class, () -> orderService.createOrder(orderRequestDto));
    }
}
