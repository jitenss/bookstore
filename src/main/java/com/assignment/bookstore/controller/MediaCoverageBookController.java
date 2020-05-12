package com.assignment.bookstore.controller;

import com.assignment.bookstore.entity.MediaCoverageBook;
import com.assignment.bookstore.exception.BadRequestException;
import com.assignment.bookstore.service.MediaCoverageBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/media_coverage_books")
public class MediaCoverageBookController {

    @Autowired
    private MediaCoverageBookService mediaCoverageBookService;

    @GetMapping
    public List<MediaCoverageBook> getMediaCoverageBooks() throws IOException {
        return mediaCoverageBookService.getAndStoreMediaCoverageBooks();
    }

    @GetMapping("/search")
    public List<String> getMediaCoverageTitles(@RequestParam String isbn) throws BadRequestException, IOException {
        return mediaCoverageBookService.getMediaCoverageBooksTitles(isbn);
    }
}
