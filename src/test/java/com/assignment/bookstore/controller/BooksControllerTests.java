package com.assignment.bookstore.controller;

import com.assignment.bookstore.dto.request.BookRequestDto;
import com.assignment.bookstore.dto.response.BookResponseDto;
import com.assignment.bookstore.elasticsearch.EsService;
import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.service.BooksService;
import com.assignment.bookstore.utilities.TestConstants;
import com.assignment.bookstore.utilities.TestEntities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BooksControllerTests {

    @Mock
    BooksService booksService;

    @Mock
    EsService esService;

    @InjectMocks
    BooksController booksController;

    @Test
    public void createBookTests() throws IOException {
        BookRequestDto bookRequestDto = TestEntities.getBookRequestDto();
        BookResponseDto bookResponseDto = TestEntities.getBookResponseDto();
        Mockito.when(booksService.createBook(Mockito.any())).thenReturn(bookResponseDto);
        BookResponseDto answer = booksController.createBook(bookRequestDto);
        Assert.assertEquals(bookResponseDto.getId(), answer.getId());
    }

    @Test
    public void searchBookTests() throws IOException {
        String query = TestConstants.QUERY;
        List<Book> books = new ArrayList<>();
        Mockito.when(esService.searchBook(query)).thenReturn(books);
        List<Book> answer = booksController.searchBook(query);
        Assert.assertEquals(books, answer);
    }
}
