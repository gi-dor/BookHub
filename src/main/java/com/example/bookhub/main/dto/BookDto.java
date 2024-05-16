package com.example.bookhub.main.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class BookDto {

    private Long no;
    private String name;
    private String publishedDate;
    private String description;
    private int price;
    private String cover;
    private String publisherName;
    private String categoryName;
    private List<BookAuthorDto> author;
    private String authors;
    private float averageRating;
    private int count;
}
