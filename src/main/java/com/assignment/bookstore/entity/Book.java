package com.assignment.bookstore.entity;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "books",
        indexes = {@Index(name = "isbn_unique_index", columnList = "isbn", unique = true),
        @Index(name = "title_author_unique_index", columnList = "title, author", unique = true)})
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "title", columnDefinition = "VARCHAR(255)", nullable = false)
    private String title;

    @Column(name = "author", columnDefinition = "VARCHAR(255)", nullable = false)
    private String author;

    @Column(name = "isbn", columnDefinition = "VARCHAR(32)", nullable = false)
    private String isbn;

    @Column(name = "price", columnDefinition = "float", nullable = false)
    private Float price;

    @Column(name = "created_at", columnDefinition = "Date", nullable = false, updatable = false)
    private Date createdAt = new Date();;

    @Column(name = "updated_at", columnDefinition = "Date", nullable = false)
    @UpdateTimestamp
    private Date updatedAt = new Date();
}
