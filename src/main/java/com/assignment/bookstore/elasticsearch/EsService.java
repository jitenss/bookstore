package com.assignment.bookstore.elasticsearch;

import com.assignment.bookstore.entity.Book;
import com.assignment.bookstore.entity.MediaCoverageBook;
import com.assignment.bookstore.exception.ElasticSearchException;
import com.assignment.bookstore.utilities.Constants;
import com.google.gson.Gson;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EsService {

    @Autowired
    private Gson gson;

    @Autowired
    private RestHighLevelClient client;

    private static final Logger LOGGER = LoggerFactory.getLogger(EsService.class);

    public void createBookIndex(Book book) {
        IndexRequest indexRequest = new IndexRequest(Constants.BOOKS_INDEX);
        indexRequest.source(gson.toJson(book), XContentType.JSON);
        indexRequest.id(book.getId().toString());
        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException ex){
            throw new ElasticSearchException("Error while creating book index");
        }
    }

    public List<Book> searchBook(String query) {
        query = query.toLowerCase();
        SearchRequest searchRequest = new SearchRequest(Constants.BOOKS_INDEX);

        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query, Constants.SEARCH_BOOKS_FIELDS).type(MatchQuery.Type.PHRASE_PREFIX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
        } catch (IOException ex){
            throw new ElasticSearchException("Error while searching query in book index");
        }

        SearchHit[] searchHits = searchResponse.getHits().getHits();
        List<Book> books = new ArrayList<>();

        for(SearchHit searchHit:searchHits){
            books.add(gson.fromJson(searchHit.getSourceAsString(), Book.class));
        }
        LOGGER.info("Successfully fetched from books index for query {}", query);
        return books;
    }

    public void createMediaCoverageIndex(List<MediaCoverageBook> mediaCoverageBooks) {

        for(MediaCoverageBook mediaCoverageBook:mediaCoverageBooks) {
            IndexRequest indexRequest = new IndexRequest(Constants.MEDIA_COVERAGE_BOOKS_INDEX);
            indexRequest.source(gson.toJson(mediaCoverageBook), XContentType.JSON);
            indexRequest.id(mediaCoverageBook.getId());
            try {
                client.index(indexRequest, RequestOptions.DEFAULT);
            } catch (IOException ex){
                throw new ElasticSearchException("Error while creating media index");
            }

        }
        LOGGER.info("Created media coverage index");
    }

    public List<MediaCoverageBook> searchMediaCoverageBook(String query) {
        query = query.toLowerCase();
        SearchRequest searchRequest = new SearchRequest(Constants.MEDIA_COVERAGE_BOOKS_INDEX);

        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query, Constants.SEARCH_MEDIA_COVERAGE_FIELDS).type(MatchQuery.Type.PHRASE_PREFIX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(queryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
        } catch (IOException ex){
            throw new ElasticSearchException("Error while searching query in media index");
        }

        SearchHit[] searchHits = searchResponse.getHits().getHits();
        List<MediaCoverageBook> mediaCoverageBooks = new ArrayList<>();

        for(SearchHit searchHit:searchHits){
            mediaCoverageBooks.add(gson.fromJson(searchHit.getSourceAsString(), MediaCoverageBook.class));
        }
        LOGGER.info("Successfully fetched from media coverage index for query {}", query);
        return mediaCoverageBooks;
    }
}
