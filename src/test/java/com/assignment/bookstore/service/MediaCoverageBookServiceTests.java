package com.assignment.bookstore.service;

import com.assignment.bookstore.elasticsearch.EsService;
import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.entity.MediaCoverageBook;
import com.assignment.bookstore.exception.BadRequestException;
import com.assignment.bookstore.utilities.TestConstants;
import com.assignment.bookstore.utilities.TestEntities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MediaCoverageBookServiceTests {

    @Mock
    RestTemplate restTemplate;

    @Mock
    EsService esService;

    @Mock
    BooksService booksService;

    @InjectMocks
    MediaCoverageBookService mediaCoverageBookService;

    @Test
    public void getAndStoreMediaCoverageBooksTests()  {
        String url = UriComponentsBuilder.fromHttpUrl("https://jsonplaceholder.typicode.com/posts").toUriString();
        MediaCoverageBook[] mediaCoverageBooks = new MediaCoverageBook[]{};
        ResponseEntity<MediaCoverageBook[]> responseEntity = new ResponseEntity<MediaCoverageBook[]>(mediaCoverageBooks, HttpStatus.OK);
        List<MediaCoverageBook> mediaCoverageBooksList = new ArrayList<>();
        ReflectionTestUtils.setField(mediaCoverageBookService, "mediaCoverageUrl", "https://jsonplaceholder.typicode.com/posts");

        Mockito.when(restTemplate.getForEntity(url, MediaCoverageBook[].class)).thenReturn(responseEntity);
        Mockito.doNothing().when(esService).createMediaCoverageIndex(mediaCoverageBooksList);

        List<MediaCoverageBook> answer = mediaCoverageBookService.getAndStoreMediaCoverageBooks();

        Assert.assertEquals(mediaCoverageBooksList, answer);
    }

    @Test
    public void getMediaCoverageBooksTitlesExceptionTest() {
        Book book = null;

        Mockito.when(booksService.findByIsbn(TestConstants.ISBN)).thenReturn(book);

        Assertions.assertThrows(BadRequestException.class, () -> mediaCoverageBookService.getMediaCoverageBooksTitles(TestConstants.ISBN));
    }

    @Test
    public void getMediaCoverageBooksTitlesTest() {
        Book book = TestEntities.getBook();
        List<MediaCoverageBook> mediaCoverageBooks = new ArrayList<>();
        mediaCoverageBooks.add(TestEntities.getMediaCoverageBook());
        List<String> mediaCoverageBooksTitles = new ArrayList<>();
        mediaCoverageBooksTitles.add(mediaCoverageBooks.get(0).getTitle());

        Mockito.when(booksService.findByIsbn(TestConstants.ISBN)).thenReturn(book);
        Mockito.when(esService.searchMediaCoverageBook(book.getTitle())).thenReturn(mediaCoverageBooks);

        List<String> answer = mediaCoverageBookService.getMediaCoverageBooksTitles(TestConstants.ISBN);

        Assert.assertEquals(mediaCoverageBooksTitles, answer);
    }
}
