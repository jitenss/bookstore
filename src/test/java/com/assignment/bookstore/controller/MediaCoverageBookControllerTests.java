package com.assignment.bookstore.controller;

import com.assignment.bookstore.entity.MediaCoverageBook;
import com.assignment.bookstore.service.MediaCoverageBookService;
import com.assignment.bookstore.utilities.TestConstants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MediaCoverageBookControllerTests {

    @Mock
    MediaCoverageBookService mediaCoverageBookService;

    @InjectMocks
    MediaCoverageBookController mediaCoverageBookController;

    @Test
    public void getMediaCoverageBooksTest() {
        List<MediaCoverageBook> mediaCoverageBooks= new ArrayList<>();
        Mockito.when(mediaCoverageBookService.getAndStoreMediaCoverageBooks()).thenReturn(mediaCoverageBooks);
        List<MediaCoverageBook> answer = mediaCoverageBookController.mediaCoverageBooks();
        Assert.assertEquals(mediaCoverageBooks, answer);
    }

    @Test
    public void getMediaCoverageTitlesTest() {
        List<String> titles = new ArrayList<>();
        Mockito.when(mediaCoverageBookService.getMediaCoverageBooksTitles(TestConstants.ISBN)).thenReturn(titles);
        List<String> result = mediaCoverageBookController.mediaCoverageTitles(TestConstants.ISBN);
        Assert.assertEquals(titles,result);
    }
}
