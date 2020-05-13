package com.assignment.bookstore.repository;

import com.assignment.bookstore.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Inventory findByBookId(@Param("book_id") UUID bookId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Inventory findInventoryById(@Param("id")UUID id);
}
