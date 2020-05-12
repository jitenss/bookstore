package com.assignment.bookstore.entity;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "inventory",
        indexes = {@Index(name = "book_id_unique_index", columnList = "book_id", unique = true)})
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "quantity", columnDefinition = "int", nullable = false)
    private Integer quantity;

    @Column(name = "book_id", columnDefinition = "uuid", nullable = false)
    private UUID bookId;

    @Column(name = "created_at", columnDefinition = "Date", nullable = false, updatable = false)
    private Date createdAt = new Date();;

    @Column(name = "updated_at", columnDefinition = "Date", nullable = false)
    @UpdateTimestamp
    private Date updatedAt = new Date();
}
