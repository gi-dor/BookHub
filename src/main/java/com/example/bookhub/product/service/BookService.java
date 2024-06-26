package com.example.bookhub.product.service;

import com.example.bookhub.product.dto.BookDto;
import com.example.bookhub.product.mapper.BookMapper;
import com.example.bookhub.product.vo.BookAuthor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    public BookDto getBookDetailByNo(long bookNo){
        return bookMapper.getBookDetailByNo(bookNo);
    }

    public BookDto getBookByBookNo(long bookNo){
        return bookMapper.getBookByBookNo(bookNo);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "BookMapper.getAuthorByBookNo" , condition = "")
    public List<BookAuthor> getAuthorByBookNo(long bookNo) {
        return bookMapper.getAuthorByBookNo(bookNo);
    }

    public void increaseBookViewCount(long bookNo) {
        bookMapper.increaseBookViewCount(bookNo);
    }

    public String checkStock(long bookNo, int count) {
        String checked = "";
        int stock = bookMapper.checkStock(bookNo);
        if(stock - count < 0)
            checked = "fail";
        else if(stock - count >= 0)
            checked = "pass";

        return checked;
    }
}
