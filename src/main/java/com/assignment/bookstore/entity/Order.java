package com.assignment.bookstore.entity;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders",
        indexes = {@Index(name = "book_id_unique_index", columnList = "book_id", unique = true),
                @Index(name = "customer_identity_unique_index", columnList = "customer_identity")})
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "customer_identity", columnDefinition = "varchar(32)", nullable = false)
    private String customerIdentity;

    @Column(name = "book_id", columnDefinition = "uuid", nullable = false)
    private UUID bookId;

    @Column(name = "quantity", columnDefinition = "int", nullable = false)
    private Integer quantity;

    @Column(name = "amount", columnDefinition = "float", nullable = false)
    private Float amount;

    @Column(name = "order_date", columnDefinition = "Date", nullable = false, updatable = false)
    @UpdateTimestamp
    private Date orderDate = new Date();
}
