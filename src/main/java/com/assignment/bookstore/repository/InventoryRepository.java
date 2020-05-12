package com.assignment.bookstore.repository;

import com.assignment.bookstore.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    @Query(value = "SELECT * FROM inventory i where i.book_id = :book_id", nativeQuery = true)
    public Inventory findByBookId(@Param("book_id") UUID bookId);

    @Query(value = "SELECT * FROM inventory i where i.id = :id", nativeQuery = true)
    public Inventory findInventoryById(@Param("id")UUID id);
}
