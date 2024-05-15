package com.example.bookhub.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {
    NEWBOOK("NewBookMapper.getNewBookList",10,10000),
    RECOMMENDBOOK("RecommendBookMapper.getRecommendBookList",10,10000),
    CATEGORY("BookCategoryMapper.getBookCategoryList",10,10000),
    BESTSELLER("BookSellerMapper.getBestSellerList",10,10000),
    BOOK_GETAUTHORBYBOOKNO("BookMapper.getAuthorByBookNo", 10, 10000),
    INQUIRIES("MyPageMapper.cacheInquiries" , 10,10000);

    private final String cacheName;
    private final int expiredAfterWrite;
    private final int maximumSize;

}
