package com.assignment.bookstore.controller;

import com.assignment.bookstore.dto.request.OrderRequestDto;
import com.assignment.bookstore.dto.response.OrderResponseDto;
import com.assignment.bookstore.entity.Order;
import com.assignment.bookstore.exception.BadRequestException;
import com.assignment.bookstore.service.OrderService;
import com.assignment.bookstore.utilities.TestEntities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrdersControllerTests {

    @Mock
    OrderService orderService;

    @InjectMocks
    OrdersController ordersController;

    @Test
    public void createOrderTest() throws BadRequestException {
        OrderRequestDto orderRequestDto = TestEntities.getOrderRequestDto();
        OrderResponseDto orderResponseDto = TestEntities.getOrderResponseDto();
        Mockito.when(orderService.createOrder(orderRequestDto)).thenReturn(orderResponseDto);
        OrderResponseDto answer = ordersController.createOrder(orderRequestDto);
        Assert.assertEquals(orderResponseDto.getId(), answer.getId());
    }
}
