package com.assignment.bookstore.utilities;

import com.assignment.bookstore.dto.request.BookRequestDto;
import com.assignment.bookstore.dto.request.OrderRequestDto;
import com.assignment.bookstore.dto.response.BookResponseDto;
import com.assignment.bookstore.dto.response.OrderResponseDto;
import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.entity.Inventory;
import com.assignment.bookstore.entity.MediaCoverageBook;
import com.assignment.bookstore.entity.Order;

public class TestEntities {

    public static Book getBook() {
        Book book = new Book();
        book.setId(TestConstants.ID);
        book.setPrice(TestConstants.PRICE);
        book.setIsbn(TestConstants.ISBN);
        book.setAuthor(TestConstants.AUTHOR);
        book.setTitle(TestConstants.TITLE);
        return book;
    }

    public static BookRequestDto getBookRequestDto() {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setAuthor(TestConstants.AUTHOR);
        bookRequestDto.setIsbn(TestConstants.ISBN);;
        bookRequestDto.setPrice(TestConstants.PRICE);
        bookRequestDto.setQuantity(TestConstants.QUANTITY);
        bookRequestDto.setTitle(TestConstants.TITLE);
        return bookRequestDto;
    }

    public static BookResponseDto getBookResponseDto() {
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(TestConstants.BOOK_ID);
        bookResponseDto.setExisting(true);
        return bookResponseDto;
    }

    public static Inventory getInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(TestConstants.ID);
        inventory.setBookId(TestConstants.BOOK_ID);
        inventory.setQuantity(TestConstants.QUANTITY);
        return inventory;
    }

    public static MediaCoverageBook getMediaCoverageBook() {
        MediaCoverageBook mediaCoverageBook = new MediaCoverageBook();
        mediaCoverageBook.setBody(TestConstants.BODY);
        mediaCoverageBook.setUserId(TestConstants.USER_ID);
        mediaCoverageBook.setTitle(TestConstants.TITLE);
        mediaCoverageBook.setId(TestConstants.MEDIA_ID);
        return mediaCoverageBook;
    }

    public static OrderRequestDto getOrderRequestDto() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setBookId(TestConstants.BOOK_ID);
        orderRequestDto.setCustomerIdentity(TestConstants.CUSTOMER_IDENTITY);
        orderRequestDto.setQuantity(TestConstants.QUANTITY);
        return orderRequestDto;
    }

    public static OrderResponseDto getOrderResponseDto() {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setAmount(TestConstants.AMOUNT);
        return orderResponseDto;
    }

    public static Order getOrder() {
        Order order = new Order();
        order.setId(TestConstants.ID);
        order.setAmount(TestConstants.AMOUNT);
        order.setBookId(TestConstants.BOOK_ID);
        order.setCustomerIdentity(TestConstants.CUSTOMER_IDENTITY);
        order.setQuantity(TestConstants.QUANTITY);
        return order;
    }
}
