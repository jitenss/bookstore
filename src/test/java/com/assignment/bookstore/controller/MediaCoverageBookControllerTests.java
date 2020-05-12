package com.assignment.bookstore.controller;

import com.assignment.bookstore.entity.MediaCoverageBook;
import com.assignment.bookstore.exception.BadRequestException;
import com.assignment.bookstore.service.MediaCoverageBookService;
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
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MediaCoverageBookControllerTests {

    @Mock
    MediaCoverageBookService mediaCoverageBookService;

    @InjectMocks
    MediaCoverageBookController mediaCoverageBookController;

    @Test
    public void getMediaCoverageBooksTest() throws IOException {
        List<MediaCoverageBook> mediaCoverageBooks= new ArrayList<>();
        Mockito.when(mediaCoverageBookService.getAndStoreMediaCoverageBooks()).thenReturn(mediaCoverageBooks);
        List<MediaCoverageBook> answer = mediaCoverageBookController.getMediaCoverageBooks();
        Assert.assertEquals(mediaCoverageBooks, answer);
    }

    @Test
    public void getMediaCoverageTitlesTest() throws BadRequestException, IOException {
        List<String> titles = new ArrayList<>();
        Mockito.when(mediaCoverageBookService.getMediaCoverageBooksTitles(TestConstants.ISBN)).thenReturn(titles);
        List<String> result = mediaCoverageBookController.getMediaCoverageTitles(TestConstants.ISBN);
        Assert.assertEquals(titles,result);
    }
}
