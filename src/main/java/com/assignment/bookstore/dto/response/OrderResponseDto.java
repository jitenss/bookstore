package com.assignment.bookstore.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class OrderResponseDto {

    private UUID id;

    private Float amount;

    private Date orderDate = new Date();
}
