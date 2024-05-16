package com.example.bookhub.main.service;

import com.example.bookhub.main.dto.BookDto;
import com.example.bookhub.main.dto.BookListDto;
import com.example.bookhub.main.dto.SearchCriteria;
import com.example.bookhub.main.mapper.RecommendBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommendBookService {
    private final RecommendBookMapper recommendBookMapper;
    @Transactional(readOnly = true)
    @Cacheable(value = "RecommendBookMapper.getRecommendBookList" , condition = "")
    public BookListDto getRecommendBooks(SearchCriteria criteria) {
        List<BookDto> newBook = recommendBookMapper.getRecommendBookList(criteria);

        int totalRows = recommendBookMapper.count(criteria);
        criteria.setTotalRows(totalRows);

        BookListDto dto = new BookListDto();
        dto.setBooks(newBook);
        dto.setCriteria(criteria);

        return dto;
    }
}
