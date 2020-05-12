package com.assignment.bookstore.service;


import com.assignment.bookstore.dto.request.BookRequestDto;
import com.assignment.bookstore.dto.response.BookResponseDto;
import com.assignment.bookstore.elasticsearch.EsService;
import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.entity.Inventory;
import com.assignment.bookstore.repository.BookRepository;
import com.assignment.bookstore.utilities.Converters;
import com.assignment.bookstore.utilities.TestConstants;
import com.assignment.bookstore.utilities.TestEntities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class BooksServiceTests {

    @Mock
    BookRepository bookRepository;

    @Mock
    Converters converters;

    @Mock
    InventoryService inventoryService;

    @Mock
    EsService esService;

    @InjectMocks
    BooksService booksService;

    @Test
    public void createBookWithExistingIsbnTest() throws IOException {
        BookRequestDto bookRequestDto = TestEntities.getBookRequestDto();
        Book book = TestEntities.getBook();
        Book savedBook = TestEntities.getBook();
        BookResponseDto bookResponseDto = TestEntities.getBookResponseDto();

        Mockito.when(converters.convertBookRequestDtoToBook(bookRequestDto)).thenReturn(book);
        Mockito.when(bookRepository.findByIsbn(TestConstants.ISBN)).thenReturn(savedBook);
        Mockito.when(converters.convertBookToBookResponseDto(savedBook)).thenReturn(bookResponseDto);

        BookResponseDto answer = booksService.createBook(bookRequestDto);

        Assert.assertEquals(bookResponseDto, answer);
    }

    @Test
    public void createBookWithExistingTitleAndAuthorTest() throws IOException {
        BookRequestDto bookRequestDto = TestEntities.getBookRequestDto();
        Book book = TestEntities.getBook();
        Book savedBook = TestEntities.getBook();
        BookResponseDto bookResponseDto = TestEntities.getBookResponseDto();

        Mockito.when(converters.convertBookRequestDtoToBook(bookRequestDto)).thenReturn(book);
        Mockito.when(bookRepository.findByIsbn(TestConstants.ISBN)).thenReturn(null);
        Mockito.when(bookRepository.findByTitleAndAuthor(TestConstants.TITLE, TestConstants.AUTHOR)).thenReturn(savedBook);
        Mockito.when(converters.convertBookToBookResponseDto(savedBook)).thenReturn(bookResponseDto);

        BookResponseDto answer = booksService.createBook(bookRequestDto);

        Assert.assertEquals(bookResponseDto, answer);
    }

    @Test
    public void createBookTest() throws IOException {
        BookRequestDto bookRequestDto = TestEntities.getBookRequestDto();
        Book book = TestEntities.getBook();
        Book savedBook = TestEntities.getBook();
        BookResponseDto bookResponseDto = TestEntities.getBookResponseDto();
        Inventory inventory = TestEntities.getInventory();

        Mockito.when(converters.convertBookRequestDtoToBook(bookRequestDto)).thenReturn(book);
        Mockito.when(bookRepository.findByIsbn(TestConstants.ISBN)).thenReturn(null);
        Mockito.when(bookRepository.findByTitleAndAuthor(TestConstants.TITLE, TestConstants.AUTHOR)).thenReturn(null);
        Mockito.when(bookRepository.save(book)).thenReturn(savedBook);
        Mockito.doNothing().when(esService).createBookIndex(savedBook);
        Mockito.when(inventoryService.addInventory(book.getId(), bookRequestDto.getQuantity())).thenReturn(inventory);
        Mockito.when(converters.convertBookToBookResponseDto(savedBook)).thenReturn(bookResponseDto);

        BookResponseDto answer = booksService.createBook(bookRequestDto);

        Assert.assertEquals(bookResponseDto, answer);
    }

    @Test
    public void findBookByIsbnTest() {
        Book book = TestEntities.getBook();
        Mockito.when(bookRepository.findByIsbn(TestConstants.ISBN)).thenReturn(book);
        Book answer = booksService.findByIsbn(TestConstants.ISBN);
        Assert.assertEquals(book, answer);
    }

    @Test
    public void findBookByTitleAndAuthorTest() {
        Book book = TestEntities.getBook();
        Mockito.when(bookRepository.findByTitleAndAuthor(TestConstants.TITLE, TestConstants.AUTHOR)).thenReturn(book);
        Book answer = booksService.findByTitleAndAuthor(TestConstants.TITLE, TestConstants.AUTHOR);
        Assert.assertEquals(book.getIsbn(), answer.getIsbn());
    }

    @Test
    public void findBookByIdTest() {
        Book book = TestEntities.getBook();
        Mockito.when(bookRepository.findBookById(TestConstants.ID)).thenReturn(book);
        Book answer = booksService.findBookById(TestConstants.ID);
        Assert.assertEquals(book.getIsbn(), answer.getIsbn());
    }
}
