package com.epam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private int bookId;
    @NotNull(message = "bookname cannot be null")
    @NotBlank(message = "bookname cannot be blank")
    private String bookName;
    @NotBlank(message = "bookPublisher cannot be blank")
    @NotNull(message = "bookPublisher cannot be null")
    private String bookPublisher;
    @NotNull(message = "author cannot be null")
    @NotBlank(message = "author cannot be blank")
    private String author;
}
