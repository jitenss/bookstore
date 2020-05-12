package com.assignment.bookstore.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class BookResponseDto {

    private UUID id;

    private boolean existing;
}
