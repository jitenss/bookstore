package com.assignment.bookstore.service;

import com.assignment.bookstore.elasticsearch.EsService;
import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.entity.MediaCoverageBook;
import com.assignment.bookstore.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MediaCoverageBookService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EsService esService;

    @Autowired
    private BooksService booksService;

    @Value("${media.coverage.url}")
    private String mediaCoverageUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaCoverageBookService.class);

    public List<MediaCoverageBook> getAndStoreMediaCoverageBooks() throws IOException {
        String url = UriComponentsBuilder.fromHttpUrl(mediaCoverageUrl).toUriString();
        ResponseEntity<MediaCoverageBook[]> responseEntity = restTemplate.getForEntity(url, MediaCoverageBook[].class);
        MediaCoverageBook[] mediaCoverageBooks = responseEntity.getBody();
        List<MediaCoverageBook> mediaCoverageBooksList = new ArrayList<>();
        for(MediaCoverageBook mediaCoverageBook:mediaCoverageBooks){
            mediaCoverageBooksList.add(mediaCoverageBook);
        }
        esService.createMediaCoverageIndex(mediaCoverageBooksList);
        LOGGER.info("Media Coverage Successfully fetched and added in elasticsearch");
        return mediaCoverageBooksList;
    }

    public List<String> getMediaCoverageBooksTitles(String isbn) throws BadRequestException, IOException {
        Book book = booksService.findByIsbn(isbn);
        if(book==null) {
            LOGGER.info("Book with does not exist in the database: {}", isbn);
            throw new BadRequestException("Book with this isbn does not exist in the database");
        }
        List<MediaCoverageBook> mediaCoverageBooks = esService.searchMediaCoverageBook(book.getTitle());
        List<String> mediaCoverageBooksTitles = new ArrayList<>();
        for(MediaCoverageBook mediaCoverageBook:mediaCoverageBooks) {
            mediaCoverageBooksTitles.add(mediaCoverageBook.getTitle());
        }
        LOGGER.info("MediaCoverage fetched for book with ISBN: {}", isbn);
        return mediaCoverageBooksTitles;
    }
}
