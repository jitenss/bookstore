package com.assignment.bookstore.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class OrderRequestDto {

    @NotBlank(message = "Customer Identity cannot be null")
    private String customerIdentity;

    @NotNull(message = "Book Id cannot be null")
    private UUID bookId;

    @Min(value = 1, message = "Minimum Quantity for the order should be 1")
    private Integer quantity;

}
