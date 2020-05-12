package com.assignment.bookstore.controller;

import com.assignment.bookstore.entity.MediaCoverageBook;
import com.assignment.bookstore.service.MediaCoverageBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/media_coverage_books")
public class MediaCoverageBookController {

    @Autowired
    private MediaCoverageBookService mediaCoverageBookService;

    @GetMapping
    public List<MediaCoverageBook> mediaCoverageBooks() {
        return mediaCoverageBookService.getAndStoreMediaCoverageBooks();
    }

    @GetMapping("/search")
    public List<String> mediaCoverageTitles(@RequestParam String isbn) {
        return mediaCoverageBookService.getMediaCoverageBooksTitles(isbn);
    }
}
