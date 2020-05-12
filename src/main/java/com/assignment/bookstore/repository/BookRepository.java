package com.assignment.bookstore.repository;

import com.assignment.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query(value = "SELECT * FROM books b where b.id = :id", nativeQuery = true)
    public Book findBookById(@Param("id")UUID id);


    @Query(value = "SELECT * FROM books b where b.isbn = :isbn", nativeQuery = true)
    public Book findByIsbn(@Param("isbn") String isbn);

    @Query(value = "SELECT * FROM books b where b.title = :title AND b.author = :author", nativeQuery = true)
    public Book findByTitleAndAuthor(@Param("title") String title, @Param("author") String author);
}
