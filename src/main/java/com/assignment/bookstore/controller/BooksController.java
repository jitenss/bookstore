package com.assignment.bookstore.controller;

import com.assignment.bookstore.dto.request.BookRequestDto;
import com.assignment.bookstore.dto.response.BookResponseDto;
import com.assignment.bookstore.elasticsearch.EsService;
import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksService booksService;

    @Autowired
    private EsService esService;

    @PostMapping
    public BookResponseDto createBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto bookResponseDto = booksService.createBook(bookRequestDto);
        return bookResponseDto;
    }

    @GetMapping("/search")
    public List<Book> searchBook(@RequestParam String query) {
        return esService.searchBook(query);
    }

}
