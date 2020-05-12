package com.assignment.bookstore.utilities;

import com.assignment.bookstore.dto.request.BookRequestDto;
import com.assignment.bookstore.dto.request.OrderRequestDto;
import com.assignment.bookstore.dto.response.BookResponseDto;
import com.assignment.bookstore.dto.response.OrderResponseDto;
import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converters {

    @Autowired
    private ModelMapper modelMapper;

    public Book convertBookRequestDtoToBook(BookRequestDto bookRequestDto) {
        Book book = new Book();
        modelMapper.map(bookRequestDto, book);
        return book;
    }

    public BookResponseDto convertBookToBookResponseDto(Book book) {
        BookResponseDto bookResponseDto = new BookResponseDto();
        modelMapper.map(book, bookResponseDto);
        return bookResponseDto;
    }

    public Order convertOrderRequestDtoToOrder(OrderRequestDto orderRequestDto) {
        return modelMapper.map(orderRequestDto, Order.class);
    }

    public OrderResponseDto convertOrderToOrderResponseDto(Order order) {
        return modelMapper.map(order, OrderResponseDto.class);
    }
}
