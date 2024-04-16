package com.example.bookhub.product.dto;

import lombok.Data;

@Data
public class CartBookDto {

    private Long cartNo;
    private Long bookNo;
    private String name;
    private int price;
    private String cover;
    private int count;
}