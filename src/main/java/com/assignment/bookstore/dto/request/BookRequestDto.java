package com.assignment.bookstore.dto.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class BookRequestDto {

    @NotBlank(message = "Title cannot be NULL while creating book")
    private String title;

    @NotBlank(message = "Author cannot be NULL while creating book")
    private String author;

    @NotBlank(message = "ISBN cannot be NULL while creating book")
    private String isbn;

    @DecimalMin(value = "1.0", message = "Minimum price for the book is 1 ")
    private Float price;

    @Min(value = 1, message = "Minimum Quantity for the book to be added is 1")
    private Integer quantity;
}
