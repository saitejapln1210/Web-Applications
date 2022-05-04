package com.epam.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private int bookId;
    private String bookName;
    private String bookPublisher;
    private String author;
}
