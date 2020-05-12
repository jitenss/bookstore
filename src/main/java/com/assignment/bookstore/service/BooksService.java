package com.assignment.bookstore.service;

import com.assignment.bookstore.dto.request.BookRequestDto;
import com.assignment.bookstore.dto.response.BookResponseDto;
import com.assignment.bookstore.elasticsearch.EsService;
import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.repository.BookRepository;
import com.assignment.bookstore.utilities.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.UUID;

@Service
public class BooksService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Converters converters;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private EsService esService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BooksService.class);

    @Transactional(rollbackFor = Exception.class)
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {

        Book book = converters.convertBookRequestDtoToBook(bookRequestDto);

        Book existingBook = checkExistingBook(book);

        if(existingBook!=null) {
            BookResponseDto bookResponseDto = converters.convertBookToBookResponseDto(existingBook);
            bookResponseDto.setExisting(true);
            return bookResponseDto;
        }

        bookRepository.save(book);
        esService.createBookIndex(book);
        inventoryService.addInventory(book.getId(), bookRequestDto.getQuantity());

        LOGGER.info("Successfully created and indexed Book: {}", book.getId());

        return converters.convertBookToBookResponseDto(book);
    }

    private Book checkExistingBook(Book book) {
        Book existingBook = findByIsbn(book.getIsbn());

        if(existingBook==null){
            existingBook = findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        }
        LOGGER.info("Book exists with id: {}", book.getId());
        return existingBook;
    }

    public Book findBookById(UUID id){
        return bookRepository.findBookById(id);
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public Book findByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

}
